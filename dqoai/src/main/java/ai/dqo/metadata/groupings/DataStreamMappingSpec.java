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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Configuration of the data stream that is used for a table.
 * Data streams levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 * We can also pull data stream levels directly from the database if a table has a column that identifies a business area.
 * Data streams dynamically identified in the database are added to the GROUP BY clause. Sensor values are extracted for each data stream separately,
 * a time series is build for each data stream separately.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class DataStreamConfigurationSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DataStreamConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("level_1", o -> o.level1);
			put("level_2", o -> o.level2);
			put("level_3", o -> o.level3);
			put("level_4", o -> o.level4);
			put("level_5", o -> o.level5);
			put("level_6", o -> o.level6);
			put("level_7", o -> o.level7);
			put("level_8", o -> o.level8);
			put("level_9", o -> o.level9);
        }
    };

    @JsonPropertyDescription("Data stream level 1 configuration.")
    @JsonProperty("level_1")
    private DataStreamLevelSpec level1;

    @JsonPropertyDescription("Data stream level 2 configuration.")
    @JsonProperty("level_2")
    private DataStreamLevelSpec level2;

    @JsonPropertyDescription("Data stream level 3 configuration.")
    @JsonProperty("level_3")
    private DataStreamLevelSpec level3;

    @JsonPropertyDescription("Data stream level 4 configuration.")
    @JsonProperty("level_4")
    private DataStreamLevelSpec level4;

    @JsonPropertyDescription("Data stream level 5 configuration.")
    @JsonProperty("level_5")
    private DataStreamLevelSpec level5;

    @JsonPropertyDescription("Data stream level 6 configuration.")
    @JsonProperty("level_6")
    private DataStreamLevelSpec level6;

    @JsonPropertyDescription("Data stream level 7 configuration.")
    @JsonProperty("level_7")
    private DataStreamLevelSpec level7;

    @JsonPropertyDescription("Data stream level 8 configuration.")
    @JsonProperty("level_8")
    private DataStreamLevelSpec level8;

    @JsonPropertyDescription("Data stream level 9 configuration.")
    @JsonProperty("level_9")
    private DataStreamLevelSpec level9;

    /**
     * Returns the configuration of level_1.
     * @return Data stream level 1 configuration.
     */
    public DataStreamLevelSpec getLevel1() {
        return level1;
    }

    /**
     * Sets the configuration of the data stream level 1.
     * @param level1 New data stream level configuration.
     */
    public void setLevel1(DataStreamLevelSpec level1) {
		setDirtyIf(!Objects.equals(this.level1, level1));
        this.level1 = level1;
		propagateHierarchyIdToField(level1, "level_1");
    }

    /**
     * Returns the configuration of level_2.
     * @return Data stream level 2 configuration.
     */
    public DataStreamLevelSpec getLevel2() {
        return level2;
    }

    /**
     * Sets the configuration of the data stream level 2.
     * @param level2 New data stream level configuration.
     */
    public void setLevel2(DataStreamLevelSpec level2) {
		setDirtyIf(!Objects.equals(this.level2, level2));
        this.level2 = level2;
		propagateHierarchyIdToField(level2, "level_2");
    }

    /**
     * Returns the configuration of level_3.
     * @return Data stream level 3 configuration.
     */
    public DataStreamLevelSpec getLevel3() {
        return level3;
    }

    /**
     * Sets the configuration of the data stream level 3.
     * @param level3 New data stream level configuration.
     */
    public void setLevel3(DataStreamLevelSpec level3) {
		setDirtyIf(!Objects.equals(this.level3, level3));
        this.level3 = level3;
		propagateHierarchyIdToField(level3, "level_3");
    }

    /**
     * Returns the configuration of level_4.
     * @return Data stream level 4 configuration.
     */
    public DataStreamLevelSpec getLevel4() {
        return level4;
    }

    /**
     * Sets the configuration of the data stream level 4.
     * @param level4 New data stream level configuration.
     */
    public void setLevel4(DataStreamLevelSpec level4) {
		setDirtyIf(!Objects.equals(this.level4, level4));
        this.level4 = level4;
		propagateHierarchyIdToField(level4, "level_4");
    }

    /**
     * Returns the configuration of level_5.
     * @return Data stream level 5 configuration.
     */
    public DataStreamLevelSpec getLevel5() {
        return level5;
    }

    /**
     * Sets the configuration of the data stream level 5.
     * @param level5 New data stream level configuration.
     */
    public void setLevel5(DataStreamLevelSpec level5) {
		setDirtyIf(!Objects.equals(this.level5, level5));
        this.level5 = level5;
		propagateHierarchyIdToField(level5, "level_5");
    }

    /**
     * Returns the configuration of level_6.
     * @return Data stream level 6 configuration.
     */
    public DataStreamLevelSpec getLevel6() {
        return level6;
    }

    /**
     * Sets the configuration of the data stream level 6.
     * @param level6 New data stream level configuration.
     */
    public void setLevel6(DataStreamLevelSpec level6) {
		setDirtyIf(!Objects.equals(this.level6, level6));
        this.level6 = level6;
		propagateHierarchyIdToField(level6, "level_6");
    }

    /**
     * Returns the configuration of level_7.
     * @return Data stream level 7 configuration.
     */
    public DataStreamLevelSpec getLevel7() {
        return level7;
    }

    /**
     * Sets the configuration of the data stream level 7.
     * @param level7 New data stream level configuration.
     */
    public void setLevel7(DataStreamLevelSpec level7) {
		setDirtyIf(!Objects.equals(this.level7, level7));
        this.level7 = level7;
		propagateHierarchyIdToField(level7, "level_7");
    }

    /**
     * Returns the configuration of level_8.
     * @return Data stream level 8 configuration.
     */
    public DataStreamLevelSpec getLevel8() {
        return level8;
    }

    /**
     * Sets the configuration of the data stream level 8.
     * @param level8 New data stream level configuration.
     */
    public void setLevel8(DataStreamLevelSpec level8) {
		setDirtyIf(!Objects.equals(this.level8, level8));
        this.level8 = level8;
		propagateHierarchyIdToField(level8, "level_8");
    }

    /**
     * Returns the configuration of level_9.
     * @return Data stream level 9 configuration.
     */
    public DataStreamLevelSpec getLevel9() {
        return level9;
    }

    /**
     * Sets the configuration of the data stream level 9.
     * @param level9 New data stream level configuration.
     */
    public void setLevel9(DataStreamLevelSpec level9) {
		setDirtyIf(!Objects.equals(this.level9, level9));
        this.level9 = level9;
		propagateHierarchyIdToField(level9, "level_9");
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
    public DataStreamConfigurationSpec clone() {
        try {
            DataStreamConfigurationSpec cloned = (DataStreamConfigurationSpec) super.clone();
            if (cloned.level1 != null) {
                cloned.level1 = cloned.level1.clone();
            }
            if (cloned.level2 != null) {
                cloned.level2 = cloned.level2.clone();
            }
            if (cloned.level3 != null) {
                cloned.level3 = cloned.level3.clone();
            }
            if (cloned.level4 != null) {
                cloned.level4 = cloned.level4.clone();
            }
            if (cloned.level5 != null) {
                cloned.level5 = cloned.level5.clone();
            }
            if (cloned.level6 != null) {
                cloned.level6 = cloned.level6.clone();
            }
            if (cloned.level7 != null) {
                cloned.level7 = cloned.level7.clone();
            }
            if (cloned.level8 != null) {
                cloned.level8 = cloned.level8.clone();
            }
            if (cloned.level9 != null) {
                cloned.level9 = cloned.level9.clone();
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
    public DataStreamConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            DataStreamConfigurationSpec cloned = (DataStreamConfigurationSpec) super.clone();
            if (cloned.level1 != null) {
                cloned.level1 = cloned.level1.expandAndTrim(secretValueProvider);
            }
            if (cloned.level2 != null) {
                cloned.level2 = cloned.level2.expandAndTrim(secretValueProvider);
            }
            if (cloned.level3 != null) {
                cloned.level3 = cloned.level3.expandAndTrim(secretValueProvider);
            }
            if (cloned.level4 != null) {
                cloned.level4 = cloned.level4.expandAndTrim(secretValueProvider);
            }
            if (cloned.level5 != null) {
                cloned.level5 = cloned.level5.expandAndTrim(secretValueProvider);
            }
            if (cloned.level6 != null) {
                cloned.level6 = cloned.level6.expandAndTrim(secretValueProvider);
            }
            if (cloned.level7 != null) {
                cloned.level7 = cloned.level7.expandAndTrim(secretValueProvider);
            }
            if (cloned.level8 != null) {
                cloned.level8 = cloned.level8.expandAndTrim(secretValueProvider);
            }
            if (cloned.level9 != null) {
                cloned.level9 = cloned.level9.expandAndTrim(secretValueProvider);
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
        return this.level1 == null &&
				this.level2 == null &&
				this.level3 == null &&
				this.level4 == null &&
				this.level5 == null &&
				this.level6 == null &&
				this.level7 == null &&
				this.level8 == null &&
				this.level9 == null;
    }
}
