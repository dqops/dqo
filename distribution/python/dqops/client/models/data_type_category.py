from enum import Enum


class DataTypeCategory(str, Enum):
    ARRAY = "array"
    BINARY = "binary"
    BOOL = "bool"
    CLOB = "clob"
    DATETIME_DATE = "datetime_date"
    DATETIME_DATETIME = "datetime_datetime"
    DATETIME_TIME = "datetime_time"
    DATETIME_TIMESTAMP = "datetime_timestamp"
    JSON = "json"
    NUMERIC_DECIMAL = "numeric_decimal"
    NUMERIC_FLOAT = "numeric_float"
    NUMERIC_INTEGER = "numeric_integer"
    OTHER = "other"
    TEXT = "text"

    def __str__(self) -> str:
        return str(self.value)
