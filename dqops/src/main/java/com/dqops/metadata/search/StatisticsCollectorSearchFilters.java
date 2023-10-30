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

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyIdModel;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

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
    private Collection<String> columnNames = new LinkedHashSet<>();

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
    public StatisticsCollectorSearchFiltersVisitor createProfilerSearchFilterVisitor() {
        return new StatisticsCollectorSearchFiltersVisitor(this);
    }

    /**
     * Returns a set of target column names. When the collection of column names is not empty, only column level statistics are collected.
     * @return Collection of target column names.
     */
    public Collection<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Sets a set of target column names.
     * @param columnNames Set of target column names.
     */
    public void setColumnNames(Collection<String> columnNames) {
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
        if (collectorNameSearchPattern == null && collectorName != null) {
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
        if (sensorNameSearchPattern == null && sensorName != null) {
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
                cloned.collectorsHierarchyIds = new HashSet<>(this.collectorsHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }

    public static StatisticsCollectorSearchFilters fromTableSearchFilters(TableSearchFilters tableSearchFilters) {
        return new StatisticsCollectorSearchFilters() {{
            setConnectionName(tableSearchFilters.getConnectionName());
            setSchemaTableName(tableSearchFilters.getSchemaTableName());
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
