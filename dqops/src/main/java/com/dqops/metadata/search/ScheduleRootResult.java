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
package com.dqops.metadata.search;

import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.SchedulingRootNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Result object returned by the schedule root search operation (to find nodes that have a cron schedule that is applicable to all nodes inside).
 * This object is a pair of the node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec})
 * and a list of scheduling group types (profiling, monitoring daily, monitoring monthly, etc).
 */
public class ScheduleRootResult {
    private Set<CheckRunScheduleGroup> schedulingGroups = new HashSet<>();
    private SchedulingRootNode scheduleRootNode;

    /**
     * Creates a schedule root find result.
     * @param scheduleRootNode Metadata root node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public ScheduleRootResult(SchedulingRootNode scheduleRootNode) {
        this.scheduleRootNode = scheduleRootNode;
    }

    /**
     * Returns the metadata node do be scheduled.
     * @return Metadata root node ({@link com.dqops.metadata.sources.ConnectionSpec}, {@link com.dqops.metadata.sources.TableSpec} or {@link com.dqops.checks.AbstractCheckSpec}) that has a schedule defined.
     */
    public SchedulingRootNode getScheduleRootNode() {
        return scheduleRootNode;
    }

    /**
     * Adds a schedule group to the list of groups.
     * @param scheduleGroup Schedule group.
     */
    public void addSchedulingGroup(CheckRunScheduleGroup scheduleGroup) {
        this.schedulingGroups.add(scheduleGroup);
    }

    /**
     * Returns a set of configured schedule groups. Returns null if the list is empty, which means that filtering by target schedule group is disabled.
     * @return A set of configured schedule groups.
     */
    public Set<CheckRunScheduleGroup> getSchedulingGroups() {
        return this.schedulingGroups.isEmpty() ? null : this.schedulingGroups;
    }

    /**
     * Looks up the list of scheduling groups. Returns true if any group is added (any type of check will be run).
     * @return True - some scheduling groups were added, false - none added.
     */
    public boolean hasAnySchedulingGroups() {
        return !this.schedulingGroups.isEmpty();
    }
}
