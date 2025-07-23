/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.execution.checks.ruleeval.RuleEvaluationResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.sources.TableSpec;

/**
 * Progress event raised after data quality rules were executed for all rows of normalized sensor results.
 */
public class RuleExecutedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorReadoutsNormalizedResult normalizedSensorResults;
    private final RuleEvaluationResult ruleEvaluationResult;

    /**
     * Creates a progress event object.
     *
     * @param tableSpec               Target table specification.
     * @param sensorRunParameters     Sensor run parameters.
     * @param normalizedSensorResults Normalized sensor results that were passed to the rule evaluation.
     * @param ruleEvaluationResult    Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     */
    public RuleExecutedEvent(TableSpec tableSpec,
                             SensorExecutionRunParameters sensorRunParameters,
                             SensorReadoutsNormalizedResult normalizedSensorResults,
                             RuleEvaluationResult ruleEvaluationResult) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.normalizedSensorResults = normalizedSensorResults;
        this.ruleEvaluationResult = ruleEvaluationResult;
    }

    /**
     * Target table specification.
     *
     * @return Target table specification.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Normalized sensor results that were passed to the rule evaluation.
     *
     * @return Normalized sensor results that were passed to the rule evaluation.
     */
    public SensorReadoutsNormalizedResult getNormalizedSensorResults() {
        return normalizedSensorResults;
    }

    /**
     * Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     *
     * @return Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     */
    public RuleEvaluationResult getRuleEvaluationResult() {
        return ruleEvaluationResult;
    }
}
