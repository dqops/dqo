from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.column_daily_partitioned_check_categories_spec import (
    ColumnDailyPartitionedCheckCategoriesSpec,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    json_body: ColumnDailyPartitionedCheckCategoriesSpec,
) -> Dict[str, Any]:
    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            columnName=column_name,
        ),
        "json": json_json_body,
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
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    client: AuthenticatedClient,
    json_body: ColumnDailyPartitionedCheckCategoriesSpec,
) -> Response[Any]:
    """updateColumnPartitionedChecksDaily

     Updates configuration of daily column level data quality partitioned checks on a column.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        json_body (ColumnDailyPartitionedCheckCategoriesSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    *,
    client: AuthenticatedClient,
    json_body: ColumnDailyPartitionedCheckCategoriesSpec,
) -> Response[Any]:
    """updateColumnPartitionedChecksDaily

     Updates configuration of daily column level data quality partitioned checks on a column.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        json_body (ColumnDailyPartitionedCheckCategoriesSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
