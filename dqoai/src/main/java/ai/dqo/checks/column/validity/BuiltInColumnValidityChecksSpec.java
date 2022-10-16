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
package ai.dqo.checks.column.validity;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured validity checks executed on a column level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BuiltInColumnValidityChecksSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<BuiltInColumnValidityChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("not_null_count", o -> o.notNullCount);
			put("non_negative_percent", o -> o.nonNegativePercent);
            put("date_type_percent", o -> o.dateTypePercent);
            put("string_length_in_range_percent", o -> o.stringLengthInRangePercent);
            put("value_in_range_numerical_percent", o -> o.valueInRangeNumericalPercent);
            put("value_in_range_date_percent", o -> o.valueInRangeDatePercent);
            put("values_in_set_percent", o -> o.valuesInSetPercent);
            put("regex_match_percent", o -> o.regexMatchPercent);
            put("non_negative_count", o -> o.nonNegativeCount);
            put("numerical_type_percent", o -> o.numericalTypePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the count of not negative values in a column (select count(<column_name>) from <table>) meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNonNegativeCountCheckSpec nonNegativeCount;

    @JsonPropertyDescription("Verifies that the percent of values that can be interpreted as a length string in range meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityStringLengthInRangePercentCheckSpec stringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percent of values which are in specified range meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValueInRangeNumericalPercentCheckSpec valueInRangeNumericalPercent;

    @JsonPropertyDescription("Verifies that the percent of values which are in specified range meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValueInRangeDatePercentCheckSpec valueInRangeDatePercent;

    @JsonPropertyDescription("Verifies that the percent of values that fit to a specified regex meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityRegexMatchPercentCheckSpec regexMatchPercent;

    @JsonPropertyDescription("Verifies that the percent of values that can be interpreted as a non negative meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNonNegativePercentCheckSpec nonNegativePercent;

    @JsonPropertyDescription("Verifies that the percent of values that can be interpreted as a numerical type meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNumericalTypePercentCheckSpec numericalTypePercent;

    @JsonPropertyDescription("Verifies that the count of not null values in a column (select count(<column_name>) from <table>) meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNotNullCountCheckSpec notNullCount;

    @JsonPropertyDescription("Verifies that the percent of values that can be interpreted as date format meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityDateTypePercentCheckSpec dateTypePercent;

    @JsonPropertyDescription("Verifies that the percent of values agree with given list format meets the required rules, like a minimum count.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValuesInSetPercentCheckSpec valuesInSetPercent;

    /**
     * Returns a not null count check.
     * @return Percent of not null values.
     */
    public ColumnValidityNotNullCountCheckSpec getNotNullCount() {
        return notNullCount;
    }

    /**
     * Returns a non negative percent check.
     * @return Percent of values that can be interpreted as non negative.
     */
    public ColumnValidityNonNegativePercentCheckSpec getNonNegativePercent() {
        return nonNegativePercent;
    }

    /**
     * Returns a non negative count check.
     * @return Percent of values that can be interpreted as non negative.
     */
    public ColumnValidityNonNegativeCountCheckSpec getNonNegativeCount() {
        return nonNegativeCount;
    }

    /**
     * Returns a string length in range percent check.
     * @return Percent of string lengths in specified range.
     */
    public ColumnValidityStringLengthInRangePercentCheckSpec getStringLengthInRangePercent() {
        return stringLengthInRangePercent;
    }

    /**
     * Returns a value in range numerical percent check.
     * @return Percent of values in specified range.
     */
    public ColumnValidityValueInRangeNumericalPercentCheckSpec getValueInRangeNumericalPercent() {
        return valueInRangeNumericalPercent;
    }

    /**
     * Returns a value in range date percent check.
     * @return Percent of values in specified range.
     */
    public ColumnValidityValueInRangeDatePercentCheckSpec getValueInRangeDatePercent() {
        return valueInRangeDatePercent;
    }

    /**
     * Returns a regex match percent check.
     * @return Percent of values that fit to regex.
     */
    public ColumnValidityRegexMatchPercentCheckSpec getRegexMatchPercent() {
        return regexMatchPercent;
    }

    /**
     * Returns a numerical type percent check.
     * @return Percent of numerical value.
     */
    public ColumnValidityNumericalTypePercentCheckSpec getNumericalTypePercent() {
        return numericalTypePercent;
    }

    /**
     * Sets a new definition of a non-negative count check.
     * @param nonNegativeCount Percent of values that can be interpreted as non-negative.
     */
    public void setNonNegativeCount(ColumnValidityNonNegativeCountCheckSpec nonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.nonNegativeCount, nonNegativeCount));
        this.nonNegativeCount = nonNegativeCount;
        propagateHierarchyIdToField(nonNegativeCount, "non_negative_count");
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param stringLengthInRangePercent Percent of values that can be interpreted as in specified range.
     */
    public void setStringLengthInRangePercent(ColumnValidityStringLengthInRangePercentCheckSpec stringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.stringLengthInRangePercent, stringLengthInRangePercent));
        this.stringLengthInRangePercent = stringLengthInRangePercent;
        propagateHierarchyIdToField(stringLengthInRangePercent, "string_length_in_range_percent");
    }

    /**
     * Sets a new definition of a value in range numerical percent check.
     * @param valueInRangeNumericalPercent Percent of values in specified range.
     */
    public void setValueInRangeNumericalPercent(ColumnValidityValueInRangeNumericalPercentCheckSpec valueInRangeNumericalPercent) {
        this.setDirtyIf(!Objects.equals(this.valueInRangeNumericalPercent, valueInRangeNumericalPercent));
        this.valueInRangeNumericalPercent = valueInRangeNumericalPercent;
        propagateHierarchyIdToField(valueInRangeNumericalPercent, "value_in_range_numerical_percent");
    }

    /**
     * Sets a new definition of a value in range date percent check.
     * @param valueInRangeDatePercent Percent of values in specified range.
     */
    public void setValueInRangeDatePercent(ColumnValidityValueInRangeDatePercentCheckSpec valueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.valueInRangeDatePercent, valueInRangeDatePercent));
        this.valueInRangeDatePercent = valueInRangeDatePercent;
        propagateHierarchyIdToField(valueInRangeDatePercent, "value_in_range_date_percent");
    }

    /**
     * Sets a new definition of a regex match percent check.
     * @param regexMatchPercent Percent of values that match to regex.
     */
    public void setRegexMatchPercent(ColumnValidityRegexMatchPercentCheckSpec regexMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.regexMatchPercent, regexMatchPercent));
        this.regexMatchPercent = regexMatchPercent;
        propagateHierarchyIdToField(regexMatchPercent, "regex_match_percent");
    }

    /**
     * Sets a new definition of a numerical type percent check.
     * @param numericalTypePercent Percent of values that can be interpreted as numerical.
     */
    public void setNumericalTypePercent(ColumnValidityNumericalTypePercentCheckSpec numericalTypePercent) {
        this.setDirtyIf(!Objects.equals(this.numericalTypePercent, numericalTypePercent));
        this.numericalTypePercent = numericalTypePercent;
        propagateHierarchyIdToField(numericalTypePercent, "numerical_type_percent");
    }

    /**
     * Sets a new definition of a row count check.
     * @param notNullCount Row count check.
     */
    public void setNotNullCount(ColumnValidityNotNullCountCheckSpec notNullCount) {
		this.setDirtyIf(!Objects.equals(this.notNullCount, notNullCount));
        this.notNullCount = notNullCount;
		propagateHierarchyIdToField(notNullCount, "not_null_count");
    }

    /**
     * Sets a new definition of a row count check.
     * @param nonNegativePercent Row count check.
     */
    public void setNonNegativePercent(ColumnValidityNonNegativePercentCheckSpec nonNegativePercent) {
		this.setDirtyIf(!Objects.equals(this.nonNegativePercent, nonNegativePercent));
        this.nonNegativePercent = nonNegativePercent;
		propagateHierarchyIdToField(nonNegativePercent, "non_negative_percent");
    }

    /**
     * Returns a date type percent check.
     * @return Percent of values that can be interpreted as dates.
     */
    public ColumnValidityDateTypePercentCheckSpec getDateTypePercent() {
        return dateTypePercent;
    }

    /**
     * Sets a new definition of a date type percent check.
     * @param dateTypePercent Percent of values that can be interpreted as dates.
     */
    public void setDateTypePercent(ColumnValidityDateTypePercentCheckSpec dateTypePercent) {
        this.setDirtyIf(!Objects.equals(dateTypePercent, this.dateTypePercent));
        this.dateTypePercent = dateTypePercent;
        propagateHierarchyIdToField(dateTypePercent, "date_type_percent");
    }

    /**
     * Returns the column validity values in set percent check.
     * @return Column validity values in set percent check.
     */
    public ColumnValidityValuesInSetPercentCheckSpec getValuesInSetPercent() {
        return valuesInSetPercent;
    }

    /**
     * Sets a new definition of a date type percent check.
     * @param valuesInSetPercent Percent of values agree with given list.
     */
    public void setValuesInSetPercent(ColumnValidityValuesInSetPercentCheckSpec valuesInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.valuesInSetPercent,valuesInSetPercent));
        this.valuesInSetPercent = valuesInSetPercent;
        propagateHierarchyIdToField(valuesInSetPercent, "values_in_set_percent");
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
}
