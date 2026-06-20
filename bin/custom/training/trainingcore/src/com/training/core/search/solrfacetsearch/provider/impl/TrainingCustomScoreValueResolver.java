package com.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import com.training.model.ElectronicsProductModel;

public class TrainingCustomScoreValueResolver extends AbstractValueResolver<ProductModel, Object, Object> {

    @Override
    protected void addFieldValues(final InputDocument document, final IndexerBatchContext batchContext,
                                  final IndexedProperty indexedProperty, final ProductModel product,
                                  final ValueResolverContext<Object, Object> resolverContext) 
                                  throws FieldValueProviderException {
        
        if (product instanceof ElectronicsProductModel) {
            final ElectronicsProductModel electronicsProduct = (ElectronicsProductModel) product;
            final Integer score = electronicsProduct.getCustomScore();

            if (score != null && score >= 1 && score <= 5) {
                final StringBuilder stars = new StringBuilder();
                for (int i = 0; i < score; i++) {
                    stars.append("⭐");
                }
                document.addField(indexedProperty, stars.toString(), resolverContext.getFieldQualifier());
            }
        }
    }
}
