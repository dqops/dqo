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
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.search.DimensionSearcherObject;
import ai.dqo.metadata.search.LabelsSearcherObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Single dimension configuration. A dimension may be configured as a hardcoded value or a mapping to a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class DimensionMappingSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DimensionMappingSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Hardcoded dimension value. Assign a hardcoded (static) dimension value when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.")
    private DimensionMappingSource source = DimensionMappingSource.STATIC_VALUE;

    @JsonPropertyDescription("Hardcoded dimension value. Assign a hardcoded (static) dimension value when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.")
    private String staticValue;

    @JsonPropertyDescription("Column name that contains the dimension value (for dynamic data-driven dimensions). Sensor queries will be extended with a GROUP BY {dimension colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.")
    private String column;

    /**
     * Dimension value source.
     * @return Gets the dimension value source.
     */
    public DimensionMappingSource getSource() {
        return source;
    }

    /**
     * Sets the source of the dimension values. A dimension could be a static value or a dynamic value, returned from the data.
     * @param source Dimension mode.
     */
    public void setSource(DimensionMappingSource source) {
		setDirtyIf(!Objects.equals(this.source, source));
        this.source = source;
    }

    /**
     * Returns a static value assigned to a dimension.
     * @return Static dimension value.
     */
    public String getStaticValue() {
        return staticValue;
    }

    /**
     * Sets a new static value of the dimension.
     * @param staticValue Static dimension value.
     */
    public void setStaticValue(String staticValue) {
		setDirtyIf(!Objects.equals(this.staticValue, staticValue));
        this.staticValue = staticValue;
    }

    /**
     * Gets the column name that is added to the GROUP BY clause. Sensor results retrieved for each grouping (each unique value of the column) is tagged with the value of the column.
     * @return Column name used to make a dynamic dimension.
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets a name of the column used to populate dynamic dimensions.
     * @param column Dynamic column name.
     */
    public void setColumn(String column) {
		setDirtyIf(!Objects.equals(this.column, column));
        this.column = column;
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
     * Creates and returns a copy of this object.
     */
    @Override
    public DimensionMappingSpec clone() {
        try {
            DimensionMappingSpec cloned = (DimensionMappingSpec) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public DimensionMappingSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            DimensionMappingSpec cloned = (DimensionMappingSpec) super.clone();
            cloned.staticValue = secretValueProvider.expandValue(cloned.staticValue);
            cloned.column = secretValueProvider.expandValue(cloned.column);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
