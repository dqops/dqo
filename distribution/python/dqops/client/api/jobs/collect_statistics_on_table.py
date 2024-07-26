from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.collect_statistics_queue_job_result import (
    CollectStatisticsQueueJobResult,
)
from ...models.statistics_collector_search_filters import (
    StatisticsCollectorSearchFilters,
)
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    json_body: StatisticsCollectorSearchFilters,
    job_business_key: Union[Unset, None, str] = UNSET,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["jobBusinessKey"] = job_business_key

    params["wait"] = wait

    params["waitTimeout"] = wait_timeout

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": "api/jobs/collectstatistics/table",
        "json": json_json_body,
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CollectStatisticsQueueJobResult]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CollectStatisticsQueueJobResult.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[CollectStatisticsQueueJobResult]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    json_body: StatisticsCollectorSearchFilters,
    job_business_key: Union[Unset, None, str] = UNSET,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[CollectStatisticsQueueJobResult]:
    """collectStatisticsOnTable

     Starts a new background job that will run selected data statistics collectors for the entire table

    Args:
        job_business_key (Union[Unset, None, str]):
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (StatisticsCollectorSearchFilters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CollectStatisticsQueueJobResult]
    """

    kwargs = _get_kwargs(
        json_body=json_body,
        job_business_key=job_business_key,
        wait=wait,
        wait_timeout=wait_timeout,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    json_body: StatisticsCollectorSearchFilters,
    job_business_key: Union[Unset, None, str] = UNSET,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[CollectStatisticsQueueJobResult]:
    """collectStatisticsOnTable

     Starts a new background job that will run selected data statistics collectors for the entire table

    Args:
        job_business_key (Union[Unset, None, str]):
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (StatisticsCollectorSearchFilters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CollectStatisticsQueueJobResult
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
        job_business_key=job_business_key,
        wait=wait,
        wait_timeout=wait_timeout,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    json_body: StatisticsCollectorSearchFilters,
    job_business_key: Union[Unset, None, str] = UNSET,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[CollectStatisticsQueueJobResult]:
    """collectStatisticsOnTable

     Starts a new background job that will run selected data statistics collectors for the entire table

    Args:
        job_business_key (Union[Unset, None, str]):
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (StatisticsCollectorSearchFilters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CollectStatisticsQueueJobResult]
    """

    kwargs = _get_kwargs(
        json_body=json_body,
        job_business_key=job_business_key,
        wait=wait,
        wait_timeout=wait_timeout,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    json_body: StatisticsCollectorSearchFilters,
    job_business_key: Union[Unset, None, str] = UNSET,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[CollectStatisticsQueueJobResult]:
    """collectStatisticsOnTable

     Starts a new background job that will run selected data statistics collectors for the entire table

    Args:
        job_business_key (Union[Unset, None, str]):
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (StatisticsCollectorSearchFilters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CollectStatisticsQueueJobResult
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
            job_business_key=job_business_key,
            wait=wait,
            wait_timeout=wait_timeout,
        )
    ).parsed
