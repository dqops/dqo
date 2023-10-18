from enum import Enum


class HistoricDataPointsGrouping(str, Enum):
    DAY = "day"
    HOUR = "hour"
    LAST_N_READOUTS = "last_n_readouts"
    MONTH = "month"
    QUARTER = "quarter"
    WEEK = "week"
    YEAR = "year"

    def __str__(self) -> str:
        return str(self.value)
