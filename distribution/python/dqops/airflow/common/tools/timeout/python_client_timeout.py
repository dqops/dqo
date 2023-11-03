import logging

from httpx import ReadTimeout


def handle_python_timeout(exception: ReadTimeout, fail_on_timeout: bool):
    """
    Method handles timeout from python client.

    Parameters
    ----------
    exception : ReadTimeout
        The ReadTimeout exception from httpx library.
    fail_on_timeout : bool
        When set to True it causes that airflow task is marked as Failed by throwing an AirflowException.
    """

    timeout_message: str = "Python client has timed out!"
    if fail_on_timeout:
        logging.error(timeout_message)
        raise exception
    else:
        logging.info(timeout_message)
