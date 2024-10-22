from enum import Enum


class RunChecksTarget(str, Enum):
    ONLY_RULES = "only_rules"
    ONLY_SENSORS = "only_sensors"
    SENSORS_AND_RULES = "sensors_and_rules"

    def __str__(self) -> str:
        return str(self.value)
