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
import com.dqops.checks.column.profiling.*;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
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
 * The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 * for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultProfilingColumnObservabilityCheckSettingsSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultProfilingColumnObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("numeric", o -> o.numeric);
            put("strings", o -> o.strings);
            put("uniqueness", o -> o.uniqueness);
            put("datetime", o -> o.datetime);
            put("pii", o -> o.pii);
            put("bool", o -> o.bool);
            put("datatype", o -> o.datatype);
            put("anomaly", o -> o.anomaly);
            put("schema", o -> o.schema);
        }
    };


    @JsonPropertyDescription("The default configuration of column level checks that verify nulls and blanks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsProfilingChecksSpec nulls;

    @JsonPropertyDescription("The default configuration of column level checks that verify negative values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericProfilingChecksSpec numeric;

    @JsonPropertyDescription("The default configuration of strings checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextProfilingChecksSpec strings;

    @JsonPropertyDescription("The default configuration of uniqueness checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessProfilingChecksSpec uniqueness;

    @JsonPropertyDescription("The default configuration of datetime checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeProfilingChecksSpec datetime;

    @JsonPropertyDescription("The default configuration of Personal Identifiable Information (PII) checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiProfilingChecksSpec pii;

    @JsonPropertyDescription("The default configuration of booleans checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolProfilingChecksSpec bool;

    @JsonPropertyDescription("The default configuration of datatype checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeProfilingChecksSpec datatype;

    @JsonPropertyDescription("The default configuration of anomaly checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyProfilingChecksSpec anomaly;

    @JsonPropertyDescription("The default configuration of schema checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaProfilingChecksSpec schema;


    /**
     * Returns the nulls check configuration on a column level.
     * @return Nulls check configuration.
     */
    public ColumnNullsProfilingChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the nulls check configuration on a column level.
     * @param nulls New nulls checks configuration.
     */
    public void setNulls(ColumnNullsProfilingChecksSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the negative values check configuration on a column level.
     * @return Negative values check configuration.
     */
    public ColumnNumericProfilingChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the negative values check configuration on a column level.
     * @param numeric New negative values checks configuration.
     */
    public void setNumeric(ColumnNumericProfilingChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the strings check configuration on a column level.
     * @return Strings check configuration.
     */
    public ColumnTextProfilingChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the string check configuration on a column level.
     * @param strings New string checks configuration.
     */
    public void setStrings(ColumnTextProfilingChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the uniqueness check configuration on a column level.
     * @return Uniqueness check configuration.
     */
    public ColumnUniquenessProfilingChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the uniqueness check configuration on a column level.
     * @param uniqueness New uniqueness checks configuration.
     */
    public void setUniqueness(ColumnUniquenessProfilingChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the datetime check configuration on a column level.
     * @return Datetime check configuration.
     */
    public ColumnDatetimeProfilingChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the datetime check configuration on a column level.
     * @param datetime New datetime checks configuration.
     */
    public void setDatetime(ColumnDatetimeProfilingChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the Personal Identifiable Information (PII) check configuration on a column level.
     * @return Personal Identifiable Information (PII) check configuration.
     */
    public ColumnPiiProfilingChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the Personal Identifiable Information (PII) check configuration on a column level.
     * @param pii New Personal Identifiable Information (PII) checks configuration.
     */
    public void setPii(ColumnPiiProfilingChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the booleans check configuration on a column level.
     * @return Boolean check configuration.
     */
    public ColumnBoolProfilingChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets the boolean check configuration on a column level.
     * @param bool New boolean checks configuration.
     */
    public void setBool(ColumnBoolProfilingChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        this.propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns the datatype check configuration on a column level.
     * @return Datatype check configuration.
     */
    public ColumnDatatypeProfilingChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets the datatype check configuration on a column level.
     * @param datatype New datatype checks configuration.
     */
    public void setDatatype(ColumnDatatypeProfilingChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        this.propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns the anomaly check configuration on a column level.
     * @return Anomaly check configuration.
     */
    public ColumnAnomalyProfilingChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets the anomaly check configuration on a column level.
     * @param anomaly New anomaly checks configuration.
     */
    public void setAnomaly(ColumnAnomalyProfilingChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        this.propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the schema checks container on a column level.
     * @return Schema checks container.
     */
    public ColumnSchemaProfilingChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets teh schema checks container for column level checks.
     * @param schema Schema checks container.
     */
    public void setSchema(ColumnSchemaProfilingChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
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
     * Retrieves or creates and retrieves the check categories class on a column.
     * @param targetColumn Target column.
     * @return Check categories.
     */
    protected ColumnProfilingCheckCategoriesSpec getColumnCheckCategories(ColumnSpec targetColumn) {
        ColumnProfilingCheckCategoriesSpec checkCategoriesSpec = targetColumn.getProfilingChecks();
        if (checkCategoriesSpec == null) {
            checkCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
            targetColumn.setProfilingChecks(checkCategoriesSpec);
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

        if (this.nulls != null && !this.nulls.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setNulls(this.nulls.deepClone());
        }

        if (this.numeric != null && !this.numeric.isDefault() && DataTypeCategory.isNumericType(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setNumeric(this.numeric.deepClone());
        }

        if (this.strings != null && !this.strings.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setText(this.strings.deepClone());
        }

        if (this.uniqueness != null && !this.uniqueness.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setUniqueness(this.uniqueness.deepClone());
        }

        if (this.datetime != null && !this.datetime.isDefault() && DataTypeCategory.hasDate(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setDatetime(this.datetime.deepClone());
        }

        if (this.pii != null && !this.pii.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setPii(this.pii.deepClone());
        }

        if (this.bool != null && !this.bool.isDefault() && dataTypeCategory == DataTypeCategory.bool) {
            this.getColumnCheckCategories(targetColumn).setBool(this.bool.deepClone());
        }

        if (this.datatype != null && !this.datatype.isDefault() && dataTypeCategory == DataTypeCategory.string) {
            this.getColumnCheckCategories(targetColumn).setDatatype(this.datatype.deepClone());
        }

        if (this.anomaly != null && !this.anomaly.isDefault() && DataTypeCategory.isNumericType(dataTypeCategory)) {
            this.getColumnCheckCategories(targetColumn).setAnomaly(this.anomaly.deepClone());
        }

        if (this.schema != null && !this.schema.isDefault()) {
            this.getColumnCheckCategories(targetColumn).setSchema(this.schema.deepClone());
        }

        if (this.getCustom() != null && !this.getCustom().isEmpty()) {
            this.getColumnCheckCategories(targetColumn).setCustom(this.getCustom().deepClone());
        }
    }

    /**
     * Returns the type of checks (profiling, monitoring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.profiling;
    }

    /**
     * Returns the time scale for monitoring and partitioned checks (daily, monthly, etc.).
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
        return CheckTarget.table;
    }

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     *
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunScheduleGroup getSchedulingGroup() {
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
