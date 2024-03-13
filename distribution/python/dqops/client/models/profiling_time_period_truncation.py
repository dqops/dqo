from enum import Enum


class ProfilingTimePeriodTruncation(str, Enum):
    STORE_ALL_RESULTS_WITHOUT_DATE_TRUNCATION = (
        "store_all_results_without_date_truncation"
    )
    STORE_THE_MOST_RECENT_RESULT_PER_DAY = "store_the_most_recent_result_per_day"
    STORE_THE_MOST_RECENT_RESULT_PER_HOUR = "store_the_most_recent_result_per_hour"
    STORE_THE_MOST_RECENT_RESULT_PER_MONTH = "store_the_most_recent_result_per_month"
    STORE_THE_MOST_RECENT_RESULT_PER_WEEK = "store_the_most_recent_result_per_week"

    def __str__(self) -> str:
        return str(self.value)
