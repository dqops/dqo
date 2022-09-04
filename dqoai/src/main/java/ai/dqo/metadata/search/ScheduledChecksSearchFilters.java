/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;

/**
 * Hierarchy node search filters used to find all check nodes that match exactly a schedule (by a cron expression)
 * or have no schedule at all, so they are just collected.
 */
public class ScheduledChecksSearchFilters {
    private Boolean enabled = true;
    private RecurringScheduleSpec schedule;

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
    public RecurringScheduleSpec getSchedule() {
        return schedule;
    }

    /**
     * Sets a schedule template that must equal exactly.
     * @param schedule Expected schedule.
     */
    public void setSchedule(RecurringScheduleSpec schedule) {
        this.schedule = schedule;
    }
}
