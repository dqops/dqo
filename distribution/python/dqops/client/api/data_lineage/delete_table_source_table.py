from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    source_connection: str,
    source_schema: str,
    source_table: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "delete",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/from/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            sourceConnection=source_connection,
            sourceSchema=source_schema,
            sourceTable=source_table,
        ),
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
    source_connection: str,
    source_schema: str,
    source_table: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """deleteTableSourceTable

     Deletes a specific data lineage source table of the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        source_connection (str):
        source_schema (str):
        source_table (str):

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
        source_connection=source_connection,
        source_schema=source_schema,
        source_table=source_table,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    source_connection: str,
    source_schema: str,
    source_table: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """deleteTableSourceTable

     Deletes a specific data lineage source table of the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        source_connection (str):
        source_schema (str):
        source_table (str):

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
        source_connection=source_connection,
        source_schema=source_schema,
        source_table=source_table,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)