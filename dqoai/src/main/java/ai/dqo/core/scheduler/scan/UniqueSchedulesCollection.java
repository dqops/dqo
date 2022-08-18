package ai.dqo.core.scheduler.scan;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;

import java.util.*;

/**
 * Collection (hash set) of unique schedule configurations that were detected for running the data quality checks.
 */
public class UniqueSchedulesCollection {
    private Set<RecurringScheduleSpec> uniqueSchedules = new LinkedHashSet<>();

    /**
     * Adds a recurring schedule to the collection. Only unique schedules are added.
     * @param recurringSchedule Recurring schedule specification that was found in the metadata.
     */
    public void add(RecurringScheduleSpec recurringSchedule) {
        if (!uniqueSchedules.contains(recurringSchedule)) {
            RecurringScheduleSpec detachedSchedule = recurringSchedule.clone();
            detachedSchedule.setHierarchyId(null);
            uniqueSchedules.add(detachedSchedule);
        }
    }

    /**
     * Returns a collection of unique schedules for iteration.
     * @return Unique schedules that were detected.
     */
    public Collection<RecurringScheduleSpec> getUniqueSchedules() {
        return Collections.unmodifiableCollection(this.uniqueSchedules);
    }

    /**
     * Returns a new collection of unique schedules that contains only schedules that are in the current collection, but are
     * not present in the other collection.
     * @param other Other collection to compare.
     * @return New instance of the schedule's collection, without common schedules.
     */
    public UniqueSchedulesCollection minus(UniqueSchedulesCollection other) {
        UniqueSchedulesCollection missingInMine = new UniqueSchedulesCollection();
        for (RecurringScheduleSpec mySchedule : uniqueSchedules) {
            if (other.uniqueSchedules.contains(mySchedule)) {
                missingInMine.add(mySchedule);
            }
        }

        return missingInMine;
    }

    /**
     * Adds all unique schedules from the given collection.
     * @param schedules Collection of non unique schedules.
     */
    public void addAll(Collection<RecurringScheduleSpec> schedules) {
        for (RecurringScheduleSpec recurringSchedule : schedules) {
            this.add(recurringSchedule);
        }
    }
}
