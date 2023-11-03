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

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyIdModel;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Target data quality checks filter, identifies which checks on which tables and columns should be executed.
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@ApiModel(value = "CheckSearchFilters", description = "Target data quality checks filter, identifies which checks on which tables and columns should be executed.")
public class CheckSearchFilters extends TableSearchFilters implements Cloneable {
    private String columnName;
    private String columnDataType;
    private Boolean columnNullable;
    private CheckTarget checkTarget;
    private CheckType checkType;
    private CheckTimeScale timeScale;
    private String checkCategory;
    private String tableComparisonName;
    private String checkName;
    private String sensorName;
    private Boolean checkConfigured;

    @JsonIgnore // we can't serialize it because it is a mix of types, will not support deserialization correctly
    private Set<HierarchyId> checkHierarchyIds;
    
    @JsonIgnore
    private SearchPattern columnNameSearchPattern;
    @JsonIgnore
    private SearchPattern sensorNameSearchPattern;
    @JsonIgnore
    private SearchPattern checkNameSearchPattern;

    /**
     * Create a hierarchy tree node traversal visitor that will search for nodes matching the current filter.
     * @return Search visitor.
     */
    public CheckSearchFiltersVisitor createCheckSearchFilterVisitor() {
        return new CheckSearchFiltersVisitor(this);
    }

    /**
     * Gets a column name search pattern.
     * @return Column name search pattern.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets a column name search pattern.
     * @param columnName Column name search pattern.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Gets a column data type.
     * @return Column data type.
     */
    public String getColumnDataType() {
        return columnDataType;
    }

    /**
     * Sets a column data type.
     * @param columnDataType New column data type.
     */
    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    /**
     * Gets a flag should the column be nullable.
     * @return Column nullable flag.
     */
    public Boolean getColumnNullable() {
        return columnNullable;
    }

    /**
     * Gets a flag should the column be nullable.
     * @param columnNullable New column nullable flag.
     */
    public void setColumnNullable(Boolean columnNullable) {
        this.columnNullable = columnNullable;
    }

    /**
     * Gets a data quality check search pattern.
     * @return Data quality check search pattern.
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * Sets a data quality check search pattern.
     * @param checkName Data quality check search pattern.
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName;
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
     * Returns a check type (profiling, monitoring, partitioned) filter.
     * @return Check type filter.
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets a check type (profiling, monitoring, partitioned) filter.
     * @param checkType Check type filter.
     */
    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    /**
     * Returns a check target (table, column) filter.
     * @return Check target filter.
     */
    public CheckTarget getCheckTarget() {
        return checkTarget;
    }

    /**
     * Sets a check target (table, column) filter.
     * @param checkTarget Check target filter.
     */
    public void setCheckTarget(CheckTarget checkTarget) {
        this.checkTarget = checkTarget;
    }

    /**
     * Returns a time scale filter for monitoring and partitioned checks.
     * @return Time scale filter.
     */
    public CheckTimeScale getTimeScale() {
        return timeScale;
    }

    /**
     * Sets a time scale filter for monitoring and partitioned checks.
     * @param timeScale Time scale filter.
     */
    public void setTimeScale(CheckTimeScale timeScale) {
        this.timeScale = timeScale;
    }

    /**
     * Returns the check category name as used in YAML to group the checks.
     * @return Check category name (standard, etc).
     */
    public String getCheckCategory() {
        return checkCategory;
    }

    /**
     * Sets the check category name as used in YAML.
     * @param checkCategory Check category name.
     */
    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    /**
     * Returns the data comparison name (the name of a reference table configuration).
     * @return The name of the data comparison.
     */
    public String getTableComparisonName() {
        return tableComparisonName;
    }

    /**
     * Sets the name of the data comparison (the reference table name).
     * @param tableComparisonName The name of the reference table for table comparisons.
     */
    public void setTableComparisonName(String tableComparisonName) {
        this.tableComparisonName = tableComparisonName;
    }

    /**
     * Get the check configured filter flag.
     * @return Check configured filter.
     */
    public Boolean getCheckConfigured() {
        return checkConfigured;
    }

    /**
     * Sets the check configured filter flag.
     * @param checkConfigured New check configured filter.
     */
    public void setCheckConfigured(Boolean checkConfigured) {
        this.checkConfigured = checkConfigured;
    }

    /**
     * Returns a set of hierarchy ids of checks that must match. When not null, only checks in the set are returned as a result.
     * @return A set of checks' hierarchy ids that must match or null when the check hierarchy id is not used for matching.
     */
    public Set<HierarchyId> getCheckHierarchyIds() {
        return checkHierarchyIds;
    }

    /**
     * Sets a set of hierarchy ids of checks that must match. When not null, only checks in the set are returned as a result.
     * @param checkHierarchyIds A set of checks' hierarchy ids that must match or null when the check hierarchy id is not used for matching.
     */
    public void setCheckHierarchyIds(Set<HierarchyId> checkHierarchyIds) {
        this.checkHierarchyIds = checkHierarchyIds;
    }

    /**
     * Returns a list of check id models that could be returned to the UI.
     * @return List of hierarchy id models of selected checks.
     */
    public List<HierarchyIdModel> getCheckHierarchyIdsModels() {
        if (this.checkHierarchyIds == null) {
            return null;
        }

        return this.checkHierarchyIds.stream()
                .map(hierarchyId -> hierarchyId.toHierarchyIdModel())
                .collect(Collectors.toList());
    }

    /**
     * Sets a list of hierarchy ids of selected checks, deserialized from JSON.
     * @param hierarchyIdsModels List of hierarchy ids, deserialized.
     */
    public void setCheckHierarchyIdsModels(List<HierarchyIdModel> hierarchyIdsModels) {
        if (hierarchyIdsModels == null) {
            this.checkHierarchyIds = null;
            return;
        }

        Set<HierarchyId> hierarchyIds = hierarchyIdsModels.stream()
                .map(hierarchyIdModel -> hierarchyIdModel.toHierarchyId())
                .collect(Collectors.toSet());
        this.checkHierarchyIds = hierarchyIds;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>columnName</code>.
     * Lazy getter, parses <code>columnName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>columnName</code>.
     */
    @JsonIgnore
    public SearchPattern getColumnNameSearchPattern() {
        if (columnNameSearchPattern == null && columnName != null) {
            columnNameSearchPattern = SearchPattern.create(false, columnName);
        }
        
        return columnNameSearchPattern;
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
     * Returns the {@link SearchPattern} related to <code>checkName</code>.
     * Lazy getter, parses <code>checkName</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>checkName</code>.
     */
    @JsonIgnore
    public SearchPattern getCheckNameSearchPattern() {
        if (checkNameSearchPattern == null && checkName != null) {
            checkNameSearchPattern = SearchPattern.create(false, checkName);
        }

        return checkNameSearchPattern;
    }

    /**
     * Creates a deep clone of the search object.
     * @return Deep cloned object.
     */
    @Override
    public CheckSearchFilters clone() {
        try {
            CheckSearchFilters cloned = (CheckSearchFilters) super.clone();
            if (this.checkHierarchyIds != null) {
                cloned.checkHierarchyIds = new HashSet<>(this.checkHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }
}
