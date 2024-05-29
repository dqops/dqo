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

package com.dqops.sensors.column.accuracy;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.ReferencedTableParameters;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that calculates the percentage of the difference in average of a column in a table and average of a column of another table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec extends AbstractSensorParametersSpec
        implements ReferencedTableParameters {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyTotalAverageMatchPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, " +
            "for example landing_zone.customer_raw, or only a table name. When only a table name is used, " +
            "DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.")
    @SampleValues(values = { "landing_zone.customer_raw" })
    @RequiredField
    private String referencedTable;

    @JsonPropertyDescription("The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.")
    @SampleValues(values = { "customer_id" })
    @RequiredField
    private String referencedColumn;

    /**
     * Returns the table name.
     * @return referencedTable.
     */
    public String getReferencedTable() {
        return referencedTable;
    }

    /**
     * Sets the table name.
     * @param referencedTable table name.
     */
    public void setReferencedTable(String referencedTable) {
        this.setDirtyIf(!Objects.equals(this.referencedTable, referencedTable));
        this.referencedTable = referencedTable;
    }

    /**
     * Returns the column name.
     * @return referencedColumn.
     */
    public String getReferencedColumn() {
        return referencedColumn;
    }

    /**
     * Sets the column name.
     * @param referencedColumn column name.
     */
    public void setReferencedColumn(String referencedColumn) {
        this.setDirtyIf(!Objects.equals(this.referencedColumn, referencedColumn));
        this.referencedColumn = referencedColumn;
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
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return "column/accuracy/total_average_match_percent";
    }

    /**
     * Returns true if the sensor supports data streams. The default value is true.
     * @return True when the sensor supports data streams.
     */
    @JsonIgnore
    @Override
    public boolean getSupportsDataGrouping() {
        return false;
    }

    /**
     * Returns true if the sensor supports partitioned checks. The default value is true.
     * @return True when the sensor support partitioned checks.
     */
    @JsonIgnore
    @Override
    public boolean getSupportsPartitionedChecks() {
        return false;
    }
}
