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
package ai.dqo.execution.sensors.progress;

/**
 * Base interface for sensor execution progress listeners. Progress listeners that want to track the progress of executing sensors
 * must implement this interface.
 */
public interface SensorExecutionProgressListener {
    /**
     * Called before a sensor is preparing for execution for a single check. The check (and sensor) is identified in the <code>sensorRunParameters</code>.
     * @param event Log event.
     */
    void onPreparingSensor(PreparingSensorEvent event);

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
     * Called after a sensor was executed but failed.
     * @param event Log event.
     */
    void onSensorFailed(SensorFailedEvent event);

    /**
     * Called before SQL template is expanded (rendered).
     * @param event Log event.
     */
    void onBeforeSqlTemplateRender(BeforeSqlTemplateRenderEvent event);

    /**
     * Called after an SQL template was rendered from a Jinja2 template.
     * @param event Log event.
     */
    void onSqlTemplateRendered(SqlTemplateRenderedRenderedEvent event);

    /**
     * Called before a sensor SQL is executed on a connection.
     * @param event Log event.
     */
    void onExecutingSqlOnConnection(ExecutingSqlOnConnectionEvent event);
}
