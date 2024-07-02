from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_model import CheckContainerModel
from ...models.check_time_scale import CheckTimeScale
from ...models.mono_response_entity_mono_object import MonoResponseEntityMonoObject
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    json_body: CheckContainerModel,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            timeScale=time_scale,
        ),
        "json": json_json_body,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[MonoResponseEntityMonoObject]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoResponseEntityMonoObject.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[MonoResponseEntityMonoObject]:
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
    json_body: CheckContainerModel,
) -> Response[MonoResponseEntityMonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoResponseEntityMonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
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
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Optional[MonoResponseEntityMonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoResponseEntityMonoObject
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
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckContainerModel,
) -> Response[MonoResponseEntityMonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoResponseEntityMonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        json_body=json_body,
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
    json_body: CheckContainerModel,
) -> Optional[MonoResponseEntityMonoObject]:
    """updateTablePartitionedChecksModel

     Updates the data quality partitioned checks from a model that contains a patch with changes.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckContainerModel): Model that returns the form definition and the form data
            to edit all data quality checks divided by categories.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoResponseEntityMonoObject
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
