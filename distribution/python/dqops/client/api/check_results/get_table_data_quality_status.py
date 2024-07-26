import datetime
from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_time_scale import CheckTimeScale
from ...models.table_current_data_quality_status_model import (
    TableCurrentDataQualityStatusModel,
)
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    months: Union[Unset, None, int] = UNSET,
    since: Union[Unset, None, datetime.datetime] = UNSET,
    profiling: Union[Unset, None, bool] = UNSET,
    monitoring: Union[Unset, None, bool] = UNSET,
    partitioned: Union[Unset, None, bool] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
    data_group: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    quality_dimension: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["months"] = months

    json_since: Union[Unset, None, str] = UNSET
    if not isinstance(since, Unset):
        json_since = since.isoformat() if since else None

    params["since"] = json_since

    params["profiling"] = profiling

    params["monitoring"] = monitoring

    params["partitioned"] = partitioned

    json_check_time_scale: Union[Unset, None, str] = UNSET
    if not isinstance(check_time_scale, Unset):
        json_check_time_scale = check_time_scale.value if check_time_scale else None

    params["checkTimeScale"] = json_check_time_scale

    params["dataGroup"] = data_group

    params["checkName"] = check_name

    params["category"] = category

    params["tableComparison"] = table_comparison

    params["qualityDimension"] = quality_dimension

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[TableCurrentDataQualityStatusModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TableCurrentDataQualityStatusModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[TableCurrentDataQualityStatusModel]:
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
    months: Union[Unset, None, int] = UNSET,
    since: Union[Unset, None, datetime.datetime] = UNSET,
    profiling: Union[Unset, None, bool] = UNSET,
    monitoring: Union[Unset, None, bool] = UNSET,
    partitioned: Union[Unset, None, bool] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
    data_group: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    quality_dimension: Union[Unset, None, str] = UNSET,
) -> Response[TableCurrentDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        since (Union[Unset, None, datetime.datetime]):
        profiling (Union[Unset, None, bool]):
        monitoring (Union[Unset, None, bool]):
        partitioned (Union[Unset, None, bool]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):
        data_group (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        quality_dimension (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableCurrentDataQualityStatusModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        months=months,
        since=since,
        profiling=profiling,
        monitoring=monitoring,
        partitioned=partitioned,
        check_time_scale=check_time_scale,
        data_group=data_group,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        quality_dimension=quality_dimension,
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
    months: Union[Unset, None, int] = UNSET,
    since: Union[Unset, None, datetime.datetime] = UNSET,
    profiling: Union[Unset, None, bool] = UNSET,
    monitoring: Union[Unset, None, bool] = UNSET,
    partitioned: Union[Unset, None, bool] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
    data_group: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    quality_dimension: Union[Unset, None, str] = UNSET,
) -> Optional[TableCurrentDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        since (Union[Unset, None, datetime.datetime]):
        profiling (Union[Unset, None, bool]):
        monitoring (Union[Unset, None, bool]):
        partitioned (Union[Unset, None, bool]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):
        data_group (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        quality_dimension (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableCurrentDataQualityStatusModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        months=months,
        since=since,
        profiling=profiling,
        monitoring=monitoring,
        partitioned=partitioned,
        check_time_scale=check_time_scale,
        data_group=data_group,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        quality_dimension=quality_dimension,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    since: Union[Unset, None, datetime.datetime] = UNSET,
    profiling: Union[Unset, None, bool] = UNSET,
    monitoring: Union[Unset, None, bool] = UNSET,
    partitioned: Union[Unset, None, bool] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
    data_group: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    quality_dimension: Union[Unset, None, str] = UNSET,
) -> Response[TableCurrentDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        since (Union[Unset, None, datetime.datetime]):
        profiling (Union[Unset, None, bool]):
        monitoring (Union[Unset, None, bool]):
        partitioned (Union[Unset, None, bool]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):
        data_group (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        quality_dimension (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableCurrentDataQualityStatusModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        months=months,
        since=since,
        profiling=profiling,
        monitoring=monitoring,
        partitioned=partitioned,
        check_time_scale=check_time_scale,
        data_group=data_group,
        check_name=check_name,
        category=category,
        table_comparison=table_comparison,
        quality_dimension=quality_dimension,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    since: Union[Unset, None, datetime.datetime] = UNSET,
    profiling: Union[Unset, None, bool] = UNSET,
    monitoring: Union[Unset, None, bool] = UNSET,
    partitioned: Union[Unset, None, bool] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
    data_group: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    category: Union[Unset, None, str] = UNSET,
    table_comparison: Union[Unset, None, str] = UNSET,
    quality_dimension: Union[Unset, None, str] = UNSET,
) -> Optional[TableCurrentDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        since (Union[Unset, None, datetime.datetime]):
        profiling (Union[Unset, None, bool]):
        monitoring (Union[Unset, None, bool]):
        partitioned (Union[Unset, None, bool]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):
        data_group (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        category (Union[Unset, None, str]):
        table_comparison (Union[Unset, None, str]):
        quality_dimension (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableCurrentDataQualityStatusModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            months=months,
            since=since,
            profiling=profiling,
            monitoring=monitoring,
            partitioned=partitioned,
            check_time_scale=check_time_scale,
            data_group=data_group,
            check_name=check_name,
            category=category,
            table_comparison=table_comparison,
            quality_dimension=quality_dimension,
        )
    ).parsed
