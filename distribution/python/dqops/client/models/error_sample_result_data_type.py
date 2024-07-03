from enum import Enum


class ErrorSampleResultDataType(str, Enum):
    BOOLEAN = "boolean"
    DATE = "date"
    DATETIME = "datetime"
    FLOAT = "float"
    INSTANT = "instant"
    INTEGER = "integer"
    NULL = "null"
    STRING = "string"
    TIME = "time"

    def __str__(self) -> str:
        return str(self.value)
