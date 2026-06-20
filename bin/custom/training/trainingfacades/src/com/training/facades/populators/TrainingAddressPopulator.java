/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Populates custom attributes from AddressModel to AddressData.
 */
public class TrainingAddressPopulator implements Populator<AddressModel, AddressData>
{
	@Override
	public void populate(final AddressModel source, final AddressData target) throws ConversionException
	{
		if (source != null && target != null)
		{
			target.setDeliveryInstructions(source.getDeliveryInstructions());
		}
	}
}
