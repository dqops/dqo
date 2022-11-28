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
package ai.dqo.checks.column.adhoc;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.consistency.BuiltInColumnConsistencyChecksSpec;
import ai.dqo.checks.column.custom.CustomColumnCheckSpecMap;
import ai.dqo.checks.column.uniqueness.BuiltInColumnUniquenessChecksSpec;
import ai.dqo.checks.column.validity.BuiltInColumnValidityChecksSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.groupings.TimeSeriesMode;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
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
 * Container of column level, preconfigured checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocCheckCategoriesSpec extends AbstractRootChecksContainerSpec implements TimeSeriesConfigurationProvider {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocCheckCategoriesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("nulls", o -> o.nulls);
            put("numeric", o -> o.numeric);
            put("strings", o -> o.strings);
//            put("validity", o -> o.validity);
//			put("uniqueness", o -> o.uniqueness);
//            put("completeness", o -> o.completeness);
//            put("consistency", o -> o.consistency);
//            put("custom", o -> o.custom);
        }
    };

    @JsonPropertyDescription("Configuration of column level checks that verify nulls and blanks.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAdHocNullsChecksSpec nulls;

    @JsonPropertyDescription("Configuration of column level checks that verify negative values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAdHocNumericChecksSpec numeric;

    @JsonPropertyDescription("Configuration of strings checks on a column level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnAdHocStringsChecksSpec strings;

//    @JsonPropertyDescription("Configuration of validity checks on a column level. Validity checks verify hard rules on the data using static rules like valid column value ranges.")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    @Deprecated  // to be modified
//    private BuiltInColumnValidityChecksSpec validity;

//    @JsonPropertyDescription("Configuration of uniqueness checks on a table level. Uniqueness checks verify that the column values are unique or the percentage of duplicates is acceptable.")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    @Deprecated
//    private BuiltInColumnUniquenessChecksSpec uniqueness;

//    //TODO add description
//    @JsonPropertyDescription("Configuration of completeness checks on a column level. Completeness checks verify...")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private BuiltInColumnCompletenessChecksSpec completeness;

//    @JsonPropertyDescription("Custom data quality checks configured as a dictionary of sensors. Pick a friendly (business relevant) sensor name as a key and configure the sensor and rules for it.")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    @Deprecated
//    private CustomColumnCheckSpecMap custom;

    //TODO add description
//    @JsonPropertyDescription("Configuration of consistency checks on a column level. Consistency checks verify...")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    @Deprecated
//    private BuiltInColumnConsistencyChecksSpec consistency;

    /**
     * Returns the nulls check configuration on a column level.
     * @return Nulls check configuration.
     */
    public ColumnAdHocNullsChecksSpec getNulls() {
        return nulls;
    }

    /**
     * Sets the nulls check configuration on a column level.
     * @param nulls New nulls checks configuration.
     */
    public void setNulls(ColumnAdHocNullsChecksSpec nulls) {
        this.setDirtyIf(!Objects.equals(this.nulls, nulls));
        this.nulls = nulls;
        this.propagateHierarchyIdToField(nulls, "nulls");
    }

    /**
     * Returns the negative values check configuration on a column level.
     * @return Negative values check configuration.
     */
    public ColumnAdHocNumericChecksSpec getNumeric() {
        return numeric;
    }

    /**
     * Sets the negative values check configuration on a column level.
     * @param numeric New negative values checks configuration.
     */
    public void setNumeric(ColumnAdHocNumericChecksSpec numeric) {
        this.setDirtyIf(!Objects.equals(this.numeric, numeric));
        this.numeric = numeric;
        this.propagateHierarchyIdToField(numeric, "numeric");
    }

    /**
     * Returns the strings check configuration on a column level.
     * @return Strings check configuration.
     */
    public ColumnAdHocStringsChecksSpec getStrings() {
        return strings;
    }

    /**
     * Sets the string check configuration on a column level.
     * @param strings New string checks configuration.
     */
    public void setStrings(ColumnAdHocStringsChecksSpec strings) {
        this.setDirtyIf(!Objects.equals(this.strings, strings));
        this.strings = strings;
        this.propagateHierarchyIdToField(strings, "strings");
    }

//    /**
//     * Returns the validity check configuration on a column level.
//     * @return Validity check configuration.
//     */
//    public BuiltInColumnValidityChecksSpec getValidity() {
//        return validity;
//    }
//
//    /**
//     * Sets the validity check configuration on a column level.
//     * @param validity New validity checks configuration.
//     */
//    public void setValidity(BuiltInColumnValidityChecksSpec validity) {
//		this.setDirtyIf(!Objects.equals(this.validity, validity));
//        this.validity = validity;
//		this.propagateHierarchyIdToField(validity, "validity");
//    }
//
//    /**
//     * Column uniqueness checks.
//     * @return Column uniqueness checks.
//     */
//    public BuiltInColumnUniquenessChecksSpec getUniqueness() {
//        return uniqueness;
//    }
//
//    /**
//     * Sets the set of column uniqueness checks.
//     * @param uniqueness Column uniqueness checks.
//     */
//    public void setUniqueness(BuiltInColumnUniquenessChecksSpec uniqueness) {
//		this.setDirtyIf(!Objects.equals(this.uniqueness, uniqueness));
//        this.uniqueness = uniqueness;
//		this.propagateHierarchyIdToField(uniqueness, "uniqueness");
//    }
//
//    /**
//     * Returns a dictionary of custom sensors.
//     * @return Custom sensors map.
//     */
//    public CustomColumnCheckSpecMap getCustom() {
//        return custom;
//    }
//
//    /**
//     * Sets a dictionary of custom sensors.
//     * @param custom Custom sensors map.
//     */
//    public void setCustom(CustomColumnCheckSpecMap custom) {
//		this.setDirtyIf(!Objects.equals(this.custom, custom));
//        this.custom = custom;
//		this.propagateHierarchyIdToField(custom, "custom");
//    }
//
//    /**
//     * Column consistency checks.
//     * @return Column consistency checks.
//     */
//    public BuiltInColumnConsistencyChecksSpec getConsistency() {
//        return consistency;
//    }
//
//    /**
//     * Sets the set of column consistency checks.
//     * @param consistency Column consistency checks.
//     */
//    public void setConsistency(BuiltInColumnConsistencyChecksSpec consistency) {
//        this.setDirtyIf(!Objects.equals(this.consistency, consistency));
//        this.consistency = consistency;
//        this.propagateHierarchyIdToField(consistency, "consistency");
//    }

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
        return new TimeSeriesConfigurationSpec()
        {{
            setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimeSeriesGradient.MILLISECOND);
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
        return CheckType.ADHOC;
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
        return null;
    }
}
