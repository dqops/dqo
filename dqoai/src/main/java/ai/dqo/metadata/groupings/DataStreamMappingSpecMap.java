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

import java.util.Iterator;
import java.util.Map;

/**
 * Dictionary of named data stream mappings defined on a table level.
 */
public class DataStreamMappingSpecMap extends AbstractDirtyTrackingSpecMap<DataStreamMappingSpec> {
    public static final String DEFAULT_MAPPING_NAME = "default";

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
    public DataStreamMappingSpecMap clone() {
        DataStreamMappingSpecMap cloned = new DataStreamMappingSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
        }

        for (Map.Entry<String, DataStreamMappingSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().clone());
        }

        if (!this.isDirty()) {
            cloned.clearDirty(false);
        }

        return cloned;
    }

    /**
     * Creates a copy of the data stream mappings map. Additionally, parameters are expanded.
     * @return Deep cloned map with parameters expanded inside all data stream levels.
     */
    public DataStreamMappingSpecMap expandAndTrim(SecretValueProvider secretValueProvider) {
        DataStreamMappingSpecMap trimmed = new DataStreamMappingSpecMap();
        for(Map.Entry<String, DataStreamMappingSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider));
        }
        return trimmed;
    }

    /**
     * Returns the first data stream mapping. It is assumed as the default mapping if there is a problem to decide which
     * data stream mapping should be the default.
     * @return The first data stream mapping or null when there are no data stream mappings.
     */
    public DataStreamMappingSpec getFirstDataStreamMapping() {
        if (this.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<String, DataStreamMappingSpec>> iterator = this.entrySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next().getValue();
        }

        return null;
    }

    /**
     * Sets the first data stream mapping. Replaces the first if there was already a data stream mapping or adds a new
     * data stream mapping using the default name {@link DataStreamMappingSpecMap#DEFAULT_MAPPING_NAME}.
     * @param dataStreamMapping Data stream mapping to store. When the value is null, the first data stream mapping is removed.
     */
    public void setFirstDataStreamMapping(DataStreamMappingSpec dataStreamMapping) {
        Iterator<Map.Entry<String, DataStreamMappingSpec>> iterator = this.entrySet().iterator();

        if (dataStreamMapping != null) {
            if (iterator.hasNext()) {
                String key = iterator.next().getKey();
                this.put(key, dataStreamMapping); // replace
            } else {
                this.put(DEFAULT_MAPPING_NAME, dataStreamMapping);
            }
        }
        else {
            if (iterator.hasNext()) {
                this.remove(iterator.next().getKey());
            }
        }
    }

    /**
     * Returns the name of the first data stream mapping.
     * @return The name of the first data stream mapping in the hashtable or null when there are no named data streams.
     */
    public String getFirstDataStreamMappingName() {
        if (this.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<String, DataStreamMappingSpec>> iterator = this.entrySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next().getKey();
        }

        return null;
    }
}
