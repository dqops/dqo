from enum import Enum


class TrinoEngineType(str, Enum):
    ATHENA = "athena"
    TRINO = "trino"

    def __str__(self) -> str:
        return str(self.value)
