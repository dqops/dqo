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
import com.dqops.checks.column.partitioned.acceptedvalues.ColumnAcceptedValuesDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.anomaly.ColumnAnomalyDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.whitespace.ColumnWhitespaceDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.bool.ColumnBoolDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.comparison.ColumnComparisonDailyPartitionedChecksSpecMap;
import com.dqops.checks.column.partitioned.conversions.ColumnConversionsDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datatype.ColumnDatatypeDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datetime.ColumnDatetimeDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.integrity.ColumnIntegrityDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.nulls.ColumnNullsDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.numeric.ColumnNumericDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.patterns.ColumnPatternsDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.pii.ColumnPiiDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.customsql.ColumnCustomSqlDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.text.ColumnTextDailyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.uniqueness.ColumnUniquenessDailyPartitionedChecksSpec;
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
 * Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDailyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDailyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("uniqueness", o -> o.uniqueness);
            put("accepted_values", o -> o.acceptedValues);
            put("text", o -> o.text);
            put("conversions", o -> o.conversions);
            put("whitespace", o -> o.whitespace);
            put("patterns", o -> o.patterns);
            put("pii", o -> o.pii);
            put("numeric", o -> o.numeric);
            put("anomaly", o -> o.anomaly);
            put("datetime", o -> o.datetime);
            put("bool", o -> o.bool);
            put("integrity", o -> o.integrity);
