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
package ai.dqo.checks.column.checkpoints;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.checkpoints.nulls.ColumnNullsDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.numeric.ColumnNumericDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.sql.ColumnSqlDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.strings.ColumnStringsDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.uniqueness.ColumnUniquenessDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.datetime.ColumnDatetimeDailyCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.pii.ColumnPiiDailyCheckpointsSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.groupings.TimeSeriesMode;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
 * Container of column level daily checkpoints. Contains categories of daily checkpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyCheckpointCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyCheckpointCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
           put("nulls", o -> o.nulls);
           put("numeric", o -> o.numeric);
           put("strings", o -> o.strings);
           put("uniqueness", o -> o.uniqueness);
           put("datetime", o -> o.datetime);
           put("pii", o -> o.pii);
           put("sql", o -> o.sql);
        }
    };

    @JsonPropertyDescription("Daily checkpoints of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyCheckpointsSpec nulls;

    @JsonPropertyDescription("Daily checkpoints of numeric in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyCheckpointsSpec numeric;

    @JsonPropertyDescription("Daily checkpoints of strings in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsDailyCheckpointsSpec strings;

    @JsonPropertyDescription("Daily checkpoints of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyCheckpointsSpec uniqueness;

    @JsonPropertyDescription("Daily checkpoints of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyCheckpointsSpec datetime;

    @JsonPropertyDescription("Daily checkpoints of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyCheckpointsSpec pii;

    @JsonPropertyDescription("Daily checkpoints of custom SQL checks in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlDailyCheckpointsSpec sql;

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnNullsDailyCheckpointsSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of nulls data quality checks (checkpoints).
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsDailyCheckpointsSpec nulls) {
		this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
		this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnNumericDailyCheckpointsSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of numeric data quality checks (checkpoints).
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericDailyCheckpointsSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnStringsDailyCheckpointsSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of strings data quality checks (checkpoints).
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsDailyCheckpointsSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnUniquenessDailyCheckpointsSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of uniqueness data quality checks (checkpoints).
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessDailyCheckpointsSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnDatetimeDailyCheckpointsSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of datetime data quality checks (checkpoints).
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeDailyCheckpointsSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of checkpoints for standard data quality checks.
     * @return Container of row standard data quality checkpoints.
     */
    public ColumnPiiDailyCheckpointsSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of Personal Identifiable Information (PII) data quality checks (checkpoints).
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiDailyCheckpointsSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the container of custom SQL checks that use custom SQL expressions in checks.
     * @return Custom SQL checks.
     */
    public ColumnSqlDailyCheckpointsSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param sql Custom SQL checks.
     */
    public void setSql(ColumnSqlDailyCheckpointsSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
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
            setTimeGradient(TimeSeriesGradient.DAY);
        }};
    }

    /**
     * Returns the type of checks (adhoc, checkpoint, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.CHECKPOINT;
    }

    /**
     * Returns the time range for checkpoint and partitioned checks (daily, monthly, etc.).
     * Adhoc checks do not have a time range and return null.
     *
     * @return Time range (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }
}
