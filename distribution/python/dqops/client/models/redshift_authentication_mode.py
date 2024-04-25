from enum import Enum


class RedshiftAuthenticationMode(str, Enum):
    DEFAULT_CREDENTIALS = "default_credentials"
    IAM = "iam"
    USER_PASSWORD = "user_password"

    def __str__(self) -> str:
        return str(self.value)
