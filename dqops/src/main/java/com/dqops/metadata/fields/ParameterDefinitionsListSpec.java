/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fields;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleValueFactory;

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
