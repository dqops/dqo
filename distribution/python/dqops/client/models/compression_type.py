from enum import Enum


class CompressionType(str, Enum):
    AUTO = "auto"
    GZIP = "gzip"
    LZ4 = "lz4"
    NONE = "none"
    SNAPPY = "snappy"
    ZSTD = "zstd"

    def __str__(self) -> str:
        return str(self.value)
