from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_results_overview_data_model import CheckResultsOverviewDataModel
from ...models.check_time_scale import CheckTimeScale
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
    *,
    category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    results_count: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["category"] = category

    params["checkName"] = check_name

    params["resultsCount"] = results_count

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/overview".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            columnName=column_name,
            timeScale=time_scale,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["CheckResultsOverviewDataModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = CheckResultsOverviewDataModel.from_dict(
                response_200_item_data
            )

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["CheckResultsOverviewDataModel"]]:
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
    column_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    results_count: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckResultsOverviewDataModel"]]:
    """getColumnPartitionedChecksOverview

     Returns an overview of the most recent column level partitioned checks executions for a requested
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (CheckTimeScale):
        category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        results_count (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultsOverviewDataModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        category=category,
        check_name=check_name,
        results_count=results_count,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    results_count: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckResultsOverviewDataModel"]]:
    """getColumnPartitionedChecksOverview

     Returns an overview of the most recent column level partitioned checks executions for a requested
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (CheckTimeScale):
        category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        results_count (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultsOverviewDataModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        client=client,
        category=category,
        check_name=check_name,
        results_count=results_count,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    results_count: Union[Unset, None, int] = UNSET,
) -> Response[List["CheckResultsOverviewDataModel"]]:
    """getColumnPartitionedChecksOverview

     Returns an overview of the most recent column level partitioned checks executions for a requested
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (CheckTimeScale):
        category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        results_count (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckResultsOverviewDataModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        column_name=column_name,
        time_scale=time_scale,
        category=category,
        check_name=check_name,
        results_count=results_count,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    column_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
    results_count: Union[Unset, None, int] = UNSET,
) -> Optional[List["CheckResultsOverviewDataModel"]]:
    """getColumnPartitionedChecksOverview

     Returns an overview of the most recent column level partitioned checks executions for a requested
    time scale

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        column_name (str):
        time_scale (CheckTimeScale):
        category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):
        results_count (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckResultsOverviewDataModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            column_name=column_name,
            time_scale=time_scale,
            client=client,
            category=category,
            check_name=check_name,
            results_count=results_count,
        )
    ).parsed
