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
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;

/**
 * Metadata node search visitor that is searching for all unique recurring schedules.
 */
public class RecurringScheduleSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private RecurringScheduleSearchFilters filters;

    /**
     * Creates a visitor given the search filters.
     * @param filters Search filters.
     */
    public RecurringScheduleSearchFiltersVisitor(RecurringScheduleSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a recurring schedule specification, it is the cron expression how to schedule the job.
     *
     * @param recurringScheduleSpec Recurring schedule.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RecurringScheduleSpec recurringScheduleSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getScheduleEnabled();
        if (enabledFilter != null) {
            boolean mustBeDisabled = !enabledFilter;
            if (recurringScheduleSpec.isDisabled() != mustBeDisabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN; // no children possible
            }
        }

        parameter.getNodes().add(recurringScheduleSpec);
        return TreeNodeTraversalResult.SKIP_CHILDREN; // no children possible
    }
}
