from enum import Enum


class GetTablePartitionedChecksModelFilterTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
