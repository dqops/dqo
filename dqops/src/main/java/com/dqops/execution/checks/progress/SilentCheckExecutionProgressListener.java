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

import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.execution.sensors.progress.*;
import com.dqops.utils.serialization.JsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Checks execution context progress listener that is silent and is not producing any messages to the console.
 * Just ignores all messages.
 */
public class SilentCheckExecutionProgressListener implements CheckExecutionProgressListener {
    protected final TerminalWriter terminalWriter;
    protected final JsonSerializer jsonSerializer;
    protected final Object lock = new Object();
    private boolean showSummary = true;

    /**
     * Default constructor.
     */
    public SilentCheckExecutionProgressListener() {
        this(null, null);
    }

    /**
     * Creates a CLI progress listener using a terminal writer to print out the results.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public SilentCheckExecutionProgressListener(TerminalWriter terminalWriter, JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Renders the header before an event.
     */
    public void renderEventHeader() {
        this.terminalWriter.writeLine(StringUtils.repeat('*', 50));
    }

    /**
     * Renders the footer after an event.
     */
    public void renderEventFooter() {
        this.terminalWriter.writeLine(StringUtils.repeat('*', 50));
        this.terminalWriter.writeLine("");
    }

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

    }

    /**
     * Called before a sensor is preparing for execution for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onPreparingSensor(PreparingSensorEvent event) {

    }

    /**
     * Called before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSensor(ExecutingSensorEvent event) {

    }

    /**
     * Called after a sensor was executed and returned raw (not normalized) results.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorExecuted(SensorExecutedEvent event) {

    }

    /**
     * Called after a sensor was executed but failed.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorFailed(SensorFailedEvent event) {

    }

    /**
     * Called after sensor results returned from the sensor were normalized to a standard tabular format.
     *
     * @param event Log event.
     */
    @Override
    public void onSensorResultsNormalized(SensorResultsNormalizedEvent event) {

    }

    /**
     * Called after data quality rules were executed for all rows of normalized sensor results.
     *
     * @param event Log event.
     */
    @Override
    public void onRuleExecuted(RuleExecutedEvent event) {

    }

    /**
     * Called after data quality rule were executed for all rows of normalized sensor results, but the rule failed with an exception.
     *
     * @param event Log event.
     */
    @Override
    public void onRuleFailed(RuleFailedEvent event) {

    }

    /**
     * Called before the sensor results are saved for later use (they may be used later for time series calculation).
     *
     * @param event Log event.
     */
    @Override
    public void onSavingSensorResults(SavingSensorResultsEvent event) {

    }

    /**
     * Called before rule evaluation results are saved.
     *
     * @param event Log event.
     */
    @Override
    public void onSavingRuleEvaluationResults(SavingRuleEvaluationResultsEvent event) {

    }

    /**
     * Called before errors are saved.
     *
     * @param event Log event.
     */
    @Override
    public void onSavingErrors(SavingErrorsEvent event) {

    }

    /**
     * Called after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
     *
     * @param event Log event.
     */
    @Override
    public void onTableChecksProcessingFinished(TableChecksProcessingFinishedEvent event) {

    }

    /**
     * Called before SQL template is expanded (rendered).
     *
     * @param event Log event.
     */
    @Override
    public void onBeforeSqlTemplateRender(BeforeSqlTemplateRenderEvent event) {

    }

    /**
     * Called after an SQL template was rendered from a Jinja2 template.
     *
     * @param event Log event.
     */
    @Override
    public void onSqlTemplateRendered(SqlTemplateRenderedRenderedEvent event) {

    }

    /**
     * Called before a sensor SQL is executed on a connection.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSqlOnConnection(ExecutingSqlOnConnectionEvent event) {

    }

    /**
     * Called after all data quality checks were executed.
     *
     * @param event Data quality check execution summary for one batch of checks.
     */
    @Override
    public void onCheckExecutionFinished(CheckExecutionFinishedEvent event) {

    }
}
