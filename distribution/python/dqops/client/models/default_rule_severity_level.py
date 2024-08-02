from enum import Enum


class DefaultRuleSeverityLevel(str, Enum):
    ERROR = "error"
    FATAL = "fatal"
    NONE = "none"
    WARNING = "warning"

    def __str__(self) -> str:
        return str(self.value)
