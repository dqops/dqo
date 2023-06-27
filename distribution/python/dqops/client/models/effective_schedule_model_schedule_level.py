from enum import Enum


class EffectiveScheduleModelScheduleLevel(str, Enum):
    CHECK_OVERRIDE = "check_override"
    CONNECTION = "connection"
    TABLE_OVERRIDE = "table_override"

    def __str__(self) -> str:
        return str(self.value)
