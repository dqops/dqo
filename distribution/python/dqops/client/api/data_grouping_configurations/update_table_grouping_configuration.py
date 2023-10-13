from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.data_grouping_configuration_trimmed_model import (
    DataGroupingConfigurationTrimmedModel,
)
from ...models.mono_void import MonoVoid
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    data_grouping_configuration_name: str,
    *,
    json_body: DataGroupingConfigurationTrimmedModel,
) -> Dict[str, Any]:
    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            dataGroupingConfigurationName=data_grouping_configuration_name,
        ),
        "json": json_json_body,
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
    data_grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DataGroupingConfigurationTrimmedModel,
) -> Response[MonoVoid]:
    """updateTableGroupingConfiguration

     Updates a data grouping configuration according to the provided model

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):
        json_body (DataGroupingConfigurationTrimmedModel): Data grouping configuration model with
            trimmed path

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
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    data_grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DataGroupingConfigurationTrimmedModel,
) -> Optional[MonoVoid]:
    """updateTableGroupingConfiguration

     Updates a data grouping configuration according to the provided model

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):
        json_body (DataGroupingConfigurationTrimmedModel): Data grouping configuration model with
            trimmed path

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
        data_grouping_configuration_name=data_grouping_configuration_name,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    data_grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DataGroupingConfigurationTrimmedModel,
) -> Response[MonoVoid]:
    """updateTableGroupingConfiguration

     Updates a data grouping configuration according to the provided model

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):
        json_body (DataGroupingConfigurationTrimmedModel): Data grouping configuration model with
            trimmed path

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
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    data_grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DataGroupingConfigurationTrimmedModel,
) -> Optional[MonoVoid]:
    """updateTableGroupingConfiguration

     Updates a data grouping configuration according to the provided model

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_grouping_configuration_name (str):
        json_body (DataGroupingConfigurationTrimmedModel): Data grouping configuration model with
            trimmed path

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
            data_grouping_configuration_name=data_grouping_configuration_name,
            client=client,
            json_body=json_body,
        )
    ).parsed
