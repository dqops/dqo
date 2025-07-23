/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errors.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies the source of the error: sensor (when the sensor failed to execute) or rule (when the rule failed to execute).
 */
public enum ErrorSource {
    /**
     * The error was raised in a sensor.
     */
    @JsonProperty("sensor")
    sensor,

    /**
     * The error was raised in a rule.
     */
    @JsonProperty("rule")
    rule
}
