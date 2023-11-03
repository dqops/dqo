from typing import Union

from httpx import Timeout

from dqops.client import Client
from dqops.client.types import UNSET, Unset

# extra time for python client to wait for dqo after it times out
EXTRA_TIMEOUT_SECONDS: int = 1


def create_client(base_url: str, *, wait_timeout: Union[int, Unset] = Unset) -> Client:
    """Creates python client for airflow operators.

    Parameters
    ----------
    base_url : str
        The base url to DQOps application. Default value is http://localhost:8888/
    wait_timeout : int
        Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.

    Returns
        DQOps client object.
    """
    client: Client = Client(base_url=base_url)
    if wait_timeout is not UNSET:
        client.with_timeout(Timeout(wait_timeout + EXTRA_TIMEOUT_SECONDS))

    return client
