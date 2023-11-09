from airflow.exceptions import AirflowException


class DqopsEmptyResponseException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that the response from DQO api is empty.

    """

    def __init__(self):
        error_message: str = "DQOps did not return any data!"
        super().__init__(error_message)
