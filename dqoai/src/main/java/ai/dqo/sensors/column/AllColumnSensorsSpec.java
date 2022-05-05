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
package ai.dqo.sensors.column;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.sensors.column.completeness.ColumnCompletenessMissingDatesCountSensorParametersSpec;
import ai.dqo.sensors.column.uniqueness.ColumnUniquenessDistinctCountPercentSensorParametersSpec;
import ai.dqo.sensors.column.uniqueness.ColumnUniquenessDistinctCountSensorParametersSpec;
import ai.dqo.sensors.column.validity.*;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * All column level sensor definitions. Just pick one.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AllColumnSensorsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AllColumnSensorsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
			put("validity_not_null_count", o -> o.validityNotNullCount);
			put("validity_non_negative_percent", o -> o.validityNonNegativePercent);
            put("validity_value_in_range_date_percent", o -> o.validityValueInRangeDatePercent);
            put("validity_string_length_in_range_percent", o -> o.validityStringLengthInRangePercent);
            put("validity_date_type_percent", o -> o.validityDateTypePercent);
            put("validity_value_in_range_numerical_percent", o -> o.validityValueInRangeNumericalPercent);
            put("validity_regex_match_percent", o -> o.validityRegexMatchPercent);
            put("validity_non_negative_count", o -> o.validityNonNegativeCount);
            put("validity_numerical_type_percent", o -> o.validityNumericalTypePercent);
            put("validity_values_in_set_percent", o -> o.validityValuesInSetPercent);
			put("uniqueness_distinct_count", o -> o.uniquenessDistinctCount);
			put("uniqueness_distinct_count_percent", o -> o.uniquenessDistinctCountPercent);
            put("completeness_missing_dates_count", o -> o.completenessMissingDatesCount);
        }
    };

    @JsonPropertyDescription("Validity check - counts not null values in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNotNullCountSensorParametersSpec validityNotNullCount;
    
    
    @JsonPropertyDescription("Validity check - counts non-negative values in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNonNegativeCountSensorParametersSpec validityNonNegativeCount;

    @JsonPropertyDescription("Validity check - calculates the percent of non-negative values in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNonNegativePercentSensorParametersSpec validityNonNegativePercent;

    @JsonPropertyDescription("Validity check - calculates the percent of within a certain range in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValueInRangeDatePercentSensorParametersSpec validityValueInRangeDatePercent;

    @JsonPropertyDescription("Validity check - calculates the percent of strings length values in a specified range in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityStringLengthInRangePercentSensorParametersSpec validityStringLengthInRangePercent;

    @JsonPropertyDescription("Validity check - safe casts data as date or float and calculates the percentage of not NULL values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityDateTypePercentSensorParametersSpec validityDateTypePercent;

    @JsonPropertyDescription("Validity check - calculates the percent of numerical values within a certain range.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValueInRangeNumericalPercentSensorParametersSpec validityValueInRangeNumericalPercent;

    @JsonPropertyDescription("Validity check - calculates the percent of numerical types in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityNumericalTypePercentSensorParametersSpec validityNumericalTypePercent;

    @JsonPropertyDescription("Validity check - calculates the percent of values in a column that fit to regex.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityRegexMatchPercentSensorParametersSpec validityRegexMatchPercent;

    @JsonPropertyDescription("Validity check - calculates the percent of values transmitted by the list in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnValidityValuesInSetPercentSensorParametersSpec validityValuesInSetPercent;


    @JsonPropertyDescription("Uniqueness check - counts unique values in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctCountSensorParametersSpec uniquenessDistinctCount;

    @JsonPropertyDescription("Uniqueness check - counts unique values percentage in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctCountPercentSensorParametersSpec uniquenessDistinctCountPercent;

    @JsonPropertyDescription("Completeness - counts missing dates in a column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnCompletenessMissingDatesCountSensorParametersSpec completenessMissingDatesCount;

    // TODO: add remaining test, the names are "category" + "_" + sensor name


    /**
     * Returns a validity not null count sensor parameters.
     * @return Validity not null row count.
     */
    public ColumnValidityNotNullCountSensorParametersSpec getValidityNotNullCount() {
        return validityNotNullCount;
    }

    /**
     * Returns a validity non-negative count sensor parameters.
     * @return Validity non-negative count.
     */
    public ColumnValidityNonNegativeCountSensorParametersSpec getValidityNonNegativeCount() {
        return validityNonNegativeCount;
    }

    /**
     * Returns a validity non-negative percent sensor parameters.
     * @return Validity non-negative percent.
     */
    public ColumnValidityNonNegativePercentSensorParametersSpec getValidityNonNegativePercent() {
        return validityNonNegativePercent;
    }

    /**
     * Returns a validity value in range date percent sensor parameters
     * @return Validity value in range date percent
     */
    public ColumnValidityValueInRangeDatePercentSensorParametersSpec getValidityValueInRangeDatePercent() {
        return validityValueInRangeDatePercent;
    }

    /**
     * Returns a validity string length in range percent sensor parameters.
     * @return Validity string length in range percent.
     */
    public ColumnValidityStringLengthInRangePercentSensorParametersSpec getValidityStringLengthInRangePercent() {
        return validityStringLengthInRangePercent;
    }

    /**
     * Returns validity date type percent sensor parameters.
     * @return Validity date type percent.
     */
    public ColumnValidityDateTypePercentSensorParametersSpec getValidityDateTypePercent() {
        return validityDateTypePercent;
    }

    /**
     * Returns a value in range numerical percent sensor parameters.
     * @return Value in range numerical percent.
     */
    public ColumnValidityValueInRangeNumericalPercentSensorParametersSpec getValidityValueInRangeNumericalPercent() {
        return validityValueInRangeNumericalPercent;
    }

    /**
     * Returns a numerical type percent sensor parameters.
     * @return Numerical type percent.
     */
    public ColumnValidityNumericalTypePercentSensorParametersSpec getValidityNumericalTypePercent() {
        return validityNumericalTypePercent;
    }

    /**
     * Returns a  regex match sensor parameters.
     * @return regex match percent.
     */
    public ColumnValidityRegexMatchPercentSensorParametersSpec getValidityRegexMatchPercent() {
        return validityRegexMatchPercent;
    }

    /**
     * Returns a value in set percent sensor parameters.
     * @return Values in set percent.
     */
    public ColumnValidityValuesInSetPercentSensorParametersSpec getValidityValuesInSetPercent() {
        return validityValuesInSetPercent;
    }

    /**
     * Returns a uniqueness distinct count sensor parameters.
     * @return Uniqueness distinct count.
     */
    public ColumnUniquenessDistinctCountSensorParametersSpec getUniquenessDistinctCount() {
        return uniquenessDistinctCount;
    }

    /**
     * Returns a uniqueness distinct count percent sensor parameters.
     * @return Uniqueness distinct count percent.
     */
    public ColumnUniquenessDistinctCountPercentSensorParametersSpec getUniquenessDistinctCountPercent() {
        return uniquenessDistinctCountPercent;
    }

    /**
     * Returns a completeness missing dates count sensor parameters.
     * @return Completeness missing dates count.
     */
    public ColumnCompletenessMissingDatesCountSensorParametersSpec getCompletenessMissingDatesCount() {
        return completenessMissingDatesCount;
    }

    /**
     * Sets the validity not null count sensor parameters.
     * @param validityNotNullCount Not null sensor parameters.
     */
    public void setValidityNotNullCount(ColumnValidityNotNullCountSensorParametersSpec validityNotNullCount) {
		this.setDirtyIf(!Objects.equals(this.validityNotNullCount, validityNotNullCount));
        this.validityNotNullCount = validityNotNullCount;
		propagateHierarchyIdToField(validityNotNullCount, "validity_not_null_count");
    }

    /**
     * Sets the validity non-negative count sensor parameters.
     * @param validityNonNegativeCount Non-negative count sensor parameters.
     */
    public void setValidityNonNegativeCount(ColumnValidityNonNegativeCountSensorParametersSpec validityNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.validityNonNegativeCount, validityNonNegativeCount));
        this.validityNonNegativeCount = validityNonNegativeCount;
        propagateHierarchyIdToField(validityNonNegativeCount, "validity_non_negative_count");
    }

    /**
     * Sets the validity non-negative percent sensor parameters.
     * @param validityNonNegativePercent Non-negative percent sensor parameters.
     */
    public void setValidityNonNegativePercent(ColumnValidityNonNegativePercentSensorParametersSpec validityNonNegativePercent) {
		this.setDirtyIf(!Objects.equals(this.validityNonNegativePercent, validityNonNegativePercent));
        this.validityNonNegativePercent = validityNonNegativePercent;
		propagateHierarchyIdToField(validityNonNegativePercent, "validity_non_negative_percent");
    }

    /**
     * Sets the validity value in range date percent.
     * @param validityValueInRangeDatePercent Value in range date percent.
     */
    public void setValidityValueInRangeDatePercent(ColumnValidityValueInRangeDatePercentSensorParametersSpec validityValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.validityValueInRangeDatePercent, validityValueInRangeDatePercent));
        this.validityValueInRangeDatePercent = validityValueInRangeDatePercent;
        propagateHierarchyIdToField(validityValueInRangeDatePercent, "validity_value_in_range_date_percent");
    }

    /**
     * Sets the validity string length in range percent sensor parameters.
     * @param validityStringLengthInRangePercent Fixed length string percent sensor parameters.
     */
    public void setValidityStringLengthInRangePercent(ColumnValidityStringLengthInRangePercentSensorParametersSpec validityStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.validityStringLengthInRangePercent, validityStringLengthInRangePercent));
        this.validityStringLengthInRangePercent = validityStringLengthInRangePercent;
        propagateHierarchyIdToField(validityStringLengthInRangePercent, "validity_string_length_in_range_percent");
    }

    /**
     * Sets the validity date type percent sensor parameters.
     * @param validityDateTypePercent Date type sensor parameters.
     */
    public void setValidityDateTypePercent(ColumnValidityDateTypePercentSensorParametersSpec validityDateTypePercent) {
        this.setDirtyIf(!Objects.equals(this.validityDateTypePercent, validityDateTypePercent));
        this.validityDateTypePercent = validityDateTypePercent;
        propagateHierarchyIdToField(validityDateTypePercent, "validity_date_type_percent");
    }

    /**
     * Sets the validity value in range numerical percent sensor parameters.
     * @param validityValueInRangeNumericalPercent Value in range numerical percent parameters.
     */
    public void setValidityValueInRangeNumericalPercent(ColumnValidityValueInRangeNumericalPercentSensorParametersSpec validityValueInRangeNumericalPercent) {
        this.setDirtyIf(!Objects.equals(this.validityValueInRangeNumericalPercent, validityValueInRangeNumericalPercent));
        this.validityValueInRangeNumericalPercent = validityValueInRangeNumericalPercent;
        propagateHierarchyIdToField(validityValueInRangeNumericalPercent, "validity_value_in_range_numerical_percent");    }

    /**
     * Sets the validity numerical type percent sensor parameters.
     * @param validityNumericalTypePercent Numerical type percent sensor parameters.
     */
    public void setValidityNumericalTypePercent(ColumnValidityNumericalTypePercentSensorParametersSpec validityNumericalTypePercent) {
        this.setDirtyIf(!Objects.equals(this.validityNumericalTypePercent, validityNumericalTypePercent));
        this.validityNumericalTypePercent = validityNumericalTypePercent;
        propagateHierarchyIdToField(validityNumericalTypePercent, "validity_numerical_type_percent");
    }

    /**
     * Sets the validity regex match percent sensor parameters.
     * @param validityRegexMatchPercent regex match percent sensor parameters.
     */
    public void setValidityRegexMatchPercent(ColumnValidityRegexMatchPercentSensorParametersSpec validityRegexMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.validityRegexMatchPercent, validityRegexMatchPercent));
        this.validityRegexMatchPercent = validityRegexMatchPercent;
        propagateHierarchyIdToField(validityRegexMatchPercent, "validity_regex_match_percent");
    }

    /**
     * Sets the validity values in set percent sensor parameters.
     * @param validityValuesInSetPercent Value in set percent parameters.
     */
    public void setValidityValuesInSetPercent(ColumnValidityValuesInSetPercentSensorParametersSpec validityValuesInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.validityValuesInSetPercent, validityValuesInSetPercent));
        this.validityValuesInSetPercent = validityValuesInSetPercent;
        propagateHierarchyIdToField(validityValuesInSetPercent, "validity_values_in_set_percent");
    }
    /**

    /**
     * Sets the uniqueness distinct count sensor parameters.
     * @param uniquenessDistinctCount Distinct sensor parameters.
     */
    public void setUniquenessDistinctCount(ColumnUniquenessDistinctCountSensorParametersSpec uniquenessDistinctCount) {
		this.setDirtyIf(!Objects.equals(this.uniquenessDistinctCount, uniquenessDistinctCount));
        this.uniquenessDistinctCount = uniquenessDistinctCount;
		propagateHierarchyIdToField(uniquenessDistinctCount, "uniqueness_distinct_count");
    }

    /**
     * Sets the validity not null count percent sensor parameters.
     * @param uniquenessDistinctCountPercent Not null sensor parameters.
     */
    public void setUniquenessDistinctCountPercent(ColumnUniquenessDistinctCountPercentSensorParametersSpec uniquenessDistinctCountPercent) {
		this.setDirtyIf(!Objects.equals(this.uniquenessDistinctCountPercent, uniquenessDistinctCountPercent));
        this.uniquenessDistinctCountPercent = uniquenessDistinctCountPercent;
		propagateHierarchyIdToField(uniquenessDistinctCountPercent, "uniqueness_distinct_count_percent");
    }

    public void setCompletenessMissingDatesCount(ColumnCompletenessMissingDatesCountSensorParametersSpec completenessMissingDatesCount) {
        this.setDirtyIf(!Objects.equals(this.completenessMissingDatesCount, completenessMissingDatesCount));
        this.completenessMissingDatesCount = completenessMissingDatesCount;
        propagateHierarchyIdToField(completenessMissingDatesCount, "completeness_missing_dates_count");
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
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }
}
