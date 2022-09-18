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
		this.events.add(event);
    }

    /**
     * Called after data quality rules were executed for all rows of normalized sensor results.
     *
     * @param event Log event.
     */
    @Override
    public void onRulesExecuted(RulesExecutedEvent event) {
		this.events.add(event);
    }

    /**
     * Called before the sensor results are saved for later use (they may be used later for time series calculation).
     *
     * @param event Log event.
     */
    @Override
    public void onSavingSensorResults(SavingSensorResultsEvent event) {
		this.events.add(event);
    }

    /**
     * Called before rule evaluation results are saved.
     *
     * @param event Log event.
     */
    @Override
    public void onSavingRuleEvaluationResults(SavingRuleEvaluationResults event) {
		this.events.add(event);
    }

    /**
     * Called after all checks for a table were executed, rules evaluated and the results written which ends the check execution for that table (no more work).
     *
     * @param event Log event.
     */
    @Override
    public void onTableChecksProcessingFinished(TableChecksProcessingFinished event) {
		this.events.add(event);
    }

    /**
     * Called before SQL template is expanded (rendered).
     *
     * @param event Log event.
     */
    @Override
    public void onBeforeSqlTemplateRender(BeforeSqlTemplateRenderEvent event) {
		this.events.add(event);
    }

    /**
     * Called after an SQL template was rendered from a Jinja2 template.
     *
     * @param event Log event.
     */
    @Override
    public void onSqlTemplateRendered(SqlTemplateRenderedRenderedEvent event) {
		this.events.add(event);
    }

    /**
     * Called before a sensor SQL is executed on a connection.
     *
     * @param event Log event.
     */
    @Override
    public void onExecutingSqlOnConnection(ExecutingSqlOnConnectionEvent event) {
		this.events.add(event);
    }

    /**
     * Called after all data quality checks were executed.
     *
     * @param event Data quality check execution summary for one batch of checks.
     */
    @Override
    public void onCheckExecutionFinished(CheckExecutionFinishedEvent event) {
        this.events.add(event);
    }
}
