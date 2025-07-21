/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.groupings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

import java.util.Map;

/**
 * Dictionary of named data grouping configurations defined on a table level.
 */
public class DataGroupingConfigurationSpecMap extends AbstractDirtyTrackingSpecMap<DataGroupingConfigurationSpec> {
    public static final String DEFAULT_CONFIGURATION_NAME = "default";

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DataGroupingConfigurationSpecMap deepClone() {
        DataGroupingConfigurationSpecMap cloned = new DataGroupingConfigurationSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (Map.Entry<String, DataGroupingConfigurationSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Creates a copy of the data group mappings map. Additionally, parameters are expanded.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup.
     * @return Deep cloned map with parameters expanded inside all data group levels.
     */
    public DataGroupingConfigurationSpecMap expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        DataGroupingConfigurationSpecMap trimmed = new DataGroupingConfigurationSpecMap();
        for(Map.Entry<String, DataGroupingConfigurationSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        return trimmed;
    }
}
