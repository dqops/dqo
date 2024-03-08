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
package com.dqops.checks.column.profiling;

import com.dqops.checks.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
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
 * Container of column level, preconfigured profiling checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnProfilingCheckCategoriesSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
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

    @JsonPropertyDescription("Configuration of column level checks that detect null values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNullsProfilingChecksSpec nulls;

    @JsonPropertyDescription("Configuration of uniqueness checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessProfilingChecksSpec uniqueness;

    @JsonPropertyDescription("Configuration of accepted values checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAcceptedValuesProfilingChecksSpec acceptedValues;

    @JsonPropertyDescription("Configuration of column level checks that verify text values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextProfilingChecksSpec text;

    @JsonPropertyDescription("Configuration of column level checks that detect blank and whitespace values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnWhitespaceProfilingChecksSpec whitespace;

    @JsonPropertyDescription("Configuration of conversion testing checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnConversionsProfilingChecksSpec conversions;

    @JsonPropertyDescription("Configuration of pattern match checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPatternsProfilingChecksSpec patterns;

    @JsonPropertyDescription("Configuration of Personal Identifiable Information (PII) checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnPiiProfilingChecksSpec pii;

    @JsonPropertyDescription("Configuration of column level checks that verify numeric values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericProfilingChecksSpec numeric;

    @JsonPropertyDescription("Configuration of anomaly checks on a column level that detect anomalies in numeric columns.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAnomalyProfilingChecksSpec anomaly;

    @JsonPropertyDescription("Configuration of datetime checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatetimeProfilingChecksSpec datetime;

    @JsonPropertyDescription("Configuration of booleans checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolProfilingChecksSpec bool;

    @JsonPropertyDescription("Configuration of integrity checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnIntegrityProfilingChecksSpec integrity;

    @JsonPropertyDescription("Configuration of accuracy checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAccuracyProfilingChecksSpec accuracy;

    @JsonPropertyDescription("Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCustomSqlProfilingChecksSpec customSql;

    @JsonPropertyDescription("Configuration of datatype checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeProfilingChecksSpec datatype;

    @JsonPropertyDescription("Configuration of schema checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSchemaProfilingChecksSpec schema;

    @JsonPropertyDescription("Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnComparisonProfilingChecksSpecMap comparisons = new ColumnComparisonProfilingChecksSpecMap();

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
     * Returns the accepted values check configuration on a column level.
     * @return Accepted values check configuration.
     */
    public ColumnAcceptedValuesProfilingChecksSpec getAcceptedValues() {
        return acceptedValues;
    }

    /**
     * Sets the accepted values check configuration on a column level.
     * @param acceptedValues New accepted values checks configuration.
     */
    public void setAcceptedValues(ColumnAcceptedValuesProfilingChecksSpec acceptedValues) {
        this.setDirtyIf(!Objects.equals(this.acceptedValues, acceptedValues));
        this.acceptedValues = acceptedValues;
        this.propagateHierarchyIdToField(acceptedValues, "accepted_values");
    }

    /**
     * Returns the strings check configuration on a column level.
     * @return Strings check configuration.
     */
    public ColumnTextProfilingChecksSpec getText() {
        return text;
    }

    /**
     * Sets the string check configuration on a column level.
     * @param text New string checks configuration.
     */
    public void setText(ColumnTextProfilingChecksSpec text) {
        this.setDirtyIf(!Objects.equals(this.text, text));
        this.text = text;
        this.propagateHierarchyIdToField(text, "text");
    }

    /**
     * Returns the blanks check configuration on a column level.
     * @return Blanks check configuration.
     */
    public ColumnWhitespaceProfilingChecksSpec getWhitespace() {
        return whitespace;
    }

    /**
     * Sets the blanks check configuration on a column level.
     * @param whitespace New blanks checks configuration.
     */
    public void setWhitespace(ColumnWhitespaceProfilingChecksSpec whitespace) {
        this.setDirtyIf(!Objects.equals(this.whitespace, whitespace));
        this.whitespace = whitespace;
        this.propagateHierarchyIdToField(whitespace, "whitespace");
    }

    /**
     * Returns the container of text conversion checks.
     * @return Text conversion checks.
     */
    public ColumnConversionsProfilingChecksSpec getConversions() {
        return conversions;
    }

    /**
     * Sets the container of text conversion checks.
     * @param conversions Text conversion checks.
     */
    public void setConversions(ColumnConversionsProfilingChecksSpec conversions) {
        this.setDirtyIf(!Objects.equals(this.conversions, conversions));
        this.conversions = conversions;
        this.propagateHierarchyIdToField(conversions, "conversions");
    }

    /**
     * Returns the pattern match check configuration on a column level.
     * @return Pattern match check configuration.
     */
    public ColumnPatternsProfilingChecksSpec getPatterns() {
        return patterns;
    }

    /**
     * Sets the pattern match check configuration on a column level.
     * @param patterns New pattern match checks configuration.
     */
    public void setPatterns(ColumnPatternsProfilingChecksSpec patterns) {
        this.setDirtyIf(!Objects.equals(this.patterns, patterns));
        this.patterns = patterns;
        this.propagateHierarchyIdToField(patterns, "patterns");
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
     * Returns the integrity check configuration on a column level.
     * @return Integrity check configuration.
     */
    public ColumnIntegrityProfilingChecksSpec getIntegrity() {
        return integrity;
    }

    /**
     * Sets the integrity check configuration on a column level.
     * @param integrity New integrity checks configuration.
     */
    public void setIntegrity(ColumnIntegrityProfilingChecksSpec integrity) {
        this.setDirtyIf(!Objects.equals(this.integrity, integrity));
        this.integrity = integrity;
        this.propagateHierarchyIdToField(integrity, "integrity");
    }

    /**
     * Returns the accuracy check configuration on a column level.
     * @return Accuracy check configuration.
     */
    public ColumnAccuracyProfilingChecksSpec getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy check configuration on a column level.
     * @param accuracy New accuracy checks configuration.
     */
    public void setAccuracy(ColumnAccuracyProfilingChecksSpec accuracy) {
        this.setDirtyIf(!Objects.equals(this.accuracy, accuracy));
        this.accuracy = accuracy;
        this.propagateHierarchyIdToField(accuracy, "accuracy");
    }

    /**
     * Returns the configuration of custom SQL checks.
     * @return Configuration of custom sql checks.
     */
    public ColumnCustomSqlProfilingChecksSpec getCustomSql() {
        return customSql;
    }

    /**
     * Sets a reference to the configuration of custom SQL checks.
     * @param customSql Custom sql checks.
     */
    public void setCustomSql(ColumnCustomSqlProfilingChecksSpec customSql) {
        this.setDirtyIf(!Objects.equals(this.customSql, customSql));
        this.customSql = customSql;
        this.propagateHierarchyIdToField(customSql, "custom_sql");
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
     * Returns the container of column level comparisons to columns in the reference table.
     * @return Dictionary of comparisons to columns.
     */
    @Override
    public ColumnComparisonProfilingChecksSpecMap getComparisons() {
        return comparisons;
    }

    /**
     * Sets the container of named comparisons to columns in other reference tables.
     * @param comparisons Container of column level comparisons.
     */
    public void setComparisons(ColumnComparisonProfilingChecksSpecMap comparisons) {
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns time series configuration for the given group of checks.
     *
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    @Override
    public TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec) {
        ProfilingTimePeriodTruncation profilingTimePeriodTruncation = tableSpec != null && tableSpec.getProfilingChecks() != null &&
                tableSpec.getProfilingChecks().getResultTruncation() != null ?
                tableSpec.getProfilingChecks().getResultTruncation() : ProfilingTimePeriodTruncation.one_per_month;

        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(profilingTimePeriodTruncation.toTimePeriodGradient());
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
        return CheckType.profiling;
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
        return CheckRunScheduleGroup.profiling;
    }

    public static class ColumnProfilingCheckCategoriesSpecSampleFactory implements SampleValueFactory<ColumnProfilingCheckCategoriesSpec> {
        @Override
        public ColumnProfilingCheckCategoriesSpec createSample() {
            return new ColumnProfilingCheckCategoriesSpec() {{
                setNulls(new ColumnNullsProfilingChecksSpec.ColumnNullsProfilingChecksSpecSampleFactory().createSample());
            }};
        }
    }
}
