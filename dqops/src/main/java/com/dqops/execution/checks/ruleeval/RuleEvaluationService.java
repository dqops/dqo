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
