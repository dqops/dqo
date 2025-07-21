/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tabular output format for printing the tabular results.
 */
public enum TabularOutputFormat {
    /**
     * The output is an ASCII formatted table.
     */
    @JsonProperty("table")
    TABLE,
    /**
     * The output is a csv table.
     */
    @JsonProperty("csv")
    CSV,
    /**
     * The output is a json table.
     */
    @JsonProperty("json")
    JSON,
}
