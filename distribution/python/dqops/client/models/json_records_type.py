from enum import Enum


class JsonRecordsType(str, Enum):
    AUTO = "auto"
    FALSE = "false"
    TRUE = "true"

    def __str__(self) -> str:
        return str(self.value)
