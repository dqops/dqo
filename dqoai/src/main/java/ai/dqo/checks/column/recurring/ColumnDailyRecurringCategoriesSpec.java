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
package ai.dqo.checks.column.recurring;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.recurring.accuracy.ColumnAccuracyDailyRecurringSpec;
import ai.dqo.checks.column.recurring.bool.ColumnBoolDailyRecurringSpec;
import ai.dqo.checks.column.recurring.datetime.ColumnDatetimeDailyRecurringSpec;
import ai.dqo.checks.column.recurring.nulls.ColumnNullsDailyRecurringSpec;
import ai.dqo.checks.column.recurring.numeric.ColumnNumericDailyRecurringSpec;
import ai.dqo.checks.column.recurring.pii.ColumnPiiDailyRecurringSpec;
import ai.dqo.checks.column.recurring.sql.ColumnSqlDailyRecurringSpec;
import ai.dqo.checks.column.recurring.strings.ColumnStringsDailyRecurringSpec;
import ai.dqo.checks.column.recurring.uniqueness.ColumnUniquenessDailyRecurringSpec;
import ai.dqo.checks.column.recurring.integrity.ColumnIntegrityDailyRecurringSpec;
import ai.dqo.checks.column.recurring.consistency.ColumnConsistencyDailyRecurringSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.groupings.TimeSeriesMode;
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
 * Container of column level daily recurring. Contains categories of daily recurring.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyRecurringCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyRecurringCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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

        }
    };

    @JsonPropertyDescription("Daily recurring of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyRecurringSpec nulls;

    @JsonPropertyDescription("Daily recurring of numeric in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyRecurringSpec numeric;

    @JsonPropertyDescription("Daily recurring of strings in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsDailyRecurringSpec strings;

    @JsonPropertyDescription("Daily recurring of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyRecurringSpec uniqueness;

    @JsonPropertyDescription("Daily recurring of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyRecurringSpec datetime;

    @JsonPropertyDescription("Daily recurring of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyRecurringSpec pii;

    @JsonPropertyDescription("Daily recurring of custom SQL checks in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlDailyRecurringSpec sql;

    @JsonPropertyDescription("Daily recurring of booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolDailyRecurringSpec bool;

    @JsonPropertyDescription("Daily recurring of integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityDailyRecurringSpec integrity;

    @JsonPropertyDescription("Daily recurring of accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyDailyRecurringSpec accuracy;

    @JsonPropertyDescription("Daily recurring of consistency in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConsistencyDailyRecurringSpec consistency;

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnNullsDailyRecurringSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of nulls data quality checks (recurring).
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsDailyRecurringSpec nulls) {
		this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
		this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnNumericDailyRecurringSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of numeric data quality checks (recurring).
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericDailyRecurringSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnStringsDailyRecurringSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of strings data quality checks (recurring).
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsDailyRecurringSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnUniquenessDailyRecurringSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of uniqueness data quality checks (recurring).
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessDailyRecurringSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnDatetimeDailyRecurringSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of datetime data quality checks (recurring).
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeDailyRecurringSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnPiiDailyRecurringSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of Personal Identifiable Information (PII) data quality checks (recurring).
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiDailyRecurringSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the container of custom SQL checks that use custom SQL expressions in checks.
     * @return Custom SQL checks.
     */
    public ColumnSqlDailyRecurringSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Custom SQL checks.
     */
    public void setSql(ColumnSqlDailyRecurringSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnBoolDailyRecurringSpec getBool() {
        return bool;
    }

    /**
     * Sets the container of booleans data quality checks (recurring).
     * @param bool New booleans checks.
     */
    public void setBool(ColumnBoolDailyRecurringSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        this.propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnIntegrityDailyRecurringSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets the container of integrity data quality checks (recurring).
     * @param integrity New integrity checks.
     */
    public void setIntegrity(ColumnIntegrityDailyRecurringSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        this.propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnAccuracyDailyRecurringSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the container of accuracy data quality checks (recurring).
     * @param accuracy New accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyDailyRecurringSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns the container of recurring for standard data quality checks.
     * @return Container of row standard data quality recurring.
     */
    public ColumnConsistencyDailyRecurringSpec getConsistency() {
        return consistency;
    }

    /**
     * Sets the container of consistency data quality checks (recurring).
     * @param consistency New consistency checks.
     */
    public void setConsistency(ColumnConsistencyDailyRecurringSpec consistency) {
        this.setDirtyIf(!Objects.equals(this.consistency, consistency));
        this.consistency = consistency;
        this.propagateHierarchyIdToField(consistency, "consistency");
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
            setTimeGradient(TimeSeriesGradient.day);
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
        return CheckType.RECURRING;
    }

    /**
     * Returns the time range for recurring and partitioned checks (daily, monthly, etc.).
     * Adhoc checks do not have a time range and return null.
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
