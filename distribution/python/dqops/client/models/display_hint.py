from enum import Enum


class DisplayHint(str, Enum):
    COLUMN_NAMES = "column_names"
    TEXTAREA = "textarea"

    def __str__(self) -> str:
        return str(self.value)
