from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.incident_status import IncidentStatus
from ...types import UNSET, Response


def _get_kwargs(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    status: IncidentStatus,
) -> Dict[str, Any]:
    pass

    params: Dict[str, Any] = {}
    json_status = status.value

    params["status"] = json_status

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "post",
        "url": "api/incidents/{connectionName}/{year}/{month}/{incidentId}/status".format(
            connectionName=connection_name,
            year=year,
            month=month,
            incidentId=incident_id,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.NO_CONTENT:
        return None
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Any]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: AuthenticatedClient,
    status: IncidentStatus,
) -> Response[Any]:
    """setIncidentStatus

     Changes the incident's status to a new status.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        status (IncidentStatus):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        status=status,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: AuthenticatedClient,
    status: IncidentStatus,
) -> Response[Any]:
    """setIncidentStatus

     Changes the incident's status to a new status.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        status (IncidentStatus):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        status=status,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
