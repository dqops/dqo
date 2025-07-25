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

import com.dqops.checks.*;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyIdModel;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.parquet.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Target data quality checks filter, identifies which checks on which tables and columns should be executed.
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@ApiModel(value = "CheckSearchFilters", description = "Target data quality checks filter, identifies which checks on which tables and columns should be executed.")
public class CheckSearchFilters extends TableSearchFilters implements Cloneable {
    @JsonPropertyDescription("The column name. This field accepts search patterns in the format: 'fk_\\*', '\\*_id', 'prefix\\*suffix'.")
    private String column;

    @JsonPropertyDescription("The column data type that was imported from the data source and is stored in the " +
            "[columns -> column_name -> type_snapshot -> column_type](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.")
    private String columnDataType;

    @JsonPropertyDescription("Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the " +
            "[columns -> column_name -> type_snapshot -> nullable](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.")
    private Boolean columnNullable;

    @JsonPropertyDescription("The target type of object to run checks. Supported values are: *table* to run only table level checks or *column* to run only column level checks.")
    private CheckTarget checkTarget;

    @JsonPropertyDescription("The target type of checks to run. Supported values are *profiling*, *monitoring* and *partitioned*.")
    private CheckType checkType;

    @JsonPropertyDescription("The time scale of *monitoring* or *partitioned* checks to run. Supports running only *daily* or *monthly* checks. " +
            "Daily monitoring checks will replace today's value for all captured check results.")
    private CheckTimeScale timeScale;

    @JsonPropertyDescription("The target check category, for example: *nulls*, *volume*, *anomaly*.")
    private String checkCategory;

    @JsonPropertyDescription("The target data quality dimension, for example: *Completeness*, *Accuracy*, *Consistency*, *Timeliness*, *Availability*.")
    private String qualityDimension;

    @JsonPropertyDescription("The name of a configured table comparison. When the table comparison is provided, DQOps will only perform table comparison checks that compare data between tables.")
    private String tableComparisonName;

    @JsonPropertyDescription("The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. " +
           "This field supports search patterns such as: 'profiling_\\*', '\\*_count', 'profiling_\\*_percent'.")
    private String checkName;

    @JsonPropertyDescription("The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. " +
            "This field supports search patterns such as: 'table/volume/row_\\*', '\\*_count', 'table/volume/prefix_\\*_suffix'.")
    private String sensorName;

    /**
     * Finds only configured checks. Used only to find checks in-memory when a full object was created.
     */
    @JsonIgnore
    private Boolean checkConfigured;

    @JsonIgnore // we can't serialize it because it is a mix of types, will not support deserialization correctly
    private Set<HierarchyId> checkHierarchyIds;
    
    @JsonIgnore
    private SearchPattern columnNameSearchPattern;
    @JsonIgnore
    private SearchPattern sensorNameSearchPattern;
    @JsonIgnore
    private SearchPattern checkNameSearchPattern;
    @JsonIgnore
    private SearchPattern columnDataTypeSearchPattern;

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
    public String getColumn() {
        return column;
    }

