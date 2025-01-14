from enum import Enum


class DuckdbFilesFormatType(str, Enum):
    AVRO = "avro"
    CSV = "csv"
    DELTA_LAKE = "delta_lake"
    ICEBERG = "iceberg"
    JSON = "json"
    PARQUET = "parquet"

    def __str__(self) -> str:
        return str(self.value)
