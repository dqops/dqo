from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.table_lineage_model import TableLineageModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    upstream: Union[Unset, None, bool] = UNSET,
    downstream: Union[Unset, None, bool] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["upstream"] = upstream

    params["downstream"] = downstream

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/tree".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[TableLineageModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TableLineageModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[TableLineageModel]:
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
    *,
    client: AuthenticatedClient,
    upstream: Union[Unset, None, bool] = UNSET,
    downstream: Union[Unset, None, bool] = UNSET,
) -> Response[TableLineageModel]:
    """getTableDataLineageGraph

     Returns a data lineage graph around the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        upstream (Union[Unset, None, bool]):
        downstream (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableLineageModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        upstream=upstream,
        downstream=downstream,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    upstream: Union[Unset, None, bool] = UNSET,
    downstream: Union[Unset, None, bool] = UNSET,
) -> Optional[TableLineageModel]:
    """getTableDataLineageGraph

     Returns a data lineage graph around the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        upstream (Union[Unset, None, bool]):
        downstream (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableLineageModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        upstream=upstream,
        downstream=downstream,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    upstream: Union[Unset, None, bool] = UNSET,
    downstream: Union[Unset, None, bool] = UNSET,
) -> Response[TableLineageModel]:
    """getTableDataLineageGraph

     Returns a data lineage graph around the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        upstream (Union[Unset, None, bool]):
        downstream (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableLineageModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        upstream=upstream,
        downstream=downstream,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    upstream: Union[Unset, None, bool] = UNSET,
    downstream: Union[Unset, None, bool] = UNSET,
) -> Optional[TableLineageModel]:
    """getTableDataLineageGraph

     Returns a data lineage graph around the given table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        upstream (Union[Unset, None, bool]):
        downstream (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableLineageModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            upstream=upstream,
            downstream=downstream,
        )
    ).parsed
