from enum import Enum


class TopIncidentGrouping(str, Enum):
    CATEGORY = "category"
    CONNECTION = "connection"
    DIMENSION = "dimension"

    def __str__(self) -> str:
        return str(self.value)
