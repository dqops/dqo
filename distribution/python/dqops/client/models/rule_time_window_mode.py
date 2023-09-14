from enum import Enum


class RuleTimeWindowMode(str, Enum):
    CURRENT_VALUE = "current_value"
    PREVIOUS_READOUTS = "previous_readouts"

    def __str__(self) -> str:
        return str(self.value)
