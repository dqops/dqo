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

package ai.dqo.sensors.column.integrity;

import ai.dqo.metadata.fields.SampleValues;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that calculates the percentage of values that matches values in column of another table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.")
    @SampleValues(values = { "dim_customer" })
    private String foreignTable;

    @JsonPropertyDescription("This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.")
    @SampleValues(values = { "customer_id" })
    private String foreignColumn;

    /**
     * Returns the table name.
     * @return foreignTable.
     */
    public String getForeignTable() {
        return foreignTable;
    }

    /**
     * Sets the table name.
     * @param foreignTable table name.
     */
    public void setForeignTable(String foreignTable) {
        this.setDirtyIf(!Objects.equals(this.foreignTable, foreignTable));
        this.foreignTable = foreignTable;
    }

    /**
     * Returns the column name.
     * @return foreignColumn.
     */
    public String getForeignColumn() {
        return foreignColumn;
    }

    /**
     * Sets the column name.
     * @param foreignColumn column name.
     */
    public void setForeignColumn(String foreignColumn) {
        this.setDirtyIf(!Objects.equals(this.foreignColumn, foreignColumn));
        this.foreignColumn = foreignColumn;
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
        return "column/integrity/foreign_key_match_percent";
    }

}
