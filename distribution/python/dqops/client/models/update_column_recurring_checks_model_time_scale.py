from enum import Enum


class UpdateColumnRecurringChecksModelTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
