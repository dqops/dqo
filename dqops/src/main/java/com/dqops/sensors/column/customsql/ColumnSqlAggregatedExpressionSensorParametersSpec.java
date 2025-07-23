/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.customsql;

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
 * Column level sensor that executes a given SQL expression on a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSqlAggregatedExpressionSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlAggregatedExpressionSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or within a GROUP BY clause for daily partitions and/or data groups. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlDisplayHint(DisplayHint.textarea)
    @SampleValues(values = { "MAX({column})" })
    @RequiredField
    private String sqlExpression;

    /**
     * Returns a custom SQL expression that is rendered inside a query.
     * @return SQL Expression.
     */
    public String getSqlExpression() {
        return sqlExpression;
    }

    /**
     * Sets a custom SQL expression that is used in a sensor.
     * @param sqlExpression SQL expression.
     */
    public void setSqlExpression(String sqlExpression) {
        this.setDirtyIf(!Objects.equals(this.sqlExpression, sqlExpression));
        this.sqlExpression = sqlExpression;
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
        return "column/custom_sql/sql_aggregated_expression";
    }
}
