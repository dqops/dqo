from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_model import CheckContainerModel
from ...models.get_column_partitioned_checks_model_filter_time_scale import (
    GetColumnPartitionedChecksModelFilterTimeScale,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: GetColumnPartitionedChecksModelFilterTimeScale,
    check_category: str,
    check_name: str,
    *,
    client: AuthenticatedClient,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        tableName=table_name,
        columnName=column_name,
        timeScale=time_scale,
        checkCategory=check_category,
        checkName=check_name,
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
) -> Optional[CheckContainerModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckContainerModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[CheckContainerModel]:
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
    column_name: str,
    time_scale: GetColumnPartitionedChecksModelFilterTimeScale,
    check_category: str,
    check_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerModel]:
    """getColumnPartitionedChecksModelFilter

     Return a UI friendly model of column level data quality partitioned checks on a column, filtered by
    category and check name

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (GetColumnPartitionedChecksModelFilterTimeScale):
        check_category (str):
        check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        check_category=check_category,
        check_name=check_name,
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
    column_name: str,
    time_scale: GetColumnPartitionedChecksModelFilterTimeScale,
    check_category: str,
    check_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerModel]:
    """getColumnPartitionedChecksModelFilter

     Return a UI friendly model of column level data quality partitioned checks on a column, filtered by
    category and check name

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (GetColumnPartitionedChecksModelFilterTimeScale):
        check_category (str):
        check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        check_category=check_category,
        check_name=check_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: GetColumnPartitionedChecksModelFilterTimeScale,
    check_category: str,
    check_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerModel]:
    """getColumnPartitionedChecksModelFilter

     Return a UI friendly model of column level data quality partitioned checks on a column, filtered by
    category and check name

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (GetColumnPartitionedChecksModelFilterTimeScale):
        check_category (str):
        check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        check_category=check_category,
        check_name=check_name,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: GetColumnPartitionedChecksModelFilterTimeScale,
    check_category: str,
    check_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerModel]:
    """getColumnPartitionedChecksModelFilter

     Return a UI friendly model of column level data quality partitioned checks on a column, filtered by
    category and check name

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (GetColumnPartitionedChecksModelFilterTimeScale):
        check_category (str):
        check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            column_name=column_name,
            time_scale=time_scale,
            check_category=check_category,
            check_name=check_name,
            client=client,
        )
    ).parsed
