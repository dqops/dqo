from enum import Enum


class GetSchemaMonitoringChecksTemplatesCheckTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
