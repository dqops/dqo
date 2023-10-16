import logging
from airflow.exceptions import AirflowException

class DqopsTimeoutException(AirflowException):
    def __init__(self):         
        error_message: str = "DQOps' job has timed out!"  
        super().__init__(error_message)
