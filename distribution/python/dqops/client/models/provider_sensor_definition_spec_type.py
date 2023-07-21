from enum import Enum


class ProviderSensorDefinitionSpecType(str, Enum):
    JAVA_CLASS = "java_class"
    SQL_TEMPLATE = "sql_template"

    def __str__(self) -> str:
        return str(self.value)
