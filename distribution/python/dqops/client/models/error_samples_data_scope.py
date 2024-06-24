from enum import Enum


class ErrorSamplesDataScope(str, Enum):
    DATA_GROUP = "data_group"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
