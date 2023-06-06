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
package ai.dqo.data.errors.factory;

import ai.dqo.data.readouts.factory.SensorReadoutsTableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;

/**
 * Factory that creates an empty tablesaw table for storing the errors. The table schema is configured.
 */
@Component
public class ErrorsTableFactoryImpl implements ErrorsTableFactory {
    private SensorReadoutsTableFactory sensorReadoutsTableFactory;

    @Autowired
    public ErrorsTableFactoryImpl(SensorReadoutsTableFactory sensorReadoutsTableFactory) {
        this.sensorReadoutsTableFactory = sensorReadoutsTableFactory;
    }

    /**
     * Creates an empty normalized sensor execution errors table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty sensor execution errors table.
     */
    @Override
    public Table createEmptyErrorsTable(String tableName) {
        Table table = this.sensorReadoutsTableFactory.createEmptySensorReadoutsTable(tableName);
        table.addColumns(
                TextColumn.create(ErrorsColumnNames.READOUT_ID_COLUMN_NAME),
                TextColumn.create(ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME),
                TextColumn.create(ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME),
                DateTimeColumn.create(ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME)
        );

        return table;
    }
}
