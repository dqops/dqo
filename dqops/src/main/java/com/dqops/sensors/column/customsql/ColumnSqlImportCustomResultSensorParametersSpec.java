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
package com.dqops.sensors.column.customsql;

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
 * Column level sensor that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check on a column by a custom
 * data quality check, hardcoded in the data pipeline. The result is retrieved by querying a separate **logging table**, whose schema is not fixed.
 * The logging table should have columns that identify a table and a column for which they store custom data quality check results, and a *severity* column of the data quality issue.
 * The SQL query that is configured in this external data quality results importer must be
 * a complete SELECT statement that queries a dedicated logging table, created by the data engineering team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSqlImportCustomResultSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlImportCustomResultSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("A custom SELECT statement that queries a logging table with custom results of data quality checks executed by the data pipeline. " +
            "The query must return a result column named *severity*. The values of the *severity* column must be: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue. " +
            "The query can return *actual_value* and *expected_value* results that are imported into DQOps data lake. " +
            "The query can use a {table_name} placeholder that is replaced with a table name for which the results are imported, and a {column_name} placeholder replaced with the column name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlDisplayHint(DisplayHint.textarea)
    @SampleValues(values = {
            "SELECT\n" +
            "  logs.my_actual_value as actual_value,\n" +
            "  logs.my_expected_value as expected_value,\n" +
            "  logs.error_severity as severity\n" +
            "FROM custom_data_quality_results as logs\n" +
            "WHERE logs.analyzed_schema_name = '{schema_name}' AND\n" +
            "      logs.analyzed_table_name = '{table_name}' AND\n" +
            "      logs.analyzed_column_name = '{column_name}'"
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
        return "column/custom_sql/import_custom_result";
    }
}
