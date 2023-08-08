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
package com.dqops.checks.column.recurring;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.recurring.accuracy.ColumnAccuracyDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.anomaly.ColumnAnomalyDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.bool.ColumnBoolDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.comparison.ColumnComparisonDailyRecurringChecksSpecMap;
import com.dqops.checks.column.recurring.datatype.ColumnDatatypeDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.datetime.ColumnDatetimeDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.integrity.ColumnIntegrityDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.nulls.ColumnNullsDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.numeric.ColumnNumericDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.pii.ColumnPiiDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.schema.ColumnSchemaDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.sql.ColumnSqlDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.strings.ColumnStringsDailyRecurringChecksSpec;
import com.dqops.checks.column.recurring.uniqueness.ColumnUniquenessDailyRecurringChecksSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
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
 * Container of column level daily recurring checks. Contains categories of daily recurring checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyRecurringCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyRecurringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("numeric", o -> o.numeric);
            put("strings", o -> o.strings);
            put("uniqueness", o -> o.uniqueness);
            put("datetime", o -> o.datetime);
            put("pii", o -> o.pii);
            put("sql", o -> o.sql);
            put("bool", o -> o.bool);
            put("integrity", o -> o.integrity);
            put("accuracy", o -> o.accuracy);
            put("datatype", o -> o.datatype);
            put("anomaly", o -> o.anomaly);
            put("schema", o -> o.schema);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Daily recurring checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyRecurringChecksSpec nulls;

    @JsonPropertyDescription("Daily recurring checks of numeric in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyRecurringChecksSpec numeric;

    @JsonPropertyDescription("Daily recurring checks of strings in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsDailyRecurringChecksSpec strings;

    @JsonPropertyDescription("Daily recurring checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyRecurringChecksSpec uniqueness;

    @JsonPropertyDescription("Daily recurring checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyRecurringChecksSpec datetime;

    @JsonPropertyDescription("Daily recurring checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyRecurringChecksSpec pii;

    @JsonPropertyDescription("Daily recurring checks of custom SQL checks in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlDailyRecurringChecksSpec sql;

    @JsonPropertyDescription("Daily recurring checks of booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolDailyRecurringChecksSpec bool;

    @JsonPropertyDescription("Daily recurring checks of integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityDailyRecurringChecksSpec integrity;

    @JsonPropertyDescription("Daily recurring checks of accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyDailyRecurringChecksSpec accuracy;

    @JsonPropertyDescription("Daily recurring checks of datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeDailyRecurringChecksSpec datatype;

    @JsonPropertyDescription("Daily recurring checks of anomaly in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyDailyRecurringChecksSpec anomaly;

    @JsonPropertyDescription("Daily recurring column schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaDailyRecurringChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonDailyRecurringChecksSpecMap comparisons = new ColumnComparisonDailyRecurringChecksSpecMap();

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnNullsDailyRecurringChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of nulls data quality checks (recurring).
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsDailyRecurringChecksSpec nulls) {
		this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
		this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnNumericDailyRecurringChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of numeric data quality checks (recurring).
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericDailyRecurringChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnStringsDailyRecurringChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of strings data quality checks (recurring).
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsDailyRecurringChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnUniquenessDailyRecurringChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of uniqueness data quality checks (recurring).
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessDailyRecurringChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnDatetimeDailyRecurringChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of datetime data quality checks (recurring).
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeDailyRecurringChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnPiiDailyRecurringChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of Personal Identifiable Information (PII) data quality checks (recurring).
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiDailyRecurringChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the container of custom SQL checks that use custom SQL expressions in checks.
     * @return Custom SQL checks.
     */
    public ColumnSqlDailyRecurringChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Custom SQL checks.
     */
    public void setSql(ColumnSqlDailyRecurringChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnBoolDailyRecurringChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets the container of booleans data quality checks (recurring).
     * @param bool New booleans checks.
     */
    public void setBool(ColumnBoolDailyRecurringChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        this.propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnIntegrityDailyRecurringChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets the container of integrity data quality checks (recurring).
     * @param integrity New integrity checks.
     */
    public void setIntegrity(ColumnIntegrityDailyRecurringChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        this.propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnAccuracyDailyRecurringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the container of accuracy data quality checks (recurring).
     * @param accuracy New accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyDailyRecurringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnDatatypeDailyRecurringChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets the container of datatype data quality checks (recurring).
     * @param datatype New datatype checks.
     */
    public void setDatatype(ColumnDatatypeDailyRecurringChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        this.propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of anomaly data quality recurring.
     */
    public ColumnAnomalyDailyRecurringChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets the container of anomaly data quality checks (recurring).
     * @param anomaly New anomaly checks.
     */
    public void setAnomaly(ColumnAnomalyDailyRecurringChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        this.propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the container of daily recurring column schema checks.
     * @return Container of column schema checks.
     */
    public ColumnSchemaDailyRecurringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets the container of daily recurring schema checks.
     * @param schema Container of schema checks.
     */
    public void setSchema(ColumnSchemaDailyRecurringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
    public ColumnComparisonDailyRecurringChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonDailyRecurringChecksSpecMap comparisons) {
        this.setDirtyIf(!Objects.equals(this.comparisons, comparisons));
        this.comparisons = comparisons;
        this.propagateHierarchyIdToField(comparisons, "comparisons");
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
     * Returns time series configuration for the given group of checks.
     *
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    @Override
    public TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec) {
        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimePeriodGradient.day);
        }};
    }

    /**
     * Returns the type of checks (profiling, recurring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.recurring;
    }

    /**
     * Returns the time range for recurring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time range and return null.
     *
     * @return Time range (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }

    /**
     * Returns the check target, where the check could be applied.
     *
     * @return Check target, "table" or "column".
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
    }

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     *
     * @return Recurring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunRecurringScheduleGroup getSchedulingGroup() {
        return CheckRunRecurringScheduleGroup.recurring_daily;
    }
}
