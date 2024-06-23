from enum import Enum


class DqoJobType(str, Enum):
    COLLECT_ERROR_SAMPLES = "collect_error_samples"
    COLLECT_ERROR_SAMPLES_ON_TABLE = "collect_error_samples_on_table"
    COLLECT_STATISTICS = "collect_statistics"
    COLLECT_STATISTICS_ON_TABLE = "collect_statistics_on_table"
    DELETE_STORED_DATA = "delete_stored_data"
    IMPORT_SCHEMA = "import_schema"
    IMPORT_TABLES = "import_tables"
    QUEUE_THREAD_SHUTDOWN = "queue_thread_shutdown"
    REPAIR_STORED_DATA = "repair_stored_data"
    RUN_CHECKS = "run_checks"
    RUN_CHECKS_ON_TABLE = "run_checks_on_table"
    RUN_SCHEDULED_CHECKS_CRON = "run_scheduled_checks_cron"
    SYNCHRONIZE_FOLDER = "synchronize_folder"
    SYNCHRONIZE_MULTIPLE_FOLDERS = "synchronize_multiple_folders"

    def __str__(self) -> str:
        return str(self.value)
