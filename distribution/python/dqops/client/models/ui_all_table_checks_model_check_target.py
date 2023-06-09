from enum import Enum


class UIAllTableChecksModelCheckTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
