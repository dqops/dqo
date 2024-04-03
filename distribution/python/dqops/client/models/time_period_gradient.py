from enum import Enum


class TimePeriodGradient(str, Enum):
    DAY = "day"
    HOUR = "hour"
    MILLISECOND = "millisecond"
    MONTH = "month"
    QUARTER = "quarter"
    WEEK = "week"
    YEAR = "year"

    def __str__(self) -> str:
        return str(self.value)
