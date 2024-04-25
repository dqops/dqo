from enum import Enum


class AwsAuthenticationMode(str, Enum):
    DEFAULT_CREDENTIALS = "default_credentials"
    IAM = "iam"

    def __str__(self) -> str:
        return str(self.value)
