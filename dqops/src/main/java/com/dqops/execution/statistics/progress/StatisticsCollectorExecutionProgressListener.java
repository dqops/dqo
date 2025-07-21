/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.progress;

import com.dqops.execution.sensors.progress.ExecutingSensorEvent;
import com.dqops.execution.sensors.progress.SensorExecutedEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Statistics collector execution progress listener interface. Implemented by progress listeners.
 */
public interface StatisticsCollectorExecutionProgressListener extends SensorExecutionProgressListener {
    /**
     * Returns the flag that says if the summary should be printed.
     * @return true when the summary will be printed, false otherwise.
     */
    boolean isShowSummary();

    /**
     * Sets the flag to show the summary.
     * @param showSummary Show summary (effective only when the mode is not silent).
     */
    void setShowSummary(boolean showSummary);

    /**
     * Called before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     * @param event Log event.
     */
    void onExecutingSensor(ExecutingSensorEvent event);

    /**
     * Called after a sensor was executed and returned raw (not normalized) results.
     * @param event Log event.
     */
    void onSensorExecuted(SensorExecutedEvent event);

    /**
     * Called when collecting statistics on a table is started, but before any sensors are executed.
     * @param event Statistics collector on table event with the table and a list of collectors to execute.
     */
    void onExecuteStatisticsCollectorsOnTableStart(ExecuteStatisticsCollectorsOnTableStartEvent event);

    /**
     * Called before the statistics results are saved to parquet files.
     * @param event Log event with the results to be saved for a table.
     */
    void onSavingStatisticsResults(SavingStatisticsResultsEvent event);

    /**
     * Called after basic profiling statistics were collected on a table.
     * @param event Log event with the summary of the statistics collectors that were processed.
     */
    void onTableStatisticsCollectionFinished(ExecuteStatisticsCollectorsOnTableFinishedEvent event);

    /**
     * Called after all data collectors were executed.
     * @param event Data statistics collectors execution summary for one batch of checks.
     */
    void onCollectorsExecutionFinished(StatisticsCollectorExecutionFinishedEvent event);
}
