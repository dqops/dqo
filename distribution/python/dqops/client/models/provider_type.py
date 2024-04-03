from enum import Enum


class ProviderType(str, Enum):
    BIGQUERY = "bigquery"
    DATABRICKS = "databricks"
    DUCKDB = "duckdb"
    MYSQL = "mysql"
    ORACLE = "oracle"
    POSTGRESQL = "postgresql"
    PRESTO = "presto"
    REDSHIFT = "redshift"
    SNOWFLAKE = "snowflake"
    SPARK = "spark"
    SQLSERVER = "sqlserver"
    TRINO = "trino"

    def __str__(self) -> str:
        return str(self.value)
