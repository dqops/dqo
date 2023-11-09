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
