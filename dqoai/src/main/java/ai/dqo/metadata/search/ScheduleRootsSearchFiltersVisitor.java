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

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;

import java.util.Objects;

/**
 * Visitor for {@link ScheduleRootsSearchFilters} that finds any node (connection, table, column, check) that has a given filter configured.
 */
public class ScheduleRootsSearchFiltersVisitor extends AbstractSearchVisitor<FoundResultsCollector<ScheduleRootResult>> {
    private final ScheduleRootsSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public ScheduleRootsSearchFiltersVisitor(ScheduleRootsSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param foundNodes        Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        RecurringSchedulesSpec schedules = connectionSpec.getSchedules();
        assert this.filters.getSchedule() != null;
        assert this.filters.getSchedule() != null;

        if (schedules != null) {
            if (Objects.equals(schedules.getProfiling(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.profiling, connectionWrapper));
            }

            if (Objects.equals(schedules.getRecurringDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.recurring_daily, connectionWrapper));
            }

            if (Objects.equals(schedules.getRecurringMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.recurring_monthly, connectionWrapper));
            }

            if (Objects.equals(schedules.getPartitionedDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.partitioned_daily, connectionWrapper));
            }

            if (Objects.equals(schedules.getPartitionedMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.partitioned_monthly, connectionWrapper));
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec  Table specification.
     * @param foundNodes Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            boolean tableIsEnabled = !tableSpec.isDisabled();
            if (enabledFilter != tableIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        RecurringSchedulesSpec schedulesOverride = tableSpec.getSchedulesOverride();
        assert this.filters.getSchedule() != null;

        if (schedulesOverride != null) {
            if (Objects.equals(schedulesOverride.getProfiling(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.profiling, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getRecurringDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.recurring_daily, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getRecurringMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.recurring_monthly, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getPartitionedDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.partitioned_daily, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getPartitionedMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunRecurringScheduleGroup.partitioned_monthly, tableSpec));
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param foundNodes        Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> abstractCheckSpec, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        RecurringScheduleSpec checkSchedule = abstractCheckSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (checkSchedule != null && !checkSchedule.isDefault()) {
            if (Objects.equals(checkSchedule, this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(null, abstractCheckSpec));
            }
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to traverse deeper
    }
}
