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
    scheduling_group: CheckRunScheduleGroup,
    *,
    json_body: CronScheduleSpec,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/connections/{connectionName}/schedules/{schedulingGroup}".format(
            connectionName=connection_name,
            schedulingGroup=scheduling_group,
        ),
        "json": json_json_body,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.NO_CONTENT:
        return None
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Any]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
    json_body: CronScheduleSpec,
) -> Response[Any]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (CheckRunScheduleGroup):
        json_body (CronScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        scheduling_group=scheduling_group,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    scheduling_group: CheckRunScheduleGroup,
    *,
    client: AuthenticatedClient,
    json_body: CronScheduleSpec,
) -> Response[Any]:
    """updateConnectionSchedulingGroup

     Updates the schedule of a connection for a scheduling group (named schedule for checks with a
    similar time series configuration)

    Args:
        connection_name (str):
        scheduling_group (CheckRunScheduleGroup):
        json_body (CronScheduleSpec):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        scheduling_group=scheduling_group,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
