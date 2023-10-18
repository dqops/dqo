from enum import Enum


class EffectiveScheduleLevelModel(str, Enum):
    CHECK_OVERRIDE = "check_override"
    CONNECTION = "connection"
    TABLE_OVERRIDE = "table_override"

    def __str__(self) -> str:
        return str(self.value)
