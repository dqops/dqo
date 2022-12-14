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
package ai.dqo.data.ruleresults.factory;

import ai.dqo.data.readouts.factory.SensorReadoutsTableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;

/**
 * Factory that creates an empty tablesaw table for storing the rule evaluation results. The table schema is configured.
 */
@Component
public class RuleResultsTableFactoryImpl implements RuleResultsTableFactory {
    private final SensorReadoutsTableFactory sensorReadoutsTableFactory;

    /**
     * Default injection constructor.
     *
     * @param sensorReadoutsTableFactory Sensor readouts table factory, called to create shared columns.
     */
    @Autowired
    public RuleResultsTableFactoryImpl(SensorReadoutsTableFactory sensorReadoutsTableFactory) {
        this.sensorReadoutsTableFactory = sensorReadoutsTableFactory;
    }

    /**
     * Creates an empty normalized rule result (alerts) table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty rule evaluation results (alerts) table.
     */
    public Table createEmptyRuleResultsTable(String tableName) {
        Table table = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable(tableName);
        table.addColumns(
                IntColumn.create(RuleResultsColumnNames.SEVERITY_COLUMN_NAME),
                BooleanColumn.create(RuleResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME),
                BooleanColumn.create(RuleResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME),
                DoubleColumn.create(RuleResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME)
        );

        return table;
    }
}
