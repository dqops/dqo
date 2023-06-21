from enum import Enum


class GetColumnPartitionedChecksResultsTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
