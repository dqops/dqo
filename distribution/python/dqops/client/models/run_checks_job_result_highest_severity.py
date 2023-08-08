from enum import Enum


class RunChecksJobResultHighestSeverity(str, Enum):
    ERROR = "error"
    FATAL = "fatal"
    VALID = "valid"
    WARNING = "warning"

    def __str__(self) -> str:
        return str(self.value)
