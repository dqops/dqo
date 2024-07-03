from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.data_grouping_configuration_model import DataGroupingConfigurationModel
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    grouping_configuration_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            groupingConfigurationName=grouping_configuration_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[DataGroupingConfigurationModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DataGroupingConfigurationModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[DataGroupingConfigurationModel]:
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
    grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[DataGroupingConfigurationModel]:
    """getTableGroupingConfiguration

     Returns a model of the data grouping configuration

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DataGroupingConfigurationModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        grouping_configuration_name=grouping_configuration_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DataGroupingConfigurationModel]:
    """getTableGroupingConfiguration

     Returns a model of the data grouping configuration

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DataGroupingConfigurationModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        grouping_configuration_name=grouping_configuration_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[DataGroupingConfigurationModel]:
    """getTableGroupingConfiguration

     Returns a model of the data grouping configuration

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DataGroupingConfigurationModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        grouping_configuration_name=grouping_configuration_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    grouping_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DataGroupingConfigurationModel]:
    """getTableGroupingConfiguration

     Returns a model of the data grouping configuration

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        grouping_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DataGroupingConfigurationModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            grouping_configuration_name=grouping_configuration_name,
            client=client,
        )
    ).parsed
