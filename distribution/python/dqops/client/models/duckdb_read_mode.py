from enum import Enum


class DuckdbReadMode(str, Enum):
    FILES = "files"
    IN_MEMORY = "in_memory"

    def __str__(self) -> str:
        return str(self.value)
