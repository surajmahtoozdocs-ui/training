/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Populates custom attributes from AddressData to AddressModel.
 */
public class TrainingAddressReversePopulator extends de.hybris.platform.commercefacades.user.converters.populator.AddressReversePopulator
{
	@Override
	public void populate(final AddressData source, final AddressModel target) throws ConversionException
	{
		super.populate(source, target);
		if (source != null && target != null)
		{
			target.setDeliveryInstructions(source.getDeliveryInstructions());
		}
	}
}
