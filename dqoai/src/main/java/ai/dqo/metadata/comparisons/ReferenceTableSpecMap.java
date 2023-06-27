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

package ai.dqo.metadata.comparisons;

import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;

import java.util.Map;

/**
 * Dictionary of comparisons of the current table (the parent of this node) and another reference table (the source of truth)
 * to which we are comparing the tables to measure the accuracy of the data.
 */
public class ReferenceTableSpecMap extends AbstractDirtyTrackingSpecMap<ReferenceTableSpec> {
    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public ReferenceTableSpecMap deepClone() {
        ReferenceTableSpecMap cloned = new ReferenceTableSpecMap();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
        }

        for (Map.Entry<String, ReferenceTableSpec> keyPair : this.entrySet()) {
            cloned.put(keyPair.getKey(), keyPair.getValue().deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }
}
