/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.metadata.sources;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Dictionary of columns indexed by a physical column name.
 */
public class ColumnSpecMap extends AbstractDirtyTrackingSpecMap<ColumnSpec> {
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
    public ColumnSpecMap deepClone() {
        ColumnSpecMap cloned = new ColumnSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (Map.Entry<String, ColumnSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    /**
     * Creates a copy of the column map using trimmed column specifications. Additionally, parameters are expanded.
     * Trimmed column specifications are serialized to json and forwarded to a jinja2 template (sensor runner).
     * A trimmed column specification is missing the checks and is smaller.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Trimmed copy of the column map. With parameters expanded.
     */
    public ColumnSpecMap expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        ColumnSpecMap trimmed = new ColumnSpecMap();
        for(Map.Entry<String, ColumnSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider, secretValueLookupContext));
        }
        return trimmed;
    }

    /**
     * Creates a copy of the column map using trimmed column specifications. Additionally, parameters are expanded.
     * Trimmed column specifications are serialized to json and forwarded to a jinja2 template (sensor runner).
     * A trimmed column specification is missing the checks and is smaller.
     * @return Trimmed copy of the column map. With unnecessary objects detached (like the time series or dimensions).
     */
    public ColumnSpecMap trim() {
        ColumnSpecMap trimmed = new ColumnSpecMap();
        for(Map.Entry<String, ColumnSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().trim());
        }
        return trimmed;
    }

    /**
     * Finds the most similar column by name.
     * First finds an exact name (case-sensitive), when not found, finds a column name with the name that differs by case.
     * When case insensitive search does not result in finding a column, then tries to find a column that contains the name of the expected column
     * or the expected column name contains the name of the column in this collection.
     * @param columnNameToFind Name of the column to find.
     * @return Column that was found or null when no column matched.
     */
    public ColumnSpec findMostSimilarColumn(String columnNameToFind) {
        ColumnSpec columnSpec = this.get(columnNameToFind);
        if (columnSpec != null) {
            return columnSpec; // we found a column with exactly the same name
        }

        for (String columnName : this.keySet()) {
            if (columnName.equalsIgnoreCase(columnNameToFind)){
                return this.get(columnName);
            }
        }

        for (String columnName : this.keySet()) {
            if (StringUtils.containsIgnoreCase(columnName, columnNameToFind) || StringUtils.containsIgnoreCase(columnNameToFind, columnName)) {
                return this.get(columnName);
            }
        }

        return null;
    }
}
