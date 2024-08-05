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
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
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
 * Data quality rule that verifies if a data quality check percentage readout is between an accepted range of percentages.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BetweenPercentRuleParametersSpec extends AbstractRuleParametersSpec implements RuleMiningRule {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenPercentRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum accepted percentage of rows passing the check (inclusive).")
    @SampleValues(values = "80.0")
    private Double minPercent = 100.0;

    @JsonPropertyDescription("Maximum accepted percentage of rows passing the check (inclusive).")
    @SampleValues(values = "90.0")
    private Double maxPercent;

    public BetweenPercentRuleParametersSpec() {
    }

    /**
     * Constructor with parameters.
     * @param minPercent Minimum percent.
     * @param maxPercent Maximum percent.
     */
    public BetweenPercentRuleParametersSpec(Double minPercent, Double maxPercent) {
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
    }

    /**
     * Returns a minimum value for a data quality check readout, a minimum percentage.
     * @return Minimum value for a data quality check readout.
     */
    public Double getMinPercent() {
        return minPercent;
    }

    /**
     * Sets a minimum data quality check readout that is accepted, a minimum percentage.
     * @param minPercent Minimum value that is accepted.
     */
    public void setMinPercent(Double minPercent) {
		this.setDirtyIf(!Objects.equals(this.minPercent, minPercent));
        this.minPercent = minPercent;
    }

    /**
     * Returns a maximum value for a data quality check readout, a maximum percentage.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxPercent() {
        return maxPercent;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, a maximum percentage.
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
        return "comparison/between_percent";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.minPercent != null) {
            double minActualValue = checkResultsSingleCheck.getActualValueColumn().min();
            if (minActualValue < this.minPercent) {
                this.minPercent = minActualValue - Math.min((this.minPercent - minActualValue) * 0.3, minActualValue * 0.3);
            }
        }

        if (this.maxPercent != null) {
            double maxActualValue = checkResultsSingleCheck.getActualValueColumn().max();
            if (maxActualValue > this.maxPercent) {
                this.maxPercent = maxActualValue + Math.min((maxActualValue - this.maxPercent) * 0.3, (100.0 - maxActualValue) * 0.3);
            }
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
        if (sourceProfilingCheck.getActualValue() == 0.0) {
            return null; // current percent is zero
        }

        if (sourceProfilingCheck.getActualValue() < 0.0 || sourceProfilingCheck.getActualValue() > 100.0) {
            return null; // invalid value, the sensor must be corrupted
        }

        if (dataAssetProfilingResults instanceof ColumnDataAssetProfilingResults) {
            ColumnDataAssetProfilingResults columnDataAssetProfilingResults = (ColumnDataAssetProfilingResults)dataAssetProfilingResults;
            Long notNullCount = columnDataAssetProfilingResults.getNotNullsCount();
            if (notNullCount == null) {
                return null;
            }

            if (notNullCount < checkMiningConfigurationProperties.getMinReasonableNotNullsCount()) {
                return null; // not enough not-null values to call it reasonable
            }

            if (100.0 - sourceProfilingCheck.getActualValue() < miningParameters.getFailChecksAtPercentErrorRows()) {
                return new BetweenPercentRuleParametersSpec(100.0, 100.0); // so close to 100.0 that it falls within the limit of raising an error
            }

            if (myCheckModel.getCheckSpec() instanceof ColumnDistinctPercentCheckSpec) {
                if (sourceProfilingCheck.getActualValue() * notNullCount / 100.0 <= checkMiningConfigurationProperties.getMaxDistinctCount()) {
                    return null; // this is a distinct percent check, it has soo little distinct values that the distinct count check will be configured instead
                }
            }
        } else {
            Long rowCount = tableProfilingResults.getRowCount();
            if (rowCount == null) {
                return null; // cannot assess how many records the table has
            }

            if (rowCount < checkMiningConfigurationProperties.getMinReasonableNotNullsCount()) {
                return null;
            }
        }

        double referencePercent = Math.abs(sourceProfilingCheck.getActualValue());
        if (referencePercent > 50.0) {
            referencePercent = 100.0 - referencePercent; // if we are close to zero percent, we will propose around 0 percent, when we are close to 100.0, we will propose around 100.0
        }
        double delta = referencePercent * checkMiningConfigurationProperties.getPercentCheckDeltaRate();
        double expectedMinPercent = DoubleRounding.roundToKeepEffectiveDigits(sourceProfilingCheck.getActualValue() - delta);
        double expectedMaxPercent = DoubleRounding.roundToKeepEffectiveDigits(sourceProfilingCheck.getActualValue() + delta);

        if (expectedMinPercent < 0.0) {
            expectedMinPercent = 0.0;
        }

        if (expectedMaxPercent > 100.0) {
            expectedMaxPercent = 100.0;
        }

        if (sourceProfilingCheck.getActualValue() == 100.0) {
            expectedMinPercent = 100.0;
            expectedMaxPercent = 100.0; // special case, to fix too big values
        }

        if (expectedMaxPercent < expectedMinPercent) {
            expectedMaxPercent = expectedMinPercent;
        }

        return new BetweenPercentRuleParametersSpec(expectedMinPercent, expectedMaxPercent);
    }
}
