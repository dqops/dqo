from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_list_model import CheckContainerListModel
from ...models.check_time_scale import CheckTimeScale
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/basic".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            timeScale=time_scale,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CheckContainerListModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckContainerListModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[CheckContainerListModel]:
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
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerListModel]:
    """getTablePartitionedChecksBasicModel

     Return a simplistic UI friendly model of table level data quality partitioned checks on a table for
    a given time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerListModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerListModel]:
    """getTablePartitionedChecksBasicModel

     Return a simplistic UI friendly model of table level data quality partitioned checks on a table for
    a given time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerListModel
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
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerListModel]:
    """getTablePartitionedChecksBasicModel

     Return a simplistic UI friendly model of table level data quality partitioned checks on a table for
    a given time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerListModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerListModel]:
    """getTablePartitionedChecksBasicModel

     Return a simplistic UI friendly model of table level data quality partitioned checks on a table for
    a given time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerListModel
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
