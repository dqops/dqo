from enum import Enum


class ParameterDefinitionSpecDataType(str, Enum):
    BOOLEAN = "boolean"
    COLUMN_NAME = "column_name"
    DATE = "date"
    DOUBLE = "double"
    ENUM = "enum"
    INSTANT = "instant"
    INTEGER = "integer"
    INTEGER_LIST = "integer_list"
    LONG = "long"
    OBJECT = "object"
    STRING = "string"
    STRING_LIST = "string_list"

    def __str__(self) -> str:
        return str(self.value)
