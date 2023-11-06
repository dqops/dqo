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
package com.dqops.metadata.fields;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.SampleValueFactory;

import java.util.Collection;
import java.util.List;

/**
 * List of parameter definitions - the parameters for custom sensors or custom rules.
 */
public class ParameterDefinitionsListSpec extends AbstractDirtyTrackingSpecList<ParameterDefinitionSpec> {
    public ParameterDefinitionsListSpec() {
    }

    public ParameterDefinitionsListSpec(Collection<ParameterDefinitionSpec> parameters) {
        this.addAll(parameters);
    }

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
    public ParameterDefinitionsListSpec deepClone() {
        ParameterDefinitionsListSpec cloned = new ParameterDefinitionsListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (ParameterDefinitionSpec parameterDefinitionSpec : this) {
            cloned.add(parameterDefinitionSpec.deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }

    public static class ParameterDefinitionsListSpecSampleFactory implements SampleValueFactory<ParameterDefinitionsListSpec> {
        @Override
        public ParameterDefinitionsListSpec createSample() {
            return new ParameterDefinitionsListSpec(List.of(
                    new ParameterDefinitionSpec() {{
                        setFieldName("sample_string_param");
                        setDataType(ParameterDataType.string_type);
                    }},
                    new ParameterDefinitionSpec() {{
                        setFieldName("sample_double_param");
                        setDataType(ParameterDataType.double_type);
                    }}
            ));
        }
    }
}
