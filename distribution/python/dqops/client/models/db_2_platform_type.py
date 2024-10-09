from enum import Enum


class Db2PlatformType(str, Enum):
    LUW = "luw"
    ZOS = "zos"

    def __str__(self) -> str:
        return str(self.value)
