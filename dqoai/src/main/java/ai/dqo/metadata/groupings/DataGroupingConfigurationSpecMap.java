/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.metadata.groupings;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
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
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
        }

        for (Map.Entry<String, DataGroupingConfigurationSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Creates a copy of the data group mappings map. Additionally, parameters are expanded.
     * @return Deep cloned map with parameters expanded inside all data group levels.
     */
    public DataGroupingConfigurationSpecMap expandAndTrim(SecretValueProvider secretValueProvider) {
        DataGroupingConfigurationSpecMap trimmed = new DataGroupingConfigurationSpecMap();
        for(Map.Entry<String, DataGroupingConfigurationSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider));
        }
        return trimmed;
    }

    /**
     * Returns the first data grouping configuration. It is assumed as the default mapping if there is a problem to decide which
     * data grouping configuration should be the default.
     * @return The first data grouping configuration or null when there are no data group mappings.
     */
    @JsonIgnore
    public DataGroupingConfigurationSpec getFirstDataGroupingConfiguration() {
        if (this.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<String, DataGroupingConfigurationSpec>> iterator = this.entrySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next().getValue();
        }

        return null;
    }

    /**
     * Sets the first data grouping configuration. Replaces the first if there was already a data grouping configuration or adds a new
     * data grouping configuration using the default name {@link DataGroupingConfigurationSpecMap#DEFAULT_CONFIGURATION_NAME}.
     * @param dataGroupingConfigurationSpec Data grouping configuration to store. When the value is null, the first data grouping configuration is removed.
     */
    public void setFirstDataGroupingConfiguration(DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
        Iterator<Map.Entry<String, DataGroupingConfigurationSpec>> iterator = this.entrySet().iterator();

        if (dataGroupingConfigurationSpec != null) {
            if (iterator.hasNext()) {
                String key = iterator.next().getKey();
                this.put(key, dataGroupingConfigurationSpec); // replace
            } else {
                this.put(DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec);
            }
        }
        else {
            if (iterator.hasNext()) {
                this.remove(iterator.next().getKey());
            }
        }
    }

    /**
     * Returns the name of the first data grouping configuration.
     * @return The name of the first data grouping configuration in the hashtable or null when there are no named data streams.
     */
    public String getFirstDataGroupingConfigurationName() {
        if (this.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<String, DataGroupingConfigurationSpec>> iterator = this.entrySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next().getKey();
        }

        return null;
    }
}
