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
package ai.dqo.data.alerts.factory;

import ai.dqo.data.readings.factory.SensorReadingsTableFactory;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Factory that creates an empty tablesaw table for storing the rule evaluation results. The table schema is configured.
 */
@Component
public class RuleResultsTableFactoryImpl implements RuleResultsTableFactory {
    private final SensorReadingsTableFactory sensorReadingsTableFactory;

    /**
     * Default injection constructor.
     *
     * @param sensorReadingsTableFactory Sensor reading table factory, called to create shared columns.
     */
    @Autowired
    public RuleResultsTableFactoryImpl(SensorReadingsTableFactory sensorReadingsTableFactory) {
        this.sensorReadingsTableFactory = sensorReadingsTableFactory;
    }

    /**
     * Creates an empty normalized rule result (alerts) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty rule evaluation results (alerts) table.
     */
    public Table createEmptyRuleResultsTable(String tableName) {
        Table table = this.sensorReadingsTableFactory.createEmptySensorReadoutsTable(tableName);
        table.addColumns(
                IntColumn.create(RuleEvaluationResult.SEVERITY_COLUMN_NAME),
                BooleanColumn.create(RuleEvaluationResult.INCLUDE_IN_KPI_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.FATAL_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.FATAL_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.ERROR_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.ERROR_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.WARNING_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleEvaluationResult.WARNING_UPPER_BOUND_COLUMN_NAME)
        );

        return table;
    }
}
