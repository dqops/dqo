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
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.progress.*;
import com.dqops.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import tech.tablesaw.api.Table;

/**
 * Check execution progress listener that is reporting all operations to the console, performing a debug mode reporting.
 */
public class InfoCheckExecutionProgressListener extends SummaryCheckExecutionProgressListener {
    /**
     * Creates a CLI progress listener using a terminal writer to print out the results.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public InfoCheckExecutionProgressListener(TerminalWriter terminalWriter, JsonSerializer jsonSerializer) {
        super(terminalWriter, jsonSerializer);
    }

    /**
     * Called before checks are started on a target table.
     *
     * @param event Log event.
     */
    @Override
    public void onExecuteChecksOnTableStart(ExecuteChecksOnTableStartEvent event) {
        synchronized (this.lock) {
            renderEventHeader();
            String connectionName = event.getConnectionWrapper().getName();
            String tableName = event.getTargetTable().getPhysicalTableName().toString();
            this.terminalWriter.writeLine(String.format("Executing data quality checks on table %s from connection %s", tableName, connectionName));
            renderEventFooter();
        }
    }

    /**
     * Called before a sensor is preparing for execution for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onPreparingSensor(PreparingSensorEvent event) {
        synchronized (this.lock) {
            renderEventHeader();
            String tableName = event.getTableSpec().getPhysicalTableName().toString();
            SensorExecutionRunParameters sensorRunParameters = event.getSensorRunParameters();
            String checkName = sensorRunParameters.getCheck().getCheckName();
            String sensorDefinitionName = sensorRunParameters.getSensorParameters().getSensorDefinitionName();
            this.terminalWriter.writeLine(String.format("Preparing a sensor for a check %s on the table %s using a sensor definition %s",
                    checkName, tableName, sensorDefinitionName));
            renderEventFooter();
        }
    }

    /**
     * Called before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSensor(ExecutingSensorEvent event) {
        synchronized (this.lock) {
            renderEventHeader();
            String tableName = event.getTableSpec().getPhysicalTableName().toString();
            SensorExecutionRunParameters sensorRunParameters = event.getSensorPrepareResult().getSensorRunParameters();
            String checkName = sensorRunParameters.getCheck().getCheckName();
            String sensorDefinitionName = sensorRunParameters.getSensorParameters().getSensorDefinitionName();
            this.terminalWriter.writeLine(String.format("Executing a sensor for a check %s on the table %s using a sensor definition %s",
                    checkName, tableName, sensorDefinitionName));
            renderEventFooter();
        }
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
        synchronized (this.lock) {
            renderEventHeader();
            String tableName = event.getTableSpec().getPhysicalTableName().toString();
            String checkName = event.getSensorRunParameters().getCheck().getCheckName();
            Table ruleResultsTable = event.getRuleEvaluationResult().getRuleResultsTable();
            int evaluatedRulesCount = ruleResultsTable.rowCount();
            this.terminalWriter.writeLine(String.format("Finished executing rules (thresholds) for a check %s on the table %s, verified rules count: %d",
                    checkName, tableName, evaluatedRulesCount));

            renderEventFooter();
        }
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
}
