from enum import Enum


class ParameterDataType(str, Enum):
    BOOLEAN_TYPE = "boolean_type"
    COLUMN_NAME_TYPE = "column_name_type"
    DATETIME_TYPE = "datetime_type"
    DATE_TYPE = "date_type"
    DOUBLE_TYPE = "double_type"
    ENUM_TYPE = "enum_type"
    INTEGER_LIST_TYPE = "integer_list_type"
    INTEGER_TYPE = "integer_type"
    LONG_TYPE = "long_type"
    OBJECT_TYPE = "object_type"
    STRING_LIST_TYPE = "string_list_type"
    STRING_TYPE = "string_type"

    def __str__(self) -> str:
        return str(self.value)
