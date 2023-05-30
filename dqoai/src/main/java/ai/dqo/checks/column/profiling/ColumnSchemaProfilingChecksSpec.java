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
package ai.dqo.checks.column.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.bool.ColumnFalsePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.schema.ColumnSchemaColumnExistsCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking the column schema.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSchemaProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSchemaProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("column_exists", o -> o.columnExists);
        }
    };

    @JsonPropertyDescription("Checks the metadata of the monitored table and verifies if the column exists.")
    private ColumnSchemaColumnExistsCheckSpec columnExists;


    /**
     * Returns a column exists check specification.
     * @return Column exists check specification.
     */
    public ColumnSchemaColumnExistsCheckSpec getColumnExists() {
        return columnExists;
    }

    /**
     * Sets the column exists check specification.
     * @param columnExists Column exists check specification.
     */
    public void setColumnExists(ColumnSchemaColumnExistsCheckSpec columnExists) {
        this.setDirtyIf(!Objects.equals(this.columnExists, columnExists));
        this.columnExists = columnExists;
        propagateHierarchyIdToField(columnExists, "column_exists");
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