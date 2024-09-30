from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_run_schedule_group import CheckRunScheduleGroup
from ...models.cron_schedule_spec import CronScheduleSpec
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    scheduling_group: CheckRunScheduleGroup,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            schedulingGroup=scheduling_group,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CronScheduleSpec]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CronScheduleSpec.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[CronScheduleSpec]:
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
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Response[CronScheduleSpec]:
    """getTableSchedulingGroupOverride

     Return the schedule override configuration for a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CronScheduleSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        scheduling_group=scheduling_group,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Optional[CronScheduleSpec]:
    """getTableSchedulingGroupOverride

     Return the schedule override configuration for a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CronScheduleSpec
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        scheduling_group=scheduling_group,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Response[CronScheduleSpec]:
    """getTableSchedulingGroupOverride

     Return the schedule override configuration for a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CronScheduleSpec]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        scheduling_group=scheduling_group,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Optional[CronScheduleSpec]:
    """getTableSchedulingGroupOverride

     Return the schedule override configuration for a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CronScheduleSpec
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            scheduling_group=scheduling_group,
            client=client,
        )
    ).parsed
