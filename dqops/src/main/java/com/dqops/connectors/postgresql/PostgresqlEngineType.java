package com.dqops.connectors.postgresql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The engine type of Postgresql
 */
public enum PostgresqlEngineType {

    @JsonProperty("postgresql")
    postgresql,

    @JsonProperty("timescale")
    timescale;

    public String getDisplayName() {
        switch (this) {
            case postgresql:
                return "PostgreSQL";
            case timescale:
                return "Timescale";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }

}
