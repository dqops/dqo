from enum import Enum


class ParameterDefinitionSpecDisplayHint(str, Enum):
    TEXTAREA = "textarea"

    def __str__(self) -> str:
        return str(self.value)
