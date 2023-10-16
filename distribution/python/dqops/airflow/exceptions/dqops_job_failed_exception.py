import logging
from airflow.exceptions import AirflowException

class DqopsJobFailedException(AirflowException):
    def __init__(self):            
        error_message: str = "DQOps job failed!"
        super().__init__(error_message)
