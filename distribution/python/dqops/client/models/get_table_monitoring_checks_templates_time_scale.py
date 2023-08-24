from enum import Enum


class GetTableMonitoringChecksTemplatesTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
