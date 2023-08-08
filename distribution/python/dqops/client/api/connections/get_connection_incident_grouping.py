from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import Client
from ...models.connection_incident_grouping_spec import ConnectionIncidentGroupingSpec
from ...types import Response


def _get_kwargs(
    connection_name: str,
    *,
    client: Client,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/incidentgrouping".format(
        client.base_url, connectionName=connection_name
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
) -> Optional[ConnectionIncidentGroupingSpec]:
    if response.status_code == HTTPStatus.OK:
        response_200 = ConnectionIncidentGroupingSpec.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[ConnectionIncidentGroupingSpec]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    *,
    client: Client,
) -> Response[ConnectionIncidentGroupingSpec]:
    """getConnectionIncidentGrouping

     Retrieves the configuration of data quality incident grouping and incident notifications

    Args:
        connection_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionIncidentGroupingSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    *,
    client: Client,
) -> Optional[ConnectionIncidentGroupingSpec]:
    """getConnectionIncidentGrouping

     Retrieves the configuration of data quality incident grouping and incident notifications

    Args:
        connection_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionIncidentGroupingSpec
    """

    return sync_detailed(
        connection_name=connection_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    *,
    client: Client,
) -> Response[ConnectionIncidentGroupingSpec]:
    """getConnectionIncidentGrouping

     Retrieves the configuration of data quality incident grouping and incident notifications

    Args:
        connection_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionIncidentGroupingSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    *,
    client: Client,
) -> Optional[ConnectionIncidentGroupingSpec]:
    """getConnectionIncidentGrouping

     Retrieves the configuration of data quality incident grouping and incident notifications

    Args:
        connection_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionIncidentGroupingSpec
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            client=client,
        )
    ).parsed
