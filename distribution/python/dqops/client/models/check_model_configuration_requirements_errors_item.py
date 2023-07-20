from enum import Enum


class CheckModelConfigurationRequirementsErrorsItem(str, Enum):
    MISSING_EVENT_AND_INGESTION_COLUMNS = "missing_event_and_ingestion_columns"
    MISSING_EVENT_TIMESTAMP_COLUMN = "missing_event_timestamp_column"
    MISSING_INGESTION_TIMESTAMP_COLUMN = "missing_ingestion_timestamp_column"
    MISSING_TIME_PERIOD_PARTITIONING_COLUMN = "missing_time_period_partitioning_column"

    def __str__(self) -> str:
        return str(self.value)
