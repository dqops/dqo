from enum import Enum


class UICheckModelCheckTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
