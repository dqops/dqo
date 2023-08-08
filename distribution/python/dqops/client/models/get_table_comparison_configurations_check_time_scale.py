from enum import Enum


class GetTableComparisonConfigurationsCheckTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
