from enum import Enum


class TextBuiltInDateFormats(str, Enum):
    DDMMYYYY = "DD/MM/YYYY"
    MMDDYYYY = "MM/DD/YYYY"
    MONTH_D_YYYY = "Month D, YYYY"
    YYYYMMDD = "YYYY/MM/DD"
    YYYY_MM_DD = "YYYY-MM-DD"

    def __str__(self) -> str:
        return str(self.value)
