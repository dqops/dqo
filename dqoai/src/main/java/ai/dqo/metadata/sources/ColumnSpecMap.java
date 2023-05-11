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
package ai.dqo.metadata.sources;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;

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
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
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
     * @return Trimmed copy of the column map. With parameters expanded.
     */
    public ColumnSpecMap expandAndTrim(SecretValueProvider secretValueProvider) {
        ColumnSpecMap trimmed = new ColumnSpecMap();
        for(Map.Entry<String, ColumnSpec> keyValuePair : this.entrySet()) {
            trimmed.put(keyValuePair.getKey(), keyValuePair.getValue().expandAndTrim(secretValueProvider));
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
}
