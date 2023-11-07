from airflow.exceptions import AirflowException


class DqopsUnfinishedJobException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that wait for job action has not completed yet.

    """

    def __init__(self):
        error_message: str = "Job has not completed yet."
        super().__init__(error_message)
