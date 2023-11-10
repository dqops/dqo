from airflow.exceptions import AirflowException
from airflow.models.taskinstance import TaskInstance

class DqopsDataQualityIssueDetectedException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that DQO has detected a data quality issue.

    """

    def __init__(self, ti: TaskInstance, return_value: dict):
        error_message: str = "DQOps has detected a data quality issue!"
        super().__init__(error_message)
        ti.xcom_push("return_value", return_value)
