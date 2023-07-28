from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import Client
from ...models.mono_object import MonoObject
from ...models.recurring_schedule_spec import RecurringScheduleSpec
from ...models.update_connection_scheduling_group_scheduling_group import (
    UpdateConnectionSchedulingGroupSchedulingGroup,
)
from ...types import Response


def _get_kwargs(
    connection_name: str,
    scheduling_group: UpdateConnectionSchedulingGroupSchedulingGroup,
    *,
    client: Client,
    json_body: RecurringScheduleSpec,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schedules/{schedulingGroup}".format(
        client.base_url,
        connectionName=connection_name,
        schedulingGroup=scheduling_group,
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
    scheduling_group: UpdateConnectionSchedulingGroupSchedulingGroup,
    *,
    client: Client,
    json_body: RecurringScheduleSpec,
) -> Response[MonoObject]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (UpdateConnectionSchedulingGroupSchedulingGroup):
        json_body (RecurringScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        scheduling_group=scheduling_group,
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
    scheduling_group: UpdateConnectionSchedulingGroupSchedulingGroup,
    *,
    client: Client,
    json_body: RecurringScheduleSpec,
) -> Optional[MonoObject]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (UpdateConnectionSchedulingGroupSchedulingGroup):
        json_body (RecurringScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return sync_detailed(
        connection_name=connection_name,
        scheduling_group=scheduling_group,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    scheduling_group: UpdateConnectionSchedulingGroupSchedulingGroup,
    *,
    client: Client,
    json_body: RecurringScheduleSpec,
) -> Response[MonoObject]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (UpdateConnectionSchedulingGroupSchedulingGroup):
        json_body (RecurringScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        scheduling_group=scheduling_group,
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    scheduling_group: UpdateConnectionSchedulingGroupSchedulingGroup,
    *,
    client: Client,
    json_body: RecurringScheduleSpec,
) -> Optional[MonoObject]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (UpdateConnectionSchedulingGroupSchedulingGroup):
        json_body (RecurringScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            scheduling_group=scheduling_group,
            client=client,
            json_body=json_body,
        )
    ).parsed
