from enum import Enum


class GetSchemaRecurringChecksTemplatesCheckTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
