from airflow.exceptions import AirflowException


class DqopsChecksFailedException(AirflowException):
    def __init__(self):
        error_message: str = "DQOps checks failed!"
        super().__init__(error_message)
