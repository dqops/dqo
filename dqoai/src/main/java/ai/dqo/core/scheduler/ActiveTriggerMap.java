package ai.dqo.core.scheduler;

import ai.dqo.core.scheduler.scan.DetectedUniqueSchedulesCollection;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import org.quartz.Trigger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maintains a dictionary of schedules to Quartz trigger mappings of active triggers.
 */
public class ActiveTriggerMap {
    private Map<RecurringScheduleSpec, Trigger> activeTriggers = new LinkedHashMap();

    /**
     * Retrieves the collection of active schedules.
     * @return Collection of active schedules.
     */
    public DetectedUniqueSchedulesCollection getCurrentSchedules() {
        DetectedUniqueSchedulesCollection currentSchedules = new DetectedUniqueSchedulesCollection();
        currentSchedules.addAll(activeTriggers.keySet());
        return currentSchedules;
    }


}
