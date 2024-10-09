from enum import Enum


class ProviderType(str, Enum):
    BIGQUERY = "bigquery"
    DATABRICKS = "databricks"
    DB2 = "db2"
    DUCKDB = "duckdb"
    HANA = "hana"
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
