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

import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Hierarchy node search filters to find nodes that have a configured schedule and match a cron expression.
 */
public class ScheduleRootsSearchFilters {
    @JsonPropertyDescription("Boolean flag to search only for enabled rules or only disabled scheduling root objects - objects that have their own CRON schedule defined. The default value is *true*.")
    private Boolean enabled = true;

    private MonitoringScheduleSpec schedule;

    public ScheduleRootsSearchFilters() {
    }

    public ScheduleRootsSearchFilters(Boolean enabled, MonitoringScheduleSpec schedule) {
        this.enabled = enabled;
        this.schedule = schedule;
    }

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public ScheduleRootsSearchFiltersVisitor createSearchFilterVisitor() {
        return new ScheduleRootsSearchFiltersVisitor(this);
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
     * Returns a schedule settings (cron expression, etc.) that must match.
     * @return Schedule configuration with a time zone on the connection.
     */
    public MonitoringScheduleSpec getSchedule() {
        return schedule;
    }

    /**
     * Sets a schedule (with connection's time zone) that must match.
     * @param schedule Schedule.
     */
    public void setSchedule(MonitoringScheduleSpec schedule) {
        this.schedule = schedule;
    }
}
