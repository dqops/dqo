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
