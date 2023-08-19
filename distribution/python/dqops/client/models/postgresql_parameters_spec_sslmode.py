from enum import Enum


class PostgresqlParametersSpecSslmode(str, Enum):
    ALLOW = "allow"
    DISABLE = "disable"
    PREFER = "prefer"
    REQUIRE = "require"
    VERIFY_CA = "verify-ca"
    VERIFY_FULL = "verify-full"

    def __str__(self) -> str:
        return str(self.value)
