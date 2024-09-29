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
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.Set;

/**
 * Hierarchy node search filters used to find all check nodes that match exactly a schedule (by a cron expression)
 * or have no schedule at all, so they are just collected.
 */
public class ScheduledChecksSearchFilters {
    @JsonPropertyDescription("Boolean flag to search only for enabled rules or only disabled checks. The default value is *true*.")
    private Boolean enabled = true;

    private CronScheduleSpec schedule;

    private Set<CheckRunScheduleGroup> schedulingGroups;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public ScheduledChecksSearchFiltersVisitor createSearchFilterVisitor() {
        return new ScheduledChecksSearchFiltersVisitor(this);
    }

    /**
     * Sets the enabled search criteria. null - search is ignored on the enabled/disabled flags,
     * true - only enabled (not explicitly disabled) nodes are returned (disabled connection or table stops search for nested elements),
     * false - only nodes that are disabled are returned.
     * @return Enabled search flag.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets an enabled (disabled) search flag.
     * @param enabled Enabled search flag.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns a schedule that must be equal.
     * @return Schedule that must equal.
     */
    public CronScheduleSpec getSchedule() {
        return schedule;
    }

    /**
     * Sets a schedule template that must equal exactly.
     * @param schedule Expected schedule.
     */
    public void setSchedule(CronScheduleSpec schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns an optional collection of target scheduling groups (monitoring daily, monitoring monthly, profiling, ...) to filter check container nodes.
     * The schedule groups set can have a null value to return all checks or when the search root object is an abstract check that has an individual schedule.
     * @return Optional set of target scheduling groups (types of checks).
     */
    public Set<CheckRunScheduleGroup> getSchedulingGroups() {
        return schedulingGroups;
    }

    /**
     * Sets a reference to a collection (set) of target scheduling groups - types of checks to run.
     * @param schedulingGroups Collection of scheduling groups.
     */
    public void setSchedulingGroups(Set<CheckRunScheduleGroup> schedulingGroups) {
        this.schedulingGroups = schedulingGroups;
    }
}
