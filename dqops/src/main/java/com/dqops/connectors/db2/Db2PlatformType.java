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
