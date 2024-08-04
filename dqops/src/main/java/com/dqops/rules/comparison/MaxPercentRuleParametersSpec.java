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
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mining.*;
import com.dqops.utils.conversion.DoubleRounding;
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
 * Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MaxPercentRuleParametersSpec extends AbstractRuleParametersSpec implements MaxPercentRule, RuleMiningRule {
    private static final ChildHierarchyNodeFieldMapImpl<MaxPercentRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    /**
     * The rule name from the DQOps home folder that is used by this rule.
     */
    public static final String RULE_NAME = "comparison/max_percent";


    @JsonPropertyDescription("Maximum accepted value for the actual_value returned by the sensor (inclusive).")
    @SampleValues(values = "0.5")
    @RequiredField
    private Double maxPercent;

    /**
     * Default constructor, the minimum accepted value is 0.
     */
    public MaxPercentRuleParametersSpec() {
    }

    /**
     * Creates a rule with a given value.
     * @param maxPercent Minimum accepted value.
     */
    public MaxPercentRuleParametersSpec(Double maxPercent) {
        this.maxPercent = maxPercent;
    }

    /**
     * Returns a maximum value for a data quality check readout, for example a maximum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxPercent() {
        return maxPercent;
    }

    /**
     * Sets a maximum data quality check readout at is accepted, for example a maximum row count.
     * @param maxPercent Maximum value that is accepted.
     */
    public void setMaxPercent(Double maxPercent) {
        this.setDirtyIf(!Objects.equals(this.maxPercent, maxPercent));
        this.maxPercent = maxPercent;
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
        return RULE_NAME;
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxPercent == null) {
            return;
        }

        if (this.maxPercent <= 0.0) {
            this.maxPercent = checkResultsSingleCheck.getActualValueColumn().max();
            return;
        }

        if (this.maxPercent < 70.0) {
            this.maxPercent = DoubleRounding.roundToKeepEffectiveDigits(this.maxPercent * 1.3);
        }
        else {
            this.maxPercent = DoubleRounding.roundToKeepEffectiveDigits(this.maxPercent + (0.3 * (100.0 - this.maxPercent)));
        }
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
        double expectedMaxPercent = 0.0;

        if (sourceProfilingCheck.getActualValue() > miningParameters.getFailChecksAtPercentErrorRows()) {
            if (sourceProfilingCheck.getActualValue() > miningParameters.getMaxPercentErrorRowsForPercentChecks()) {
                return null;
            }

            expectedMaxPercent = sourceProfilingCheck.getActualValue() +
                    (sourceProfilingCheck.getActualValue() * checkMiningConfigurationProperties.getPercentCheckDeltaRate());

            if (expectedMaxPercent > (100.0 * (1.0 - checkMiningConfigurationProperties.getPercentCheckDeltaRate()))) {
                expectedMaxPercent = sourceProfilingCheck.getActualValue() +
                        ((100.0 - sourceProfilingCheck.getActualValue()) * checkMiningConfigurationProperties.getPercentCheckDeltaRate());
            }
        }

        return new MaxPercentRuleParametersSpec(DoubleRounding.roundToKeepEffectiveDigits(expectedMaxPercent));
    }
}