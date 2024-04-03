from enum import Enum


class DisplayHint(str, Enum):
    TEXTAREA = "textarea"

    def __str__(self) -> str:
        return str(self.value)
