/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Dynamic populator to calculate estimated loyalty points for the cart.
 */
public class TrainingCartPopulator implements Populator<CartModel, CartData>
{
	@Override
	public void populate(final CartModel source, final CartData target) throws ConversionException
	{
		if (source != null && target != null)
		{
			final Double total = source.getTotalPrice();
			if (total != null)
			{
				// Estimate points: 1 point per $1 spent, rounded down
				target.setEstimatedLoyaltyPoints((int) Math.floor(total));
			}
			else
			{
				target.setEstimatedLoyaltyPoints(0);
			}
		}
	}
}
