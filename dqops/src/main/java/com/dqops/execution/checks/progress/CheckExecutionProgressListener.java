/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;

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
     * Called after data quality rule was executed for all rows of normalized sensor results.
     * @param event Log event.
     */
    void onRuleExecuted(RuleExecutedEvent event);

    /**
     * Called after data quality rule were executed for all rows of normalized sensor results, but the rule failed with an exception.
     * @param event Log event.
     */
    void onRuleFailed(RuleFailedEvent event);

    /**
     * Called before the sensor results are saved for later use (they may be used later for time series calculation).
     * @param event Log event.
     */
    void onSavingSensorResults(SavingSensorResultsEvent event);

    /**
     * Called before rule evaluation results are saved.
     * @param event Log event.
     */
    void onSavingRuleEvaluationResults(SavingRuleEvaluationResultsEvent event);

    /**
     * Called before errors are saved.
     * @param event Log event.
     */
    void onSavingErrors(SavingErrorsEvent event);

    /**
     * Called after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
     * @param event Log event.
     */
    void onTableChecksProcessingFinished(TableChecksProcessingFinishedEvent event);

    /**
     * Called after all data quality checks were executed.
     * @param event Data quality check execution summary for one batch of checks.
     */
    void onCheckExecutionFinished(CheckExecutionFinishedEvent event);
}
