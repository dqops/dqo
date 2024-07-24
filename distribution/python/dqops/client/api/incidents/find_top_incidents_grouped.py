from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.incident_status import IncidentStatus
from ...models.top_incident_grouping import TopIncidentGrouping
from ...models.top_incidents_model import TopIncidentsModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    status: Union[Unset, None, IncidentStatus] = UNSET,
    group_by: Union[Unset, None, TopIncidentGrouping] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    days: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    json_status: Union[Unset, None, str] = UNSET
    if not isinstance(status, Unset):
        json_status = status.value if status else None

    params["status"] = json_status

    json_group_by: Union[Unset, None, str] = UNSET
    if not isinstance(group_by, Unset):
        json_group_by = group_by.value if group_by else None

    params["groupBy"] = json_group_by

    params["limit"] = limit

    params["days"] = days

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/topincidents",
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[TopIncidentsModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TopIncidentsModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[TopIncidentsModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    status: Union[Unset, None, IncidentStatus] = UNSET,
    group_by: Union[Unset, None, TopIncidentGrouping] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    days: Union[Unset, None, int] = UNSET,
) -> Response[TopIncidentsModel]:
    """findTopIncidentsGrouped

     Finds the most recent incidents grouped by one of the incident's attribute, such as a data quality
    dimension, a data quality check category or the connection name.

    Args:
        status (Union[Unset, None, IncidentStatus]):
        group_by (Union[Unset, None, TopIncidentGrouping]):
        limit (Union[Unset, None, int]):
        days (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TopIncidentsModel]
    """

    kwargs = _get_kwargs(
        status=status,
        group_by=group_by,
        limit=limit,
        days=days,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    status: Union[Unset, None, IncidentStatus] = UNSET,
    group_by: Union[Unset, None, TopIncidentGrouping] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    days: Union[Unset, None, int] = UNSET,
) -> Optional[TopIncidentsModel]:
    """findTopIncidentsGrouped

     Finds the most recent incidents grouped by one of the incident's attribute, such as a data quality
    dimension, a data quality check category or the connection name.

    Args:
        status (Union[Unset, None, IncidentStatus]):
        group_by (Union[Unset, None, TopIncidentGrouping]):
        limit (Union[Unset, None, int]):
        days (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TopIncidentsModel
    """

    return sync_detailed(
        client=client,
        status=status,
        group_by=group_by,
        limit=limit,
        days=days,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    status: Union[Unset, None, IncidentStatus] = UNSET,
    group_by: Union[Unset, None, TopIncidentGrouping] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    days: Union[Unset, None, int] = UNSET,
) -> Response[TopIncidentsModel]:
    """findTopIncidentsGrouped

     Finds the most recent incidents grouped by one of the incident's attribute, such as a data quality
    dimension, a data quality check category or the connection name.

    Args:
        status (Union[Unset, None, IncidentStatus]):
        group_by (Union[Unset, None, TopIncidentGrouping]):
        limit (Union[Unset, None, int]):
        days (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TopIncidentsModel]
    """

    kwargs = _get_kwargs(
        status=status,
        group_by=group_by,
        limit=limit,
        days=days,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    status: Union[Unset, None, IncidentStatus] = UNSET,
    group_by: Union[Unset, None, TopIncidentGrouping] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    days: Union[Unset, None, int] = UNSET,
) -> Optional[TopIncidentsModel]:
    """findTopIncidentsGrouped

     Finds the most recent incidents grouped by one of the incident's attribute, such as a data quality
    dimension, a data quality check category or the connection name.

    Args:
        status (Union[Unset, None, IncidentStatus]):
        group_by (Union[Unset, None, TopIncidentGrouping]):
        limit (Union[Unset, None, int]):
        days (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TopIncidentsModel
    """

    return (
        await asyncio_detailed(
            client=client,
            status=status,
            group_by=group_by,
            limit=limit,
            days=days,
        )
    ).parsed
