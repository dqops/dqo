/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.storage;

import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.data.checkresults.factory.CheckResultsTableFactoryImpl;
import com.dqops.data.errors.factory.ErrorsTableFactoryImpl;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactoryImpl;
import com.dqops.data.incidents.factory.IncidentsTableFactoryImpl;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactory;
import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import com.dqops.data.statistics.factory.StatisticsResultsTableFactoryImpl;
import com.dqops.utils.exceptions.DqoRuntimeException;
import tech.tablesaw.api.Table;

/**
 * Singleton class that returns singletons - the samples of parquet files.
 */
public class ParquetTableSchemaSamples {
    private static final Table SENSOR_READOUTS_SAMPLE = new SensorReadoutsTableFactoryImpl()
            .createEmptySensorReadoutsTable("sensor_readouts_sample");
    private static final Table CHECK_RESULTS_SAMPLE = new CheckResultsTableFactoryImpl(new SensorReadoutsTableFactoryImpl())
            .createEmptyCheckResultsTable("check_results_sample");
    private static final Table STATISTICS_SAMPLE = new StatisticsResultsTableFactoryImpl()
            .createEmptyStatisticsTable("statistics_sample");
    private static final Table ERRORS_SAMPLE = new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl())
            .createEmptyErrorsTable("errors_sample");
    private static final Table INCIDENTS_SAMPLE = new IncidentsTableFactoryImpl()
            .createEmptyIncidentsTable("incidents_sample");
    private static final Table ERROR_SAMPLES_SAMPLE = new ErrorSamplesTableFactoryImpl()
            .createEmptyErrorSamplesTable("error_samples_sample");

    /**
     * Retrieves a table sample for a given parquet table type. Returns an empty table with a proper schema.
     * @param tableType Table type.
     * @return Empty table with an expected schema.
     */
    public static Table getSampleTable(DqoRoot tableType) {
        switch (tableType) {
            case data_sensor_readouts:
                return SENSOR_READOUTS_SAMPLE;
            case  data_check_results:
                return CHECK_RESULTS_SAMPLE;
            case data_statistics:
                return STATISTICS_SAMPLE;
            case data_errors:
                return ERRORS_SAMPLE;
            case data_incidents:
                return INCIDENTS_SAMPLE;
            case data_error_samples:
                return ERROR_SAMPLES_SAMPLE;
            default:
                throw new DqoRuntimeException("No sample table for " + tableType);
        }
    }
}
