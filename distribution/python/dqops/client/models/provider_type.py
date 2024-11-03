from enum import Enum


class ProviderType(str, Enum):
    BIGQUERY = "bigquery"
    CLICKHOUSE = "clickhouse"
    DATABRICKS = "databricks"
    DB2 = "db2"
    DUCKDB = "duckdb"
    HANA = "hana"
    MARIADB = "mariadb"
    MYSQL = "mysql"
    ORACLE = "oracle"
    POSTGRESQL = "postgresql"
    PRESTO = "presto"
    QUESTDB = "questdb"
    REDSHIFT = "redshift"
    SNOWFLAKE = "snowflake"
    SPARK = "spark"
    SQLSERVER = "sqlserver"
    TERADATA = "teradata"
    TRINO = "trino"

    def __str__(self) -> str:
        return str(self.value)
