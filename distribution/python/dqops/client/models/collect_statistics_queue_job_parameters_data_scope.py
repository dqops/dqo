from enum import Enum


class CollectStatisticsQueueJobParametersDataScope(str, Enum):
    DATA_STREAM = "data_stream"
    TABLE = "table"

    def __str__(self) -> str:
        return str(self.value)
