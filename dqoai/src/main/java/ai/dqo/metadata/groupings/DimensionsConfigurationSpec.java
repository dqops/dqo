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
import ai.dqo.utils.serialization.YamlNotRenderWhenDefault;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Configuration of dimensions that are used for a table or column.
 * Dimensions may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 * We can also pull dimensions directly from the database if a table has a column that identifies a business area.
 * Dimensions pulled from the database are added to the GROUP BY clause. Sensor values are extracted for each dimension separately,
 * a time series is build for each dimension separately.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class DimensionsConfigurationSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DimensionsConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("dimension_1", o -> o.dimension1);
			put("dimension_2", o -> o.dimension2);
			put("dimension_3", o -> o.dimension3);
			put("dimension_4", o -> o.dimension4);
			put("dimension_5", o -> o.dimension5);
			put("dimension_6", o -> o.dimension6);
			put("dimension_7", o -> o.dimension7);
			put("dimension_8", o -> o.dimension8);
			put("dimension_9", o -> o.dimension9);
        }
    };

    @JsonPropertyDescription("Dimension 1 configuration.")
    @JsonProperty("dimension_1")
    private DimensionMappingSpec dimension1;

    @JsonPropertyDescription("Dimension 2 configuration.")
    @JsonProperty("dimension_2")
    private DimensionMappingSpec dimension2;

    @JsonPropertyDescription("Dimension 3 configuration.")
    @JsonProperty("dimension_3")
    private DimensionMappingSpec dimension3;

    @JsonPropertyDescription("Dimension 4 configuration.")
    @JsonProperty("dimension_4")
    private DimensionMappingSpec dimension4;

    @JsonPropertyDescription("Dimension 5 configuration.")
    @JsonProperty("dimension_5")
    private DimensionMappingSpec dimension5;

    @JsonPropertyDescription("Dimension 6 configuration.")
    @JsonProperty("dimension_6")
    private DimensionMappingSpec dimension6;

    @JsonPropertyDescription("Dimension 7 configuration.")
    @JsonProperty("dimension_7")
    private DimensionMappingSpec dimension7;

    @JsonPropertyDescription("Dimension 8 configuration.")
    @JsonProperty("dimension_8")
    private DimensionMappingSpec dimension8;

    @JsonPropertyDescription("Dimension 9 configuration.")
    @JsonProperty("dimension_9")
    private DimensionMappingSpec dimension9;

    /**
     * Returns the configuration of dimension_1.
     * @return Dimension 1 configuration.
     */
    public DimensionMappingSpec getDimension1() {
        return dimension1;
    }

    /**
     * Sets the configuration of dimension 1.
     * @param dimension1 New dimension configuration.
     */
    public void setDimension1(DimensionMappingSpec dimension1) {
		setDirtyIf(!Objects.equals(this.dimension1, dimension1));
        this.dimension1 = dimension1;
		propagateHierarchyIdToField(dimension1, "dimension_1");
    }

    /**
     * Returns the configuration of dimension_2.
     * @return Dimension 2 configuration.
     */
    public DimensionMappingSpec getDimension2() {
        return dimension2;
    }

    /**
     * Sets the configuration of dimension 2.
     * @param dimension2 New dimension configuration.
     */
    public void setDimension2(DimensionMappingSpec dimension2) {
		setDirtyIf(!Objects.equals(this.dimension2, dimension2));
        this.dimension2 = dimension2;
		propagateHierarchyIdToField(dimension2, "dimension_2");
    }

    /**
     * Returns the configuration of dimension_3.
     * @return Dimension 3 configuration.
     */
    public DimensionMappingSpec getDimension3() {
        return dimension3;
    }

    /**
     * Sets the configuration of dimension 3.
     * @param dimension3 New dimension configuration.
     */
    public void setDimension3(DimensionMappingSpec dimension3) {
		setDirtyIf(!Objects.equals(this.dimension3, dimension3));
        this.dimension3 = dimension3;
		propagateHierarchyIdToField(dimension3, "dimension_3");
    }

    /**
     * Returns the configuration of dimension_4.
     * @return Dimension 4 configuration.
     */
    public DimensionMappingSpec getDimension4() {
        return dimension4;
    }

    /**
     * Sets the configuration of dimension 4.
     * @param dimension4 New dimension configuration.
     */
    public void setDimension4(DimensionMappingSpec dimension4) {
		setDirtyIf(!Objects.equals(this.dimension4, dimension4));
        this.dimension4 = dimension4;
		propagateHierarchyIdToField(dimension4, "dimension_4");
    }

    /**
     * Returns the configuration of dimension_5.
     * @return Dimension 5 configuration.
     */
    public DimensionMappingSpec getDimension5() {
        return dimension5;
    }

    /**
     * Sets the configuration of dimension 5.
     * @param dimension5 New dimension configuration.
     */
    public void setDimension5(DimensionMappingSpec dimension5) {
		setDirtyIf(!Objects.equals(this.dimension5, dimension5));
        this.dimension5 = dimension5;
		propagateHierarchyIdToField(dimension5, "dimension_5");
    }

    /**
     * Returns the configuration of dimension_6.
     * @return Dimension 6 configuration.
     */
    public DimensionMappingSpec getDimension6() {
        return dimension6;
    }

    /**
     * Sets the configuration of dimension 6.
     * @param dimension6 New dimension configuration.
     */
    public void setDimension6(DimensionMappingSpec dimension6) {
		setDirtyIf(!Objects.equals(this.dimension6, dimension6));
        this.dimension6 = dimension6;
		propagateHierarchyIdToField(dimension6, "dimension_6");
    }

    /**
     * Returns the configuration of dimension_7.
     * @return Dimension 7 configuration.
     */
    public DimensionMappingSpec getDimension7() {
        return dimension7;
    }

    /**
     * Sets the configuration of dimension 7.
     * @param dimension7 New dimension configuration.
     */
    public void setDimension7(DimensionMappingSpec dimension7) {
		setDirtyIf(!Objects.equals(this.dimension7, dimension7));
        this.dimension7 = dimension7;
		propagateHierarchyIdToField(dimension7, "dimension_7");
    }

    /**
     * Returns the configuration of dimension_8.
     * @return Dimension 8 configuration.
     */
    public DimensionMappingSpec getDimension8() {
        return dimension8;
    }

    /**
     * Sets the configuration of dimension 8.
     * @param dimension8 New dimension configuration.
     */
    public void setDimension8(DimensionMappingSpec dimension8) {
		setDirtyIf(!Objects.equals(this.dimension8, dimension8));
        this.dimension8 = dimension8;
		propagateHierarchyIdToField(dimension8, "dimension_8");
    }

    /**
     * Returns the configuration of dimension_9.
     * @return Dimension 9 configuration.
     */
    public DimensionMappingSpec getDimension9() {
        return dimension9;
    }

    /**
     * Sets the configuration of dimension 9.
     * @param dimension9 New dimension configuration.
     */
    public void setDimension9(DimensionMappingSpec dimension9) {
		setDirtyIf(!Objects.equals(this.dimension9, dimension9));
        this.dimension9 = dimension9;
		propagateHierarchyIdToField(dimension9, "dimension_9");
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
    public DimensionsConfigurationSpec clone() {
        try {
            DimensionsConfigurationSpec cloned = (DimensionsConfigurationSpec) super.clone();
            if (cloned.dimension1 != null) {
                cloned.dimension1 = cloned.dimension1.clone();
            }
            if (cloned.dimension2 != null) {
                cloned.dimension2 = cloned.dimension2.clone();
            }
            if (cloned.dimension3 != null) {
                cloned.dimension3 = cloned.dimension3.clone();
            }
            if (cloned.dimension4 != null) {
                cloned.dimension4 = cloned.dimension4.clone();
            }
            if (cloned.dimension5 != null) {
                cloned.dimension5 = cloned.dimension5.clone();
            }
            if (cloned.dimension6 != null) {
                cloned.dimension6 = cloned.dimension6.clone();
            }
            if (cloned.dimension7 != null) {
                cloned.dimension7 = cloned.dimension7.clone();
            }
            if (cloned.dimension8 != null) {
                cloned.dimension8 = cloned.dimension8.clone();
            }
            if (cloned.dimension9 != null) {
                cloned.dimension9 = cloned.dimension9.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Creates a cloned copy of the object with all configurable parameters that have a variable like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded deep copy of the object.
     */
    public DimensionsConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            DimensionsConfigurationSpec cloned = (DimensionsConfigurationSpec) super.clone();
            if (cloned.dimension1 != null) {
                cloned.dimension1 = cloned.dimension1.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension2 != null) {
                cloned.dimension2 = cloned.dimension2.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension3 != null) {
                cloned.dimension3 = cloned.dimension3.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension4 != null) {
                cloned.dimension4 = cloned.dimension4.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension5 != null) {
                cloned.dimension5 = cloned.dimension5.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension6 != null) {
                cloned.dimension6 = cloned.dimension6.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension7 != null) {
                cloned.dimension7 = cloned.dimension7.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension8 != null) {
                cloned.dimension8 = cloned.dimension8.expandAndTrim(secretValueProvider);
            }
            if (cloned.dimension9 != null) {
                cloned.dimension9 = cloned.dimension9.expandAndTrim(secretValueProvider);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return this.dimension1 == null &&
				this.dimension2 == null &&
				this.dimension3 == null &&
				this.dimension4 == null &&
				this.dimension5 == null &&
				this.dimension6 == null &&
				this.dimension7 == null &&
				this.dimension8 == null &&
				this.dimension9 == null;
    }
}
