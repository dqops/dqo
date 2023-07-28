from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import Client
from ...models.find_recent_incidents_on_connection_direction import (
    FindRecentIncidentsOnConnectionDirection,
)
from ...models.find_recent_incidents_on_connection_order import (
    FindRecentIncidentsOnConnectionOrder,
)
from ...models.incident_model import IncidentModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    *,
    client: Client,
    months: Union[Unset, None, int] = UNSET,
    open_: Union[Unset, None, bool] = UNSET,
    acknowledged: Union[Unset, None, bool] = UNSET,
    resolved: Union[Unset, None, bool] = UNSET,
    muted: Union[Unset, None, bool] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, FindRecentIncidentsOnConnectionOrder] = UNSET,
    direction: Union[Unset, None, FindRecentIncidentsOnConnectionDirection] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/incidents/{connectionName}".format(
        client.base_url, connectionName=connection_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["months"] = months

    params["open"] = open_

    params["acknowledged"] = acknowledged

    params["resolved"] = resolved

    params["muted"] = muted

    params["page"] = page

    params["limit"] = limit

    params["filter"] = filter_

    json_order: Union[Unset, None, str] = UNSET
    if not isinstance(order, Unset):
        json_order = order.value if order else None

    params["order"] = json_order

    json_direction: Union[Unset, None, str] = UNSET
    if not isinstance(direction, Unset):
        json_direction = direction.value if direction else None

    params["direction"] = json_direction

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[List["IncidentModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = IncidentModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[List["IncidentModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    *,
    client: Client,
    months: Union[Unset, None, int] = UNSET,
    open_: Union[Unset, None, bool] = UNSET,
    acknowledged: Union[Unset, None, bool] = UNSET,
    resolved: Union[Unset, None, bool] = UNSET,
    muted: Union[Unset, None, bool] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, FindRecentIncidentsOnConnectionOrder] = UNSET,
    direction: Union[Unset, None, FindRecentIncidentsOnConnectionDirection] = UNSET,
) -> Response[List["IncidentModel"]]:
    """findRecentIncidentsOnConnection

     Returns a list of recent data quality incidents.

    Args:
        connection_name (str):
        months (Union[Unset, None, int]):
        open_ (Union[Unset, None, bool]):
        acknowledged (Union[Unset, None, bool]):
        resolved (Union[Unset, None, bool]):
        muted (Union[Unset, None, bool]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        order (Union[Unset, None, FindRecentIncidentsOnConnectionOrder]):
        direction (Union[Unset, None, FindRecentIncidentsOnConnectionDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['IncidentModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
        months=months,
        open_=open_,
        acknowledged=acknowledged,
        resolved=resolved,
        muted=muted,
        page=page,
        limit=limit,
        filter_=filter_,
        order=order,
        direction=direction,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    *,
    client: Client,
    months: Union[Unset, None, int] = UNSET,
    open_: Union[Unset, None, bool] = UNSET,
    acknowledged: Union[Unset, None, bool] = UNSET,
    resolved: Union[Unset, None, bool] = UNSET,
    muted: Union[Unset, None, bool] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, FindRecentIncidentsOnConnectionOrder] = UNSET,
    direction: Union[Unset, None, FindRecentIncidentsOnConnectionDirection] = UNSET,
) -> Optional[List["IncidentModel"]]:
    """findRecentIncidentsOnConnection

     Returns a list of recent data quality incidents.

    Args:
        connection_name (str):
        months (Union[Unset, None, int]):
        open_ (Union[Unset, None, bool]):
        acknowledged (Union[Unset, None, bool]):
        resolved (Union[Unset, None, bool]):
        muted (Union[Unset, None, bool]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        order (Union[Unset, None, FindRecentIncidentsOnConnectionOrder]):
        direction (Union[Unset, None, FindRecentIncidentsOnConnectionDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['IncidentModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        client=client,
        months=months,
        open_=open_,
        acknowledged=acknowledged,
        resolved=resolved,
        muted=muted,
        page=page,
        limit=limit,
        filter_=filter_,
        order=order,
        direction=direction,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    *,
    client: Client,
    months: Union[Unset, None, int] = UNSET,
    open_: Union[Unset, None, bool] = UNSET,
    acknowledged: Union[Unset, None, bool] = UNSET,
    resolved: Union[Unset, None, bool] = UNSET,
    muted: Union[Unset, None, bool] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, FindRecentIncidentsOnConnectionOrder] = UNSET,
    direction: Union[Unset, None, FindRecentIncidentsOnConnectionDirection] = UNSET,
) -> Response[List["IncidentModel"]]:
    """findRecentIncidentsOnConnection

     Returns a list of recent data quality incidents.

    Args:
        connection_name (str):
        months (Union[Unset, None, int]):
        open_ (Union[Unset, None, bool]):
        acknowledged (Union[Unset, None, bool]):
        resolved (Union[Unset, None, bool]):
        muted (Union[Unset, None, bool]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        order (Union[Unset, None, FindRecentIncidentsOnConnectionOrder]):
        direction (Union[Unset, None, FindRecentIncidentsOnConnectionDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['IncidentModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
        months=months,
        open_=open_,
        acknowledged=acknowledged,
        resolved=resolved,
        muted=muted,
        page=page,
        limit=limit,
        filter_=filter_,
        order=order,
        direction=direction,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    *,
    client: Client,
    months: Union[Unset, None, int] = UNSET,
    open_: Union[Unset, None, bool] = UNSET,
    acknowledged: Union[Unset, None, bool] = UNSET,
    resolved: Union[Unset, None, bool] = UNSET,
    muted: Union[Unset, None, bool] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, FindRecentIncidentsOnConnectionOrder] = UNSET,
    direction: Union[Unset, None, FindRecentIncidentsOnConnectionDirection] = UNSET,
) -> Optional[List["IncidentModel"]]:
    """findRecentIncidentsOnConnection

     Returns a list of recent data quality incidents.

    Args:
        connection_name (str):
        months (Union[Unset, None, int]):
        open_ (Union[Unset, None, bool]):
        acknowledged (Union[Unset, None, bool]):
        resolved (Union[Unset, None, bool]):
        muted (Union[Unset, None, bool]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        order (Union[Unset, None, FindRecentIncidentsOnConnectionOrder]):
        direction (Union[Unset, None, FindRecentIncidentsOnConnectionDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['IncidentModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            client=client,
            months=months,
            open_=open_,
            acknowledged=acknowledged,
            resolved=resolved,
            muted=muted,
            page=page,
            limit=limit,
            filter_=filter_,
            order=order,
            direction=direction,
        )
    ).parsed
