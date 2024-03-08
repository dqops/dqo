from enum import Enum


class CompressionType(str, Enum):
    AUTO = "auto"
    GZIP = "gzip"
    NONE = "none"
    ZSTD = "zstd"

    def __str__(self) -> str:
        return str(self.value)
