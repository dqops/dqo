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

    @JsonProperty("databricks")
    databricks,

    @JsonProperty("mysql")
    mysql,

    @JsonProperty("oracle")
    oracle,

    @JsonProperty("postgresql")
    postgresql,

    @JsonProperty("presto")
    presto,

    @JsonProperty("redshift")
    redshift,

    @JsonProperty("snowflake")
    snowflake,

    @JsonProperty("spark")
    spark,

    @JsonProperty("sqlserver")
    sqlserver,

    @JsonProperty("trino")
    trino,

    // TODO: add more connectors

    ;
    public String getDisplayName() {
        switch (this) {
            case bigquery:
                return "BigQuery";
            case databricks:
                return "Databricks";
            case mysql:
                return "MySQL";
            case oracle:
                return "Oracle";
            case postgresql:
                return "PostgreSQL";
            case presto:
                return "Presto";
            case redshift:
                return "Redshift";
            case snowflake:
                return "Snowflake";
            case spark:
                return "Spark";
            case sqlserver:
                return "SQL Server";
            case trino:
                return "Trino";
            default:
                throw new RuntimeException("Unsupported enum: " + this.name());
        }
    }
}
