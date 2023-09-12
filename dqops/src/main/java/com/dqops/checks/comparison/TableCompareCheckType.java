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

package com.dqops.checks.comparison;

/**
 * Enumeration of compare check on a table level. Identifies the check type (such as the row_count_match), to enable retrieving the same checks
 * with slightly different names from profiling, daily monitoring, and other check types.
 */
public enum TableCompareCheckType {
    /**
     * Row count match check.
     */
    row_count_match,

    /**
     * Column count match check.
     */
    column_count_match
}
