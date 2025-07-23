/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.schedules;

import com.dqops.metadata.scheduling.CronScheduleSpec;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Collection (hash set) of unique schedule configurations that were detected for running the data quality checks.
 */
public class UniqueSchedulesCollection {
    private Set<CronScheduleSpec> uniqueSchedules = new LinkedHashSet<>();

    /**
     * Adds a monitoring schedule to the collection. Only unique schedules are added.
     * @param monitoringSchedule Monitoring schedule specification that was found in the metadata.
     */
    public void add(CronScheduleSpec monitoringSchedule) {
        if (!uniqueSchedules.contains(monitoringSchedule)) {
            CronScheduleSpec detachedSchedule = monitoringSchedule.deepClone();
            uniqueSchedules.add(detachedSchedule);
        }
    }

    /**
     * Returns a collection of unique schedules for iteration.
     * @return Unique schedules that were detected.
     */
    public Collection<CronScheduleSpec> getUniqueSchedules() {
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
        for (CronScheduleSpec mySchedule : uniqueSchedules) {
            if (!other.uniqueSchedules.contains(mySchedule)) {
                missingInMine.add(mySchedule);
            }
        }

        return missingInMine;
    }

    /**
     * Adds all unique schedules from the given collection.
     * @param schedules Collection of non unique schedules.
     */
    public void addAll(Collection<CronScheduleSpec> schedules) {
        for (CronScheduleSpec monitoringSchedule : schedules) {
            this.add(monitoringSchedule);
        }
    }

    /**
     * Checks if a given schedule is present.
     * @param schedule Schedule to match (using equals and hashcode).
     * @return True when this schedule is present, false when it is not.
     */
    public boolean contains(CronScheduleSpec schedule) {
        return this.uniqueSchedules.contains(schedule);
    }
}
