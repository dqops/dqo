from enum import Enum


class FileSynchronizationDirection(str, Enum):
    DOWNLOAD = "download"
    FULL = "full"
    UPLOAD = "upload"

    def __str__(self) -> str:
        return str(self.value)
