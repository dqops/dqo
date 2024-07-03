from enum import Enum


class DuckdbStorageType(str, Enum):
    AZURE = "azure"
    GCS = "gcs"
    LOCAL = "local"
    S3 = "s3"

    def __str__(self) -> str:
        return str(self.value)
