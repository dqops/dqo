from enum import Enum


class CheckResultsDetailedLoadMode(str, Enum):
    FIRST_DATA_GROUP = "first_data_group"
    MOST_RECENT_PER_GROUP = "most_recent_per_group"

    def __str__(self) -> str:
        return str(self.value)
