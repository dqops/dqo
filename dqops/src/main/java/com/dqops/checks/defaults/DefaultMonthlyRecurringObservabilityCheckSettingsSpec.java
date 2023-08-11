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

package com.dqops.checks.defaults;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.recurring.ColumnMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnRecurringChecksRootSpec;
import com.dqops.checks.column.recurring.anomaly.ColumnAnomalyMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.bool.ColumnBoolMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.datatype.ColumnDatatypeMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.datetime.ColumnDatetimeMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.nulls.ColumnNullsMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.numeric.ColumnNumericMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.pii.ColumnPiiMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.schema.ColumnSchemaMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.strings.ColumnStringsMonthlyRecurringChecksSpec;
import com.dqops.checks.column.recurring.uniqueness.ColumnUniquenessMonthlyRecurringChecksSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableRecurringChecksSpec;
import com.dqops.checks.table.recurring.availability.TableAvailabilityMonthlyRecurringChecksSpec;
import com.dqops.checks.table.recurring.schema.TableSchemaMonthlyRecurringChecksSpec;
import com.dqops.checks.table.recurring.volume.TableVolumeMonthlyRecurringChecksSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of checks that are enabled as data observability monthly recurring checks that will be detecting anomalies
 * for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultMonthlyRecurringObservabilityCheckSettingsSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultMonthlyRecurringObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("table_volume", o -> o.tableVolume);
            put("table_availability", o -> o.tableAvailability);
            put("table_schema", o -> o.tableSchema);
            put("column_nulls", o -> o.columnNulls);
            put("column_numeric", o -> o.columnNumeric);
            put("column_strings", o -> o.columnStrings);
            put("column_uniqueness", o -> o.columnUniqueness);
            put("column_datetime", o -> o.columnDatetime);
            put("column_pii", o -> o.columnPii);
            put("column_bool", o -> o.columnBool);
            put("column_datatype", o -> o.columnDatatype);
            put("column_anomaly", o -> o.columnAnomaly);
            put("column_schema", o -> o.columnSchema);
        }
    };

    @JsonPropertyDescription("The default configuration of volume data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeMonthlyRecurringChecksSpec tableVolume;

    @JsonPropertyDescription("The default configuration of the table availability data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityMonthlyRecurringChecksSpec tableAvailability;

    @JsonPropertyDescription("The default configuration of schema (column count and schema) data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaMonthlyRecurringChecksSpec tableSchema;

    @JsonPropertyDescription("The default configuration of column level checks that verify nulls and blanks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsMonthlyRecurringChecksSpec columnNulls;

    @JsonPropertyDescription("The default configuration of column level checks that verify negative values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMonthlyRecurringChecksSpec columnNumeric;

    @JsonPropertyDescription("The default configuration of strings checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsMonthlyRecurringChecksSpec columnStrings;

    @JsonPropertyDescription("The default configuration of uniqueness checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessMonthlyRecurringChecksSpec columnUniqueness;

    @JsonPropertyDescription("The default configuration of datetime checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeMonthlyRecurringChecksSpec columnDatetime;

    @JsonPropertyDescription("The default configuration of Personal Identifiable Information (PII) checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiMonthlyRecurringChecksSpec columnPii;

    @JsonPropertyDescription("The default configuration of booleans checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolMonthlyRecurringChecksSpec columnBool;

    @JsonPropertyDescription("The default configuration of datatype checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeMonthlyRecurringChecksSpec columnDatatype;

    @JsonPropertyDescription("The default configuration of anomaly checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyMonthlyRecurringChecksSpec columnAnomaly;

    @JsonPropertyDescription("The default configuration of schema checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaMonthlyRecurringChecksSpec columnSchema;

    /**
     * Returns a container of volume check configuration on a table level.
     * @return Volume checks configuration.
     */
    public TableVolumeMonthlyRecurringChecksSpec getTableVolume() {
        return tableVolume;
    }

    /**
     * Sets a reference to a volume checks container.
     * @param tableVolume New volume checks configuration.
     */
    public void setTableVolume(TableVolumeMonthlyRecurringChecksSpec tableVolume) {
        this.setDirtyIf(!Objects.equals(this.tableVolume, tableVolume));
        this.tableVolume = tableVolume;
        this.propagateHierarchyIdToField(tableVolume, "table_volume");
    }

    /**
     * Returns a container of the table availability checks.
     * @return Table availability checks.
     */
    public TableAvailabilityMonthlyRecurringChecksSpec getTableAvailability() {
        return tableAvailability;
    }

    /**
     * Sets a reference to an availability checks container.
     * @param tableAvailability Table availability checks.
     */
    public void setTableAvailability(TableAvailabilityMonthlyRecurringChecksSpec tableAvailability) {
        this.setDirtyIf(!Objects.equals(this.tableAvailability, tableAvailability));
        this.tableAvailability = tableAvailability;
        this.propagateHierarchyIdToField(tableAvailability, "table_availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaMonthlyRecurringChecksSpec getTableSchema() {
        return tableSchema;
    }

    /**
     * Sets the reference to the table schema checks.
     * @param tableSchema Table schema checks container.
     */
    public void setTableSchema(TableSchemaMonthlyRecurringChecksSpec tableSchema) {
        this.setDirtyIf(!Objects.equals(this.tableSchema, tableSchema));
        this.tableSchema = tableSchema;
        this.propagateHierarchyIdToField(tableSchema, "table_schema");
    }


    /**
     * Returns the nulls check configuration on a column level.
     * @return Nulls check configuration.
     */
    public ColumnNullsMonthlyRecurringChecksSpec getColumnNulls() {
        return columnNulls;
    }

    /**
     * Sets the nulls check configuration on a column level.
     * @param columnNulls New nulls checks configuration.
     */
    public void setColumnNulls(ColumnNullsMonthlyRecurringChecksSpec columnNulls) {
        this.setDirtyIf(!Objects.equals(this.columnNulls, columnNulls));
        this.columnNulls = columnNulls;
        this.propagateHierarchyIdToField(columnNulls, "column_nulls");
    }

    /**
     * Returns the negative values check configuration on a column level.
     * @return Negative values check configuration.
     */
    public ColumnNumericMonthlyRecurringChecksSpec getColumnNumeric() {
        return columnNumeric;
    }

    /**
     * Sets the negative values check configuration on a column level.
     * @param columnNumeric New negative values checks configuration.
     */
    public void setColumnNumeric(ColumnNumericMonthlyRecurringChecksSpec columnNumeric) {
        this.setDirtyIf(!Objects.equals(this.columnNumeric, columnNumeric));
        this.columnNumeric = columnNumeric;
        this.propagateHierarchyIdToField(columnNumeric, "column_numeric");
    }

    /**
     * Returns the strings check configuration on a column level.
     * @return Strings check configuration.
     */
    public ColumnStringsMonthlyRecurringChecksSpec getColumnStrings() {
        return columnStrings;
    }

    /**
     * Sets the string check configuration on a column level.
     * @param columnStrings New string checks configuration.
     */
    public void setColumnStrings(ColumnStringsMonthlyRecurringChecksSpec columnStrings) {
        this.setDirtyIf(!Objects.equals(this.columnStrings, columnStrings));
        this.columnStrings = columnStrings;
        this.propagateHierarchyIdToField(columnStrings, "column_strings");
    }

    /**
     * Returns the uniqueness check configuration on a column level.
     * @return Uniqueness check configuration.
     */
    public ColumnUniquenessMonthlyRecurringChecksSpec getColumnUniqueness() {
        return columnUniqueness;
    }

    /**
     * Sets the uniqueness check configuration on a column level.
     * @param columnUniqueness New uniqueness checks configuration.
     */
    public void setColumnUniqueness(ColumnUniquenessMonthlyRecurringChecksSpec columnUniqueness) {
        this.setDirtyIf(!Objects.equals(this.columnUniqueness, columnUniqueness));
        this.columnUniqueness = columnUniqueness;
        this.propagateHierarchyIdToField(columnUniqueness, "column_uniqueness");
    }

    /**
     * Returns the datetime check configuration on a column level.
     * @return Datetime check configuration.
     */
    public ColumnDatetimeMonthlyRecurringChecksSpec getColumnDatetime() {
        return columnDatetime;
    }

    /**
     * Sets the datetime check configuration on a column level.
     * @param columnDatetime New datetime checks configuration.
     */
    public void setColumnDatetime(ColumnDatetimeMonthlyRecurringChecksSpec columnDatetime) {
        this.setDirtyIf(!Objects.equals(this.columnDatetime, columnDatetime));
        this.columnDatetime = columnDatetime;
        this.propagateHierarchyIdToField(columnDatetime, "column_datetime");
    }

    /**
     * Returns the Personal Identifiable Information (PII) check configuration on a column level.
     * @return Personal Identifiable Information (PII) check configuration.
     */
    public ColumnPiiMonthlyRecurringChecksSpec getColumnPii() {
        return columnPii;
    }

    /**
     * Sets the Personal Identifiable Information (PII) check configuration on a column level.
     * @param columnPii New Personal Identifiable Information (PII) checks configuration.
     */
    public void setColumnPii(ColumnPiiMonthlyRecurringChecksSpec columnPii) {
        this.setDirtyIf(!Objects.equals(this.columnPii, columnPii));
        this.columnPii = columnPii;
        this.propagateHierarchyIdToField(columnPii, "column_pii");
    }

    /**
     * Returns the booleans check configuration on a column level.
     * @return Boolean check configuration.
     */
    public ColumnBoolMonthlyRecurringChecksSpec getColumnBool() {
        return columnBool;
    }

    /**
     * Sets the boolean check configuration on a column level.
     * @param columnBool New boolean checks configuration.
     */
    public void setColumnBool(ColumnBoolMonthlyRecurringChecksSpec columnBool) {
        this.setDirtyIf(!Objects.equals(this.columnBool, columnBool));
        this.columnBool = columnBool;
        this.propagateHierarchyIdToField(columnBool, "column_bool");
    }

    /**
     * Returns the datatype check configuration on a column level.
     * @return Datatype check configuration.
     */
    public ColumnDatatypeMonthlyRecurringChecksSpec getColumnDatatype() {
        return columnDatatype;
    }

    /**
     * Sets the datatype check configuration on a column level.
     * @param columnDatatype New datatype checks configuration.
     */
    public void setColumnDatatype(ColumnDatatypeMonthlyRecurringChecksSpec columnDatatype) {
        this.setDirtyIf(!Objects.equals(this.columnDatatype, columnDatatype));
        this.columnDatatype = columnDatatype;
        this.propagateHierarchyIdToField(columnDatatype, "column_datatype");
    }

    /**
     * Returns the anomaly check configuration on a column level.
     * @return Anomaly check configuration.
     */
    public ColumnAnomalyMonthlyRecurringChecksSpec getColumnAnomaly() {
        return columnAnomaly;
    }

    /**
     * Sets the anomaly check configuration on a column level.
     * @param columnAnomaly New anomaly checks configuration.
     */
    public void setColumnAnomaly(ColumnAnomalyMonthlyRecurringChecksSpec columnAnomaly) {
        this.setDirtyIf(!Objects.equals(this.columnAnomaly, columnAnomaly));
        this.columnAnomaly = columnAnomaly;
        this.propagateHierarchyIdToField(columnAnomaly, "column_anomaly");
    }

    /**
     * Returns the schema checks container on a column level.
     * @return Schema checks container.
     */
    public ColumnSchemaMonthlyRecurringChecksSpec getColumnSchema() {
        return columnSchema;
    }

    /**
     * Sets teh schema checks container for column level checks.
     * @param columnSchema Schema checks container.
     */
    public void setColumnSchema(ColumnSchemaMonthlyRecurringChecksSpec columnSchema) {
        this.setDirtyIf(!Objects.equals(this.columnSchema, columnSchema));
        this.columnSchema = columnSchema;
        this.propagateHierarchyIdToField(columnSchema, "column_schema");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Retrieves or creates and retrieves the check categories class on a table.
     * @param targetTable Target table.
     * @return Check categories.
     */
    protected TableMonthlyRecurringCheckCategoriesSpec getTableCheckCategories(TableSpec targetTable) {
        TableRecurringChecksSpec recurringChecksSpec = targetTable.getRecurringChecks();
        if (recurringChecksSpec == null) {
            recurringChecksSpec = new TableRecurringChecksSpec();
            targetTable.setRecurringChecks(recurringChecksSpec);
        }

        TableMonthlyRecurringCheckCategoriesSpec checkCategoriesSpec = recurringChecksSpec.getMonthly();
        if (checkCategoriesSpec == null) {
            checkCategoriesSpec = new TableMonthlyRecurringCheckCategoriesSpec();
            recurringChecksSpec.setMonthly(checkCategoriesSpec);
        }

        return checkCategoriesSpec;
    }
    /**
     * Applies the checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.tableVolume != null && !this.tableVolume.isDefault()) {
            this.getTableCheckCategories(targetTable).setVolume(this.tableVolume.deepClone());
        }

        if (this.tableAvailability != null && !this.tableAvailability.isDefault()) {
            this.getTableCheckCategories(targetTable).setAvailability(this.tableAvailability.deepClone());
        }

        if (this.tableSchema != null && !this.tableSchema.isDefault()) {
            this.getTableCheckCategories(targetTable).setSchema(this.tableSchema.deepClone());
        }

        for (ColumnSpec columnSpec : targetTable.getColumns().values()) {
            applyOnColumn(columnSpec, dialectSettings);
        }
    }

    /**
     * Retrieves or creates and retrieves the check categories class on a column.
     * @param targetColumn Target column.
     * @return Check categories.
     */
    protected ColumnMonthlyRecurringCheckCategoriesSpec getColumnCheckCategories(ColumnSpec targetColumn) {
        ColumnRecurringChecksRootSpec recurringChecksSpec = targetColumn.getRecurringChecks();
        if (recurringChecksSpec == null) {
            recurringChecksSpec = new ColumnRecurringChecksRootSpec();
            targetColumn.setRecurringChecks(recurringChecksSpec);
        }

        ColumnMonthlyRecurringCheckCategoriesSpec checkCategoriesSpec = recurringChecksSpec.getMonthly();
        if (checkCategoriesSpec == null) {
            checkCategoriesSpec = new ColumnMonthlyRecurringCheckCategoriesSpec();
            recurringChecksSpec.setMonthly(checkCategoriesSpec);
        }

        return checkCategoriesSpec;
    }

    /**
     * Applies the checks on a target column, but only if the target column support that category of checks.
     * Non-numeric column data types (detected by the dialect settings) will not have numeric sensors applied.
     * @param targetColumn Target column.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnColumn(ColumnSpec targetColumn, ProviderDialectSettings dialectSettings) {
        DataTypeCategory dataTypeCategory = dialectSettings.detectColumnType(targetColumn.getTypeSnapshot());

        if (this.columnNulls != null && !this.columnNulls.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setNulls(this.columnNulls.deepClone());
        }

        if (this.columnNumeric != null && !this.columnNumeric.isDefault() && DataTypeCategory.isNumericType(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setNumeric(this.columnNumeric.deepClone());
        }

        if (this.columnStrings != null && !this.columnStrings.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setStrings(this.columnStrings.deepClone());
        }

        if (this.columnUniqueness != null && !this.columnUniqueness.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setUniqueness(this.columnUniqueness.deepClone());
        }

        if (this.columnDatetime != null && !this.columnDatetime.isDefault() && DataTypeCategory.hasDate(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setDatetime(this.columnDatetime.deepClone());
        }

        if (this.columnPii != null && !this.columnPii.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setPii(this.columnPii.deepClone());
        }

        if (this.columnBool != null && !this.columnBool.isDefault() && dataTypeCategory == DataTypeCategory.bool) {
            this.getColumnCheckCategories(targetColumn).setBool(this.columnBool.deepClone());
        }

        if (this.columnDatatype != null && !this.columnDatatype.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setDatatype(this.columnDatatype.deepClone());
        }

        if (this.columnAnomaly != null && !this.columnAnomaly.isDefault() && DataTypeCategory.isNumericType(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setAnomaly(this.columnAnomaly.deepClone());
        }

        if (this.columnSchema != null && !this.columnSchema.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setSchema(this.columnSchema.deepClone());
        }
    }

    /**
     * Returns the type of checks (profiling, recurring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return null;
    }

    /**
     * Returns the time scale for recurring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time scale and return null.
     *
     * @return Time scale (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }

    /**
     * Returns the check target, where the check could be applied.
     *
     * @return Check target, "table" or "column".
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return null;
    }

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     *
     * @return Recurring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunRecurringScheduleGroup getSchedulingGroup() {
        return null;
    }

    /**
     * Returns the comparisons container for table comparison checks, indexed by the reference table configuration name.
     *
     * @return Table comparison container.
     */
    @Override
    @JsonIgnore
    public AbstractComparisonCheckCategorySpecMap<?> getComparisons() {
        return null;
    }

    /**
     * Returns time series configuration for the given group of checks.
     *
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    @Override
    @JsonIgnore
    public TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec) {
        return null;
    }
}
