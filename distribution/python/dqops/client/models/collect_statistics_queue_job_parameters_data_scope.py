from enum import Enum


class CollectStatisticsQueueJobParametersDataScope(str, Enum):
    DATA_GROUPINGS = "data_groupings"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
