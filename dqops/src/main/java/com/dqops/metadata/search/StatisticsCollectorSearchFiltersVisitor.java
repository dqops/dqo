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

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.AbstractStatisticsCollectorCategorySpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.statistics.column.ColumnStatisticsCollectorsRootCategoriesSpec;
import com.dqops.statistics.table.TableStatisticsCollectorsRootCategoriesSpec;
import com.google.common.base.Strings;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Visitor for {@link StatisticsCollectorSearchFilters} that finds statistics collectors to execute.
 */
public class StatisticsCollectorSearchFiltersVisitor extends AbstractSearchVisitor<SearchParameterObject> {
    private final StatisticsCollectorSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public StatisticsCollectorSearchFiltersVisitor(StatisticsCollectorSearchFilters filters) {
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

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec Table specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, SearchParameterObject parameter) {
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

        TableStatisticsCollectorsRootCategoriesSpec statisticsCollector = tableSpec.getStatistics();
        if (statisticsCollector == null) {
            // the default traversal will not see the statistics collector, we need to create the default statistics collector and traverse it
            statisticsCollector = new TableStatisticsCollectorsRootCategoriesSpec();
            if (tableSpec.getHierarchyId() != null) {
                statisticsCollector.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "statistics_collector"));
            }
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(statisticsCollector, tableSpec.getColumns());
    }

    /**
     * Accepts a column collection (map).
     *
     * @param columnSpecMap Column collection.
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpecMap columnSpecMap, SearchParameterObject parameter) {
        Collection<String> targetColumnNames = this.filters.getColumnNames();
        if (targetColumnNames == null || targetColumnNames.isEmpty()) {
            return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
        }

        if (targetColumnNames.size() == 1) {
            String columnNameFilter = targetColumnNames.stream().findFirst().get();
            if (Strings.isNullOrEmpty(columnNameFilter)) {
                return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
            }

            if (StringPatternComparer.isSearchPattern(columnNameFilter)) {
                return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // we need to iterate anyway
            }

            // exact column name given, let's find it
            ColumnSpec columnSpec = columnSpecMap.get(columnNameFilter);
            if (columnSpec == null) {
                return TreeNodeTraversalResult.TRAVERSE_CHILDREN; // another try, maybe the name is case-sensitive
            }

            return TreeNodeTraversalResult.traverseSelectedChildNodes(columnSpec);

        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param parameter  Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
        labelsSearcherObject.setColumnLabels(columnSpec.getLabels());

        if (enabledFilter != null) {
            if (enabledFilter && columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && !columnSpec.isDisabled()) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (columnSpec.isDisabled()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        Collection<String> columnNames = this.filters.getColumnNames();
        if (columnNames != null && !columnNames.isEmpty()) {
            String columnName = columnSpec.getHierarchyId().getLast().toString();
            boolean columnNameMatch = columnNames.stream()
                    .anyMatch(columnNameFilter -> StringPatternComparer.matchSearchPattern(columnName, columnNameFilter));

            if (!columnNameMatch) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        ColumnStatisticsCollectorsRootCategoriesSpec statisticsCollector = columnSpec.getStatistics();
        if (statisticsCollector == null) {
            // the default traversal will not see the statistics collector, we need to create the default statistics collector and traverse it
            statisticsCollector = new ColumnStatisticsCollectorsRootCategoriesSpec();
            if (columnSpec.getHierarchyId() != null) {
                statisticsCollector.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "statistics_collector"));
            }
        }

        return TreeNodeTraversalResult.traverseSelectedChildNodes(statisticsCollector);
    }

    /**
     * Accepts any statistics collector specification.
     *
     * @param abstractStatisticsCollectorSpec Data statistics collector specification (any).
     * @param parameter     Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractStatisticsCollectorSpec<?> abstractStatisticsCollectorSpec, SearchParameterObject parameter) {
        Boolean enabledFilter = this.filters.getEnabled();

        AbstractSensorParametersSpec sensorParameters = abstractStatisticsCollectorSpec.getParameters();
        boolean collectorEnabled = !abstractStatisticsCollectorSpec.isDisabled();
        if (enabledFilter != null) {
            if (enabledFilter && !collectorEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
            if (!enabledFilter && collectorEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (this.filters.getLabels() != null && this.filters.getLabels().length > 0) {
            LabelSetSpec overriddenLabels = new LabelSetSpec();
            LabelsSearcherObject labelsSearcherObject = parameter.getLabelsSearcherObject();
            if (labelsSearcherObject.getColumnLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getColumnLabels());
            }

            if (labelsSearcherObject.getTableLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getTableLabels());
            }

            if (labelsSearcherObject.getConnectionLabels() != null) {
                overriddenLabels.addAll(labelsSearcherObject.getConnectionLabels());
            }
            if (!LabelsSearchMatcher.hasAllLabels(this.filters.getLabels(), overriddenLabels)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (this.filters.getTags() != null && this.filters.getTags().length > 0) {
            DataGroupingConfigurationSearcherObject dataGroupingConfigurationSearcherObject = parameter.getDataStreamSearcherObject();
            DataGroupingConfigurationSpec selectedDataGroupingConfiguration =
                    dataGroupingConfigurationSearcherObject.getDefaultGroupingConfiguration();

            if (!DataGroupingsTagsSearchMatcher.matchAllRequiredTags(this.filters.getTags(), selectedDataGroupingConfiguration)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (!collectorEnabled) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;
        }

        String collectorNameFilter = this.filters.getCollectorName();
        if (!Strings.isNullOrEmpty(collectorNameFilter)) {
            String checkName = abstractStatisticsCollectorSpec.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(checkName, collectorNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        String sensorNameFilter = this.filters.getSensorName();
        if (!Strings.isNullOrEmpty(sensorNameFilter)) {
            if (sensorParameters == null) {
                return TreeNodeTraversalResult.SKIP_CHILDREN; // sensor is not configured (has no parameters, we don't know what to run)
            }
            String sensorDefinitionName = sensorParameters.getSensorDefinitionName();
            if (!StringPatternComparer.matchSearchPattern(sensorDefinitionName, sensorNameFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        Set<HierarchyId> collectorHierarchyIds = this.filters.getCollectorsHierarchyIds();
        if (collectorHierarchyIds != null) {
            if (!collectorHierarchyIds.contains(abstractStatisticsCollectorSpec.getHierarchyId())) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        parameter.getNodes().add(abstractStatisticsCollectorSpec);

        if (this.filters.getMaxResults() != null && parameter.getNodes().size() >= this.filters.getMaxResults()) {
            return TreeNodeTraversalResult.STOP_TRAVERSAL;
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to search any deeper, we have found what we were looking for
    }

    /**
     * Accepts a container of categories of data statistics collectors.
     *
     * @param rootProfilerContainerSpec Container of data statistics collectors that has nested categories (and categories contain collectors).
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootStatisticsCollectorsContainerSpec rootProfilerContainerSpec, SearchParameterObject parameter) {
        StatisticsCollectorTarget targetFilter = this.filters.getTarget();
        if (targetFilter != null) {
            if (rootProfilerContainerSpec.getTarget() != targetFilter) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        if (rootProfilerContainerSpec.getTarget() == StatisticsCollectorTarget.table &&
                this.filters.getColumnNames() != null && !this.filters.getColumnNames().isEmpty()) {
            return TreeNodeTraversalResult.SKIP_CHILDREN;  // target columns already selected, ignoring table level checks
        }

        return super.accept(rootProfilerContainerSpec, parameter);
    }

    /**
     * Accepts a container of data statistics collector for a single category.
     *
     * @param collectorsCategorySpec    Container of data statistics collectors for a single category.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractStatisticsCollectorCategorySpec collectorsCategorySpec, SearchParameterObject parameter) {
        String collectorCategoryFilter = this.filters.getCollectorCategory();
        if (!Strings.isNullOrEmpty(collectorCategoryFilter)) {
            String categoryName = collectorsCategorySpec.getHierarchyId().getLast().toString();
            if (!StringPatternComparer.matchSearchPattern(categoryName, collectorCategoryFilter)) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        return super.accept(collectorsCategorySpec, parameter);
    }
}
