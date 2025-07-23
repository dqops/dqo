/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.ruleeval;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.sensors.SensorExecutionRunParameters;

/**
 * Service that evaluates rules for each sensor readouts returned by a sensor query.
 */
public interface RuleEvaluationService {
    /**
     * Evaluate rules for data quality checks.
     * @param executionContext Check execution context.
     * @param checkSpec Check specification with a list of rules.
     * @param sensorRunParameters Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadoutsSnapshot Snapshot of all sensor readouts loaded for the table.
     * @param progressListener Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    RuleEvaluationResult evaluateRules(ExecutionContext executionContext,
                                       AbstractCheckSpec checkSpec,
                                       SensorExecutionRunParameters sensorRunParameters,
                                       SensorReadoutsNormalizedResult normalizedSensorResults,
                                       SensorReadoutsSnapshot sensorReadoutsSnapshot,
                                       CheckExecutionProgressListener progressListener);
}
