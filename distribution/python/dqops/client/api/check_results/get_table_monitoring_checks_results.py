import datetime
from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_results_detailed_load_mode import CheckResultsDetailedLoadMode
from ...models.check_results_list_model import CheckResultsListModel
from ...models.check_time_scale import CheckTimeScale
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    data_group: Union[Unset, None, str] = UNSET,
    month_start: Union[Unset, None, datetime.date] = UNSET,
    month_end: Union[Unset, None, datetime.date] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    load_mode: Union[Unset, None, CheckResultsDetailedLoadMode] = UNSET,
    max_results_per_check: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["dataGroup"] = data_group

    json_month_start: Union[Unset, None, str] = UNSET
    if not isinstance(month_start, Unset):
        json_month_start = month_start.isoformat() if month_start else None

    params["monthStart"] = json_month_start

    json_month_end: Union[Unset, None, str] = UNSET
    if not isinstance(month_end, Unset):
        json_month_end = month_end.isoformat() if month_end else None

    params["monthEnd"] = json_month_end

    params["checkName"] = check_name

    params["category"] = category

    params["tableComparison"] = table_comparison

    json_load_mode: Union[Unset, None, str] = UNSET
    if not isinstance(load_mode, Unset):
        json_load_mode = load_mode.value if load_mode else None

    params["loadMode"] = json_load_mode

    params["maxResultsPerCheck"] = max_results_per_check

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            timeScale=time_scale,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["CheckResultsListModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = CheckResultsListModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["CheckResultsListModel"]]:
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
    data_group: Union[Unset, None, str] = UNSET,
    month_start: Union[Unset, None, datetime.date] = UNSET,
    month_end: Union[Unset, None, datetime.date] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    load_mode: Union[Unset, None, CheckResultsDetailedLoadMode] = UNSET,
    max_results_per_check: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckResultsListModel"]]:
    """getTableMonitoringChecksResults

     Returns the complete results of the most recent table level monitoring executions for the monitoring
    at a requested time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        data_group (Union[Unset, None, str]):
        month_start (Union[Unset, None, datetime.date]):
        month_end (Union[Unset, None, datetime.date]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        load_mode (Union[Unset, None, CheckResultsDetailedLoadMode]):
        max_results_per_check (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultsListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        data_group=data_group,
        month_start=month_start,
        month_end=month_end,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        load_mode=load_mode,
        max_results_per_check=max_results_per_check,
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
    data_group: Union[Unset, None, str] = UNSET,
    month_start: Union[Unset, None, datetime.date] = UNSET,
    month_end: Union[Unset, None, datetime.date] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    load_mode: Union[Unset, None, CheckResultsDetailedLoadMode] = UNSET,
    max_results_per_check: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckResultsListModel"]]:
    """getTableMonitoringChecksResults

     Returns the complete results of the most recent table level monitoring executions for the monitoring
    at a requested time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        data_group (Union[Unset, None, str]):
        month_start (Union[Unset, None, datetime.date]):
        month_end (Union[Unset, None, datetime.date]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        load_mode (Union[Unset, None, CheckResultsDetailedLoadMode]):
        max_results_per_check (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultsListModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        client=client,
        data_group=data_group,
        month_start=month_start,
        month_end=month_end,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        load_mode=load_mode,
        max_results_per_check=max_results_per_check,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    data_group: Union[Unset, None, str] = UNSET,
    month_start: Union[Unset, None, datetime.date] = UNSET,
    month_end: Union[Unset, None, datetime.date] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    load_mode: Union[Unset, None, CheckResultsDetailedLoadMode] = UNSET,
    max_results_per_check: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckResultsListModel"]]:
    """getTableMonitoringChecksResults

     Returns the complete results of the most recent table level monitoring executions for the monitoring
    at a requested time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        data_group (Union[Unset, None, str]):
        month_start (Union[Unset, None, datetime.date]):
        month_end (Union[Unset, None, datetime.date]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        load_mode (Union[Unset, None, CheckResultsDetailedLoadMode]):
        max_results_per_check (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultsListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        data_group=data_group,
        month_start=month_start,
        month_end=month_end,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        load_mode=load_mode,
        max_results_per_check=max_results_per_check,
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
    data_group: Union[Unset, None, str] = UNSET,
    month_start: Union[Unset, None, datetime.date] = UNSET,
    month_end: Union[Unset, None, datetime.date] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    load_mode: Union[Unset, None, CheckResultsDetailedLoadMode] = UNSET,
    max_results_per_check: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckResultsListModel"]]:
    """getTableMonitoringChecksResults

     Returns the complete results of the most recent table level monitoring executions for the monitoring
    at a requested time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        data_group (Union[Unset, None, str]):
        month_start (Union[Unset, None, datetime.date]):
        month_end (Union[Unset, None, datetime.date]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        load_mode (Union[Unset, None, CheckResultsDetailedLoadMode]):
        max_results_per_check (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultsListModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            time_scale=time_scale,
            client=client,
            data_group=data_group,
            month_start=month_start,
            month_end=month_end,
            check_name=check_name,
            category=category,
            table_comparison=table_comparison,
            load_mode=load_mode,
            max_results_per_check=max_results_per_check,
        )
    ).parsed
