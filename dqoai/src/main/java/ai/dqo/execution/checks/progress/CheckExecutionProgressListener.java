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
package ai.dqo.execution.checks.progress;

import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Interface implemented by a check execution progress listener that shows the progress of the sensor execution.
 */
public interface CheckExecutionProgressListener extends SensorExecutionProgressListener {
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
     * Called before checks are started on a target table.
     * @param event Log event.
     */
    void onExecuteChecksOnTableStart(ExecuteChecksOnTableStartEvent event);

    /**
     * Called after sensor results returned from the sensor were normalized to a standard tabular format.
     * @param event Log event.
     */
    void onSensorResultsNormalized(SensorResultsNormalizedEvent event);

    /**
     * Called after data quality rules were executed for all rows of normalized sensor results.
     * @param event Log event.
     */
    void onRulesExecuted(RulesExecutedEvent event);

    /**
     * Called before the sensor results are saved for later use (they may be used later for time series calculation).
     * @param event Log event.
     */
    void onSavingSensorResults(SavingSensorResultsEvent event);

    /**
     * Called before rule evaluation results are saved.
     * @param event Log event.
     */
    void onSavingRuleEvaluationResults(SavingRuleEvaluationResults event);

    /**
     * Called after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
     * @param event Log event.
     */
    void onTableChecksProcessingFinished(TableChecksProcessingFinished event);

    /**
     * Called after all data quality checks were executed.
     * @param event Data quality check execution summary for one batch of checks.
     */
    void onCheckExecutionFinished(CheckExecutionFinishedEvent event);
}
