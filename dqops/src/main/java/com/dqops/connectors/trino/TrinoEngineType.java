package com.dqops.connectors.trino;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The engine type of Trino
 */
public enum TrinoEngineType {

    @JsonProperty("trino")
    trino,

    @JsonProperty("athena")
    athena;

    public String getDisplayName() {
        switch (this) {
            case trino:
                return "Trino";
            case athena:
                return "Athena";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }

}
