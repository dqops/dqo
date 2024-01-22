package com.dqops.connectors.mysql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The engine type of MySQL
 */
public enum MysqlEngineType {

    @JsonProperty("mysql")
    mysql,

    @JsonProperty("singlestore")
    singlestore;

    public String getDisplayName() {
        switch (this) {
            case mysql:
                return "MySQL";
            case singlestore:
                return "Single Store";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }

}
