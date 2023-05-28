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

import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.execution.sensors.progress.*;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Silent statistics collector execution listener.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SilentStatisticsCollectorExecutionProgressListener implements StatisticsCollectorExecutionProgressListener {
    protected final TerminalWriter terminalWriter;
    protected final JsonSerializer jsonSerializer;
    private boolean showSummary = true;

    /**
     * Default constructor.
     */
    public SilentStatisticsCollectorExecutionProgressListener() {
        this(null, null);
    }

    @Autowired
    public SilentStatisticsCollectorExecutionProgressListener(TerminalWriter terminalWriter, JsonSerializer jsonSerializer) {
        this.terminalWriter = terminalWriter;
        this.jsonSerializer = jsonSerializer;
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
     * Called after all data collectors were executed.
     *
     * @param event Data statistics collectors execution summary for one batch of checks.
     */
    @Override
    public void onCollectorsExecutionFinished(StatisticsCollectorExecutionFinishedEvent event) {

    }
}
