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
package ai.dqo.execution.statistics.progress;

import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

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
