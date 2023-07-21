from enum import Enum


class GetTableComparisonPartitionedResultsTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
