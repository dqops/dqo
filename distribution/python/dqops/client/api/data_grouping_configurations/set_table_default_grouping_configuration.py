from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...types import UNSET, Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    data_grouping_configuration_name: str,
) -> Dict[str, Any]:
    pass

    params: Dict[str, Any] = {}
    params["dataGroupingConfigurationName"] = data_grouping_configuration_name

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "patch",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[MonoVoid]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoVoid.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[MonoVoid]:
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
    data_grouping_configuration_name: str,
) -> Response[MonoVoid]:
    """setTableDefaultGroupingConfiguration

     Sets a table's grouping configuration as the default or disables data grouping

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        data_grouping_configuration_name=data_grouping_configuration_name,
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
    data_grouping_configuration_name: str,
) -> Optional[MonoVoid]:
    """setTableDefaultGroupingConfiguration

     Sets a table's grouping configuration as the default or disables data grouping

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        data_grouping_configuration_name=data_grouping_configuration_name,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_grouping_configuration_name: str,
) -> Response[MonoVoid]:
    """setTableDefaultGroupingConfiguration

     Sets a table's grouping configuration as the default or disables data grouping

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        data_grouping_configuration_name=data_grouping_configuration_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_grouping_configuration_name: str,
) -> Optional[MonoVoid]:
    """setTableDefaultGroupingConfiguration

     Sets a table's grouping configuration as the default or disables data grouping

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            data_grouping_configuration_name=data_grouping_configuration_name,
        )
    ).parsed
