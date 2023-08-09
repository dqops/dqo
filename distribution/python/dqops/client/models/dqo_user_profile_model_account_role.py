from enum import Enum


class DqoUserProfileModelAccountRole(str, Enum):
    ADMIN = "admin"
    EDITOR = "editor"
    NONE = "none"
    OPERATOR = "operator"
    VIEWER = "viewer"

    def __str__(self) -> str:
        return str(self.value)
