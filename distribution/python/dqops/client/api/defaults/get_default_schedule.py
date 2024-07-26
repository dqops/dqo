from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_run_schedule_group import CheckRunScheduleGroup
from ...models.monitoring_schedule_spec import MonitoringScheduleSpec
from ...types import Response


def _get_kwargs(
    scheduling_group: CheckRunScheduleGroup,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/defaults/defaultschedule/{schedulingGroup}".format(
            schedulingGroup=scheduling_group,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[MonitoringScheduleSpec]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonitoringScheduleSpec.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[MonitoringScheduleSpec]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Response[MonitoringScheduleSpec]:
    """getDefaultSchedules

     Returns spec to show and edit the default configuration of schedules.

    Args:
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonitoringScheduleSpec]
    """

    kwargs = _get_kwargs(
        scheduling_group=scheduling_group,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Optional[MonitoringScheduleSpec]:
    """getDefaultSchedules

     Returns spec to show and edit the default configuration of schedules.

    Args:
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonitoringScheduleSpec
    """

    return sync_detailed(
        scheduling_group=scheduling_group,
        client=client,
    ).parsed


async def asyncio_detailed(
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Response[MonitoringScheduleSpec]:
    """getDefaultSchedules

     Returns spec to show and edit the default configuration of schedules.

    Args:
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonitoringScheduleSpec]
    """

    kwargs = _get_kwargs(
        scheduling_group=scheduling_group,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
) -> Optional[MonitoringScheduleSpec]:
    """getDefaultSchedules

     Returns spec to show and edit the default configuration of schedules.

    Args:
        scheduling_group (CheckRunScheduleGroup):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonitoringScheduleSpec
    """

    return (
        await asyncio_detailed(
            scheduling_group=scheduling_group,
            client=client,
        )
    ).parsed
