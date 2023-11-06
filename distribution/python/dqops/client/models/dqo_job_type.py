from enum import Enum


class DqoJobType(str, Enum):
    COLLECT_STATISTICS = "collect statistics"
    COLLECT_STATISTICS_ON_TABLE = "collect statistics on table"
    DELETE_STORED_DATA = "delete stored data"
    IMPORT_SCHEMA = "import schema"
    IMPORT_SELECTED_TABLES = "import selected tables"
    QUEUE_THREAD_SHUTDOWN = "queue thread shutdown"
    REPAIR_STORED_DATA = "repair stored data"
    RUN_CHECKS = "run checks"
    RUN_CHECKS_ON_TABLE = "run checks on table"
    RUN_SCHEDULED_CHECKS_BY_CRON = "run scheduled checks by cron"
    SYNCHRONIZE_FOLDER = "synchronize folder"
    SYNCHRONIZE_MULTIPLE_FOLDERS = "synchronize multiple folders"

    def __str__(self) -> str:
        return str(self.value)
