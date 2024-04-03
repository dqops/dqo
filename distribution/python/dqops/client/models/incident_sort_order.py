from enum import Enum


class IncidentSortOrder(str, Enum):
    CHECKNAME = "checkName"
    DATAGROUP = "dataGroup"
    FAILEDCHECKSCOUNT = "failedChecksCount"
    FIRSTSEEN = "firstSeen"
    HIGHESTSEVERITY = "highestSeverity"
    LASTSEEN = "lastSeen"
    QUALITYDIMENSION = "qualityDimension"
    TABLE = "table"
    TABLEPRIORITY = "tablePriority"

    def __str__(self) -> str:
        return str(self.value)
