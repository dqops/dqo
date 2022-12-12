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
package ai.dqo.sensors.column.sql;

import ai.dqo.metadata.fields.ControlDisplayHint;
import ai.dqo.metadata.fields.DisplayHint;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.table.AbstractTableSensorParametersSpec;
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
public class ColumnSqlAggregatedExpressionSensorParametersSpec extends AbstractTableSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlAggregatedExpressionSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractTableSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ControlDisplayHint(DisplayHint.textarea)
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
        return "column/sql/sql_aggregated_expression";
    }
}
