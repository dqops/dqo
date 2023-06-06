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
package ai.dqo.checks.column.recurring.schema;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import ai.dqo.checks.column.checkspecs.schema.ColumnSchemaTypeChangedCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSchemaMonthlyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSchemaMonthlyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_column_exists", o -> o.monthlyColumnExists);
            put("monthly_column_type_changed", o -> o.monthlyColumnTypeChanged);
        }
    };

    @JsonPropertyDescription("Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnSchemaColumnExistsCheckSpec monthlyColumnExists;

    @JsonPropertyDescription("Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.")
    private ColumnSchemaTypeChangedCheckSpec monthlyColumnTypeChanged;


    /**
     * Returns a column exists check specification.
     * @return Column exists check specification.
     */
    public ColumnSchemaColumnExistsCheckSpec getMonthlyColumnExists() {
        return monthlyColumnExists;
    }

    /**
     * Sets the column exists check specification.
     * @param monthlyColumnExists Column exists check specification.
     */
    public void setMonthlyColumnExists(ColumnSchemaColumnExistsCheckSpec monthlyColumnExists) {
        this.setDirtyIf(!Objects.equals(this.monthlyColumnExists, monthlyColumnExists));
        this.monthlyColumnExists = monthlyColumnExists;
        propagateHierarchyIdToField(monthlyColumnExists, "monthly_column_exists");
    }

    /**
     * Returns the check configuration that detects if the column type has changed.
     * @return Column type has changed.
     */
    public ColumnSchemaTypeChangedCheckSpec getMonthlyColumnTypeChanged() {
        return monthlyColumnTypeChanged;
    }

    /**
     * Sets the check that detects if the column type hash changed.
     * @param monthlyColumnTypeChanged Column type has changed check.
     */
    public void setMonthlyColumnTypeChanged(ColumnSchemaTypeChangedCheckSpec monthlyColumnTypeChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyColumnTypeChanged, monthlyColumnTypeChanged));
        this.monthlyColumnTypeChanged = monthlyColumnTypeChanged;
        propagateHierarchyIdToField(monthlyColumnTypeChanged, "monthly_column_type_changed");
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
}