from enum import Enum


class PostgreqlSslmode(str, Enum):
    ALLOW = "allow"
    DISABLE = "disable"
    PREFER = "prefer"
    REQUIRE = "require"
    VERIFY_CA = "verify_ca"
    VERIFY_FULL = "verify_full"

    def __str__(self) -> str:
        return str(self.value)
