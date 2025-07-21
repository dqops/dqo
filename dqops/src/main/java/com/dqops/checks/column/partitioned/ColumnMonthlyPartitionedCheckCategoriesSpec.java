/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.partitioned.acceptedvalues.ColumnAcceptedValuesMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.whitespace.ColumnWhitespaceMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.bool.ColumnBoolMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.comparison.ColumnComparisonMonthlyPartitionedChecksSpecMap;
import com.dqops.checks.column.partitioned.conversions.ColumnConversionsMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.customsql.ColumnCustomSqlMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datatype.ColumnDatatypeMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.datetime.ColumnDatetimeMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.integrity.ColumnIntegrityMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.nulls.ColumnNullsMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.numeric.ColumnNumericMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.patterns.ColumnPatternsMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.pii.ColumnPiiMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.text.ColumnTextMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.partitioned.uniqueness.ColumnUniquenessMonthlyPartitionedChecksSpec;
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
 * Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMonthlyPartitionedCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMonthlyPartitionedCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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
//            put("anomaly", o -> o.anomaly);
            put("datetime", o -> o.datetime);
            put("bool", o -> o.bool);
            put("integrity", o -> o.integrity);
//            put("accuracy", o -> o.accuracy);
            put("custom_sql", o -> o.customSql);
            put("datatype", o -> o.datatype);
            put("comparisons", o -> o.comparisons);
        }
    };

    @JsonPropertyDescription("Monthly partitioned checks of nulls in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsMonthlyPartitionedChecksSpec nulls;

    @JsonPropertyDescription("Monthly partitioned checks of uniqueness in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessMonthlyPartitionedChecksSpec uniqueness;

    @JsonPropertyDescription("Configuration of accepted values checks on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAcceptedValuesMonthlyPartitionedChecksSpec acceptedValues;

    @JsonPropertyDescription("Monthly partitioned checks of text values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextMonthlyPartitionedChecksSpec text;

    @JsonPropertyDescription("Configuration of column level checks that detect blank and whitespace values")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnWhitespaceMonthlyPartitionedChecksSpec whitespace;

    @JsonPropertyDescription("Configuration of conversion testing checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConversionsMonthlyPartitionedChecksSpec conversions;

    @JsonPropertyDescription("Monthly partitioned pattern match checks on a column level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPatternsMonthlyPartitionedChecksSpec patterns;

    @JsonPropertyDescription("Monthly partitioned checks of Personal Identifiable Information (PII) in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiMonthlyPartitionedChecksSpec pii;

    @JsonPropertyDescription("Monthly partitioned checks of numeric values in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMonthlyPartitionedChecksSpec numeric;

//    @JsonPropertyDescription("Monthly partitioned checks for anomalies in numeric columns")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnAnomalyMonthlyPartitionedChecksSpec anomaly;

    @JsonPropertyDescription("Monthly partitioned checks of datetime in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeMonthlyPartitionedChecksSpec datetime;

    @JsonPropertyDescription("Monthly partitioned checks for booleans in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolMonthlyPartitionedChecksSpec bool;

    @JsonPropertyDescription("Monthly partitioned checks for integrity in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityMonthlyPartitionedChecksSpec integrity;

//    @JsonPropertyDescription("Monthly partitioned checks for accuracy in the column")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnAccuracyMonthlyPartitionedChecksSpec accuracy;

    @JsonPropertyDescription("Monthly partitioned checks using custom SQL expressions evaluated on the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCustomSqlMonthlyPartitionedChecksSpec customSql;

    @JsonPropertyDescription("Monthly partitioned checks for datatype in the column")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeMonthlyPartitionedChecksSpec datatype;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonMonthlyPartitionedChecksSpecMap comparisons = new ColumnComparisonMonthlyPartitionedChecksSpecMap();

    /**
     * Returns the container of daily null data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnNullsMonthlyPartitionedChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the container of daily null data quality partitioned checks.
     * @param nulls New nulls checks.
     */
    public void setNulls(ColumnNullsMonthlyPartitionedChecksSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the container of daily uniqueness data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnUniquenessMonthlyPartitionedChecksSpec getUniqueness() {
        return uniqueness;
    }

    /**
     * Sets the container of daily uniqueness data quality partitioned checks.
     * @param uniqueness New uniqueness checks.
     */
    public void setUniqueness(ColumnUniquenessMonthlyPartitionedChecksSpec uniqueness) {
        this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
        this.uniqueness = uniqueness;
        propagateHierarchyIdToField(uniqueness, "uniqueness");
    }

    /**
     * Returns the accepted values check configuration on a column level.
     * @return Accepted values check configuration.
     */
    public ColumnAcceptedValuesMonthlyPartitionedChecksSpec getAcceptedValues() {
        return acceptedValues;
    }

    /**
     * Sets the accepted values check configuration on a column level.
     * @param acceptedValues New accepted values checks configuration.
     */
    public void setAcceptedValues(ColumnAcceptedValuesMonthlyPartitionedChecksSpec acceptedValues) {
        this.setDirtyIf(!Objects.equals(this.acceptedValues, acceptedValues));
        this.acceptedValues = acceptedValues;
        this.propagateHierarchyIdToField(acceptedValues, "accepted_values");
    }

    /**
     * Returns the container of daily strings data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnTextMonthlyPartitionedChecksSpec getText() {
        return text;
    }

    /**
     * Sets the container of daily strings data quality partitioned checks.
     * @param text New strings checks.
     */
    public void setText(ColumnTextMonthlyPartitionedChecksSpec text) {
        this.setDirtyIf(!Objects.equals(this.text, text));
        this.text = text;
        propagateHierarchyIdToField(text, "text");
    }

    /**
     * Returns the blanks check configuration on a column level.
     * @return Blanks check configuration.
     */
    public ColumnWhitespaceMonthlyPartitionedChecksSpec getWhitespace() {
        return whitespace;
    }

    /**
     * Sets the blanks check configuration on a column level.
     * @param whitespace New blanks checks configuration.
     */
    public void setWhitespace(ColumnWhitespaceMonthlyPartitionedChecksSpec whitespace) {
        this.setDirtyIf(!Objects.equals(this.whitespace, whitespace));
        this.whitespace = whitespace;
        this.propagateHierarchyIdToField(whitespace, "whitespace");
    }

    /**
     * Returns the container of conversion testing checks.
     * @return Conversion testing checks.
     */
    public ColumnConversionsMonthlyPartitionedChecksSpec getConversions() {
        return conversions;
    }

    /**
     * Sets the container of conversion testing checks.
     * @param conversions Conversion testing checks.
     */
    public void setConversions(ColumnConversionsMonthlyPartitionedChecksSpec conversions) {
        this.setDirtyIf(!Objects.equals(this.conversions, conversions));
        this.conversions = conversions;
        this.propagateHierarchyIdToField(conversions, "conversions");
    }

    /**
     * Returns the pattern match check configuration on a column level.
     * @return Pattern match check configuration.
     */
    public ColumnPatternsMonthlyPartitionedChecksSpec getPatterns() {
        return patterns;
    }

    /**
     * Sets the pattern match check configuration on a column level.
     * @param patterns New pattern match checks configuration.
     */
    public void setPatterns(ColumnPatternsMonthlyPartitionedChecksSpec patterns) {
        this.setDirtyIf(!Objects.equals(this.patterns, patterns));
        this.patterns = patterns;
        this.propagateHierarchyIdToField(patterns, "patterns");
    }

    /**
     * Returns the container of daily Personal Identifiable Information (PII) data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnPiiMonthlyPartitionedChecksSpec getPii() {
        return pii;
    }

    /**
     * Sets the container of daily Personal Identifiable Information (PII) data quality partitioned checks.
     * @param pii New Personal Identifiable Information (PII) checks.
     */
    public void setPii(ColumnPiiMonthlyPartitionedChecksSpec pii) {
        this.setDirtyIf(!Objects.equals(this.pii, pii));
        this.pii = pii;
        propagateHierarchyIdToField(pii, "pii");
    }

    /**
     * Returns the container of daily numeric data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnNumericMonthlyPartitionedChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the container of daily numeric data quality partitioned checks.
     * @param numeric New numeric checks.
     */
    public void setNumeric(ColumnNumericMonthlyPartitionedChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        propagateHierarchyIdToField(numeric, "numeric");
    }

//    /**
//     * Returns a container of custom accuracy checks on a column.
//     * @return Custom accuracy checks.
//     */
//    public ColumnAnomalyMonthlyPartitionedChecksSpec getAnomaly() {
//        return anomaly;
//    }
//
//    /**
//     * Sets a reference to a container of custom anomaly checks.
//     * @param anomaly Custom anomaly checks.
//     */
//    public void setAnomaly(ColumnAnomalyMonthlyPartitionedChecksSpec anomaly) {
//        this.setDirtyIf(!Objects.equals(this.anomaly, anomaly));
//        this.anomaly = anomaly;
//        propagateHierarchyIdToField(anomaly, "anomaly");
//    }

    /**
     * Returns the container of daily datetime data quality partitioned checks.
     * @return Container of row standard daily data quality partitioned checks.
     */
    public ColumnDatetimeMonthlyPartitionedChecksSpec getDatetime() {
        return datetime;
    }

    /**
     * Sets the container of daily datetime data quality partitioned checks.
     * @param datetime New datetime checks.
     */
    public void setDatetime(ColumnDatetimeMonthlyPartitionedChecksSpec datetime) {
        this.setDirtyIf(!Objects.equals(this.datetime, datetime));
        this.datetime = datetime;
        propagateHierarchyIdToField(datetime, "datetime");
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

//    /**
//     * Returns a container of custom accuracy checks on a column.
//     * @return Custom accuracy checks.
//     */
//    public ColumnAccuracyMonthlyPartitionedChecksSpec getAccuracy() {
//        return accuracy;
//    }
//
//    /**
//     * Sets a reference to a container of custom accuracy checks.
//     * @param accuracy Custom accuracy checks.
//     */
//    public void setAccuracy(ColumnAccuracyMonthlyPartitionedChecksSpec accuracy) {
//        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
//        this.accuracy = accuracy;
//        propagateHierarchyIdToField(accuracy, "accuracy");
//    }

    /**
     * Returns a container of custom SQL checks on a column.
     * @return Custom SQL checks.
     */
    public ColumnCustomSqlMonthlyPartitionedChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to a container of custom SQL checks.
     * @param customSql Custom SQL checks.
     */
    public void setCustomSql(ColumnCustomSqlMonthlyPartitionedChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        propagateHierarchyIdToField(customSql, "custom_sql");
    }

    /**
     * Returns a container of custom accuracy checks on a column.
     * @return Custom accuracy checks.
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

    public static class ColumnMonthlyPartitionedCheckCategoriesSpecSampleFactory implements SampleValueFactory<ColumnMonthlyPartitionedCheckCategoriesSpec> {
        @Override
        public ColumnMonthlyPartitionedCheckCategoriesSpec createSample() {
            return new ColumnMonthlyPartitionedCheckCategoriesSpec() {{
                setNulls(new ColumnNullsMonthlyPartitionedChecksSpec.ColumnNullsMonthlyPartitionedChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
