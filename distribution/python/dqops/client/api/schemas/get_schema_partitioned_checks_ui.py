from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import Client
from ...models.get_schema_partitioned_checks_ui_check_target import (
    GetSchemaPartitionedChecksUICheckTarget,
)
from ...models.get_schema_partitioned_checks_ui_time_scale import (
    GetSchemaPartitionedChecksUITimeScale,
)
from ...models.ui_all_checks_model import UIAllChecksModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    time_scale: GetSchemaPartitionedChecksUITimeScale,
    *,
    client: Client,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
) -> Dict[str, Any]:
    url = "{}/api/connections/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/ui".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        timeScale=time_scale,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

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

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[UIAllChecksModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = UIAllChecksModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[UIAllChecksModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: GetSchemaPartitionedChecksUITimeScale,
    *,
    client: Client,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
) -> Response[UIAllChecksModel]:
    """getSchemaPartitionedChecksUI

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (GetSchemaPartitionedChecksUITimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[UIAllChecksModel]
    """

    kwargs = _get_kwargs(
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
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    time_scale: GetSchemaPartitionedChecksUITimeScale,
    *,
    client: Client,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
) -> Optional[UIAllChecksModel]:
    """getSchemaPartitionedChecksUI

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (GetSchemaPartitionedChecksUITimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        UIAllChecksModel
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
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: GetSchemaPartitionedChecksUITimeScale,
    *,
    client: Client,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
) -> Response[UIAllChecksModel]:
    """getSchemaPartitionedChecksUI

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (GetSchemaPartitionedChecksUITimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[UIAllChecksModel]
    """

    kwargs = _get_kwargs(
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
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    time_scale: GetSchemaPartitionedChecksUITimeScale,
    *,
    client: Client,
    table_name_pattern: Union[Unset, None, str] = UNSET,
    column_name_pattern: Union[Unset, None, str] = UNSET,
    column_data_type: Union[Unset, None, str] = UNSET,
    check_target: Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    check_enabled: Union[Unset, None, bool] = UNSET,
) -> Optional[UIAllChecksModel]:
    """getSchemaPartitionedChecksUI

     Return a UI friendly model of configurations for data quality partitioned checks on a schema

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (GetSchemaPartitionedChecksUITimeScale):
        table_name_pattern (Union[Unset, None, str]):
        column_name_pattern (Union[Unset, None, str]):
        column_data_type (Union[Unset, None, str]):
        check_target (Union[Unset, None, GetSchemaPartitionedChecksUICheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        check_enabled (Union[Unset, None, bool]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        UIAllChecksModel
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
        )
    ).parsed
