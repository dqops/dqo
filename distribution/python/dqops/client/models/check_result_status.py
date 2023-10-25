from enum import Enum


class CheckResultStatus(str, Enum):
    ERROR = "error"
    EXECUTION_ERROR = "execution_error"
    FATAL = "fatal"
    VALID = "valid"
    WARNING = "warning"

    def __str__(self) -> str:
        return str(self.value)
