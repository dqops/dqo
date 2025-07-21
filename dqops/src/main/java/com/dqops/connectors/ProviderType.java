/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Data source provider type (dialect type).
 *  We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.
 */
public enum ProviderType {
    @JsonProperty("bigquery")
    bigquery,

    @JsonProperty("clickhouse")
    clickhouse,

    @JsonProperty("databricks")
    databricks,

    @JsonProperty("db2")
    db2,

    @JsonProperty("duckdb")
    duckdb,

    @JsonProperty("hana")
    hana,

    @JsonProperty("mariadb")
    mariadb,

    @JsonProperty("mysql")
    mysql,

    @JsonProperty("oracle")
    oracle,

    @JsonProperty("postgresql")
    postgresql,

    @JsonProperty("presto")
    presto,

    @JsonProperty("questdb")
    questdb,

    @JsonProperty("redshift")
    redshift,

    @JsonProperty("snowflake")
    snowflake,

    @JsonProperty("spark")
    spark,

    @JsonProperty("sqlserver")
    sqlserver,

    @JsonProperty("teradata")
    teradata,

    @JsonProperty("trino")
    trino,

    // TODO: add more connectors, keep in asc order

    ;
    public String getDisplayName() {
        switch (this) {
            case bigquery:
                return "BigQuery";
            case clickhouse:
                return "ClickHouse";
            case databricks:
                return "Databricks";
            case db2:
                return "DB2";
            case duckdb:
                return "DuckDB";
            case hana:
                return "HANA";
            case mariadb:
                return "MariaDB";
            case mysql:
                return "MySQL";
            case oracle:
                return "Oracle";
            case postgresql:
                return "PostgreSQL";
            case presto:
                return "Presto";
            case questdb:
                return "QuestDB";
            case redshift:
                return "Redshift";
            case snowflake:
                return "Snowflake";
            case spark:
                return "Spark";
            case sqlserver:
                return "SQL Server";
            case teradata:
                return "Teradata";
            case trino:
                return "Trino";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }
}
