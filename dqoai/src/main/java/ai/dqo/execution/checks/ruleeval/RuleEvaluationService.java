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
package ai.dqo.execution.checks.ruleeval;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshot;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;

/**
 * Service that evaluates rules for each sensor reading returned by a sensor query.
 */
public interface RuleEvaluationService {
    /**
     * Evaluate rules for sensor rules
     * @param checkExecutionContext Check execution context.
     * @param checkSpec Check specification with a list of rules.
     * @param sensorRunParameters Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadingsSnapshot Snapshot of all sensor readings loaded for the table.
     * @param progressListener Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    RuleEvaluationResult evaluateRules(CheckExecutionContext checkExecutionContext,
                                       AbstractCheckSpec checkSpec,
                                       SensorExecutionRunParameters sensorRunParameters,
                                       SensorNormalizedResult normalizedSensorResults,
                                       SensorReadingsSnapshot sensorReadingsSnapshot,
                                       CheckExecutionProgressListener progressListener);
}
