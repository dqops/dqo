from enum import Enum


class ProfilingTimePeriod(str, Enum):
    ALL_RESULTS = "all_results"
    ONE_PER_DAY = "one_per_day"
    ONE_PER_HOUR = "one_per_hour"
    ONE_PER_MONTH = "one_per_month"
    ONE_PER_WEEK = "one_per_week"

    def __str__(self) -> str:
        return str(self.value)
