from enum import Enum


class GetConnectionSchedulingGroupSchedulingGroup(str, Enum):
    PARTITIONED_DAILY = "partitioned_daily"
    PARTITIONED_MONTHLY = "partitioned_monthly"
    PROFILING = "profiling"
    RECURRING_DAILY = "recurring_daily"
    RECURRING_MONTHLY = "recurring_monthly"

    def __str__(self) -> str:
        return str(self.value)
