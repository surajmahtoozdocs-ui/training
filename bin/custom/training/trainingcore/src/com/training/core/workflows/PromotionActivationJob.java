package com.training.core.workflows;

import de.hybris.platform.promotionengineservices.model.PromotionSourceRuleModel;
import de.hybris.platform.ruleengineservices.maintenance.RuleMaintenanceService;
import de.hybris.platform.ruleengineservices.enums.RuleStatus;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;

import java.util.Collections;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

public class PromotionActivationJob implements AutomatedWorkflowTemplateJob {
    private static final Logger LOG = Logger.getLogger(PromotionActivationJob.class);
    private static final String PROMOTIONS_MODULE = "promotions-module";

    @Resource
    private ModelService modelService;
    @Resource
    private RuleMaintenanceService ruleMaintenanceService;

    @Override
    public WorkflowDecisionModel perform(final WorkflowActionModel action) {
        LOG.info("Executing Promotion Activation Job...");

        action.getAttachmentItems().stream()
            .filter(item -> item instanceof PromotionSourceRuleModel)
            .map(item -> (PromotionSourceRuleModel) item)
            .forEach(rule -> {
                rule.setStatus(RuleStatus.PUBLISHED);
                modelService.save(rule);

                ruleMaintenanceService.compileAndPublishRules(
                    Collections.singletonList(rule), PROMOTIONS_MODULE, false
                );
                LOG.info("Promotion rule activated and published: " + rule.getCode());
            });

        return action.getDecisions().iterator().next();
    }
}
