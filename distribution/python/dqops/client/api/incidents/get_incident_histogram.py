import datetime
from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.incident_issue_histogram_model import IncidentIssueHistogramModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["filter"] = filter_

    params["days"] = days

    json_date: Union[Unset, None, str] = UNSET
    if not isinstance(date, Unset):
        json_date = date.isoformat() if date else None

    params["date"] = json_date

    params["column"] = column

    params["check"] = check

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/incidents/{connectionName}/{year}/{month}/{incidentId}/histogram".format(
            connectionName=connection_name,
            year=year,
            month=month,
            incidentId=incident_id,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[IncidentIssueHistogramModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = IncidentIssueHistogramModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[IncidentIssueHistogramModel]:
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
    client: AuthenticatedClient,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
) -> Response[IncidentIssueHistogramModel]:
    """getIncidentHistogram

     Generates histograms of data quality issues for each day, returning the number of data quality
    issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IncidentIssueHistogramModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: AuthenticatedClient,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
) -> Optional[IncidentIssueHistogramModel]:
    """getIncidentHistogram

     Generates histograms of data quality issues for each day, returning the number of data quality
    issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IncidentIssueHistogramModel
    """

    return sync_detailed(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        client=client,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: AuthenticatedClient,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
) -> Response[IncidentIssueHistogramModel]:
    """getIncidentHistogram

     Generates histograms of data quality issues for each day, returning the number of data quality
    issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IncidentIssueHistogramModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        year=year,
        month=month,
        incident_id=incident_id,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    year: int,
    month: int,
    incident_id: str,
    *,
    client: AuthenticatedClient,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
) -> Optional[IncidentIssueHistogramModel]:
    """getIncidentHistogram

     Generates histograms of data quality issues for each day, returning the number of data quality
    issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        year (int):
        month (int):
        incident_id (str):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IncidentIssueHistogramModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            year=year,
            month=month,
            incident_id=incident_id,
            client=client,
            filter_=filter_,
            days=days,
            date=date,
            column=column,
            check=check,
        )
    ).parsed
