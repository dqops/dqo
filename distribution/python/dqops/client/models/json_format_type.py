from enum import Enum


class JsonFormatType(str, Enum):
    ARRAY = "array"
    AUTO = "auto"
    NEWLINE_DELIMITED = "newline_delimited"
    UNSTRUCTURED = "unstructured"

    def __str__(self) -> str:
        return str(self.value)
