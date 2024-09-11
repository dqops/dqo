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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.metadata.policies.column.ColumnDefaultChecksPatternList;
import com.dqops.metadata.policies.table.TableDefaultChecksPatternList;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;

import java.util.Objects;

/**
 * Visitor for {@link ScheduleRootsSearchFilters} that finds any node (connection, table, column, check) that has a given filter configured.
 */
public class ScheduledChecksSearchFiltersVisitor extends AbstractSearchVisitor<FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> > {
    private final ScheduledChecksSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public ScheduledChecksSearchFiltersVisitor(ScheduledChecksSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec  Table specification.
     * @param foundNodes Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            boolean tableIsEnabled = !tableSpec.isDisabled();
            if (enabledFilter != tableIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        DefaultSchedulesSpec schedulesOverride = tableSpec.getSchedulesOverride();
        assert this.filters.getSchedule() != null;

        if (schedulesOverride != null && !schedulesOverride.isDefault() && this.filters.getSchedulingGroups() != null) {
            for (CheckRunScheduleGroup scheduleGroup : this.filters.getSchedulingGroups()) {
                MonitoringScheduleSpec scheduleForCheckSchedulingGroup = schedulesOverride.getScheduleForCheckSchedulingGroup(scheduleGroup);

                if (scheduleForCheckSchedulingGroup == null ||
                        Objects.equals(scheduleForCheckSchedulingGroup, this.filters.getSchedule())) {
                    return TreeNodeTraversalResult.TRAVERSE_CHILDREN;  // this table has no schedule, or it is the table with the same CRON expression
                }
            }
            return TreeNodeTraversalResult.SKIP_CHILDREN;  // no schedule matched
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of categories of data quality checks.
     *
     * @param checksContainerSpec Container of data quality checks that has nested categories (and categories contain checks).
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootChecksContainerSpec checksContainerSpec, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> parameter) {
        if (this.filters.getSchedulingGroups() == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        CheckRunScheduleGroup schedulingGroupForCheckRootContainer = checksContainerSpec.getSchedulingGroup();

        if (this.filters.getSchedulingGroups().contains(schedulingGroupForCheckRootContainer)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN; // the schedule does not match this type of checks
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param foundNodes        Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> abstractCheckSpec, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();

        if (enabledFilter != null) {
            boolean checkIsEnabled = !abstractCheckSpec.isDisabled();
            if (enabledFilter != checkIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        MonitoringScheduleSpec checkSchedule = abstractCheckSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (checkSchedule != null && !checkSchedule.isDefault()) {
            if (!Objects.equals(checkSchedule, this.filters.getSchedule())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;  // do not add to the results, this is the deepest node to traverse
            }
        }

        foundNodes.add(abstractCheckSpec);

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to traverse deeper
    }

    /**
     * Accepts a list of default configuration of table observability checks wrappers.
     *
     * @param tableDefaultChecksPatternWrappers Table observability default checks list.
     * @param parameter                         Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableDefaultChecksPatternList tableDefaultChecksPatternWrappers, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default configuration of column observability checks wrapper.
     *
     * @param columnDefaultChecksPatternWrappers Column observability default checks specification.
     * @param parameter                          Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnDefaultChecksPatternList columnDefaultChecksPatternWrappers, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
