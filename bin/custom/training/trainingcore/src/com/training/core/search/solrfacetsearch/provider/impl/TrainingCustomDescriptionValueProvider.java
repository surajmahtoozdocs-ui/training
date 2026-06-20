package com.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import com.training.model.ElectronicsProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainingCustomDescriptionValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty, final Object model) 
            throws FieldValueProviderException {
        
        final List<FieldValue> fieldValues = new ArrayList<>();

        if (model instanceof ElectronicsProductModel) {
            final ElectronicsProductModel product = (ElectronicsProductModel) model;
            final String customDesc = product.getCustomDescription();

            if (customDesc != null && !customDesc.trim().isEmpty()) {
                final String formattedValue = "Custom: " + customDesc.trim();
                final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
                for (final String fieldName : fieldNames) {
                    fieldValues.add(new FieldValue(fieldName, formattedValue));
                }
            }
        }
        return fieldValues;
    }

    public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
