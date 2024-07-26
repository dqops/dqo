from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.column_daily_monitoring_check_categories_spec import (
    ColumnDailyMonitoringCheckCategoriesSpec,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            columnName=column_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[ColumnDailyMonitoringCheckCategoriesSpec]:
    if response.status_code == HTTPStatus.OK:
        response_200 = ColumnDailyMonitoringCheckCategoriesSpec.from_dict(
            response.json()
        )

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[ColumnDailyMonitoringCheckCategoriesSpec]:
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
    *,
    client: AuthenticatedClient,
) -> Response[ColumnDailyMonitoringCheckCategoriesSpec]:
    """getColumnMonitoringChecksDaily

     Return the configuration of daily column level data quality monitoring on a column

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ColumnDailyMonitoringCheckCategoriesSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[ColumnDailyMonitoringCheckCategoriesSpec]:
    """getColumnMonitoringChecksDaily

     Return the configuration of daily column level data quality monitoring on a column

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ColumnDailyMonitoringCheckCategoriesSpec
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[ColumnDailyMonitoringCheckCategoriesSpec]:
    """getColumnMonitoringChecksDaily

     Return the configuration of daily column level data quality monitoring on a column

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ColumnDailyMonitoringCheckCategoriesSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[ColumnDailyMonitoringCheckCategoriesSpec]:
    """getColumnMonitoringChecksDaily

     Return the configuration of daily column level data quality monitoring on a column

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ColumnDailyMonitoringCheckCategoriesSpec
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            column_name=column_name,
            client=client,
        )
    ).parsed
