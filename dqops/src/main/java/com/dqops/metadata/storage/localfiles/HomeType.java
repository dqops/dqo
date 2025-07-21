/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies which home: user home (custom definitions and connections) or DQO_HOME (built-in definitions) is used
 */
public enum HomeType {
    /**
     * User home with custom rule and sensor definitions.
     */
    @JsonProperty("USER_HOME")
    USER_HOME,

    /**
     * DQO_HOME system home with built-in sensor and rule definitions.
     */
    @JsonProperty("DQO_HOME")
    DQO_HOME
}
