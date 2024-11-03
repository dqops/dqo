from enum import Enum


class DisplayHint(str, Enum):
    COLUMN_NAMES = "column_names"
    REQUIRES_PAID_VERSION = "requires_paid_version"
    TEXTAREA = "textarea"

    def __str__(self) -> str:
        return str(self.value)
