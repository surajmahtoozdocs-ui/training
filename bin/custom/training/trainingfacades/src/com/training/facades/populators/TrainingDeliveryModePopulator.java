/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Dynamic populator to add shipping/promotional notes to delivery options.
 */
public class TrainingDeliveryModePopulator implements Populator<DeliveryModeModel, DeliveryModeData>
{
	@Override
	public void populate(final DeliveryModeModel source, final DeliveryModeData target) throws ConversionException
	{
		if (source != null && target != null)
		{
			final String code = source.getCode();
			if ("premium-gross".equalsIgnoreCase(code))
			{
				target.setCustomDeliveryNotes("Premium Delivery: Guaranteed delivery in 1-2 business days with 100 extra loyalty points!");
			}
			else if ("standard-gross".equalsIgnoreCase(code))
			{
				target.setCustomDeliveryNotes("Standard Delivery: Estimated delivery in 3-5 business days. Free for orders over $150.");
			}
			else
			{
				target.setCustomDeliveryNotes("Standard carrier shipping options.");
			}
		}
	}
}
