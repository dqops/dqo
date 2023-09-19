from enum import Enum


class StatisticsResultDataType(str, Enum):
    BOOLEAN = "BOOLEAN"
    DATE = "DATE"
    DATETIME = "DATETIME"
    FLOAT = "FLOAT"
    INSTANT = "INSTANT"
    INTEGER = "INTEGER"
    NULL = "NULL"
    STRING = "STRING"
    TIME = "TIME"

    def __str__(self) -> str:
        return str(self.value)
