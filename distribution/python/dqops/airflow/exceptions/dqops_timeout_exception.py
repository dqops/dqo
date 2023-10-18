from airflow.exceptions import AirflowException


class DqopsTimeoutException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that the response from DQO api has timed out.

    """

    def __init__(self):
        error_message: str = "DQOps' job has timed out!"
        super().__init__(error_message)
