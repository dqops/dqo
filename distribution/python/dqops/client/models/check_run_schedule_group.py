from enum import Enum


class CheckRunScheduleGroup(str, Enum):
    MONITORING_DAILY = "monitoring_daily"
    MONITORING_MONTHLY = "monitoring_monthly"
    PARTITIONED_DAILY = "partitioned_daily"
    PARTITIONED_MONTHLY = "partitioned_monthly"
    PROFILING = "profiling"

    def __str__(self) -> str:
        return str(self.value)
