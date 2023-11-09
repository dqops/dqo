import logging

from airflow.exceptions import AirflowException
from airflow.models.taskinstance import TaskInstance


def handle_dqo_timeout(fail_on_timeout: bool, ti: TaskInstance, return_value: dict):
    """
    Method handles timeout from dqo api.

    Parameters
    ----------
    fail_on_timeout: bool
        When set to True it causes that airflow task is marked as Failed by throwing an AirflowException.
    """

    timeout_message: str = "DQOps' job has timed out!"

    if fail_on_timeout:
        logging.error(timeout_message)
        ti.xcom_push("return_value", return_value)
        raise AirflowException(timeout_message)
    else:
        logging.warn(timeout_message)
