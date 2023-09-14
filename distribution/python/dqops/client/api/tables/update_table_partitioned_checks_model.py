from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_model import CheckContainerModel
from ...models.mono_object import MonoObject
from ...models.update_table_partitioned_checks_model_time_scale import (
    UpdateTablePartitionedChecksModelTimeScale,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: UpdateTablePartitionedChecksModelTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        tableName=table_name,
        timeScale=time_scale,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[MonoObject]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoObject.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[MonoObject]:
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
    time_scale: UpdateTablePartitionedChecksModelTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Response[MonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (UpdateTablePartitionedChecksModelTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
        json_body=json_body,
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
    time_scale: UpdateTablePartitionedChecksModelTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Optional[MonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (UpdateTablePartitionedChecksModelTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: UpdateTablePartitionedChecksModelTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Response[MonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (UpdateTablePartitionedChecksModelTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: UpdateTablePartitionedChecksModelTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Optional[MonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (UpdateTablePartitionedChecksModelTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            time_scale=time_scale,
            client=client,
            json_body=json_body,
        )
    ).parsed
