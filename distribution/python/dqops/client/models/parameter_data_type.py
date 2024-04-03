from enum import Enum


class ParameterDataType(str, Enum):
    BOOLEAN = "boolean"
    COLUMN_NAME = "column_name"
    DATE = "date"
    DATETIME = "datetime"
    DOUBLE = "double"
    ENUM = "enum"
    INTEGER = "integer"
    INTEGER_LIST = "integer_list"
    LONG = "long"
    OBJECT = "object"
    STRING = "string"
    STRING_LIST = "string_list"

    def __str__(self) -> str:
        return str(self.value)
