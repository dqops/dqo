package ai.dqo.execution.statistics.progress;

import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.execution.sensors.progress.SensorExecutionProgressListener;

/**
 * Statistics collector execution progress listener interface. Implemented by progress listeners.
 */
public interface StatisticsCollectorExecutionProgressListener extends SensorExecutionProgressListener {
    /**
     * Returns the flag that says if the summary should be printed.
     * @return true when the summary will be printed, false otherwise.
     */
    boolean isShowSummary();

    /**
     * Sets the flag to show the summary.
     * @param showSummary Show summary (effective only when the mode is not silent).
     */
    void setShowSummary(boolean showSummary);

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
     * Called after all data collectors were executed.
     * @param event Data statistics collectors execution summary for one batch of checks.
     */
    void onCollectorsExecutionFinished(StatisticsCollectorExecutionFinishedEvent event);
}
