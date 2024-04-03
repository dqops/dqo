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
import com.dqops.checks.column.monitoring.acceptedvalues.ColumnAcceptedValuesDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.accuracy.ColumnAccuracyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.anomaly.ColumnAnomalyDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.whitespace.ColumnWhitespaceDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.bool.ColumnBoolDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.comparison.ColumnComparisonDailyMonitoringChecksSpecMap;
import com.dqops.checks.column.monitoring.conversions.ColumnConversionsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datatype.ColumnDatatypeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.datetime.ColumnDatetimeDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.integrity.ColumnIntegrityDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.numeric.ColumnNumericDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.patterns.ColumnPatternsDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.pii.ColumnPiiDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.schema.ColumnSchemaDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.customsql.ColumnCustomSqlDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.text.ColumnTextDailyMonitoringChecksSpec;
import com.dqops.checks.column.monitoring.uniqueness.ColumnUniquenessDailyMonitoringChecksSpec;
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
 * Container of column level daily monitoring checks. Contains categories of daily monitoring checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyMonitoringCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyMonitoringCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("uniqueness", o -> o.uniqueness);
            put("accepted_values", o -> o.acceptedValues);
            put("text", o -> o.text);
            put("whitespace", o -> o.whitespace);
            put("conversions", o -> o.conversions);
            put("patterns", o -> o.patterns);
            put("pii", o -> o.pii);
            put("numeric", o -> o.numeric);
            put("anomaly", o -> o.anomaly);
            put("datetime", o -> o.datetime);
            put("bool", o -> o.bool);
            put("integrity", o -> o.integrity);
            put("accuracy", o -> o.accuracy);
            put("custom_sql", o -> o.customSql);
            put("datatype", o -> o.datatype);
            put("schema", o -> o.schema);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Daily monitoring checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyMonitoringChecksSpec nulls;

    @JsonPropertyDescription("Daily monitoring checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyMonitoringChecksSpec uniqueness;

    @JsonPropertyDescription("Configuration of accepted values checks on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAcceptedValuesDailyMonitoringChecksSpec acceptedValues;

    @JsonPropertyDescription("Daily monitoring checks of text values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextDailyMonitoringChecksSpec text;

    @JsonPropertyDescription("Configuration of column level checks that detect blank and whitespace values")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnWhitespaceDailyMonitoringChecksSpec whitespace;

    @JsonPropertyDescription("Configuration of conversion testing checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConversionsDailyMonitoringChecksSpec conversions;

    @JsonPropertyDescription("Daily monitoring checks of pattern matching on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPatternsDailyMonitoringChecksSpec patterns;

    @JsonPropertyDescription("Daily monitoring checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyMonitoringChecksSpec pii;

    @JsonPropertyDescription("Daily monitoring checks of numeric values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyMonitoringChecksSpec numeric;

    @JsonPropertyDescription("Daily monitoring checks of anomalies in numeric columns")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyDailyMonitoringChecksSpec anomaly;

    @JsonPropertyDescription("Daily monitoring checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyMonitoringChecksSpec datetime;

    @JsonPropertyDescription("Daily monitoring checks of booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolDailyMonitoringChecksSpec bool;

    @JsonPropertyDescription("Daily monitoring checks of integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityDailyMonitoringChecksSpec integrity;

    @JsonPropertyDescription("Daily monitoring checks of accuracy in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyDailyMonitoringChecksSpec accuracy;

    @JsonPropertyDescription("Daily monitoring checks of custom SQL checks in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCustomSqlDailyMonitoringChecksSpec customSql;

    @JsonPropertyDescription("Daily monitoring checks of datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeDailyMonitoringChecksSpec datatype;

    @JsonPropertyDescription("Daily monitoring column schema checks")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaDailyMonitoringChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonDailyMonitoringChecksSpecMap comparisons = new ColumnComparisonDailyMonitoringChecksSpecMap();


    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnNullsDailyMonitoringChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of nulls data quality checks (monitoring).
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsDailyMonitoringChecksSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnUniquenessDailyMonitoringChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of uniqueness data quality checks (monitoring).
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessDailyMonitoringChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        this.propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the accepted values check configuration on a column level.
     * @return Accepted values check configuration.
     */
    public ColumnAcceptedValuesDailyMonitoringChecksSpec getAcceptedValues() {
        return acceptedValues;
    }

    /**
     * Sets the accepted values check configuration on a column level.
     * @param acceptedValues New accepted values checks configuration.
     */
    public void setAcceptedValues(ColumnAcceptedValuesDailyMonitoringChecksSpec acceptedValues) {
        this.setDirtyIf(!Objects.equals(this.acceptedValues, acceptedValues));
        this.acceptedValues = acceptedValues;
        this.propagateHierarchyIdToField(acceptedValues, "accepted_values");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnTextDailyMonitoringChecksSpec getText() {
        return text;
    }

    /**
     * Sets the container of strings data quality checks (monitoring).
     * @param text New strings checks.
     */
    public void setText(ColumnTextDailyMonitoringChecksSpec text) {
        this.setDirtyIf(!Objects.equals(this.text, text));
        this.text = text;
        this.propagateHierarchyIdToField(text, "text");
    }

    /**
     * Returns the blanks check configuration on a column level.
     * @return Blanks check configuration.
     */
    public ColumnWhitespaceDailyMonitoringChecksSpec getWhitespace() {
        return whitespace;
    }

    /**
     * Sets the blanks check configuration on a column level.
     * @param whitespace New blanks checks configuration.
     */
    public void setWhitespace(ColumnWhitespaceDailyMonitoringChecksSpec whitespace) {
        this.setDirtyIf(!Objects.equals(this.whitespace, whitespace));
        this.whitespace = whitespace;
        this.propagateHierarchyIdToField(whitespace, "whitespace");
    }

    /**
     * Returns the container of conversion testing checks.
     * @return Conversion testing checks.
     */
    public ColumnConversionsDailyMonitoringChecksSpec getConversions() {
        return conversions;
    }

    /**
     * Sets the container of conversion testing checks.
     * @param conversions Conversion testing checks.
     */
    public void setConversions(ColumnConversionsDailyMonitoringChecksSpec conversions) {
        this.setDirtyIf(!Objects.equals(this.conversions, conversions));
        this.conversions = conversions;
        this.propagateHierarchyIdToField(conversions, "conversions");
    }

    /**
     * Returns the pattern match check configuration on a column level.
     * @return Pattern match check configuration.
     */
    public ColumnPatternsDailyMonitoringChecksSpec getPatterns() {
        return patterns;
    }

    /**
     * Sets the pattern match check configuration on a column level.
     * @param patterns New pattern match checks configuration.
     */
    public void setPatterns(ColumnPatternsDailyMonitoringChecksSpec patterns) {
        this.setDirtyIf(!Objects.equals(this.patterns, patterns));
        this.patterns = patterns;
        this.propagateHierarchyIdToField(patterns, "patterns");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnPiiDailyMonitoringChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of Personal Identifiable Information (PII) data quality checks (monitoring).
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiDailyMonitoringChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        this.propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnNumericDailyMonitoringChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of numeric data quality checks (monitoring).
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericDailyMonitoringChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of anomaly data quality monitoring.
     */
    public ColumnAnomalyDailyMonitoringChecksSpec getAnomaly() {
        return anomaly;
    }

    /**
     * Sets the container of anomaly data quality checks (monitoring).
     * @param anomaly New anomaly checks.
     */
    public void setAnomaly(ColumnAnomalyDailyMonitoringChecksSpec anomaly) {
        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
        this.anomaly = anomaly;
        this.propagateHierarchyIdToField(anomaly, "anomaly");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnDatetimeDailyMonitoringChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of datetime data quality checks (monitoring).
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeDailyMonitoringChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        this.propagateHierarchyIdToField(datetime, "datetime");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnBoolDailyMonitoringChecksSpec getBool() {
        return bool;
    }

    /**
     * Sets the container of booleans data quality checks (monitoring).
     * @param bool New booleans checks.
     */
    public void setBool(ColumnBoolDailyMonitoringChecksSpec bool) {
        this.setDirtyIf(!Objects.equals(this.bool, bool));
        this.bool = bool;
        this.propagateHierarchyIdToField(bool, "bool");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnIntegrityDailyMonitoringChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets the container of integrity data quality checks (monitoring).
     * @param integrity New integrity checks.
     */
    public void setIntegrity(ColumnIntegrityDailyMonitoringChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        this.propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnAccuracyDailyMonitoringChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the container of accuracy data quality checks (monitoring).
     * @param accuracy New accuracy checks.
     */
    public void setAccuracy(ColumnAccuracyDailyMonitoringChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns the container of custom SQL checks that use custom SQL expressions in checks.
     * @return Custom SQL checks.
     */
    public ColumnCustomSqlDailyMonitoringChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param customSql Custom SQL checks.
     */
    public void setCustomSql(ColumnCustomSqlDailyMonitoringChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        this.propagateHierarchyIdToField(customSql, "custom_sql");
    }

    /**
     * Returns the container of monitoring for standard data quality checks.
     * @return Container of row standard data quality monitoring.
     */
    public ColumnDatatypeDailyMonitoringChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets the container of datatype data quality checks (monitoring).
     * @param datatype New datatype checks.
     */
    public void setDatatype(ColumnDatatypeDailyMonitoringChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        this.propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns the container of daily monitoring column schema checks.
     * @return Container of column schema checks.
     */
    public ColumnSchemaDailyMonitoringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets the container of daily monitoring schema checks.
     * @param schema Container of schema checks.
     */
    public void setSchema(ColumnSchemaDailyMonitoringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
    public ColumnComparisonDailyMonitoringChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonDailyMonitoringChecksSpecMap comparisons) {
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
     * @return Monitoring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunScheduleGroup getSchedulingGroup() {
        return CheckRunScheduleGroup.monitoring_daily;
    }

    public static class ColumnDailyMonitoringCheckCategoriesSpecSampleFactory implements SampleValueFactory<ColumnDailyMonitoringCheckCategoriesSpec> {
        @Override
        public ColumnDailyMonitoringCheckCategoriesSpec createSample() {
            return new ColumnDailyMonitoringCheckCategoriesSpec() {{
                setNulls(new ColumnNullsDailyMonitoringChecksSpec.ColumnNullsDailyMonitoringChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
