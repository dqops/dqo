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
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Search filters used to search for the cron schedule specifications.
 */
public class CronScheduleSearchFilters {
    @JsonPropertyDescription("Boolean flag to search only for enabled schedules or only disabled schedules. The default value is *true*, which prevents searching for all schedules despite their enabled status.")
    private Boolean scheduleEnabled = true;

    @JsonPropertyDescription("The instance name of the local DQOps instance. It is used to return schedules only from connections that can run on any instance, or are limited to run on this instance.")
    private String localInstanceName;

    @JsonPropertyDescription("Disables scanning checks to find overwritten schedules inside checks.")
    private boolean ignoreChecks;

    @JsonPropertyDescription("Schedule group that identifies the schedule for a given type of checks (profiling, monitoring, partition).")
    private CheckRunScheduleGroup scheduleGroup;

    /**
     * Returns the filter for enabled only schedules.
     * @return Filter for the enabled or disabled status.
     */
    public Boolean getScheduleEnabled() {
        return scheduleEnabled;
    }

    /**
     * Sets the filter for only enabled or disabled schedules.
     * @param scheduleEnabled Not null value for a filter.
     */
    public void setScheduleEnabled(Boolean scheduleEnabled) {
        this.scheduleEnabled = scheduleEnabled;
    }

    /**
     * Returns the name of the current DQOps instance.
     * @return Current DQOps instance name.
     */
    public String getLocalInstanceName() {
        return localInstanceName;
    }

    /**
     * Sets the name of the local DQOps instance.
     * @param localInstanceName Local DQOps instance name.
     */
    public void setLocalInstanceName(String localInstanceName) {
        this.localInstanceName = localInstanceName;
    }

    /**
     * Return true when the finder service should skip finding schedules inside checks.
     * @return True when checks should be ignored.
     */
    public boolean isIgnoreChecks() {
        return ignoreChecks;
    }

    /**
     * Set a flag to ignore scanning checks.
     * @param ignoreChecks True when checks should not be scanned.
     */
    public void setIgnoreChecks(boolean ignoreChecks) {
        this.ignoreChecks = ignoreChecks;
    }

    /**
     * Return a desired schedule group, if only a given schedule group should be scanned to find only one type of schedules.
     * @return Schedule group to find.
     */
    public CheckRunScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     * Sets a filter to return schedules only for one group.
     * @param scheduleGroup Schedule group.
     */
    public void setScheduleGroup(CheckRunScheduleGroup scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public CronScheduleSearchFiltersVisitor createSearchFilterVisitor() {
        return new CronScheduleSearchFiltersVisitor(this);
    }
}
