/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.partitioned;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.partitioned.accuracy.ColumnAccuracyDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.anomaly.ColumnAnomalyDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.bool.ColumnBoolDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.comparison.ColumnComparisonDailyPartitionedChecksSpecMap;
import ai.dqo.checks.column.partitioned.consistency.ColumnConsistencyDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.datetime.ColumnDatetimeDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.integrity.ColumnIntegrityDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.nulls.ColumnNullsDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.numeric.ColumnNumericDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.pii.ColumnPiiDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.sql.ColumnSqlDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.strings.ColumnStringsDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.uniqueness.ColumnUniquenessDailyPartitionedChecksSpec;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.timeseries.TimeSeriesMode;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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
            put("consistency", o -> o.consistency);
            put("anomaly", o -> o.anomaly);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Daily partitioned checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyPartitionedChecksSpec nulls;

    @JsonPropertyDescription("Daily partitioned checks of numeric in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyPartitionedChecksSpec numeric;

    @JsonPropertyDescription("Daily partitioned checks of strings in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsDailyPartitionedChecksSpec strings;

    @JsonPropertyDescription("Daily partitioned checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyPartitionedChecksSpec uniqueness;

    @JsonPropertyDescription("Daily partitioned checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyPartitionedChecksSpec datetime;

    @JsonPropertyDescription("Daily partitioned checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyPartitionedChecksSpec pii;

    @JsonPropertyDescription("Daily partitioned checks using custom SQL expressions evaluated on the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlDailyPartitionedChecksSpec sql;

    @JsonPropertyDescription("Daily partitioned checks for booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolDailyPartitionedChecksSpec bool;

    @JsonPropertyDescription("Daily partitioned checks for integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityDailyPartitionedChecksSpec integrity;

    @JsonPropertyDescription("Daily partitioned checks for accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyDailyPartitionedChecksSpec accuracy;

    @JsonPropertyDescription("Daily partitioned checks for consistency in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConsistencyDailyPartitionedChecksSpec consistency;

    @JsonPropertyDescription("Daily partitioned checks for anomaly in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyDailyPartitionedChecksSpec anomaly;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonDailyPartitionedChecksSpecMap comparisons = new ColumnComparisonDailyPartitionedChecksSpecMap();

    /**
     * Returns the container of daily null data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnNullsDailyPartitionedChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of daily null data quality partitioned checks.
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsDailyPartitionedChecksSpec nulls) {
		this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of daily numeric data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnNumericDailyPartitionedChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of daily numeric data quality partitioned checks.
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericDailyPartitionedChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of daily strings data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnStringsDailyPartitionedChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of daily strings data quality partitioned checks.
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsDailyPartitionedChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of daily uniqueness data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnUniquenessDailyPartitionedChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of daily uniqueness data quality partitioned checks.
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessDailyPartitionedChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of daily datetime data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnDatetimeDailyPartitionedChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of daily datetime data quality partitioned checks.
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeDailyPartitionedChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of daily Personal Identifiable Information (PII) data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnPiiDailyPartitionedChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of daily Personal Identifiable Information (PII) data quality partitioned checks.
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiDailyPartitionedChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns a container of custom SQL checks on a column.
     * @return Custom SQL checks.
     */
    public ColumnSqlDailyPartitionedChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Custom SQL checks.
     */
    public void setSql(ColumnSqlDailyPartitionedChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns a container of custom boolean checks on a column.
     * @return Custom boolean checks.
     */
    public ColumnBoolDailyPartitionedChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets a reference to a container of custom boolean checks.
     * @param bool Custom boolean checks.
     */
    public void setBool(ColumnBoolDailyPartitionedChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns a container of custom integrity checks on a column.
     * @return Custom integrity checks.
     */
    public ColumnIntegrityDailyPartitionedChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets a reference to a container of custom integrity checks.
     * @param integrity Custom integrity checks.
     */
    public void setIntegrity(ColumnIntegrityDailyPartitionedChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
     */
    public ColumnAccuracyDailyPartitionedChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets a reference to a container of custom accuracy checks.
     * @param accuracy Custom accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyDailyPartitionedChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
     */
    public ColumnConsistencyDailyPartitionedChecksSpec getConsistency() {
        return consistency;
    }

    /**
     * Sets a reference to a container of custom consistency checks.
     * @param consistency Custom consistency checks.
     */
    public void setConsistency(ColumnConsistencyDailyPartitionedChecksSpec consistency) {
        this.setDirtyIf(!Objects.equals(this.consistency, consistency));
        this.consistency = consistency;
        propagateHierarchyIdToField(consistency, "consistency");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
     */
    public ColumnAnomalyDailyPartitionedChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets a reference to a container of custom anomaly checks.
     * @param anomaly Custom anomaly checks.
     */
    public void setAnomaly(ColumnAnomalyDailyPartitionedChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    public ColumnComparisonDailyPartitionedChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonDailyPartitionedChecksSpecMap comparisons) {
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
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn(tableSpec.getTimestampColumns().getPartitionByColumn());
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
        return CheckType.PARTITIONED;
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
        return CheckRunRecurringScheduleGroup.partitioned_daily;
    }
}
