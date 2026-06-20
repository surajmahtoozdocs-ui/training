/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Dynamic populator to calculate loyalty points earned for an order.
 */
public class TrainingOrderPopulator implements Populator<OrderModel, OrderData>
{
	@Override
	public void populate(final OrderModel source, final OrderData target) throws ConversionException
	{
		if (source != null && target != null)
		{
			final Double total = source.getTotalPrice();
			if (total != null)
			{
				// Calculate points: 1 point per $1 spent, rounded down
				target.setLoyaltyPointsEarned((int) Math.floor(total));
			}
			else
			{
				target.setLoyaltyPointsEarned(0);
			}
		}
	}
}
