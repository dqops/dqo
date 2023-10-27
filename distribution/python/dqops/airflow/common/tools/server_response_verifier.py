import json
import logging
from http import HTTPStatus

from dqops.airflow.common.exceptions.dqops_empty_response_exception import (
    DqopsEmptyResponseException,
)
from dqops.airflow.common.exceptions.dqops_internal_server_error_exception import (
    DqopsInternalServerErrorException,
)
from dqops.client.types import Response


def verify_server_response_correctness(response: Response):
    if response.status_code == HTTPStatus.INTERNAL_SERVER_ERROR:
        logging.error(json.loads(response.content.decode("utf-8")))
        raise DqopsInternalServerErrorException()

    # When timeout is too short, returned object is empty
    if response.content.decode("utf-8") == "":
        raise DqopsEmptyResponseException()
