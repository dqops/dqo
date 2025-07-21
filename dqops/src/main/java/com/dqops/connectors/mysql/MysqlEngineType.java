/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.mysql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The engine type of MySQL
 */
public enum MysqlEngineType {

    @JsonProperty("mysql")
    mysql,

    @JsonProperty("singlestoredb")
    singlestoredb;

    public String getDisplayName() {
        switch (this) {
            case mysql:
                return "MySQL";
            case singlestoredb:
                return "Single Store DB";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }

}