//            put("accuracy", o -> o.accuracy);
            put("custom_sql", o -> o.customSql);
            put("datatype", o -> o.datatype);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Daily partitioned checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsDailyPartitionedChecksSpec nulls;

    @JsonPropertyDescription("Daily partitioned checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDailyPartitionedChecksSpec uniqueness;

    @JsonPropertyDescription("Configuration of accepted values checks on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAcceptedValuesDailyPartitionedChecksSpec acceptedValues;

    @JsonPropertyDescription("Daily partitioned checks of text values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextDailyPartitionedChecksSpec text;

    @JsonPropertyDescription("Configuration of column level checks that detect blank and whitespace values")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnWhitespaceDailyPartitionedChecksSpec whitespace;

    @JsonPropertyDescription("Configuration of conversion testing checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConversionsDailyPartitionedChecksSpec conversions;

    @JsonPropertyDescription("Daily partitioned pattern match checks on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPatternsDailyPartitionedChecksSpec patterns;

    @JsonPropertyDescription("Daily partitioned checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiDailyPartitionedChecksSpec pii;

    @JsonPropertyDescription("Daily partitioned checks of numeric values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericDailyPartitionedChecksSpec numeric;

    @JsonPropertyDescription("Daily partitioned checks for anomalies in numeric columns")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyDailyPartitionedChecksSpec anomaly;

    @JsonPropertyDescription("Daily partitioned checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeDailyPartitionedChecksSpec datetime;

    @JsonPropertyDescription("Daily partitioned checks for booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolDailyPartitionedChecksSpec bool;

    @JsonPropertyDescription("Daily partitioned checks for integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityDailyPartitionedChecksSpec integrity;

//    @JsonPropertyDescription("Daily partitioned checks for accuracy in the column")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnAccuracyDailyPartitionedChecksSpec accuracy;

    @JsonPropertyDescription("Daily partitioned checks using custom SQL expressions evaluated on the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCustomSqlDailyPartitionedChecksSpec customSql;

    @JsonPropertyDescription("Daily partitioned checks for datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeDailyPartitionedChecksSpec datatype;

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
     * Returns the accepted values check configuration on a column level.
     * @return Accepted values check configuration.
     */
    public ColumnAcceptedValuesDailyPartitionedChecksSpec getAcceptedValues() {
        return acceptedValues;
    }

    /**
     * Sets the accepted values check configuration on a column level.
     * @param acceptedValues New accepted values checks configuration.
     */
    public void setAcceptedValues(ColumnAcceptedValuesDailyPartitionedChecksSpec acceptedValues) {
        this.setDirtyIf(!Objects.equals(this.acceptedValues, acceptedValues));
        this.acceptedValues = acceptedValues;
        this.propagateHierarchyIdToField(acceptedValues, "accepted_values");
    }

    /**
     * Returns the container of daily strings data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnTextDailyPartitionedChecksSpec getText() {
        return text;
    }

    /**
     * Sets the container of daily strings data quality partitioned checks.
     * @param text New strings checks.
     */
    public void setText(ColumnTextDailyPartitionedChecksSpec text) {
        this.setDirtyIf(!Objects.equals(this.text, text));
        this.text = text;
        propagateHierarchyIdToField(text, "text");
    }

    /**
     * Returns the blanks check configuration on a column level.
     * @return Blanks check configuration.
     */
    public ColumnWhitespaceDailyPartitionedChecksSpec getWhitespace() {
        return whitespace;
    }

    /**
     * Sets the blanks check configuration on a column level.
     * @param whitespace New blanks checks configuration.
     */
    public void setWhitespace(ColumnWhitespaceDailyPartitionedChecksSpec whitespace) {
        this.setDirtyIf(!Objects.equals(this.whitespace, whitespace));
        this.whitespace = whitespace;
        this.propagateHierarchyIdToField(whitespace, "whitespace");
    }

    /**
     * Returns the container of conversion testing checks.
     * @return Conversion testing checks.
     */
    public ColumnConversionsDailyPartitionedChecksSpec getConversions() {
        return conversions;
    }

    /**
     * Sets the container of conversion testing checks.
     * @param conversions Conversion testing checks.
     */
    public void setConversions(ColumnConversionsDailyPartitionedChecksSpec conversions) {
        this.setDirtyIf(!Objects.equals(this.conversions, conversions));
        this.conversions = conversions;
        this.propagateHierarchyIdToField(conversions, "conversions");
    }

    /**
     * Returns the pattern match check configuration on a column level.
     * @return Pattern match check configuration.
     */
    public ColumnPatternsDailyPartitionedChecksSpec getPatterns() {
        return patterns;
    }

    /**
     * Sets the pattern match check configuration on a column level.
     * @param patterns New pattern match checks configuration.
     */
    public void setPatterns(ColumnPatternsDailyPartitionedChecksSpec patterns) {
        this.setDirtyIf(!Objects.equals(this.patterns, patterns));
        this.patterns = patterns;
        this.propagateHierarchyIdToField(patterns, "patterns");
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

//    /**
//     * Returns a container of custom accuracy checks on a column.
//     * @return Custom accuracy checks.
//     */
//    public ColumnAccuracyDailyPartitionedChecksSpec getAccuracy() {
//        return accuracy;
//    }
//
//    /**
//     * Sets a reference to a container of custom accuracy checks.
//     * @param accuracy Custom accuracy checks.
//     */
//    public void setAccuracy(ColumnAccuracyDailyPartitionedChecksSpec accuracy) {
//        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
//        this.accuracy = accuracy;
//        propagateHierarchyIdToField(accuracy, "accuracy");
//    }

    /**
     * Returns a container of custom SQL checks on a column.
     * @return Custom SQL checks.
     */
    public ColumnCustomSqlDailyPartitionedChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param customSql Custom SQL checks.
     */
    public void setCustomSql(ColumnCustomSqlDailyPartitionedChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        propagateHierarchyIdToField(customSql, "custom_sql");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
     */
    public ColumnDatatypeDailyPartitionedChecksSpec getDatatype() {
        return datatype;
    }

    /**
     * Sets a reference to a container of custom datatype checks.
     * @param datatype Custom datatype checks.
     */
    public void setDatatype(ColumnDatatypeDailyPartitionedChecksSpec datatype) {
        this.setDirtyIf(!Objects.equals(this.datatype, datatype));
        this.datatype = datatype;
        propagateHierarchyIdToField(datatype, "datatype");
    }

    /**
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
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
        return CheckRunScheduleGroup.partitioned_daily;
    }

    public static class ColumnDailyPartitionedCheckCategoriesSpecSampleFactory implements SampleValueFactory<ColumnDailyPartitionedCheckCategoriesSpec> {
        @Override
        public ColumnDailyPartitionedCheckCategoriesSpec createSample() {
            return new ColumnDailyPartitionedCheckCategoriesSpec() {{
                setNulls(new ColumnNullsDailyPartitionedChecksSpec.ColumnNullsDailyPartitionedChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
