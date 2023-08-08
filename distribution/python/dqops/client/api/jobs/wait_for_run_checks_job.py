from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import Client
from ...models.run_checks_queue_job_result import RunChecksQueueJobResult
from ...types import UNSET, Response, Unset


def _get_kwargs(
    job_id: int,
    *,
    client: Client,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/jobs/runchecks/{jobId}/wait".format(client.base_url, jobId=job_id)

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["waitTimeout"] = wait_timeout

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
) -> Optional[RunChecksQueueJobResult]:
    if response.status_code == HTTPStatus.OK:
        response_200 = RunChecksQueueJobResult.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[RunChecksQueueJobResult]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    job_id: int,
    *,
    client: Client,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[RunChecksQueueJobResult]:
    """waitForRunChecksJob

     Waits for a job to finish. Returns the status of a finished job or a current state of a job that is
    still running, but the wait timeout elapsed.

    Args:
        job_id (int):
        wait_timeout (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[RunChecksQueueJobResult]
    """

    kwargs = _get_kwargs(
        job_id=job_id,
        client=client,
        wait_timeout=wait_timeout,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    job_id: int,
    *,
    client: Client,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[RunChecksQueueJobResult]:
    """waitForRunChecksJob

     Waits for a job to finish. Returns the status of a finished job or a current state of a job that is
    still running, but the wait timeout elapsed.

    Args:
        job_id (int):
        wait_timeout (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        RunChecksQueueJobResult
    """

    return sync_detailed(
        job_id=job_id,
        client=client,
        wait_timeout=wait_timeout,
    ).parsed


async def asyncio_detailed(
    job_id: int,
    *,
    client: Client,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[RunChecksQueueJobResult]:
    """waitForRunChecksJob

     Waits for a job to finish. Returns the status of a finished job or a current state of a job that is
    still running, but the wait timeout elapsed.

    Args:
        job_id (int):
        wait_timeout (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[RunChecksQueueJobResult]
    """

    kwargs = _get_kwargs(
        job_id=job_id,
        client=client,
        wait_timeout=wait_timeout,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    job_id: int,
    *,
    client: Client,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[RunChecksQueueJobResult]:
    """waitForRunChecksJob

     Waits for a job to finish. Returns the status of a finished job or a current state of a job that is
    still running, but the wait timeout elapsed.

    Args:
        job_id (int):
        wait_timeout (Union[Unset, None, int]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        RunChecksQueueJobResult
    """

    return (
        await asyncio_detailed(
            job_id=job_id,
            client=client,
            wait_timeout=wait_timeout,
        )
    ).parsed
