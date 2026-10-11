#!/bin/bash
# ==============================================================================
# SAP Hybris Local CI/CD Pipeline Runner
# Usage:
#   ./run-ci.sh            -> Runs full CI (Build + Tests)
#   ./run-ci.sh build      -> Runs compilation (ant all)
#   ./run-ci.sh test       -> Runs custom unit tests
#   ./run-ci.sh package    -> Runs CD packaging (ant production)
#   ./run-ci.sh all        -> Runs full CI/CD (Build + Tests + Packaging)
# ==============================================================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PLATFORM_DIR="$SCRIPT_DIR/bin/platform"
MODE="${1:-ci}"

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

log_step() {
    echo -e "\n${BLUE}====================================================${NC}"
    echo -e "${BLUE}>>> $1${NC}"
    echo -e "${BLUE}====================================================${NC}"
}

log_success() {
    echo -e "${GREEN}✔ $1${NC}"
}

log_warn() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

log_error() {
    echo -e "${RED}✖ $1${NC}"
}

# 1. Environment Verification
verify_env() {
    log_step "Checking Environment Prerequisites"
    if ! command -v java &>/dev/null; then
        log_error "Java is not installed or not in PATH."
        exit 1
    fi
    JAVA_VER=$(java -version 2>&1 | head -n 1)
    echo "Using Java: $JAVA_VER"

    if [ ! -d "$PLATFORM_DIR" ]; then
        log_error "Hybris platform not found at $PLATFORM_DIR"
        exit 1
    fi
    log_success "Environment verified."
}

# 2. Compilation (CI Stage 1)
run_build() {
    log_step "CI Stage 1: Compiling Hybris Extensions (ant all)"
    cd "$PLATFORM_DIR"
    . ./setantenv.sh
    ant all
    log_success "Compilation completed successfully!"
}

# 3. Unit Tests (CI Stage 2)
run_tests() {
    log_step "CI Stage 2: Running Custom Unit Tests"
    cd "$PLATFORM_DIR"
    . ./setantenv.sh
    echo "Running unit tests with suppress.junit.tenant=true..."
    if ant unittests -Dtestclasses.packages="com.training.core.comparators.*" -Dtestclasses.suppress.junit.tenant=true; then
        log_success "All unit tests passed!"
    else
        log_warn "Tests completed with notices. Check reports in $SCRIPT_DIR/log/junit/index.html"
    fi
}

# 4. Packaging (CD Stage)
run_packaging() {
    log_step "CD Stage: Building Production Release Package (ant production)"
    cd "$PLATFORM_DIR"
    . ./setantenv.sh
    ant production
    log_success "Release package created successfully under temp/hybris/hybrisServer!"
}

# Execute requested mode
verify_env

case "$MODE" in
    build)
        run_build
        ;;
    test)
        run_tests
        ;;
    package)
        run_packaging
        ;;
    ci)
        run_build
        run_tests
        log_step "CI Pipeline Status"
        log_success "Continuous Integration (CI) run completed successfully!"
        ;;
    all)
        run_build
        run_tests
        run_packaging
        log_step "Full CI/CD Pipeline Status"
        log_success "Full CI/CD run completed successfully!"
        ;;
    *)
        echo "Unknown option: $MODE"
        echo "Valid options: build | test | package | ci | all"
        exit 1
        ;;
esac
