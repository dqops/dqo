from enum import Enum


class ConnectionRemoteModelConnectionStatus(str, Enum):
    FAILURE = "FAILURE"
    SUCCESS = "SUCCESS"

    def __str__(self) -> str:
        return str(self.value)
