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

import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyList;
import com.dqops.metadata.policies.table.TableQualityPolicyList;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.google.common.base.Strings;

import java.util.Objects;

/**
 * Visitor for {@link StatisticsCollectorSearchFilters} that finds target tables on which statistics collectors are run.
 */
public class StatisticsCollectorTargetTableSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    protected final StatisticsCollectorSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public StatisticsCollectorTargetTableSearchFiltersVisitor(StatisticsCollectorSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a list of connections.
     *
     * @param connectionList List of connections.
     * @param parameter      Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionList connectionList, SearchParameterObject parameter) {
        String connectionNameFilter = this.filters.getConnection();
        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.isSearchPattern(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        // exact connection name given, let's find it
        ConnectionWrapper connectionWrapper = connectionList.getByObjectName(connectionNameFilter, true);
        if (connectionWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(connectionWrapper);
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param parameter         Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
        if (!Strings.isNullOrEmpty(this.filters.getEnabledCronScheduleExpression())) {
            CronSchedulesSpec schedules = connectionWrapper.getSpec().getSchedules();
            CronScheduleSpec profilingSchedule = schedules != null ? schedules.getProfiling() : null;
            this.filters.setIgnoreTablesWithoutSchedule(
                    profilingSchedule == null ||
                    profilingSchedule.isDisabled() ||
                    !Objects.equals(profilingSchedule.getCronExpression(), this.filters.getEnabledCronScheduleExpression()));
        }

        String connectionNameFilter = this.filters.getConnection();
        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setConnectionLabels(connectionWrapper.getSpec().getLabels());

        if (Strings.isNullOrEmpty(connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (StringPatternComparer.matchSearchPattern(connectionWrapper.getName(), connectionNameFilter)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a collection of tables inside a connection.
     *
     * @param tableList Table list.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableList tableList, SearchParameterObject parameter) {
        String schemaTableName = this.filters.getFullTableName();
        if (Strings.isNullOrEmpty(schemaTableName)) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(schemaTableName);
        if (physicalTableName.isSearchPattern()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
        }

        TableWrapper tableWrapper = tableList.getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(tableWrapper);
    }

    /**
     * Accepts a table wrapper (lazy loader).
     *
     * @param tableWrapper Table wrapper.
     * @param parameter    Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, SearchParameterObject parameter) {
        TableSpec tableSpec = tableWrapper.getSpec();
        Boolean enabledFilter = this.filters.getEnabled();

        if (this.filters.isIgnoreTablesWithoutSchedule()) {
            if (tableSpec.getSchedulesOverride() == null || tableSpec.getSchedulesOverride().getProfiling() == null ||
                    tableSpec.getSchedulesOverride().getProfiling().isDisabled() ||
                    !Objects.equals(tableSpec.getSchedulesOverride().getProfiling().getCronExpression(), this.filters.getEnabledCronScheduleExpression())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (!Strings.isNullOrEmpty(this.filters.getEnabledCronScheduleExpression())) {
            if (tableSpec.getSchedulesOverride() != null && tableSpec.getSchedulesOverride().getProfiling() != null &&
                    !tableSpec.getSchedulesOverride().getProfiling().isDisabled() &&
                    !Objects.equals(tableSpec.getSchedulesOverride().getProfiling().getCronExpression(), this.filters.getEnabledCronScheduleExpression())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setTableLabels(tableSpec.getLabels());

        if (labelsSearcherObject != null) {
            labelsSearcherObject.setTableLabels(tableWrapper.getSpec().getLabels());
        }

        LabelSetSpec overriddenLabels = new LabelSetSpec();

        if (labelsSearcherObject.getTableLabels() != null) {
            overriddenLabels.addAll(labelsSearcherObject.getTableLabels());
        }

        if (labelsSearcherObject.getConnectionLabels() != null) {
            overriddenLabels.addAll(labelsSearcherObject.getConnectionLabels());
        }

        if (!LabelsSearchMatcher.matchTableLabels(this.filters, overriddenLabels)) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        PhysicalTableName physicalTableName = this.filters.getPhysicalTableName();
        if (physicalTableName != null) {
            if (!tableWrapper.getPhysicalTableName().matchPattern(physicalTableName)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }
        else {
            parameter.getNodes().add(tableWrapper);
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (tableSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        if (enabledFilter != null) {
            if (enabledFilter && tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !tableSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        parameter.getNodes().add(tableWrapper);

        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of default configuration of table observability checks wrappers.
     *
     * @param tableDefaultChecksPatternWrappers Table observability default checks list.
     * @param parameter                         Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableQualityPolicyList tableDefaultChecksPatternWrappers, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnQualityPolicyList columnDefaultChecksPatternWrappers, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
