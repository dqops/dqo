import datetime
from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_type import CheckType
from ...models.issue_histogram_model import IssueHistogramModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    executed_since: Union[Unset, None, datetime.date] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    json_executed_since: Union[Unset, None, str] = UNSET
    if not isinstance(executed_since, Unset):
        json_executed_since = executed_since.isoformat() if executed_since else None

    params["executedSince"] = json_executed_since

    params["filter"] = filter_

    params["days"] = days

    json_date: Union[Unset, None, str] = UNSET
    if not isinstance(date, Unset):
        json_date = date.isoformat() if date else None

    params["date"] = json_date

    params["column"] = column

    params["check"] = check

    json_check_type: Union[Unset, None, str] = UNSET
    if not isinstance(check_type, Unset):
        json_check_type = check_type.value if check_type else None

    params["checkType"] = json_check_type

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/histogram".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[IssueHistogramModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = IssueHistogramModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[IssueHistogramModel]:
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
    *,
    client: AuthenticatedClient,
    executed_since: Union[Unset, None, datetime.date] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[IssueHistogramModel]:
    """getTableIssuesHistogram

     Generates a histograms of data quality issues for each day on a table, returning the number of data
    quality issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        executed_since (Union[Unset, None, datetime.date]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IssueHistogramModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        executed_since=executed_since,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
        check_type=check_type,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    executed_since: Union[Unset, None, datetime.date] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[IssueHistogramModel]:
    """getTableIssuesHistogram

     Generates a histograms of data quality issues for each day on a table, returning the number of data
    quality issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        executed_since (Union[Unset, None, datetime.date]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IssueHistogramModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        executed_since=executed_since,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
        check_type=check_type,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    executed_since: Union[Unset, None, datetime.date] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[IssueHistogramModel]:
    """getTableIssuesHistogram

     Generates a histograms of data quality issues for each day on a table, returning the number of data
    quality issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        executed_since (Union[Unset, None, datetime.date]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[IssueHistogramModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        executed_since=executed_since,
        filter_=filter_,
        days=days,
        date=date,
        column=column,
        check=check,
        check_type=check_type,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    executed_since: Union[Unset, None, datetime.date] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
    days: Union[Unset, None, int] = UNSET,
    date: Union[Unset, None, datetime.date] = UNSET,
    column: Union[Unset, None, str] = UNSET,
    check: Union[Unset, None, str] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[IssueHistogramModel]:
    """getTableIssuesHistogram

     Generates a histograms of data quality issues for each day on a table, returning the number of data
    quality issues on that day. The other histograms are by a column name and by a check name.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        executed_since (Union[Unset, None, datetime.date]):
        filter_ (Union[Unset, None, str]):
        days (Union[Unset, None, int]):
        date (Union[Unset, None, datetime.date]):
        column (Union[Unset, None, str]):
        check (Union[Unset, None, str]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        IssueHistogramModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            executed_since=executed_since,
            filter_=filter_,
            days=days,
            date=date,
            column=column,
            check=check,
            check_type=check_type,
        )
    ).parsed
