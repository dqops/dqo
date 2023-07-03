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

package com.dqops.execution.sqltemplates.grouping;

/**
 * Enumeration of SQL query component types.
 */
public enum SqlQueryFragmentType {
    /**
     * Static SQL fragment that must be the same for all similar queries that could be merged.
     */
    STATIC_FRAGMENT,

    /**
     * The SQL query fragment that computes the sensor value (the actual_value result column).
     */
    ACTUAL_VALUE,

    /**
     * The SQL query fragment that computes expected value.
     */
    EXPECTED_VALUE
}
