import datetime
from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import Client
from ...models.check_result_detailed_single_model import CheckResultDetailedSingleModel
from ...models.get_incident_issues_direction import GetIncidentIssuesDirection
from ...models.get_incident_issues_order import GetIncidentIssuesOrder
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, GetIncidentIssuesOrder] = UNSET,
    direction: Union[Unset, None, GetIncidentIssuesDirection] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/incidents/{connectionName}/{year}/{month}/{incidentId}/issues".format(
        client.base_url,
        connectionName=connection_name,
        year=year,
        month=month,
        incidentId=incident_id,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["page"] = page

    params["limit"] = limit

    params["filter"] = filter_

    params["days"] = days

    json_date: Union[Unset, None, str] = UNSET
    if not isinstance(date, Unset):
        json_date = date.isoformat() if date else None

    params["date"] = json_date

    params["column"] = column

    params["check"] = check

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
) -> Optional[List["CheckResultDetailedSingleModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = CheckResultDetailedSingleModel.from_dict(
                response_200_item_data
            )

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[List["CheckResultDetailedSingleModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, GetIncidentIssuesOrder] = UNSET,
    direction: Union[Unset, None, GetIncidentIssuesDirection] = UNSET,
) -> Response[List["CheckResultDetailedSingleModel"]]:
    """getIncidentIssues

     Return a paged list of failed data quality check results that are related to an incident.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        order (Union[Unset, None, GetIncidentIssuesOrder]):
        direction (Union[Unset, None, GetIncidentIssuesDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultDetailedSingleModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
        page=page,
        limit=limit,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
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
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, GetIncidentIssuesOrder] = UNSET,
    direction: Union[Unset, None, GetIncidentIssuesDirection] = UNSET,
) -> Optional[List["CheckResultDetailedSingleModel"]]:
    """getIncidentIssues

     Return a paged list of failed data quality check results that are related to an incident.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        order (Union[Unset, None, GetIncidentIssuesOrder]):
        direction (Union[Unset, None, GetIncidentIssuesDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultDetailedSingleModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
        page=page,
        limit=limit,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
        order=order,
        direction=direction,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, GetIncidentIssuesOrder] = UNSET,
    direction: Union[Unset, None, GetIncidentIssuesDirection] = UNSET,
) -> Response[List["CheckResultDetailedSingleModel"]]:
    """getIncidentIssues

     Return a paged list of failed data quality check results that are related to an incident.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        order (Union[Unset, None, GetIncidentIssuesOrder]):
        direction (Union[Unset, None, GetIncidentIssuesDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultDetailedSingleModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
        page=page,
        limit=limit,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
        order=order,
        direction=direction,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: Client,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    order: Union[Unset, None, GetIncidentIssuesOrder] = UNSET,
    direction: Union[Unset, None, GetIncidentIssuesDirection] = UNSET,
) -> Optional[List["CheckResultDetailedSingleModel"]]:
    """getIncidentIssues

     Return a paged list of failed data quality check results that are related to an incident.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        order (Union[Unset, None, GetIncidentIssuesOrder]):
        direction (Union[Unset, None, GetIncidentIssuesDirection]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultDetailedSingleModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            year=year,
            month=month,
            incident_id=incident_id,
            client=client,
            page=page,
            limit=limit,
            filter_=filter_,
            days=days,
            date=date,
            column=column,
            check=check,
            order=order,
            direction=direction,
        )
    ).parsed
