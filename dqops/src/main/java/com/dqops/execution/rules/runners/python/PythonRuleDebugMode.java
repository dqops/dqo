/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
