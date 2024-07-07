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

package com.dqops.services.check.mining;

import com.dqops.rules.RuleSeverityLevel;
import com.dqops.services.check.mapping.models.CheckModel;

/**
 * Information retrieved from a single profiling, which is the severity level and a configuration of a data quality check.
 */
public class ProfilingCheckResult {
    /**
     * Sensor name.
     */
    private String sensorName;

    /**
     * Check hash that identifies the check.
     */
    private Long checkHash;

    /**
     * Profiling check model with the full configuration of the profiling check, sensor parameters and rule parameters. This information must be added.
     */
    private CheckModel profilingCheckModel;

    /**
     * Rule severity level.
     */
    private RuleSeverityLevel severityLevel;

    /**
     * Actual value.
     */
    private Double actualValue;
}
