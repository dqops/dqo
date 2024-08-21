from enum import Enum


class TargetRuleSeverityLevel(str, Enum):
    ERROR = "error"
    FATAL = "fatal"
    WARNING = "warning"

    def __str__(self) -> str:
        return str(self.value)
