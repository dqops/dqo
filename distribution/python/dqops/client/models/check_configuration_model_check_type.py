from enum import Enum


class CheckConfigurationModelCheckType(str, Enum):
    PARTITIONED = "partitioned"
    PROFILING = "profiling"
    RECURRING = "recurring"

    def __str__(self) -> str:
        return str(self.value)
