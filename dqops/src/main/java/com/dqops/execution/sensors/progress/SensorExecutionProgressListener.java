/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.progress;

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
