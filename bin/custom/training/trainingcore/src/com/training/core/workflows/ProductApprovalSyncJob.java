package com.training.core.workflows;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.SyncItemJobModel;
import de.hybris.platform.catalog.synchronization.CatalogSynchronizationService;
import de.hybris.platform.catalog.synchronization.SyncConfig;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.cronjob.enums.ErrorMode;
import de.hybris.platform.cronjob.enums.JobLogLevel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

public class ProductApprovalSyncJob implements AutomatedWorkflowTemplateJob {
    private static final Logger LOG = Logger.getLogger(ProductApprovalSyncJob.class);

    @Resource
    private CatalogSynchronizationService catalogSynchronizationService;
    @Resource
    private ModelService modelService;

    @Override
    public WorkflowDecisionModel perform(final WorkflowActionModel action) {
        LOG.info("Executing Product Approval Sync Job...");

        action.getAttachmentItems().stream()
            .filter(item -> item instanceof ProductModel)
            .map(item -> (ProductModel) item)
            .forEach(product -> {
                product.setApprovalStatus(ArticleApprovalStatus.APPROVED);
                modelService.save(product);

                CatalogVersionModel stagedVersion = product.getCatalogVersion();
                List<SyncItemJobModel> syncJobs = stagedVersion.getSynchronizations();
                if (syncJobs != null && !syncJobs.isEmpty()) {
                    SyncItemJobModel syncJob = syncJobs.get(0);
                    
                    SyncConfig syncConfig = new SyncConfig();
                    syncConfig.setCreateSavedValues(Boolean.FALSE);
                    syncConfig.setForceUpdate(Boolean.FALSE);
                    syncConfig.setLogLevelDatabase(JobLogLevel.WARNING);
                    syncConfig.setLogLevelFile(JobLogLevel.WARNING);
                    syncConfig.setLogToDatabase(Boolean.FALSE);
                    syncConfig.setLogToFile(Boolean.TRUE);
                    syncConfig.setSynchronous(Boolean.TRUE);
                    syncConfig.setErrorMode(ErrorMode.IGNORE);

                    catalogSynchronizationService.performSynchronization(
                        Collections.singletonList(product), syncJob, syncConfig
                    );
                    LOG.info("Product synchronized successfully: " + product.getCode());
                } else {
                    LOG.warn("No sync jobs configured on catalog version: " + stagedVersion.getVersion());
                }
            });

        return action.getDecisions().iterator().next();
    }
}

