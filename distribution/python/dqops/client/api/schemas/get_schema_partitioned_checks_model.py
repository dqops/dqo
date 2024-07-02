from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_configuration_model import CheckConfigurationModel
from ...models.check_target import CheckTarget
from ...models.check_time_scale import CheckTimeScale
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
    check_configured: Union[Unset, None, bool] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["tableNamePattern"] = table_name_pattern

    params["columnNamePattern"] = column_name_pattern

    params["columnDataType"] = column_data_type

    json_check_target: Union[Unset, None, str] = UNSET
    if not isinstance(check_target, Unset):
        json_check_target = check_target.value if check_target else None

    params["checkTarget"] = json_check_target

    params["checkCategory"] = check_category

    params["checkName"] = check_name

    params["checkEnabled"] = check_enabled

    params["checkConfigured"] = check_configured

    params["limit"] = limit

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/model".format(
            connectionName=connection_name,
            schemaName=schema_name,
            timeScale=time_scale,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["CheckConfigurationModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = CheckConfigurationModel.from_dict(
                response_200_item_data
            )

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["CheckConfigurationModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
    check_configured: Union[Unset, None, bool] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckConfigurationModel"]]:
    """getSchemaPartitionedChecksModel

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):
        check_configured (Union[Unset, None, bool]):
        limit (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckConfigurationModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        table_name_pattern=table_name_pattern,
        column_name_pattern=column_name_pattern,
        column_data_type=column_data_type,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
        check_enabled=check_enabled,
        check_configured=check_configured,
        limit=limit,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
    check_configured: Union[Unset, None, bool] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckConfigurationModel"]]:
    """getSchemaPartitionedChecksModel

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):
        check_configured (Union[Unset, None, bool]):
        limit (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckConfigurationModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        client=client,
        table_name_pattern=table_name_pattern,
        column_name_pattern=column_name_pattern,
        column_data_type=column_data_type,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
        check_enabled=check_enabled,
        check_configured=check_configured,
        limit=limit,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
    check_configured: Union[Unset, None, bool] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckConfigurationModel"]]:
    """getSchemaPartitionedChecksModel

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):
        check_configured (Union[Unset, None, bool]):
        limit (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckConfigurationModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        table_name_pattern=table_name_pattern,
        column_name_pattern=column_name_pattern,
        column_data_type=column_data_type,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
        check_enabled=check_enabled,
        check_configured=check_configured,
        limit=limit,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
    check_configured: Union[Unset, None, bool] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckConfigurationModel"]]:
    """getSchemaPartitionedChecksModel

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):
        check_configured (Union[Unset, None, bool]):
        limit (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckConfigurationModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            time_scale=time_scale,
            client=client,
            table_name_pattern=table_name_pattern,
            column_name_pattern=column_name_pattern,
            column_data_type=column_data_type,
            check_target=check_target,
            check_category=check_category,
            check_name=check_name,
            check_enabled=check_enabled,
            check_configured=check_configured,
            limit=limit,
        )
    ).parsed
