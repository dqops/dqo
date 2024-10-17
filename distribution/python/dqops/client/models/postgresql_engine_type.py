from enum import Enum


class PostgresqlEngineType(str, Enum):
    POSTGRESQL = "postgresql"
    TIMESCALE = "timescale"

    def __str__(self) -> str:
        return str(self.value)
