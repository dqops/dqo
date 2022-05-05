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

/**
 * Interface implemented by a check execution progress listener that shows the progress of the sensor execution.
 */
public interface CheckExecutionProgressListener {
    /**
     * Called before checks are started on a target table.
     * @param event Log event.
     */
    void onExecuteChecksOnTableStart(ExecuteChecksOnTableStartEvent event);

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
     * Called before SQL template is expanded (rendered).
     * @param event Log event.
     */
    void onBeforeSqlTemplateRender(BeforeSqlTemplateRenderEvent event);

    /**
     * Called after an SQL template was rendered from a Jinja2 template.
     * @param event Log event.
     */
    void onSqlTemplateRendered(SqlTemplateRenderedRendered event);

    /**
     * Called before a sensor SQL is executed on a connection.
     * @param event Log event.
     */
    void onExecutingSqlOnConnection(ExecutingSqlOnConnectionEvent event);

}
