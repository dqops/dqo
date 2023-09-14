from enum import Enum


class BigQueryAuthenticationMode(str, Enum):
    GOOGLE_APPLICATION_CREDENTIALS = "google_application_credentials"
    JSON_KEY_CONTENT = "json_key_content"
    JSON_KEY_PATH = "json_key_path"

    def __str__(self) -> str:
        return str(self.value)
