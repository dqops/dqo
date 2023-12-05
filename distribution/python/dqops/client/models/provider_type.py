from enum import Enum


class ProviderType(str, Enum):
    BIGQUERY = "bigquery"
    MYSQL = "mysql"
    ORACLE = "oracle"
    POSTGRESQL = "postgresql"
    PRESTO = "presto"
    REDSHIFT = "redshift"
    SNOWFLAKE = "snowflake"
    SQLSERVER = "sqlserver"
    TRINO = "trino"

    def __str__(self) -> str:
        return str(self.value)
