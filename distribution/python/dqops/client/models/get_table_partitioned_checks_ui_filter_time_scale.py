from enum import Enum


class GetTablePartitionedChecksUIFilterTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
