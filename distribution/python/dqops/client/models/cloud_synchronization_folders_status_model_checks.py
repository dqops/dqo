from enum import Enum


class CloudSynchronizationFoldersStatusModelChecks(str, Enum):
    CHANGED = "changed"
    SYNCHRONIZING = "synchronizing"
    UNCHANGED = "unchanged"

    def __str__(self) -> str:
        return str(self.value)
