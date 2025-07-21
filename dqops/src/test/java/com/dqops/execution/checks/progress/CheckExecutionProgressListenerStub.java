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

import com.dqops.execution.sensors.progress.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub that captures all events that were reported.
 */
public class CheckExecutionProgressListenerStub implements CheckExecutionProgressListener {
    private final List<CheckExecutionProgressEvent> events = new ArrayList<>();
    private boolean showSummary;

    /**
     * Returns the flag that says if the summary should be printed.
     *
     * @return true when the summary will be printed, false otherwise.
     */
    @Override
    public boolean isShowSummary() {
        return this.showSummary;
    }

    /**
     * Sets the flag to show the summary.
     *
     * @param showSummary Show summary (effective only when the mode is not silent).
     */
    @Override
    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
    }

    /**
     * Called before checks are started on a target table.
     *
     * @param event Log event.
     */
    @Override
    public void onExecuteChecksOnTableStart(ExecuteChecksOnTableStartEvent event) {
		this.events.add(event);
    }

    /**
     * Called before a sensor is preparing for execution for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onPreparingSensor(PreparingSensorEvent event) {
        this.events.add(event);
    }

    /**
     * Called before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSensor(ExecutingSensorEvent event) {
		this.events.add(event);
    }

    /**
     * Called after a sensor was executed and returned raw (not normalized) results.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorExecuted(SensorExecutedEvent event) {
		this.events.add(event);
    }

    /**
     * Called after sensor results returned from the sensor were normalized to a standard tabular format.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorResultsNormalized(SensorResultsNormalizedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after data quality rules were executed for all rows of normalized sensor results.
     *
     * @param event Log event.
     */
    @Override
    public void onRuleExecuted(RuleExecutedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after data quality rule were executed for all rows of normalized sensor results, but the rule failed with an exception.
     *
     * @param event Log event.
     */
    @Override
    public void onRuleFailed(RuleFailedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after a sensor was executed but failed.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorFailed(SensorFailedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called before the sensor results are saved for later use (they may be used later for time series calculation).
     *
     * @param event Log event.
     */
    @Override
    public void onSavingSensorResults(SavingSensorResultsEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called before rule evaluation results are saved.
     *
     * @param event Log event.
     */
    @Override
    public void onSavingRuleEvaluationResults(SavingRuleEvaluationResultsEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called before errors are saved.
     *
     * @param event Log event.
     */
    @Override
    public void onSavingErrors(SavingErrorsEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
     *
     * @param event Log event.
     */
    @Override
    public void onTableChecksProcessingFinished(TableChecksProcessingFinishedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called before SQL template is expanded (rendered).
     *
     * @param event Log event.
     */
    @Override
    public void onBeforeSqlTemplateRender(BeforeSqlTemplateRenderEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after an SQL template was rendered from a Jinja2 template.
     *
     * @param event Log event.
     */
    @Override
    public void onSqlTemplateRendered(SqlTemplateRenderedRenderedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called before a sensor SQL is executed on a connection.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSqlOnConnection(ExecutingSqlOnConnectionEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }

    /**
     * Called after all data quality checks were executed.
     *
     * @param event Data quality check execution summary for one batch of checks.
     */
    @Override
    public void onCheckExecutionFinished(CheckExecutionFinishedEvent event) {
        synchronized (this) {
            this.events.add(event);
        }
    }
}
