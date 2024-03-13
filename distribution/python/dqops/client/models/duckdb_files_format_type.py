from enum import Enum


class DuckdbFilesFormatType(str, Enum):
    CSV = "csv"
    JSON = "json"
    PARQUET = "parquet"

    def __str__(self) -> str:
        return str(self.value)
