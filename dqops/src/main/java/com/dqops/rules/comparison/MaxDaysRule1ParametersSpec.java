/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.comparison;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
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
 * Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MaxDaysRule1ParametersSpec extends AbstractRuleParametersSpec implements RuleMiningRule {
    private static final ChildHierarchyNodeFieldMapImpl<MaxDaysRule1ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    public MaxDaysRule1ParametersSpec() {
    }

    /**
     * Parametrized constructor to create a rule with a custom delay.
     * @param maxDays Max delay in days.
     */
    public MaxDaysRule1ParametersSpec(Double maxDays) {
        this.maxDays = maxDays;
    }

    @JsonPropertyDescription("Maximum accepted value for the actual_value returned by the sensor (inclusive).")
    @RequiredField
    private Double maxDays = 1.0;

    /**
     * Returns a maximum value for a data quality check readout, for example a maximum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxDays() {
        return maxDays;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, for example a maximum row count.
     * @param maxDays Maximum value that is accepted.
     */
    public void setMaxDays(Double maxDays) {
        this.setDirtyIf(!Objects.equals(this.maxDays, maxDays));
        this.maxDays = maxDays;
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
        return "comparison/max_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxDays == null) {
            return;
        }

        if (this.maxDays <= 0) {
            this.maxDays = checkResultsSingleCheck.getActualValueColumn().max();
            return;
        }

        this.maxDays = DoubleRounding.roundToKeepEffectiveDigits(this.maxDays * 1.3);
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
        if (sourceProfilingCheck.getActualValue() < 0.0) {
            return null; // delay is negative, must be a wrong column
        }

        double expectedMinValue = DoubleRounding.roundToKeepEffectiveDigits(sourceProfilingCheck.getActualValue() +
                Math.abs(sourceProfilingCheck.getActualValue()) * checkMiningConfigurationProperties.getTimelinessMaxDaysMultiplier());

        return new MaxDaysRule1ParametersSpec(expectedMinValue);
    }
}
