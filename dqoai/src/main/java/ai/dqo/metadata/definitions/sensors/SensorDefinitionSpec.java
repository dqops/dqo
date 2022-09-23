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
package ai.dqo.metadata.definitions.sensors;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor's parameters, etc.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SensorDefinitionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<SensorDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Additional sensor definition parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> params = new LinkedHashMap<>();

    @JsonIgnore
    private LinkedHashMap<String, String> originalParams = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

    /**
     * Returns a key/value map of additional rule parameters.
     * @return Key/value dictionary of additional parameters passed to the rule.
     */
    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    /**
     * Sets a dictionary of parameters passed to the rule.
     * @param params Key/value dictionary with extra parameters.
     */
    public void setParams(LinkedHashMap<String, String> params) {
		setDirtyIf(!Objects.equals(this.params, params));
        this.params = params;
		this.originalParams = (LinkedHashMap<String, String>) params.clone();
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.params, this.originalParams);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalParams = (LinkedHashMap<String, String>) this.params.clone();
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
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
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public SensorDefinitionSpec trim() {
        return this; // returns self
    }
}
