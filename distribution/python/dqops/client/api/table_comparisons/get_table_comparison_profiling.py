from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.table_comparison_model import TableComparisonModel
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    table_comparison_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/profiling".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        tableName=table_name,
        tableComparisonConfigurationName=table_comparison_configuration_name,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[TableComparisonModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TableComparisonModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[TableComparisonModel]:
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
    table_comparison_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[TableComparisonModel]:
    """getTableComparisonProfiling

     Returns a model of the table comparison using profiling checks (comparison at any time)

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        table_comparison_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableComparisonModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        table_comparison_configuration_name=table_comparison_configuration_name,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    table_comparison_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[TableComparisonModel]:
    """getTableComparisonProfiling

     Returns a model of the table comparison using profiling checks (comparison at any time)

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        table_comparison_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableComparisonModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        table_comparison_configuration_name=table_comparison_configuration_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    table_comparison_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[TableComparisonModel]:
    """getTableComparisonProfiling

     Returns a model of the table comparison using profiling checks (comparison at any time)

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        table_comparison_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableComparisonModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        table_comparison_configuration_name=table_comparison_configuration_name,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    table_comparison_configuration_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[TableComparisonModel]:
    """getTableComparisonProfiling

     Returns a model of the table comparison using profiling checks (comparison at any time)

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        table_comparison_configuration_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableComparisonModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            table_comparison_configuration_name=table_comparison_configuration_name,
            client=client,
        )
    ).parsed
