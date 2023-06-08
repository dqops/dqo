from enum import Enum


class GetIncidentIssuesOrder(str, Enum):
    ACTUALVALUE = "actualValue"
    CHECKCATEGORY = "checkCategory"
    CHECKDISPLAYNAME = "checkDisplayName"
    CHECKHASH = "checkHash"
    CHECKNAME = "checkName"
    CHECKTYPE = "checkType"
    COLUMNNAME = "columnName"
    DATASTREAM = "dataStream"
    EXECUTEDAT = "executedAt"
    EXPECTEDVALUE = "expectedValue"
    QUALITYDIMENSION = "qualityDimension"
    SENSORNAME = "sensorName"
    SEVERITY = "severity"
    TIMEGRADIENT = "timeGradient"
    TIMEPERIOD = "timePeriod"

    def __str__(self) -> str:
        return str(self.value)
