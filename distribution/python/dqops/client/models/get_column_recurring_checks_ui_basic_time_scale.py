from enum import Enum


class GetColumnRecurringChecksUIBasicTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
