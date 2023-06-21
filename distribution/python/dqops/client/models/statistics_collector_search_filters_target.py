from enum import Enum


class StatisticsCollectorSearchFiltersTarget(str, Enum):
    COLUMN = "column"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
