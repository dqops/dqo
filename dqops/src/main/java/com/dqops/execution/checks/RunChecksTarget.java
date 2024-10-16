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

package com.dqops.execution.checks;

/**
 * The target that is run when performing data quality checks. The options are: only_sensors, only_rules (for data that is already there), sensors_and_rules (the default).
 */
public enum RunChecksTarget {
    /**
     * Runs the whole check: the sensor to collect metrics and the rules to validate them.
     */
    sensors_and_rules,

    /**
     * Runs only the sensor, without evaluating the rules.
     */
    only_sensors,

    /**
     * Runs only the rules on existing sensor readouts (measures).
     */
    only_rules;
}
