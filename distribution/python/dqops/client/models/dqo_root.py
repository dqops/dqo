from enum import Enum


class DqoRoot(str, Enum):
    CHECKS = "checks"
    DATA_CHECK_RESULTS = "data_check_results"
    DATA_ERRORS = "data_errors"
    DATA_INCIDENTS = "data_incidents"
    DATA_SENSOR_READOUTS = "data_sensor_readouts"
    DATA_STATISTICS = "data_statistics"
    RULES = "rules"
    SENSORS = "sensors"
    SOURCES = "sources"
    VALUE_1 = "_indexes"
    VALUE_7 = "_settings"

    def __str__(self) -> str:
        return str(self.value)
