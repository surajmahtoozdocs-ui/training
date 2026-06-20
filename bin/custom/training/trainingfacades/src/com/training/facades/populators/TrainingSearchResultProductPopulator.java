/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * Maps custom attributes from Solr SearchResultValueData to ProductData DTO.
 */
public class TrainingSearchResultProductPopulator implements Populator<SearchResultValueData, ProductData>
{
	@Override
	public void populate(final SearchResultValueData source, final ProductData target) throws ConversionException
	{
		final Object customDescription = source.getValues().get("customDescriptionIndex");
		if (customDescription instanceof String)
		{
			target.setCustomDescription((String) customDescription);
		}

		final Object customScoreStars = source.getValues().get("customScoreStars");
		if (customScoreStars instanceof String)
		{
			target.setCustomScoreStars((String) customScoreStars);
		}
	}
}
