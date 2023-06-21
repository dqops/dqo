from enum import Enum


class SynchronizeRootFolderParametersDirection(str, Enum):
    DOWNLOAD = "download"
    FULL = "full"
    UPLOAD = "upload"

    def __str__(self) -> str:
        return str(self.value)
