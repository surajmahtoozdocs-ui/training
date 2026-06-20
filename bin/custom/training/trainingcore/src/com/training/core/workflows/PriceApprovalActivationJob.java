package com.training.core.workflows;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;

import javax.annotation.Resource;
import org.apache.log4j.Logger;

public class PriceApprovalActivationJob implements AutomatedWorkflowTemplateJob {
    private static final Logger LOG = Logger.getLogger(PriceApprovalActivationJob.class);

    @Resource
    private ModelService modelService;

    @Override
    public WorkflowDecisionModel perform(final WorkflowActionModel action) {
        LOG.info("Executing Price Approval Activation Job...");

        action.getAttachmentItems().stream()
            .filter(item -> item instanceof PriceRowModel)
            .map(item -> (PriceRowModel) item)
            .forEach(priceRow -> {
                // Set the custom approvalStatus attribute to APPROVED
                priceRow.setProperty("approvalStatus", ArticleApprovalStatus.APPROVED);
                modelService.save(priceRow);
                LOG.info("PriceRow approved successfully: PK " + priceRow.getPk());
            });

        return action.getDecisions().iterator().next();
    }
}
