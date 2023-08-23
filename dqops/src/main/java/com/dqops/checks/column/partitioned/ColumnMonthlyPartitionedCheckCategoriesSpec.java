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
package com.dqops.checks.column.partitioned;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.partitioned.accuracy.ColumnAccuracyMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.anomaly.ColumnAnomalyMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.bool.ColumnBoolMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.comparison.ColumnComparisonMonthlyPartitionedChecksSpecMap;
import com.dqops.checks.column.partitioned.datatype.ColumnDatatypeMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datetime.ColumnDatetimeMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.integrity.ColumnIntegrityMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.nulls.ColumnNullsMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.numeric.ColumnNumericMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.pii.ColumnPiiMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.sql.ColumnSqlMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.strings.ColumnStringsMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.uniqueness.ColumnUniquenessMonthlyPartitionedChecksSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
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
 * Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMonthlyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMonthlyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Monthly partitioned checks of nulls values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsMonthlyPartitionedChecksSpec nulls;

    @JsonPropertyDescription("Monthly partitioned checks of numeric values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMonthlyPartitionedChecksSpec numeric;

    @JsonPropertyDescription("Monthly partitioned checks of strings values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsMonthlyPartitionedChecksSpec strings;

    @JsonPropertyDescription("Monthly partitioned checks of uniqueness values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessMonthlyPartitionedChecksSpec uniqueness;

    @JsonPropertyDescription("Monthly partitioned checks of datetime values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeMonthlyPartitionedChecksSpec datetime;

    @JsonPropertyDescription("Monthly partitioned checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiMonthlyPartitionedChecksSpec pii;

    @JsonPropertyDescription("Monthly partitioned checks using custom SQL expressions and conditions on the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlMonthlyPartitionedChecksSpec sql;

    @JsonPropertyDescription("Monthly partitioned checks for booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolMonthlyPartitionedChecksSpec bool;

    @JsonPropertyDescription("Monthly partitioned checks for integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityMonthlyPartitionedChecksSpec integrity;

    @JsonPropertyDescription("Monthly partitioned checks for accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyMonthlyPartitionedChecksSpec accuracy;

    @JsonPropertyDescription("Monthly partitioned checks for datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeMonthlyPartitionedChecksSpec datatype;

    @JsonPropertyDescription("Monthly partitioned checks for anomaly in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyMonthlyPartitionedChecksSpec anomaly;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonMonthlyPartitionedChecksSpecMap comparisons = new ColumnComparisonMonthlyPartitionedChecksSpecMap();

    /**
     * Returns the container of monthly null data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnNullsMonthlyPartitionedChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of monthly null data quality partitioned checks.
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsMonthlyPartitionedChecksSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of monthly numeric data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnNumericMonthlyPartitionedChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of monthly numeric data quality partitioned checks.
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericMonthlyPartitionedChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of monthly strings data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnStringsMonthlyPartitionedChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of monthly strings data quality partitioned checks.
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsMonthlyPartitionedChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of monthly uniqueness data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnUniquenessMonthlyPartitionedChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of monthly uniqueness data quality partitioned checks.
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessMonthlyPartitionedChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of monthly datetime data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnDatetimeMonthlyPartitionedChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of monthly datetime data quality partitioned checks.
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeMonthlyPartitionedChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of monthly Personal Identifiable Information (PII) data quality partitioned checks.
     * @return Container of row standard monthly data quality partitioned checks.
     */
    public ColumnPiiMonthlyPartitionedChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of monthly Personal Identifiable Information (PII) data quality partitioned checks.
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiMonthlyPartitionedChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns a container of custom SQL checks on a column.
     * @return Custom SQL checks.
     */
    public ColumnSqlMonthlyPartitionedChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Custom SQL checks container.
     */
    public void setSql(ColumnSqlMonthlyPartitionedChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns a container of custom boolean checks on a column.
     * @return Custom boolean checks.
     */
    public ColumnBoolMonthlyPartitionedChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets a reference to a container of custom boolean checks.
     * @param bool Custom boolean checks.
     */
    public void setBool(ColumnBoolMonthlyPartitionedChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns a container of custom integrity checks on a column.
     * @return Custom integrity checks.
     */
    public ColumnIntegrityMonthlyPartitionedChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets a reference to a container of custom integrity checks.
     * @param integrity Custom integrity checks.
     */
    public void setIntegrity(ColumnIntegrityMonthlyPartitionedChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
     */
    public ColumnAccuracyMonthlyPartitionedChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a reference to a container of custom accuracy checks.
     * @param accuracy Custom accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyMonthlyPartitionedChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of custom datatype checks on a column.
     * @return Custom datatype checks.
     */
    public ColumnDatatypeMonthlyPartitionedChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets a reference to a container of custom datatype checks.
     * @param datatype Custom datatype checks.
     */
    public void setDatatype(ColumnDatatypeMonthlyPartitionedChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns a container of custom anomaly checks on a column.
     * @return Custom anomaly checks.
     */
    public ColumnAnomalyMonthlyPartitionedChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets a reference to a container of custom anomaly checks.
     * @param anomaly Custom anomaly checks.
     */
    public void setAnomaly(ColumnAnomalyMonthlyPartitionedChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
    public ColumnComparisonMonthlyPartitionedChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonMonthlyPartitionedChecksSpecMap comparisons) {
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
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.month);
            setTimestampColumn(tableSpec.getTimestampColumns().getPartitionByColumn());
        }};
    }

    /**
     * Returns the type of checks (profiling, monitoring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Returns the time range for monitoring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time range and return null.
     *
     * @return Time range (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
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
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunScheduleGroup getSchedulingGroup() {
        return CheckRunScheduleGroup.partitioned_monthly;
    }
}
