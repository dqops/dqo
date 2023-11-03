from airflow.exceptions import AirflowException


class DqopsInternalServerErrorException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception is thrown on internal server error from DQOps server API.

    """

    def __init__(self):
        error_message: str = "DQOps server responded with Internal Server Error!"
        super().__init__(error_message)
