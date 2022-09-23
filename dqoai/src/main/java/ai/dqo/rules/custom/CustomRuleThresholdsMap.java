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
package ai.dqo.rules.custom;

import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecMap;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;

/**
 * Dictionary of custom rules that are enabled for a custom check.
 */
public class CustomRuleThresholdsMap extends AbstractDirtyTrackingSpecMap<CustomRuleThresholdsSpec> {
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
}
