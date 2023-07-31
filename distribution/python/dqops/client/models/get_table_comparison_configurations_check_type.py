from enum import Enum


class GetTableComparisonConfigurationsCheckType(str, Enum):
    PARTITIONED = "partitioned"
    PROFILING = "profiling"
    RECURRING = "recurring"

    def __str__(self) -> str:
        return str(self.value)
