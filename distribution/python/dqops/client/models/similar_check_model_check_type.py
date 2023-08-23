from enum import Enum


class SimilarCheckModelCheckType(str, Enum):
    MONITORING = "monitoring"
    PARTITIONED = "partitioned"
    PROFILING = "profiling"

    def __str__(self) -> str:
        return str(self.value)
