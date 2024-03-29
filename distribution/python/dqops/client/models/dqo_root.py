from enum import Enum


class DqoRoot(str, Enum):
    CHECKS = "checks"
    CREDENTIALS = "credentials"
    DATA_CHECK_RESULTS = "data_check_results"
    DATA_ERRORS = "data_errors"
    DATA_INCIDENTS = "data_incidents"
    DATA_SENSOR_READOUTS = "data_sensor_readouts"
    DATA_STATISTICS = "data_statistics"
    DICTIONARIES = "dictionaries"
    PATTERNS = "patterns"
    RULES = "rules"
    SENSORS = "sensors"
    SETTINGS = "settings"
    SOURCES = "sources"
    VALUE_13 = "_indexes"
    VALUE_14 = "_local_settings"

    def __str__(self) -> str:
        return str(self.value)
