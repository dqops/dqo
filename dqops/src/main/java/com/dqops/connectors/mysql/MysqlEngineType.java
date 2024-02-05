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
