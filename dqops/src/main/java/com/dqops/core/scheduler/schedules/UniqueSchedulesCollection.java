/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.core.scheduler.schedules;

import com.dqops.metadata.scheduling.RecurringScheduleSpec;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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
            RecurringScheduleSpec detachedSchedule = recurringSchedule.deepClone();
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
    public void addAll(Collection<RecurringScheduleSpec> schedules) {
        for (RecurringScheduleSpec recurringSchedule : schedules) {
            this.add(recurringSchedule);
        }
    }

    /**
     * Checks if a given schedule is present.
     * @param schedule Schedule to match (using equals and hashcode).
     * @return True when this schedule is present, false when it is not.
     */
    public boolean contains(RecurringScheduleSpec schedule) {
        return this.uniqueSchedules.contains(schedule);
    }
}
