from enum import Enum


class DataGroupingDimensionSource(str, Enum):
    COLUMN_VALUE = "column_value"
    TAG = "tag"

    def __str__(self) -> str:
        return str(self.value)
