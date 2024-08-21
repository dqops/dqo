from enum import Enum


class IncidentFilteredNotificationLocation(str, Enum):
    CONNECTION = "connection"
    GLOBAL = "global"

    def __str__(self) -> str:
        return str(self.value)
