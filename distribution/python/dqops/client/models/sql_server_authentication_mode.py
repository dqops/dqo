from enum import Enum


class SqlServerAuthenticationMode(str, Enum):
    ACTIVE_DIRECTORY_DEFAULT = "active_directory_default"
    ACTIVE_DIRECTORY_PASSWORD = "active_directory_password"
    ACTIVE_DIRECTORY_SERVICE_PRINCIPAL = "active_directory_service_principal"
    SQL_PASSWORD = "sql_password"

    def __str__(self) -> str:
        return str(self.value)
