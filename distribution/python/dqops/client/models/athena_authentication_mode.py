from enum import Enum


class AthenaAuthenticationMode(str, Enum):
    DEFAULT_CREDENTIALS = "default_credentials"
    IAM = "iam"

    def __str__(self) -> str:
        return str(self.value)
