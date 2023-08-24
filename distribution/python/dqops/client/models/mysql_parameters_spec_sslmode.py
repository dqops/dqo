from enum import Enum


class MysqlParametersSpecSslmode(str, Enum):
    DISABLED = "DISABLED"
    PREFERRED = "PREFERRED"
    REQUIRED = "REQUIRED"
    VERIFY_CA = "VERIFY_CA"
    VERIFY_IDENTITY = "VERIFY_IDENTITY"

    def __str__(self) -> str:
        return str(self.value)
