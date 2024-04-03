from enum import Enum


class RuleRunnerType(str, Enum):
    JAVA_CLASS = "java_class"
    PYTHON = "python"

    def __str__(self) -> str:
        return str(self.value)
