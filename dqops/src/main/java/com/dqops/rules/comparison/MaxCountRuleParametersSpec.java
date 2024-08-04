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
package com.dqops.rules.comparison;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.sensors.column.nulls.ColumnNullsNullsCountSensorParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mining.*;
import com.dqops.utils.conversion.LongRounding;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MaxCountRuleParametersSpec extends AbstractRuleParametersSpec implements RuleMiningRule {
    private static final ChildHierarchyNodeFieldMapImpl<MaxCountRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Maximum accepted value for the actual_value returned by the sensor (inclusive).")
    @SampleValues(values = { "5" })
    @RequiredField
    private Long maxCount;

    public MaxCountRuleParametersSpec() {
    }

    /**
     * Constructor with a parameter.
     * @param maxCount Max count parameter.
     */
    public MaxCountRuleParametersSpec(Long maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * Returns a maximum value for a data quality check readout, for example a maximum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Long getMaxCount() {
        return maxCount;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, for example a maximum row count.
     * @param maxCount Maximum value that is accepted.
     */
    public void setMaxCount(Long maxCount) {
        this.setDirtyIf(!Objects.equals(this.maxCount, maxCount));
        this.maxCount = maxCount;
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
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        return "comparison/max_count";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxCount == null) {
            return;
        }

        if (this.maxCount < 5L) {
            this.maxCount = 5L;
            return;
        }

        if (this.maxCount == 0L) {
            // disabling the check
            if (!checkResultsSingleCheck.getActualValueColumn().isNotMissing().isEmpty()) {
                double maximumValue = checkResultsSingleCheck.getActualValueColumn().max();
                this.maxCount = (long)maximumValue; // catch up to the current value and increased to accept the issue
                return;
            }
        }

        this.maxCount = (long)(maxCount * 1.3);
    }

    /**
     * Proposes the configuration of this check by using information from all related sources.
     *
     * @param sourceProfilingCheck               Previous results captured by a similar profiling check. Used to copy configuration to monitoring checks.
     * @param dataAssetProfilingResults          Profiling results from the basic statistics and profiling checks for the data asset (table or column).
     * @param tableProfilingResults              All profiling results for the table, including table-level profiling results (such as row counts) and results for all columns. Used by rule mining functions that must look into other values.
     * @param tableSpec                          Parent table specification for reference.
     * @param parentCheckRootContainer           Parent check container, to identify the type of checks.
     * @param myCheckModel                       Check model of this check. This information can be used to get access to the custom check configuration (for custom checks).
     * @param miningParameters                   Additional rule mining parameters given by the user.
     * @param columnTypeCategory                 Column type category for column checks.
     * @param checkMiningConfigurationProperties Check mining configuration properties.
     * @return A configured rule parameters class that should be converted to the target type (by serialization to JSON and back) when parameters were proposed, or null when on parameters were proposed.
     */
    @Override
    public AbstractRuleParametersSpec proposeCheckConfiguration(ProfilingCheckResult sourceProfilingCheck,
                                                                DataAssetProfilingResults dataAssetProfilingResults,
                                                                TableProfilingResults tableProfilingResults,
                                                                TableSpec tableSpec,
                                                                AbstractRootChecksContainerSpec parentCheckRootContainer,
                                                                CheckModel myCheckModel,
                                                                CheckMiningParametersModel miningParameters,
                                                                DataTypeCategory columnTypeCategory,
                                                                DqoRuleMiningConfigurationProperties checkMiningConfigurationProperties) {
        if (sourceProfilingCheck.getActualValue() == 0.0) {
            return new MaxCountRuleParametersSpec(0L); // verify that there are no errors like this, because max count checks verify the count of things
        }

        Long rowCount = tableProfilingResults.getRowCount();
        if (rowCount == null) {
            return null; // cannot assess how many records the table has
        }

        if (parentCheckRootContainer.getCheckTarget() == CheckTarget.column) {
            if (Objects.equals(sourceProfilingCheck.getSensorName(), ColumnNullsNullsCountSensorParametersSpec.SENSOR_NAME)) {
                if (sourceProfilingCheck.getActualValue() > rowCount * miningParameters.getFailChecksAtPercentErrorRows() / 100.0) {
                    return null; // too many null values, we should not measure them with a hard count
                }
            } else {
                // all regular checks
                Long notNullCount = dataAssetProfilingResults.getNotNullCount();
                if (notNullCount == null) {
                    return null;
                }

                if (notNullCount < checkMiningConfigurationProperties.getMinReasonableNotNullsCount()) {
                    return null; // not enough not-null values to call it reasonable
                }

                if (sourceProfilingCheck.getActualValue() > notNullCount.doubleValue() * miningParameters.getFailChecksAtPercentErrorRows() / 100.0) {
                    return null; // too many errors, probably this count is counting profiled values to detect them, and it is a false-positive failure
                }
            }
        } else {
            if (sourceProfilingCheck.getActualValue() > rowCount * miningParameters.getFailChecksAtPercentErrorRows() / 100.0) {
                return null; // too many errors, we will not create a check
            }
        }

        if (rowCount < checkMiningConfigurationProperties.getMinReasonableNotNullsCount()) {
            return null; // second verification, do not configure this check for a low row count table, they are not representative
        }

        return new MaxCountRuleParametersSpec(0L); // always fail, "count" check are meant to detect problems
    }
}
