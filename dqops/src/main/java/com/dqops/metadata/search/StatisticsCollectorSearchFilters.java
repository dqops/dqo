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

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyIdModel;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.apache.parquet.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class StatisticsCollectorSearchFilters extends TableSearchFilters implements Cloneable {
    @JsonPropertyDescription("The list of column names or column name patters. This field accepts search patterns in the format: 'fk_\\*', '\\*_id', 'prefix\\*suffix'.")
    private List<String> columnNames = new ArrayList<>();

    @JsonPropertyDescription("The target statistics collector name to capture only selected statistics. Uses the short collector name" +
            "This field supports search patterns such as: 'prefix\\*', '\\*suffix', 'prefix_\\*_suffix'. " +
            "In order to collect only top 10 most common column samples, use 'column_samples'.")
    private String collectorName;

    @JsonPropertyDescription("The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. " +
            "This field supports search patterns such as: 'table/volume/row_\\*', '\\*_count', 'table/volume/prefix_\\*_suffix'.")
    private String sensorName;

    @JsonPropertyDescription("The target statistics collector category, for example: *nulls*, *volume*, *sampling*.")
    private String collectorCategory;

    @JsonPropertyDescription("The target type of object to collect statistics from. Supported values are: *table* to collect only table level statistics or *column* to collect only column level statistics.")
    private StatisticsCollectorTarget target;

    @JsonIgnore // we can't serialize it because it is a mix of types, will not support deserialization correctly
    private Set<HierarchyId> collectorsHierarchyIds;

    @JsonPropertyDescription("Expected CRON profiling schedule.")
    private String enabledCronScheduleExpression;

    @JsonIgnore
    private boolean ignoreTablesWithoutSchedule;

    @JsonIgnore
    private List<SearchPattern> columnNameSearchPatterns;
    @JsonIgnore
    private SearchPattern collectorNameSearchPattern;
    @JsonIgnore
    private SearchPattern sensorNameSearchPattern;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public StatisticsCollectorSearchFiltersVisitor createCollectorSearchFilterVisitor() {
        return new StatisticsCollectorSearchFiltersVisitor(this);
    }

    /**
     * Create a hierarchy tree node traversal visitor that will search for tables matching the filters.
     * @return Search visitor.
     */
    public StatisticsCollectorTargetTableSearchFiltersVisitor createTargetTableCollectorSearchFilterVisitor() {
        return new StatisticsCollectorTargetTableSearchFiltersVisitor(this);
    }

    /**
     * Returns a set of target column names. When the collection of column names is not empty, only column level statistics are collected.
     * @return Collection of target column names.
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Sets a set of target column names.
     * @param columnNames Set of target column names.
     */
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * Gets a statistics collector name search pattern.
     * @return Data statistics collector search pattern.
     */
    public String getCollectorName() {
        return collectorName;
    }

    /**
     * Sets a data statistics collector search pattern.
     * @param collectorName Data statistics collector search pattern.
     */
    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    /**
     * Gets a sensor name search pattern.
     * @return Sensor name search pattern.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets a sensor name search pattern.
     * @param sensorName Sensor name search pattern.
     */
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
    /**
     * Returns the statistics collector category name as used in YAML to group the collectors.
     * @return Statistics collector category name (standard, etc).
     */
    public String getCollectorCategory() {
        return collectorCategory;
    }

    /**
     * Sets the statistics collector category name as used in YAML.
     * @param collectorCategory Statistics collector category name.
     */
    public void setCollectorCategory(String collectorCategory) {
        this.collectorCategory = collectorCategory;
    }

    /**
     * Returns the target type (table or column).
     * @return Statistic collector target.
     */
    public StatisticsCollectorTarget getTarget() {
        return target;
    }

    /**
     * Sets the statistics collector target (table or column).
     * @param target Statistics collector target.
     */
    public void setTarget(StatisticsCollectorTarget target) {
        this.target = target;
    }

    /**
     * Returns a set of hierarchy ids of statistics collectors that must match. When not null, only statistics collectors in the set are returned as a result.
     * @return A set of collectors' hierarchy ids that must match or null when the statistics collector hierarchy id is not used for matching.
     */
    public Set<HierarchyId> getCollectorsHierarchyIds() {
        return collectorsHierarchyIds;
    }

    /**
     * Sets a set of hierarchy ids of statistics collectors that must match. When not null, only statistics collector in the set are returned as a result.
     * @param collectorsHierarchyIds A set of statistics collectors' hierarchy ids that must match or null when the collector hierarchy id is not used for matching.
     */
    public void setCollectorsHierarchyIds(Set<HierarchyId> collectorsHierarchyIds) {
        this.collectorsHierarchyIds = collectorsHierarchyIds;
    }

    /**
     * Returns a CRON schedule that must be enabled and configured on a connection or table a a profiling schedule
     * It is used to find tables scheduled to run on this schedule.
     * @return Cron schedule.
     */
    public String getEnabledCronScheduleExpression() {
        return enabledCronScheduleExpression;
    }

    /**
     * Sets a CRON schedule that must be configured and enabled on tables.
     * @param enabledCronScheduleExpression Cron schedule expression.
     */
    public void setEnabledCronScheduleExpression(String enabledCronScheduleExpression) {
        this.enabledCronScheduleExpression = enabledCronScheduleExpression;
    }

    /**
     * Returns true if tables without an active schedule should be excluded.
     * This value is set by the search visitor when visiting every connection, when the <code>enabledCronSchedule</code>
     * is configured and the cron schedule on the connection was different, so only tables that match a cron schedule
     * can be included. Those without a schedule should be excluded, because the connection is excluded.
     * @return True when ignoring tables without a schedule.
     */
    public boolean isIgnoreTablesWithoutSchedule() {
        return ignoreTablesWithoutSchedule;
    }

    /**
     * Sets a flag to ignore tables without a schedule.
     * @param ignoreTablesWithoutSchedule Ignore tables without a schedule configured.
     */
    public void setIgnoreTablesWithoutSchedule(boolean ignoreTablesWithoutSchedule) {
        this.ignoreTablesWithoutSchedule = ignoreTablesWithoutSchedule;
    }

    /**
     * Returns a list of collector id models that could be returned to the UI.
     * @return List of hierarchy id models of selected checks.
     */
    public List<HierarchyIdModel> getCollectorsHierarchyIdsModels() {
        if (this.collectorsHierarchyIds == null) {
            return null;
        }

        return this.collectorsHierarchyIds.stream()
                .map(hierarchyId -> hierarchyId.toHierarchyIdModel())
                .collect(Collectors.toList());
    }

    /**
     * Sets a list of hierarchy ids of selected statistics collectors, deserialized from JSON.
     * @param hierarchyIdsModels List of hierarchy ids, deserialized.
     */
    public void setProfilerHierarchyIdsModels(List<HierarchyIdModel> hierarchyIdsModels) {
        if (hierarchyIdsModels == null) {
            this.collectorsHierarchyIds = null;
            return;
        }

        Set<HierarchyId> hierarchyIds = hierarchyIdsModels.stream()
                .map(hierarchyIdModel -> hierarchyIdModel.toHierarchyId())
                .collect(Collectors.toSet());
        this.collectorsHierarchyIds = hierarchyIds;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>columnName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>columnName</code>.
     */
    @JsonIgnore
    public List<SearchPattern> getColumnNameSearchPatterns() {
        if (columnNameSearchPatterns == null && this.columnNames != null) {
            columnNameSearchPatterns = this.columnNames.stream()
                    .map(cn -> SearchPattern.create(false, cn))
                    .collect(Collectors.toList());
        }

        return columnNameSearchPatterns;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>collectorName</code>.
     * Lazy getter, parses <code>collectorName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>collectorName</code>.
     */
    @JsonIgnore
    public SearchPattern getCollectorNameSearchPattern() {
        if (collectorNameSearchPattern == null && !Strings.isNullOrEmpty(collectorName)) {
            collectorNameSearchPattern = SearchPattern.create(false, collectorName);
        }

        return collectorNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>sensorName</code>.
     * Lazy getter, parses <code>sensorName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>sensorName</code>.
     */
    @JsonIgnore
    public SearchPattern getSensorNameSearchPattern() {
        if (sensorNameSearchPattern == null && !Strings.isNullOrEmpty(sensorName)) {
            sensorNameSearchPattern = SearchPattern.create(false, sensorName);
        }

        return sensorNameSearchPattern;
    }

    /**
     * Creates a deep clone of the search object.
     * @return Deep cloned object.
     */
    @Override
    public StatisticsCollectorSearchFilters clone() {
        try {
            StatisticsCollectorSearchFilters cloned = (StatisticsCollectorSearchFilters) super.clone();
            if (this.collectorsHierarchyIds != null) {
                cloned.collectorsHierarchyIds = new LinkedHashSet<>(this.collectorsHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }

    public static StatisticsCollectorSearchFilters fromTableSearchFilters(TableSearchFilters tableSearchFilters) {
        return new StatisticsCollectorSearchFilters() {{
            setConnection(tableSearchFilters.getConnection());
            setFullTableName(tableSearchFilters.getFullTableName());
            setEnabled(tableSearchFilters.getEnabled());
            setTags(tableSearchFilters.getTags());
            setLabels(tableSearchFilters.getLabels());
        }};
    }

    public static class StatisticsCollectorSearchFiltersSampleFactory implements SampleValueFactory<StatisticsCollectorSearchFilters> {
        @Override
        public StatisticsCollectorSearchFilters createSample() {
            StatisticsCollectorSearchFilters statisticsCollectorSearchFilters = fromTableSearchFilters(new TableSearchFilters.TableSearchFiltersSampleFactory().createSample());
            statisticsCollectorSearchFilters.setColumnNames(List.of(SampleStringsRegistry.getColumnName()));
            statisticsCollectorSearchFilters.setCollectorCategory(SampleStringsRegistry.getCategoryName());

            return statisticsCollectorSearchFilters;
        }
    }
}
