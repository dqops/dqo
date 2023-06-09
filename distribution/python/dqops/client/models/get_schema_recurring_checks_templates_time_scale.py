from enum import Enum


class GetSchemaRecurringChecksTemplatesTimeScale(str, Enum):
    DAILY = "daily"
    MONTHLY = "monthly"

    def __str__(self) -> str:
        return str(self.value)
