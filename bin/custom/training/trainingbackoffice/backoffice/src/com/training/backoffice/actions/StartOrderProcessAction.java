/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.backoffice.actions;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import com.hybris.backoffice.widgets.notificationarea.NotificationService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;


public class StartOrderProcessAction implements CockpitAction<OrderModel, Object>
{
	private static final String CONFIRMATION_MESSAGE = "hmc.action.startorderprocess.confirmation.message";
	private static final String START_ORDER_PROCESS_EVENT = "trainingbackoffice.startorderprocess.event";

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "businessProcessService")
	private BusinessProcessService businessProcessService;

	@Resource(name = "notificationService")
	private NotificationService notificationService;

	@Override
	public boolean canPerform(final ActionContext<OrderModel> ctx)
	{
		return ctx != null && ctx.getData() instanceof OrderModel;
	}

	@Override
	public String getConfirmationMessage(final ActionContext<OrderModel> ctx)
	{
		return ctx.getLabel(CONFIRMATION_MESSAGE);
	}

	@Override
	public boolean needsConfirmation(final ActionContext<OrderModel> arg0)
	{
		return true;
	}

	@Override
	public ActionResult<Object> perform(final ActionContext<OrderModel> ctx)
	{
		final Object data = ctx.getData();

		if ((data != null) && (data instanceof OrderModel))
		{
			final OrderModel order = (OrderModel) data;
			
			final String processCode = "order-process-" + order.getCode() + "-" + System.currentTimeMillis();
			final OrderProcessModel businessProcessModel = businessProcessService.createProcess(processCode, "order-process");
			businessProcessModel.setOrder(order);
			modelService.save(businessProcessModel);
			businessProcessService.startProcess(businessProcessModel);

			notificationService.notifyUser(notificationService.getWidgetNotificationSource(ctx), START_ORDER_PROCESS_EVENT,
					NotificationEvent.Level.SUCCESS);

			return new ActionResult<Object>(ActionResult.SUCCESS, order);
		}
		else
		{
			return new ActionResult(ActionResult.ERROR);
		}
	}
}
