from enum import Enum


class SimilarCheckModelCheckType(str, Enum):
    PARTITIONED = "partitioned"
    PROFILING = "profiling"
    RECURRING = "recurring"

    def __str__(self) -> str:
        return str(self.value)
