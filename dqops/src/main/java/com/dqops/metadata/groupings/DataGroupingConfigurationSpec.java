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
package com.dqops.metadata.groupings;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Configuration of the data groupings that is used to calculate data quality checks with a GROUP BY clause.
 * Data grouping levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 * We can also pull data grouping levels directly from the database if a table has a column that identifies a business area.
 * Data quality results for new groups are dynamically identified in the database by the GROUP BY clause. Sensor values are extracted for each data group separately,
 * a time series is build for each data group separately.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class DataGroupingConfigurationSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<DataGroupingConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
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

    @JsonPropertyDescription("Data grouping dimension level 1 configuration.")
    @JsonProperty("level_1")
    private DataGroupingDimensionSpec level1;

    @JsonPropertyDescription("Data grouping dimension level 2 configuration.")
    @JsonProperty("level_2")
    private DataGroupingDimensionSpec level2;

    @JsonPropertyDescription("Data grouping dimension level 3 configuration.")
    @JsonProperty("level_3")
    private DataGroupingDimensionSpec level3;

    @JsonPropertyDescription("Data grouping dimension level 4 configuration.")
    @JsonProperty("level_4")
    private DataGroupingDimensionSpec level4;

    @JsonPropertyDescription("Data grouping dimension level 5 configuration.")
    @JsonProperty("level_5")
    private DataGroupingDimensionSpec level5;

    @JsonPropertyDescription("Data grouping dimension level 6 configuration.")
    @JsonProperty("level_6")
    private DataGroupingDimensionSpec level6;

    @JsonPropertyDescription("Data grouping dimension level 7 configuration.")
    @JsonProperty("level_7")
    private DataGroupingDimensionSpec level7;

    @JsonPropertyDescription("Data grouping dimension level 8 configuration.")
    @JsonProperty("level_8")
    private DataGroupingDimensionSpec level8;

    @JsonPropertyDescription("Data grouping dimension level 9 configuration.")
    @JsonProperty("level_9")
    private DataGroupingDimensionSpec level9;

    /**
     * Returns the configuration of level_1.
     * @return Data grouping dimension level 1 configuration.
     */
    public DataGroupingDimensionSpec getLevel1() {
        return level1;
    }

    /**
     * Sets the configuration of the data grouping dimension level 1.
     * @param level1 New data grouping dimension level configuration.
     */
    public void setLevel1(DataGroupingDimensionSpec level1) {
		setDirtyIf(!Objects.equals(this.level1, level1));
        this.level1 = level1;
		propagateHierarchyIdToField(level1, "level_1");
    }

    /**
     * Returns the configuration of level_2.
     * @return Data grouping dimension level 2 configuration.
     */
    public DataGroupingDimensionSpec getLevel2() {
        return level2;
    }

    /**
     * Sets the configuration of the data grouping dimension level 2.
     * @param level2 New data grouping dimension level configuration.
     */
    public void setLevel2(DataGroupingDimensionSpec level2) {
		setDirtyIf(!Objects.equals(this.level2, level2));
        this.level2 = level2;
		propagateHierarchyIdToField(level2, "level_2");
    }

    /**
     * Returns the configuration of level_3.
     * @return Data grouping dimension level 3 configuration.
     */
    public DataGroupingDimensionSpec getLevel3() {
        return level3;
    }

    /**
     * Sets the configuration of the data grouping dimension level 3.
     * @param level3 New data grouping dimension level configuration.
     */
    public void setLevel3(DataGroupingDimensionSpec level3) {
		setDirtyIf(!Objects.equals(this.level3, level3));
        this.level3 = level3;
		propagateHierarchyIdToField(level3, "level_3");
    }

    /**
     * Returns the configuration of level_4.
     * @return Data grouping dimension level 4 configuration.
     */
    public DataGroupingDimensionSpec getLevel4() {
        return level4;
    }

    /**
     * Sets the configuration of the data grouping dimension level 4.
     * @param level4 New data grouping dimension level configuration.
     */
    public void setLevel4(DataGroupingDimensionSpec level4) {
		setDirtyIf(!Objects.equals(this.level4, level4));
        this.level4 = level4;
		propagateHierarchyIdToField(level4, "level_4");
    }

    /**
     * Returns the configuration of level_5.
     * @return Data grouping dimension level 5 configuration.
     */
    public DataGroupingDimensionSpec getLevel5() {
        return level5;
    }

    /**
     * Sets the configuration of the data grouping dimension level 5.
     * @param level5 New data grouping dimension level configuration.
     */
    public void setLevel5(DataGroupingDimensionSpec level5) {
		setDirtyIf(!Objects.equals(this.level5, level5));
        this.level5 = level5;
		propagateHierarchyIdToField(level5, "level_5");
    }

    /**
     * Returns the configuration of level_6.
     * @return Data grouping dimension level 6 configuration.
     */
    public DataGroupingDimensionSpec getLevel6() {
        return level6;
    }

    /**
     * Sets the configuration of the data grouping dimension level 6.
     * @param level6 New data grouping dimension level configuration.
     */
    public void setLevel6(DataGroupingDimensionSpec level6) {
		setDirtyIf(!Objects.equals(this.level6, level6));
        this.level6 = level6;
		propagateHierarchyIdToField(level6, "level_6");
    }

    /**
     * Returns the configuration of level_7.
     * @return Data grouping dimension level 7 configuration.
     */
    public DataGroupingDimensionSpec getLevel7() {
        return level7;
    }

    /**
     * Sets the configuration of the data grouping dimension level 7.
     * @param level7 New data grouping dimension level configuration.
     */
    public void setLevel7(DataGroupingDimensionSpec level7) {
		setDirtyIf(!Objects.equals(this.level7, level7));
        this.level7 = level7;
		propagateHierarchyIdToField(level7, "level_7");
    }

    /**
     * Returns the configuration of level_8.
     * @return Data grouping dimension level 8 configuration.
     */
    public DataGroupingDimensionSpec getLevel8() {
        return level8;
    }

    /**
     * Sets the configuration of the data grouping dimension level 8.
     * @param level8 New data grouping dimension level configuration.
     */
    public void setLevel8(DataGroupingDimensionSpec level8) {
		setDirtyIf(!Objects.equals(this.level8, level8));
        this.level8 = level8;
		propagateHierarchyIdToField(level8, "level_8");
    }

    /**
     * Returns the configuration of level_9.
     * @return Data grouping dimension level 9 configuration.
     */
    public DataGroupingDimensionSpec getLevel9() {
        return level9;
    }

    /**
     * Sets the configuration of the data grouping dimension level 9.
     * @param level9 New data grouping dimension level configuration.
     */
    public void setLevel9(DataGroupingDimensionSpec level9) {
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
    public DataGroupingConfigurationSpec deepClone() {
        DataGroupingConfigurationSpec cloned = (DataGroupingConfigurationSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned copy of the object with all configurable parameters that have a variable like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Cloned and expanded deep copy of the object.
     */
    public DataGroupingConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        DataGroupingConfigurationSpec cloned = this.deepClone();
        if (cloned.level1 != null) {
            cloned.level1 = cloned.level1.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level2 != null) {
            cloned.level2 = cloned.level2.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level3 != null) {
            cloned.level3 = cloned.level3.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level4 != null) {
            cloned.level4 = cloned.level4.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level5 != null) {
            cloned.level5 = cloned.level5.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level6 != null) {
            cloned.level6 = cloned.level6.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level7 != null) {
            cloned.level7 = cloned.level7.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level8 != null) {
            cloned.level8 = cloned.level8.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.level9 != null) {
            cloned.level9 = cloned.level9.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        return cloned;
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

    /**
     * Finds the index of the last configured data grouping dimension level.
     * @return 0 - no data group levels are configured, 1 - only level_1 is configured, 2 - level_2 is configured (and maybe also level_1), ...
     */
    public int countConfiguredLevels() {
        int maxConfiguredLevel = 0;
        if (this.level1 != null) {
            maxConfiguredLevel++;
        }

        if (this.level2 != null) {
            maxConfiguredLevel++;
        }

        if (this.level3 != null) {
            maxConfiguredLevel++;
        }

        if (this.level4 != null) {
            maxConfiguredLevel++;
        }

        if (this.level5 != null) {
            maxConfiguredLevel++;
        }

        if (this.level6 != null) {
            maxConfiguredLevel++;
        }

        if (this.level7 != null) {
            maxConfiguredLevel++;
        }

        if (this.level8 != null) {
            maxConfiguredLevel++;
        }

        if (this.level9 != null) {
            maxConfiguredLevel++;
        }

        return maxConfiguredLevel;
    }

    /**
     * Retrieves a data group hierarchy level at a given index.
     * @param levelIndex Hierarchy level index in the rage 1..9.
     * @return Data grouping dimension level's configuration.
     */
    public DataGroupingDimensionSpec getLevel(int levelIndex) {
        switch (levelIndex) {
            case 1:
                return this.level1;
            case 2:
                return this.level2;
            case 3:
                return this.level3;
            case 4:
                return this.level4;
            case 5:
                return this.level5;
            case 6:
                return this.level6;
            case 7:
                return this.level7;
            case 8:
                return this.level8;
            case 9:
                return this.level9;
        }

        throw new IllegalArgumentException("Data group level out of range, must be 1..9, but was: " + levelIndex);
    }

    /**
     * Sets a configuration of the dimension level at a given index.
     * @param levelIndex 1-based index (up to 9) for the level to update.
     * @param levelSpec The configuration of the dimension level to store (or null).
     */
    public void setLevel(int levelIndex, DataGroupingDimensionSpec levelSpec) {
        switch (levelIndex) {
            case 1:
                this.setLevel1(levelSpec);
                return;
            case 2:
                this.setLevel2(levelSpec);
                return;
            case 3:
                this.setLevel3(levelSpec);
                return;
            case 4:
                this.setLevel4(levelSpec);
                return;
            case 5:
                this.setLevel5(levelSpec);
                return;
            case 6:
                this.setLevel6(levelSpec);
                return;
            case 7:
                this.setLevel7(levelSpec);
                return;
            case 8:
                this.setLevel8(levelSpec);
                return;
            case 9:
                this.setLevel9(levelSpec);
                return;
        }

        throw new IllegalArgumentException("Data group level out of range, must be 1..9, but was: " + levelIndex);
    }

    /**
     * Creates a truncated data grouping mapping to be used by the Jinja2 renderer. Contains only data grouping dimension level configuration that are valid and reference columns.
     * @return Cloned data grouping mapping with only valid entries.
     */
    public DataGroupingConfigurationSpec truncateToColumns() {
        DataGroupingConfigurationSpec truncated = new DataGroupingConfigurationSpec();

        if (this.level1 != null) {
            truncated.setLevel1(this.level1.truncateForSqlRendering());
        }

        if (this.level2 != null) {
            truncated.setLevel2(this.level2.truncateForSqlRendering());
        }

        if (this.level3 != null) {
            truncated.setLevel3(this.level3.truncateForSqlRendering());
        }

        if (this.level4 != null) {
            truncated.setLevel4(this.level4.truncateForSqlRendering());
        }

        if (this.level5 != null) {
            truncated.setLevel5(this.level5.truncateForSqlRendering());
        }

        if (this.level6 != null) {
            truncated.setLevel6(this.level6.truncateForSqlRendering());
        }

        if (this.level7!= null) {
            truncated.setLevel7(this.level7.truncateForSqlRendering());
        }

        if (this.level8 != null) {
            truncated.setLevel8(this.level8.truncateForSqlRendering());
        }

        if (this.level9 != null) {
            truncated.setLevel9(this.level9.truncateForSqlRendering());
        }

        return truncated;
    }

    /**
     * Retrieves the name of the data grouping configuration.
     * @return The name of the data grouping configuration.
     */
    @JsonIgnore
    public String getDataGroupingConfigurationName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME;
        }

        return hierarchyId.getLast().toString();
    }
}
