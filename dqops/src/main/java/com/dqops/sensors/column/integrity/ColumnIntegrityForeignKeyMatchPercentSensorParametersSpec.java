/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.sensors.column.integrity;

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
 * Column level sensor that calculates the percentage of values that match values in a column of another dictionary table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec extends AbstractSensorParametersSpec
        implements ReferencedTableParameters {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.")
    @SampleValues(values = { "public.dim_customer" })
    @RequiredField
    private String foreignTable;

    @JsonPropertyDescription("This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.")
    @SampleValues(values = { "customer_id" })
    @RequiredField
    private String foreignColumn;

    /**
     * Returns the table name.
     * @return foreignTable.
     */
    public String getForeignTable() {
        return foreignTable;
    }

    /**
     * Alias on getForeignTable method for ReferencedTableParameters interface implementation
     */
    @JsonIgnore
    public String getReferencedTable(){ return getForeignTable(); }

    /**
     * Sets the table name.
     * @param foreignTable table name.
     */
    public void setForeignTable(String foreignTable) {
        this.setDirtyIf(!Objects.equals(this.foreignTable, foreignTable));
        this.foreignTable = foreignTable;
    }

    /**
     * Alias on setForeignTable method for ReferencedTableParameters interface implementation
     * @param referencedTable table name.
     */
    @JsonIgnore
    public void setReferencedTable(String referencedTable){ setForeignTable(referencedTable); }

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
