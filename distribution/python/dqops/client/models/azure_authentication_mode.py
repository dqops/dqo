from enum import Enum


class AzureAuthenticationMode(str, Enum):
    CONNECTION_STRING = "connection_string"
    CREDENTIAL_CHAIN = "credential_chain"
    DEFAULT_CREDENTIALS = "default_credentials"
    SERVICE_PRINCIPAL = "service_principal"

    def __str__(self) -> str:
        return str(self.value)
