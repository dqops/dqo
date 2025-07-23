/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
