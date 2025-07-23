/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.postgresql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of sslMode connection parameters for finer control of the SSL connection in PostgreSql.
 */

public enum PostgresqlSslMode {
    @JsonProperty("disable")
    disable("disable"),

    @JsonProperty("allow")
    allow("allow"),

    @JsonProperty("prefer")
    prefer("prefer"),

    @JsonProperty("require")
    require("require"),

    @JsonProperty("verify-ca")
    verify_ca("verify-ca"),

    @JsonProperty("verify-full")
    verify_full("verify-full");

    private final String value;

    PostgresqlSslMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
