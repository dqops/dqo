/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.errorsampling.progress;

import com.dqops.execution.sensors.progress.ExecutingSensorEvent;
import com.dqops.execution.sensors.progress.SensorExecutedEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Error samples collector execution progress listener interface. Implemented by progress listeners.
 */
public interface ErrorSamplerExecutionProgressListener extends SensorExecutionProgressListener {
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
     * Called when an error sampler on a table is started, but before any sensors are executed.
     * @param event Table error sampler event with the table and a list of checks to execute.
     */
    void onTableErrorSamplersStart(ExecuteErrorSamplerOnTableStartEvent event);

    /**
     * Called before the error samples results are saved to parquet files.
     * @param event Log event with the results to be saved for a table.
     */
    void onSavingErrorSamplesResults(SavingErrorSamplesResultsEvent event);

    /**
     * Called after error samples were collected on a table.
     * @param event Log event with the summary of the error samplers that were processed.
     */
    void onTableErrorSamplesFinished(ExecuteErrorSamplerOnTableFinishedEvent event);

    /**
     * Called after all error samplers were executed.
     * @param event Error sampler execution summary for one batch of checks.
     */
    void onErrorSamplersExecutionFinished(ErrorSamplersExecutionFinishedEvent event);
}
