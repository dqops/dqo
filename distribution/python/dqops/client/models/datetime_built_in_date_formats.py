from enum import Enum


class DatetimeBuiltInDateFormats(str, Enum):
    DAYDASHMONTHDASHYEAR = "DayDashMonthDashYear"
    DAYDOTMONTHDOTYEAR = "DayDotMonthDotYear"
    DAYSLASHMONTHSLASHYEAR = "DaySlashMonthSlashYear"
    ISO8601 = "ISO8601"

    def __str__(self) -> str:
        return str(self.value)
