from enum import Enum


class RuleSeverityLevel(str, Enum):
    ERROR = "error"
    FATAL = "fatal"
    VALID = "valid"
    WARNING = "warning"

    def __str__(self) -> str:
        return str(self.value)
