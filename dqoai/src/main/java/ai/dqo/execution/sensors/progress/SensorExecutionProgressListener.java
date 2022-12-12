package ai.dqo.execution.sensors.progress;

/**
 * Base interface for sensor execution progress listeners. Progress listeners that want to track the progress of executing sensors
 * must implement this interface.
 */
public interface SensorExecutionProgressListener {
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
