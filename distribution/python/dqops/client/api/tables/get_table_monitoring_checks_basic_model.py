from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_basic_model import CheckContainerBasicModel
from ...models.get_table_monitoring_checks_basic_model_time_scale import (
    GetTableMonitoringChecksBasicModelTimeScale,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: GetTableMonitoringChecksBasicModelTimeScale,
    *,
    client: AuthenticatedClient,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/basic".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        tableName=table_name,
        timeScale=time_scale,
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
) -> Optional[CheckContainerBasicModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckContainerBasicModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[CheckContainerBasicModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: GetTableMonitoringChecksBasicModelTimeScale,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerBasicModel]:
    """getTableMonitoringChecksBasicModel

     Return a simplistic UI friendly model of table level data quality monitoring on a table for a given
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (GetTableMonitoringChecksBasicModelTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerBasicModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: GetTableMonitoringChecksBasicModelTimeScale,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerBasicModel]:
    """getTableMonitoringChecksBasicModel

     Return a simplistic UI friendly model of table level data quality monitoring on a table for a given
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (GetTableMonitoringChecksBasicModelTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerBasicModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: GetTableMonitoringChecksBasicModelTimeScale,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerBasicModel]:
    """getTableMonitoringChecksBasicModel

     Return a simplistic UI friendly model of table level data quality monitoring on a table for a given
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (GetTableMonitoringChecksBasicModelTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerBasicModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: GetTableMonitoringChecksBasicModelTimeScale,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerBasicModel]:
    """getTableMonitoringChecksBasicModel

     Return a simplistic UI friendly model of table level data quality monitoring on a table for a given
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (GetTableMonitoringChecksBasicModelTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerBasicModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            time_scale=time_scale,
            client=client,
        )
    ).parsed
