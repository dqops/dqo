from enum import Enum


class DqoUserRole(str, Enum):
    ADMIN = "ADMIN"
    EDITOR = "EDITOR"
    NONE = "NONE"
    OPERATOR = "OPERATOR"
    VIEWER = "VIEWER"

    def __str__(self) -> str:
        return str(self.value)
