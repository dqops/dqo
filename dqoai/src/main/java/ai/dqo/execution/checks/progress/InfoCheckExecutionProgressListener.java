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

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.progress.*;
import ai.dqo.utils.serialization.JsonSerializer;
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
        renderEventHeader();
        String connectionName = event.getConnectionWrapper().getName();
        String tableName = event.getTargetTable().getPhysicalTableName().toString();
        this.terminalWriter.writeLine(String.format("Executing data quality checks on table %s from connection %s", tableName, connectionName));
        renderEventFooter();
    }

    /**
     * Called before a sensor is executed for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSensor(ExecutingSensorEvent event) {
        renderEventHeader();
        String tableName = event.getTableSpec().getPhysicalTableName().toString();
        SensorExecutionRunParameters sensorRunParameters = event.getSensorRunParameters();
        String checkName = sensorRunParameters.getCheck().getCheckName();
        String sensorDefinitionName = sensorRunParameters.getSensorParameters().getSensorDefinitionName(
                sensorRunParameters.getCheck(), sensorRunParameters.getProfiler());
        this.terminalWriter.writeLine(String.format("Executing a sensor for a check %s on the table %s using a sensor definition %s",
                checkName, tableName, sensorDefinitionName));
        renderEventFooter();
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
        renderEventHeader();
        String tableName = event.getTableSpec().getPhysicalTableName().toString();
        String checkName = event.getSensorRunParameters().getCheck().getCheckName();
        Table ruleResultsTable = event.getRuleEvaluationResult().getRuleResultsTable();
        int evaluatedRulesCount = ruleResultsTable.rowCount();
        this.terminalWriter.writeLine(String.format("Finished executing rules (thresholds) for a check %s on the table %s, verified rules count: %d",
                checkName, tableName, evaluatedRulesCount));

        renderEventFooter();
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
