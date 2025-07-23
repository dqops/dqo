/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.db2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The platform type of DB2
 */
public enum Db2PlatformType {

    @JsonProperty("luw")
    luw,

    @JsonProperty("zos")
    zos;

    public String getDisplayName() {
        switch (this) {
            case luw:
                return "LUW";
            case zos:
                return "z/OS";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }

}
