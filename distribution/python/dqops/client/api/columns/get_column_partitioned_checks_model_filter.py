from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_model import CheckContainerModel
from ...models.check_time_scale import CheckTimeScale
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
    check_category: str,
    check_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            columnName=column_name,
            timeScale=time_scale,
            checkCategory=check_category,
            checkName=check_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CheckContainerModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckContainerModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
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
    time_scale: CheckTimeScale,
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
        time_scale (CheckTimeScale):
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
    time_scale: CheckTimeScale,
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
        time_scale (CheckTimeScale):
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
    time_scale: CheckTimeScale,
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
        time_scale (CheckTimeScale):
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
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
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
        time_scale (CheckTimeScale):
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
