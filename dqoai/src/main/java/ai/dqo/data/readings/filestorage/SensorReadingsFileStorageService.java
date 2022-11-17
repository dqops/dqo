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
package ai.dqo.data.readings.filestorage;

import ai.dqo.data.delta.ChangeDeltaMode;
import ai.dqo.metadata.sources.PhysicalTableName;
import tech.tablesaw.api.Table;

import java.time.LocalDate;

/**
 * Service that provides access to sensor results.
 */
public interface SensorReadingsFileStorageService {
    String PARQUET_FILE_NAME = "sensor_readout.0.parquet";

    /**
     * Returns a supported delta mode.
     * @return Delta mode that is supported.
     */
    ChangeDeltaMode getDeltaMode();

    /**
     * Saves sensor reading results for a connection, table and month.
     * @param data Data for the given month.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param month The date of the first date of the month.
     */
    void saveTableMonth(Table data, String connectionName, PhysicalTableName tableName, LocalDate month);

    /**
     * Reads data for a single month.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param month The date of the first date of the month.
     * @return Returns a dataset table with the results. Returns null if the data is not present (missing file).
     */
    Table loadForTableAndMonth(String connectionName, PhysicalTableName tableName, LocalDate month);

    /**
     * Loads readings that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more readings than expected, because it operates on full months.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param start Start date, that is truncated to the beginning of the first loaded month.
     * @param end End date, the whole month of the given date is loaded.
     * @return Table with results.
     */
    Table loadForTableAndMonthsRange(String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end);

    /**
     * Saves all months that cover the range between <code>start</code> and <code>end</code>.
     * @param table Table with full months for the given period.
     * @param connectionName Connection name.
     * @param tableName Table name.
     * @param start Start date (a first day of the month is best).
     * @param end End date (the first day of the month is enough because the full month until the last day is saved).
     */
    void saveTableInMonthsRange(Table table, String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end);
}
