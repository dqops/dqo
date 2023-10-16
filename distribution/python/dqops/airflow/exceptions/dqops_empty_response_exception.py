from airflow.exceptions import AirflowException

class DqopsEmptyResponseException(AirflowException):
    def __init__(self):         
        error_message: str = "DQOps did not return any data!"  
        super().__init__(error_message)
