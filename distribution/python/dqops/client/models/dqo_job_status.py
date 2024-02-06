from enum import Enum


class DqoJobStatus(str, Enum):
    CANCELLED = "cancelled"
    CANCEL_REQUESTED = "cancel_requested"
    FAILED = "failed"
    FINISHED = "finished"
    QUEUED = "queued"
    RUNNING = "running"
    WAITING = "waiting"

    def __str__(self) -> str:
        return str(self.value)
