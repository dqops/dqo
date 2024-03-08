from enum import Enum


class DuckdbSecretsType(str, Enum):
    AZURE = "azure"
    GCS = "gcs"
    R2 = "r2"
    S3 = "s3"

    def __str__(self) -> str:
        return str(self.value)