    /**
     * Sets a column name search pattern.
     * @param column Column name search pattern.
     */
    public void setColumn(String column) {
        this.column = column;
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
     * Return the data quality dimension name.
     * @return Data quality dimension name.
     */
    public String getQualityDimension() {
        return qualityDimension;
    }

    /**
     * Sets the data quality dimension name.
     * @param qualityDimension Data quality dimension name.
     */
    public void setQualityDimension(String qualityDimension) {
        this.qualityDimension = qualityDimension;
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
        if (columnNameSearchPattern == null && !Strings.isNullOrEmpty(column)) {
            columnNameSearchPattern = SearchPattern.create(false, column);
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
        if (sensorNameSearchPattern == null && !Strings.isNullOrEmpty(sensorName)) {
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
        if (checkNameSearchPattern == null && !Strings.isNullOrEmpty(checkName)) {
            checkNameSearchPattern = SearchPattern.create(false, checkName);
        }

        return checkNameSearchPattern;
    }

    /**
     * Returns the {@link SearchPattern} related to <code>columnDataType</code>.
     * Lazy getter, parses <code>columnDataType</code> as a search pattern and returns parsed object.
     * @return {@link SearchPattern} related to <code>columnDataType</code>.
     */
    @JsonIgnore
    public SearchPattern getColumnDataTypeSearchPattern() {
        if (columnDataTypeSearchPattern == null && !Strings.isNullOrEmpty(columnDataType)) {
            columnDataTypeSearchPattern = SearchPattern.create(false, columnDataType);
        }

        return columnDataTypeSearchPattern;
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
                cloned.checkHierarchyIds = new LinkedHashSet<>(this.checkHierarchyIds);
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object", ex);
        }
    }

    /**
     * Creates a check search filter given an instance of a check inside a given connection.
     * @param tableSpec Table where the check is applied.
     * @param checkSpec Check specification instance, must be inside a user home and the connection that was given.
     * @return Check search filters with all filters that identify the check.
     */
    public static CheckSearchFilters fromCheckSpecInstance(TableSpec tableSpec, AbstractCheckSpec<?,?,?,?> checkSpec) {
        HierarchyId checkHierarchyId = checkSpec.getHierarchyId();
        HierarchyNode[] allNodesToCheck = checkHierarchyId.getNodesOnPath(tableSpec);
        AbstractRootChecksContainerSpec rootChecksContainerSpec = HierarchyNode.findNodeOfType(allNodesToCheck, AbstractRootChecksContainerSpec.class);

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setConnection(checkHierarchyId.getConnectionName());
        PhysicalTableName physicalTableName = checkHierarchyId.getPhysicalTableName();
        checkSearchFilters.setPhysicalTableName(physicalTableName);
        checkSearchFilters.setFullTableName(physicalTableName.toTableSearchFilter());
        checkSearchFilters.setColumn(checkHierarchyId.getColumnName());
        checkSearchFilters.setCheckCategory(checkSearchFilters.getCheckCategory());
        checkSearchFilters.setCheckName(checkSpec.getCheckName());
        checkSearchFilters.setCheckTarget(rootChecksContainerSpec.getCheckTarget());
        checkSearchFilters.setCheckType(rootChecksContainerSpec.getCheckType());
        checkSearchFilters.setTimeScale(rootChecksContainerSpec.getCheckTimeScale());

        if (checkSpec.isTableComparisonCheck()) {
            AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = HierarchyNode.findNodeOfType(allNodesToCheck, AbstractComparisonCheckCategorySpec.class);
            checkSearchFilters.setTableComparisonName(comparisonCheckCategorySpec.getComparisonName());
        }

        return checkSearchFilters;
    }

    /**
     * Create a check search filter from a table search filter object, copying shared attributes.
     * @param tableSearchFilters Source table search filter.
     * @return Check search filter.
     */
    public static CheckSearchFilters fromTableSearchFilters(TableSearchFilters tableSearchFilters) {
        return new CheckSearchFilters() {{
            setConnection(tableSearchFilters.getConnection());
            setFullTableName(tableSearchFilters.getFullTableName());
            setEnabled(tableSearchFilters.getEnabled());
            setTags(tableSearchFilters.getTags());
            setLabels(tableSearchFilters.getLabels());
        }};
    }

    public static class CheckSearchFiltersSampleFactory implements SampleValueFactory<CheckSearchFilters> {
        @Override
        public CheckSearchFilters createSample() {
            CheckSearchFilters checkSearchFilters = fromTableSearchFilters(new TableSearchFilters.TableSearchFiltersSampleFactory().createSample());
            checkSearchFilters.setColumn(SampleStringsRegistry.getColumnName());

            ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec.ColumnTypeSnapshotSpecSampleFactory().createSample();
            checkSearchFilters.setColumnDataType(columnTypeSnapshotSpec.getColumnType());

            return checkSearchFilters;
        }
    }
}
