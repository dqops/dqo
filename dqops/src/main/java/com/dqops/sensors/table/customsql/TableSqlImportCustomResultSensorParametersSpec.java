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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table level sensor that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 * in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 * a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableSqlImportCustomResultSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableSqlImportCustomResultSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("A custom SELECT statement that queries a logging table with custom results of data quality checks executed by the data pipeline. " +
            "The query must return a result column named *severity*. The values of the *severity* column must be: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue. " +
            "The query can return *actual_value* and *expected_value* results that are imported into DQOps data lake. " +
            "The query can use a {table_name} placeholder that is replaced with a table name for which the results are imported.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlDisplayHint(DisplayHint.textarea)
    @SampleValues(values = {
            "SELECT\n" +
            "  logs.my_actual_value as actual_value,\n" +
            "  logs.my_expected_value as expected_value,\n" +
            "  logs.error_severity as severity\n" +
            "FROM custom_data_quality_results as logs\n" +
            "WHERE logs.analyzed_schema_name = '{schema_name}' AND logs.analyzed_table_name = '{table_name}'"
    })

    @RequiredField
    private String sqlQuery;

    /**
     * Returns a custom SQL condition (expression) that is rendered inside a query.
     * @return SQL condition.
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * Sets a custom SQL expression that is a boolean condition. It is used in a sensor.
     * @param sqlQuery SQL condition.
     */
    public void setSqlQuery(String sqlQuery) {
        this.setDirtyIf(!Objects.equals(this.sqlQuery, sqlQuery));
        this.sqlQuery = sqlQuery;
    }

    /**
     * Returns true if the sensor supports data streams. The default value is true.
     *
     * @return True when the sensor supports data streams.
     */
    @Override
    @JsonIgnore
    public boolean getSupportsDataGrouping() {
        return false;
    }

    /**
     * Returns true if the sensor supports partitioned checks. The default value is true.
     *
     * @return True when the sensor support partitioned checks.
     */
    @Override
    @JsonIgnore
    public boolean getSupportsPartitionedChecks() {
        return true;
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
        return "table/custom_sql/import_custom_result";
    }
}
