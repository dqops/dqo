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

import com.dqops.checks.column.profiling.*;
import com.dqops.checks.table.profiling.TableAvailabilityProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableSchemaProfilingChecksSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of checks that are enabled as data observability advanced profiling checks that will be detecting anomalies
 * for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultProfilingObservabilityCheckSettingsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultProfilingObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
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
            put("column_consistency", o -> o.columnConsistency);
            put("column_anomaly", o -> o.columnAnomaly);
            put("column_schema", o -> o.columnSchema);
        }
    };

    @JsonPropertyDescription("The default configuration of volume data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeProfilingChecksSpec tableVolume;

    @JsonPropertyDescription("The default configuration of the table availability data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityProfilingChecksSpec tableAvailability;

    @JsonPropertyDescription("The default configuration of schema (column count and schema) data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaProfilingChecksSpec tableSchema;

    @JsonPropertyDescription("The default configuration of column level checks that verify nulls and blanks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsProfilingChecksSpec columnNulls;

    @JsonPropertyDescription("The default configuration of column level checks that verify negative values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericProfilingChecksSpec columnNumeric;

    @JsonPropertyDescription("The default configuration of strings checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsProfilingChecksSpec columnStrings;

    @JsonPropertyDescription("The default configuration of uniqueness checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessProfilingChecksSpec columnUniqueness;

    @JsonPropertyDescription("The default configuration of datetime checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeProfilingChecksSpec columnDatetime;

    @JsonPropertyDescription("The default configuration of Personal Identifiable Information (PII) checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiProfilingChecksSpec columnPii;

    @JsonPropertyDescription("The default configuration of booleans checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolProfilingChecksSpec columnBool;

    @JsonPropertyDescription("The default configuration of consistency checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConsistencyProfilingChecksSpec columnConsistency;

    @JsonPropertyDescription("The default configuration of anomaly checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyProfilingChecksSpec columnAnomaly;

    @JsonPropertyDescription("The default configuration of schema checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaProfilingChecksSpec columnSchema;

    /**
     * Returns a container of volume check configuration on a table level.
     * @return Volume checks configuration.
     */
    public TableVolumeProfilingChecksSpec getTableVolume() {
        return tableVolume;
    }

    /**
     * Sets a reference to a volume checks container.
     * @param tableVolume New volume checks configuration.
     */
    public void setTableVolume(TableVolumeProfilingChecksSpec tableVolume) {
        this.setDirtyIf(!Objects.equals(this.tableVolume, tableVolume));
        this.tableVolume = tableVolume;
        this.propagateHierarchyIdToField(tableVolume, "table_volume");
    }

    /**
     * Returns a container of the table availability checks.
     * @return Table availability checks.
     */
    public TableAvailabilityProfilingChecksSpec getTableAvailability() {
        return tableAvailability;
    }

    /**
     * Sets a reference to an availability checks container.
     * @param tableAvailability Table availability checks.
     */
    public void setTableAvailability(TableAvailabilityProfilingChecksSpec tableAvailability) {
        this.setDirtyIf(!Objects.equals(this.tableAvailability, tableAvailability));
        this.tableAvailability = tableAvailability;
        this.propagateHierarchyIdToField(tableAvailability, "table_availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaProfilingChecksSpec getTableSchema() {
        return tableSchema;
    }

    /**
     * Sets the reference to the table schema checks.
     * @param tableSchema Table schema checks container.
     */
    public void setTableSchema(TableSchemaProfilingChecksSpec tableSchema) {
        this.setDirtyIf(!Objects.equals(this.tableSchema, tableSchema));
        this.tableSchema = tableSchema;
        this.propagateHierarchyIdToField(tableSchema, "table_schema");
    }


    /**
     * Returns the nulls check configuration on a column level.
     * @return Nulls check configuration.
     */
    public ColumnNullsProfilingChecksSpec getColumnNulls() {
        return columnNulls;
    }

    /**
     * Sets the nulls check configuration on a column level.
     * @param columnNulls New nulls checks configuration.
     */
    public void setColumnNulls(ColumnNullsProfilingChecksSpec columnNulls) {
        this.setDirtyIf(!Objects.equals(this.columnNulls, columnNulls));
        this.columnNulls = columnNulls;
        this.propagateHierarchyIdToField(columnNulls, "column_nulls");
    }

    /**
     * Returns the negative values check configuration on a column level.
     * @return Negative values check configuration.
     */
    public ColumnNumericProfilingChecksSpec getColumnNumeric() {
        return columnNumeric;
    }

    /**
     * Sets the negative values check configuration on a column level.
     * @param columnNumeric New negative values checks configuration.
     */
    public void setColumnNumeric(ColumnNumericProfilingChecksSpec columnNumeric) {
        this.setDirtyIf(!Objects.equals(this.columnNumeric, columnNumeric));
        this.columnNumeric = columnNumeric;
        this.propagateHierarchyIdToField(columnNumeric, "column_numeric");
    }

    /**
     * Returns the strings check configuration on a column level.
     * @return Strings check configuration.
     */
    public ColumnStringsProfilingChecksSpec getColumnStrings() {
        return columnStrings;
    }

    /**
     * Sets the string check configuration on a column level.
     * @param columnStrings New string checks configuration.
     */
    public void setColumnStrings(ColumnStringsProfilingChecksSpec columnStrings) {
        this.setDirtyIf(!Objects.equals(this.columnStrings, columnStrings));
        this.columnStrings = columnStrings;
        this.propagateHierarchyIdToField(columnStrings, "column_strings");
    }

    /**
     * Returns the uniqueness check configuration on a column level.
     * @return Uniqueness check configuration.
     */
    public ColumnUniquenessProfilingChecksSpec getColumnUniqueness() {
        return columnUniqueness;
    }

    /**
     * Sets the uniqueness check configuration on a column level.
     * @param columnUniqueness New uniqueness checks configuration.
     */
    public void setColumnUniqueness(ColumnUniquenessProfilingChecksSpec columnUniqueness) {
        this.setDirtyIf(!Objects.equals(this.columnUniqueness, columnUniqueness));
        this.columnUniqueness = columnUniqueness;
        this.propagateHierarchyIdToField(columnUniqueness, "column_uniqueness");
    }

    /**
     * Returns the datetime check configuration on a column level.
     * @return Datetime check configuration.
     */
    public ColumnDatetimeProfilingChecksSpec getColumnDatetime() {
        return columnDatetime;
    }

    /**
     * Sets the datetime check configuration on a column level.
     * @param columnDatetime New datetime checks configuration.
     */
    public void setColumnDatetime(ColumnDatetimeProfilingChecksSpec columnDatetime) {
        this.setDirtyIf(!Objects.equals(this.columnDatetime, columnDatetime));
        this.columnDatetime = columnDatetime;
        this.propagateHierarchyIdToField(columnDatetime, "column_datetime");
    }

    /**
     * Returns the Personal Identifiable Information (PII) check configuration on a column level.
     * @return Personal Identifiable Information (PII) check configuration.
     */
    public ColumnPiiProfilingChecksSpec getColumnPii() {
        return columnPii;
    }

    /**
     * Sets the Personal Identifiable Information (PII) check configuration on a column level.
     * @param columnPii New Personal Identifiable Information (PII) checks configuration.
     */
    public void setColumnPii(ColumnPiiProfilingChecksSpec columnPii) {
        this.setDirtyIf(!Objects.equals(this.columnPii, columnPii));
        this.columnPii = columnPii;
        this.propagateHierarchyIdToField(columnPii, "column_pii");
    }

    /**
     * Returns the booleans check configuration on a column level.
     * @return Boolean check configuration.
     */
    public ColumnBoolProfilingChecksSpec getColumnBool() {
        return columnBool;
    }

    /**
     * Sets the boolean check configuration on a column level.
     * @param columnBool New boolean checks configuration.
     */
    public void setColumnBool(ColumnBoolProfilingChecksSpec columnBool) {
        this.setDirtyIf(!Objects.equals(this.columnBool, columnBool));
        this.columnBool = columnBool;
        this.propagateHierarchyIdToField(columnBool, "column_bool");
    }

    /**
     * Returns the consistency check configuration on a column level.
     * @return Consistency check configuration.
     */
    public ColumnConsistencyProfilingChecksSpec getColumnConsistency() {
        return columnConsistency;
    }

    /**
     * Sets the consistency check configuration on a column level.
     * @param columnConsistency New consistency checks configuration.
     */
    public void setColumnConsistency(ColumnConsistencyProfilingChecksSpec columnConsistency) {
        this.setDirtyIf(!Objects.equals(this.columnConsistency, columnConsistency));
        this.columnConsistency = columnConsistency;
        this.propagateHierarchyIdToField(columnConsistency, "column_consistency");
    }

    /**
     * Returns the anomaly check configuration on a column level.
     * @return Anomaly check configuration.
     */
    public ColumnAnomalyProfilingChecksSpec getColumnAnomaly() {
        return columnAnomaly;
    }

    /**
     * Sets the anomaly check configuration on a column level.
     * @param columnAnomaly New anomaly checks configuration.
     */
    public void setColumnAnomaly(ColumnAnomalyProfilingChecksSpec columnAnomaly) {
        this.setDirtyIf(!Objects.equals(this.columnAnomaly, columnAnomaly));
        this.columnAnomaly = columnAnomaly;
        this.propagateHierarchyIdToField(columnAnomaly, "column_anomaly");
    }

    /**
     * Returns the schema checks container on a column level.
     * @return Schema checks container.
     */
    public ColumnSchemaProfilingChecksSpec getColumnSchema() {
        return columnSchema;
    }

    /**
     * Sets teh schema checks container for column level checks.
     * @param columnSchema Schema checks container.
     */
    public void setColumnSchema(ColumnSchemaProfilingChecksSpec columnSchema) {
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
     * Applies the checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.tableVolume != null) {
            targetTable.getProfilingChecks().setVolume(this.tableVolume.deepClone());
        }

        if (this.tableAvailability != null) {
            targetTable.getProfilingChecks().setAvailability(this.tableAvailability.deepClone());
        }

        if (this.tableSchema != null) {
            targetTable.getProfilingChecks().setSchema(this.tableSchema.deepClone());
        }
    }
}
