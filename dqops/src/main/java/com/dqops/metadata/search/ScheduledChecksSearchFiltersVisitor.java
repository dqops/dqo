/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyList;
import com.dqops.metadata.policies.table.TableQualityPolicyList;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
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

        CronSchedulesSpec schedulesOverride = tableSpec.getSchedulesOverride();
        assert this.filters.getSchedule() != null;

        if (schedulesOverride != null && !schedulesOverride.isDefault() && this.filters.getSchedulingGroups() != null) {
            for (CheckRunScheduleGroup scheduleGroup : this.filters.getSchedulingGroups()) {
                CronScheduleSpec scheduleForCheckSchedulingGroup = schedulesOverride.getScheduleForCheckSchedulingGroup(scheduleGroup);

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
        if (abstractCheckSpec.isDoNotSchedule()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        Boolean enabledFilter = this.filters.getEnabled();

        if (enabledFilter != null) {
            boolean checkIsEnabled = !abstractCheckSpec.isDisabled();
            if (enabledFilter != checkIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        CronScheduleSpec checkSchedule = abstractCheckSpec.getScheduleOverride();
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
    public TreeNodeTraversalResult accept(TableQualityPolicyList tableDefaultChecksPatternWrappers, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> parameter) {
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
    public TreeNodeTraversalResult accept(ColumnQualityPolicyList columnDefaultChecksPatternWrappers, FoundResultsCollector<AbstractCheckSpec<?, ?, ?, ?>> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
