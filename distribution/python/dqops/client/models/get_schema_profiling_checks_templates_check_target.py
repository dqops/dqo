from enum import Enum


class GetSchemaProfilingChecksTemplatesCheckTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
