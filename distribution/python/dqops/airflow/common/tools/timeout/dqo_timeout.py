import logging

from airflow.exceptions import AirflowException


def handle_dqo_timeout(fail_on_timeout: bool):
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
        raise AirflowException(timeout_message)
    else:
        logging.warn(timeout_message)
