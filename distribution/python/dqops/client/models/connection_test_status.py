from enum import Enum


class ConnectionTestStatus(str, Enum):
    CONNECTION_ALREADY_EXISTS = "CONNECTION_ALREADY_EXISTS"
    FAILURE = "FAILURE"
    SUCCESS = "SUCCESS"

    def __str__(self) -> str:
        return str(self.value)
