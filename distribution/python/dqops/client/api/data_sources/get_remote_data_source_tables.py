from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.remote_table_list_model import RemoteTableListModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    *,
    table_name_contains: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["tableNameContains"] = table_name_contains

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/datasource/connections/{connectionName}/schemas/{schemaName}/tables".format(
            connectionName=connection_name,
            schemaName=schema_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["RemoteTableListModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = RemoteTableListModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["RemoteTableListModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    *,
    client: AuthenticatedClient,
    table_name_contains: Union[Unset, None, str] = UNSET,
) -> Response[List["RemoteTableListModel"]]:
    """getRemoteDataSourceTables

     Introspects the list of columns inside a schema on a remote data source that is identified by a
    connection that was added to DQOps.

    Args:
        connection_name (str):
        schema_name (str):
        table_name_contains (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['RemoteTableListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name_contains=table_name_contains,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    *,
    client: AuthenticatedClient,
    table_name_contains: Union[Unset, None, str] = UNSET,
) -> Optional[List["RemoteTableListModel"]]:
    """getRemoteDataSourceTables

     Introspects the list of columns inside a schema on a remote data source that is identified by a
    connection that was added to DQOps.

    Args:
        connection_name (str):
        schema_name (str):
        table_name_contains (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['RemoteTableListModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        client=client,
        table_name_contains=table_name_contains,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    *,
    client: AuthenticatedClient,
    table_name_contains: Union[Unset, None, str] = UNSET,
) -> Response[List["RemoteTableListModel"]]:
    """getRemoteDataSourceTables

     Introspects the list of columns inside a schema on a remote data source that is identified by a
    connection that was added to DQOps.

    Args:
        connection_name (str):
        schema_name (str):
        table_name_contains (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['RemoteTableListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name_contains=table_name_contains,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    *,
    client: AuthenticatedClient,
    table_name_contains: Union[Unset, None, str] = UNSET,
) -> Optional[List["RemoteTableListModel"]]:
    """getRemoteDataSourceTables

     Introspects the list of columns inside a schema on a remote data source that is identified by a
    connection that was added to DQOps.

    Args:
        connection_name (str):
        schema_name (str):
        table_name_contains (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['RemoteTableListModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            client=client,
            table_name_contains=table_name_contains,
        )
    ).parsed
