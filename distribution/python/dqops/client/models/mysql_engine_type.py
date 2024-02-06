from enum import Enum


class MysqlEngineType(str, Enum):
    MYSQL = "mysql"
    SINGLESTOREDB = "singlestoredb"

    def __str__(self) -> str:
        return str(self.value)
