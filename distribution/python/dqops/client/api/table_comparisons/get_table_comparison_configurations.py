from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_time_scale import CheckTimeScale
from ...models.check_type import CheckType
from ...models.table_comparison_configuration_model import (
    TableComparisonConfigurationModel,
)
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    json_check_type: Union[Unset, None, str] = UNSET
    if not isinstance(check_type, Unset):
        json_check_type = check_type.value if check_type else None

    params["checkType"] = json_check_type

    json_check_time_scale: Union[Unset, None, str] = UNSET
    if not isinstance(check_time_scale, Unset):
        json_check_time_scale = check_time_scale.value if check_time_scale else None

    params["checkTimeScale"] = json_check_time_scale

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["TableComparisonConfigurationModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = TableComparisonConfigurationModel.from_dict(
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
) -> Response[List["TableComparisonConfigurationModel"]]:
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
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Response[List["TableComparisonConfigurationModel"]]:
    """getTableComparisonConfigurations

     Returns the list of table comparison configurations on a compared table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['TableComparisonConfigurationModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        check_type=check_type,
        check_time_scale=check_time_scale,
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
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Optional[List["TableComparisonConfigurationModel"]]:
    """getTableComparisonConfigurations

     Returns the list of table comparison configurations on a compared table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['TableComparisonConfigurationModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        check_type=check_type,
        check_time_scale=check_time_scale,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Response[List["TableComparisonConfigurationModel"]]:
    """getTableComparisonConfigurations

     Returns the list of table comparison configurations on a compared table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['TableComparisonConfigurationModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        check_type=check_type,
        check_time_scale=check_time_scale,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Optional[List["TableComparisonConfigurationModel"]]:
    """getTableComparisonConfigurations

     Returns the list of table comparison configurations on a compared table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['TableComparisonConfigurationModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            check_type=check_type,
            check_time_scale=check_time_scale,
        )
    ).parsed
