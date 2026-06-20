/*
 * Copyright (c) 2026 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantProductModel;
import com.training.model.ElectronicsProductModel;

/**
 * Populates {@link ProductData} with customDescription and customScoreStars.
 */
public class TrainingProductPopulator implements Populator<ProductModel, ProductData>
{
	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{
		final ProductModel baseProduct = getBaseProduct(source);

		if (baseProduct instanceof ElectronicsProductModel)
		{
			final ElectronicsProductModel electronicsProduct = (ElectronicsProductModel) baseProduct;
			target.setCustomDescription(electronicsProduct.getCustomDescription());

			final Integer score = electronicsProduct.getCustomScore();
			if (score != null && score >= 1 && score <= 5)
			{
				final StringBuilder stars = new StringBuilder();
				for (int i = 0; i < score; i++)
				{
					stars.append("⭐");
				}
				target.setCustomScoreStars(stars.toString());
			}
		}
	}

	protected ProductModel getBaseProduct(final ProductModel productModel)
	{
		ProductModel currentProduct = productModel;
		while (currentProduct instanceof VariantProductModel)
		{
			final VariantProductModel variant = (VariantProductModel) currentProduct;
			currentProduct = variant.getBaseProduct();
		}
		return currentProduct;
	}
}
