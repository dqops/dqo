from airflow.exceptions import AirflowException
from airflow.models.taskinstance import TaskInstance


class DqopsJobFailedException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that a DQOps' job has failed.

    """

    def __init__(self, ti: TaskInstance, return_value: dict):
        error_message: str = "DQOps job has failed!"
        super().__init__(error_message)
        ti.xcom_push("return_value", return_value)
