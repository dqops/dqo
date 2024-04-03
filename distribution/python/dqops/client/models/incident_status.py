from enum import Enum


class IncidentStatus(str, Enum):
    ACKNOWLEDGED = "acknowledged"
    MUTED = "muted"
    OPEN = "open"
    RESOLVED = "resolved"

    def __str__(self) -> str:
        return str(self.value)
