/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.table.customsql;

import com.dqops.metadata.fields.ControlDisplayHint;
import com.dqops.metadata.fields.DisplayHint;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table level sensor that uses a custom SQL query to count rows of invalid values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSqlInvalidRecordCountSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlInvalidRecordCountSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SQL query that returns invalid values. The condition is evaluated for each row. The expression can use a {table} placeholder that is replaced with a full table name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlDisplayHint(DisplayHint.textarea)
    @SampleValues(values = { "SELECT age AS actual_value\n" +
                             "FROM customers\n" +
                             "WHERE age < 18" })
    @RequiredField
    private String sqlQuery;

    /**
     * Returns a custom SQL query that is rendered inside a sensor template.
     * @return SQL condition.
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * Sets a custom SQL query. It is used in a sensor.
     * @param sqlQuery SQL condition.
     */
    public void setSqlQuery(String sqlQuery) {
        this.setDirtyIf(!Objects.equals(this.sqlQuery, sqlQuery));
        this.sqlQuery = sqlQuery;
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
        return "table/custom_sql/sql_invalid_record_count";
    }
}
