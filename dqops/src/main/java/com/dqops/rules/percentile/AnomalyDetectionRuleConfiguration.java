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

package com.dqops.rules.percentile;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Constants with additional configuration of parameters that are passed to anomaly detection checks that use the t-student distribution.
 */
public final class AnomalyDetectionRuleConfiguration {
    /**
     * The additional rule parameters passed to the rules that use t-student distribution.
     * We are using our "Degree of freedom" to be prepared for bigger anomalies.
     */
    public static final Map<String, String> T_STUDENT_DISTRIBUTION_PARAMETERS = Collections.unmodifiableMap(new LinkedHashMap<>() {{
        put("degrees_of_freedom", "5");
    }});
}