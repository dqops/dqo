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

package com.dqops.execution.rules.runners.python;

/**
 * Enumeration of the debugging modes for Python rules. The rules can store all data in files.
 */
public enum PythonRuleDebugMode {
    /**
     * Store all rule parameters and results for passed and failed rules.
     */
    all,

    /**
     * Store the parameters and results only for rules that returned a failed result.
     */
    failed,

    /**
     * Store the parameters and results only for rules that failed with an exception.
     */
    exception,

    /**
     * Do not store any debugging information (a silent mode).
     */
    silent
}
