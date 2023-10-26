from airflow.exceptions import AirflowException


class DqopsJobFailedException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that a DQOps' job has failed.

    """

    def __init__(self):
        error_message: str = "DQOps job has failed!"
        super().__init__(error_message)
