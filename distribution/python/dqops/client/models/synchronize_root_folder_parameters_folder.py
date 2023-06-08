from enum import Enum


class SynchronizeRootFolderParametersFolder(str, Enum):
    CHECKS = "checks"
    DATA_CHECK_RESULTS = "data_check_results"
    DATA_ERRORS = "data_errors"
    DATA_INCIDENTS = "data_incidents"
    DATA_SENSOR_READOUTS = "data_sensor_readouts"
    DATA_STATISTICS = "data_statistics"
    RULES = "rules"
    SENSORS = "sensors"
    SOURCES = "sources"
    VALUE_10 = "_settings"
    VALUE_9 = "_indexes"

    def __str__(self) -> str:
        return str(self.value)
