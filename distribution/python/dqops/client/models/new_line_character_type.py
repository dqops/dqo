from enum import Enum


class NewLineCharacterType(str, Enum):
    CR = "cr"
    CRLF = "crlf"
    LF = "lf"

    def __str__(self) -> str:
        return str(self.value)
