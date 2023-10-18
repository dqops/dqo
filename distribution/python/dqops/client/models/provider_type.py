from enum import Enum


class ProviderType(str, Enum):
    BIGQUERY = "bigquery"
    MYSQL = "mysql"
    ORACLE = "oracle"
    POSTGRESQL = "postgresql"
    REDSHIFT = "redshift"
    SNOWFLAKE = "snowflake"
    SQLSERVER = "sqlserver"

    def __str__(self) -> str:
        return str(self.value)
