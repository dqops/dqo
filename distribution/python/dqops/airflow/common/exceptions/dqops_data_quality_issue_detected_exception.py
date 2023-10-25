from airflow.exceptions import AirflowException


class DqopsDataQualityIssueDetectedException(AirflowException):
    """
    Exception used in airflow to mark status of task execution as Failed.
    The exception informs that DQO has detected a data quality issue.

    """

    def __init__(self):
        error_message: str = "DQOps has detected a data quality issue!"
        super().__init__(error_message)
