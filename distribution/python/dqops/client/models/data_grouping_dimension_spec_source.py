from enum import Enum


class DataGroupingDimensionSpecSource(str, Enum):
    COLUMN_VALUE = "column_value"
    TAG = "tag"

    def __str__(self) -> str:
        return str(self.value)
