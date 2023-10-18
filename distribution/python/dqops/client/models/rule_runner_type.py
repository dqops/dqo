from enum import Enum


class RuleRunnerType(str, Enum):
    CUSTOM_CLASS = "custom_class"
    PYTHON = "python"

    def __str__(self) -> str:
        return str(self.value)
