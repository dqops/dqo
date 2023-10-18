from enum import Enum


class StringsBuiltInDateFormats(str, Enum):
    DAYMONTHYEAR = "DayMonthYear"
    ISO8601 = "ISO8601"
    MONTHDAYYEAR = "MonthDayYear"
    MONTHNAMEDAYYEAR = "MonthNameDayYear"
    YEARMONTHDAY = "YearMonthDay"

    def __str__(self) -> str:
        return str(self.value)
