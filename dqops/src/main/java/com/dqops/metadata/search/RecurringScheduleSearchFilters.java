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

/**
 * Search filters used to search for the recurring schedule specifications.
 */
public class RecurringScheduleSearchFilters {
    private Boolean scheduleEnabled = true;

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
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public RecurringScheduleSearchFiltersVisitor createSearchFilterVisitor() {
        return new RecurringScheduleSearchFiltersVisitor(this);
    }
}
