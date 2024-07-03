from enum import Enum


class CheckResultSortOrder(str, Enum):
    ACTUALVALUE = "actualValue"
    CHECKCATEGORY = "checkCategory"
    CHECKDISPLAYNAME = "checkDisplayName"
    CHECKHASH = "checkHash"
    CHECKNAME = "checkName"
    CHECKTYPE = "checkType"
    COLUMNNAME = "columnName"
    DATAGROUP = "dataGroup"
    EXECUTEDAT = "executedAt"
    EXPECTEDVALUE = "expectedValue"
    QUALITYDIMENSION = "qualityDimension"
    SENSORNAME = "sensorName"
    SEVERITY = "severity"
    TIMEGRADIENT = "timeGradient"
    TIMEPERIOD = "timePeriod"
    UPDATEDAT = "updatedAt"

    def __str__(self) -> str:
        return str(self.value)
