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
package com.dqops.checks.column.monitoring;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.monitoring.accuracy.ColumnAccuracyMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.bool.ColumnBoolMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.comparison.ColumnComparisonMonthlyMonitoringChecksSpecMap;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datetime.ColumnDatetimeMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.integrity.ColumnIntegrityMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.numeric.ColumnNumericMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.pii.ColumnPiiMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.sql.ColumnSqlMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.strings.ColumnStringsMonthlyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.uniqueness.ColumnUniquenessMonthlyMonitoringChecksSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.utils.docs.generators.SampleValueFactory;
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
 * Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMonthlyMonitoringCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMonthlyMonitoringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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

    @JsonPropertyDescription("Monthly monitoring checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsMonthlyMonitoringChecksSpec nulls;

    @JsonPropertyDescription("Monthly monitoring checks of numeric in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMonthlyMonitoringChecksSpec numeric;

    @JsonPropertyDescription("Monthly monitoring checks of strings in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsMonthlyMonitoringChecksSpec strings;

    @JsonPropertyDescription("Monthly monitoring checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessMonthlyMonitoringChecksSpec uniqueness;

    @JsonPropertyDescription("Monthly monitoring checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeMonthlyMonitoringChecksSpec datetime;

    @JsonPropertyDescription("Monthly monitoring checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiMonthlyMonitoringChecksSpec pii;

    @JsonPropertyDescription("Monthly monitoring checks of custom SQL checks in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlMonthlyMonitoringChecksSpec sql;

    @JsonPropertyDescription("Monthly monitoring checks of booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolMonthlyMonitoringChecksSpec bool;

    @JsonPropertyDescription("Monthly monitoring checks of integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityMonthlyMonitoringChecksSpec integrity;

    @JsonPropertyDescription("Monthly monitoring checks of accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyMonthlyMonitoringChecksSpec accuracy;

    @JsonPropertyDescription("Monthly monitoring checks of datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeMonthlyMonitoringChecksSpec datatype;

    @JsonPropertyDescription("Monthly monitoring checks of anomaly in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyMonthlyMonitoringChecksSpec anomaly;

    @JsonPropertyDescription("Monthly monitoring column schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaMonthlyMonitoringChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonMonthlyMonitoringChecksSpecMap comparisons = new ColumnComparisonMonthlyMonitoringChecksSpecMap();


    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnNullsMonthlyMonitoringChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of nulls data quality checks (monitoring).
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsMonthlyMonitoringChecksSpec nulls) {
		this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
		this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnNumericMonthlyMonitoringChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of numeric data quality checks (monitoring).
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericMonthlyMonitoringChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnStringsMonthlyMonitoringChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the container of strings data quality checks (monitoring).
     * @param strings New strings checks.
     */
    public void setStrings(ColumnStringsMonthlyMonitoringChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnUniquenessMonthlyMonitoringChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of uniqueness data quality checks (monitoring).
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessMonthlyMonitoringChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnDatetimeMonthlyMonitoringChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of datetime data quality checks (monitoring).
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeMonthlyMonitoringChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnPiiMonthlyMonitoringChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of Personal Identifiable Information (PII) data quality checks (monitoring).
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiMonthlyMonitoringChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns a container of custom SQL checks.
     * @return Custom SQL checks.
     */
    public ColumnSqlMonthlyMonitoringChecksSpec getSql() {
        return sql;
    }

    /**
     * Sets a reference to a container with custom SQL checks.
     * @param sql Custom SQL checks.
     */
    public void setSql(ColumnSqlMonthlyMonitoringChecksSpec sql) {
        this.setDirtyIf(!Objects.equals(this.sql, sql));
        this.sql = sql;
        this.propagateHierarchyIdToField(sql, "sql");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnBoolMonthlyMonitoringChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets the container of booleans data quality checks (monitoring).
     * @param bool New booleans checks.
     */
    public void setBool(ColumnBoolMonthlyMonitoringChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        this.propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnIntegrityMonthlyMonitoringChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets the container of integrity data quality checks (monitoring).
     * @param integrity New integrity checks.
     */
    public void setIntegrity(ColumnIntegrityMonthlyMonitoringChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        this.propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnAccuracyMonthlyMonitoringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the container of accuracy data quality checks (monitoring).
     * @param accuracy New accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyMonthlyMonitoringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnDatatypeMonthlyMonitoringChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets the container of datatype data quality checks (monitoring).
     * @param datatype New datatype checks.
     */
    public void setDatatype(ColumnDatatypeMonthlyMonitoringChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        this.propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of anomaly data quality monitoring checks.
     */
    public ColumnAnomalyMonthlyMonitoringChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets the container of anomaly data quality checks (monitoring).
     * @param anomaly New anomaly checks.
     */
    public void setAnomaly(ColumnAnomalyMonthlyMonitoringChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        this.propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the container of monthly monitoring column schema checks.
     * @return Container of column schema checks.
     */
    public ColumnSchemaMonthlyMonitoringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets a new container of column schema checks (monthly).
     * @param schema Container of monthly monitoring column schema checks.
     */
    public void setSchema(ColumnSchemaMonthlyMonitoringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
    public ColumnComparisonMonthlyMonitoringChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonMonthlyMonitoringChecksSpecMap comparisons) {
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
            setTimeGradient(TimePeriodGradient.month);
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
        return CheckType.monitoring;
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
        return CheckRunScheduleGroup.monitoring_monthly;
    }

    public static class ColumnMonthlyMonitoringCheckCategoriesSpecSampleFactory implements SampleValueFactory<ColumnMonthlyMonitoringCheckCategoriesSpec> {
        @Override
        public ColumnMonthlyMonitoringCheckCategoriesSpec createSample() {
            return new ColumnMonthlyMonitoringCheckCategoriesSpec() {{
                setNulls(new ColumnNullsMonthlyMonitoringChecksSpec.ColumnNullsMonthlyMonitoringChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
