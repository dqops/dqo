from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import Client
from ...models.incident_model import IncidentModel
from ...types import Response


def _get_kwargs(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
) -> Dict[str, Any]:
    url = "{}api/incidents/{connectionName}/{year}/{month}/{incidentId}".format(
        client.base_url,
        connectionName=connection_name,
        year=year,
        month=month,
        incidentId=incident_id,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[IncidentModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = IncidentModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[IncidentModel]:
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
    client: Client,
) -> Response[IncidentModel]:
    """getIncident

     Return a single data quality incident's details.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IncidentModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
) -> Optional[IncidentModel]:
    """getIncident

     Return a single data quality incident's details.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IncidentModel
    """

    return sync_detailed(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
) -> Response[IncidentModel]:
    """getIncident

     Return a single data quality incident's details.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IncidentModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
) -> Optional[IncidentModel]:
    """getIncident

     Return a single data quality incident's details.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IncidentModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            year=year,
            month=month,
            incident_id=incident_id,
            client=client,
        )
    ).parsed
