from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.delete_stored_data_queue_job_parameters import (
    DeleteStoredDataQueueJobParameters,
)
from ...models.delete_stored_data_queue_job_result import DeleteStoredDataQueueJobResult
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    client: AuthenticatedClient,
    json_body: DeleteStoredDataQueueJobParameters,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/jobs/deletestoreddata".format(client.base_url)

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["wait"] = wait

    params["waitTimeout"] = wait_timeout

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[DeleteStoredDataQueueJobResult]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DeleteStoredDataQueueJobResult.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[DeleteStoredDataQueueJobResult]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    json_body: DeleteStoredDataQueueJobParameters,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[DeleteStoredDataQueueJobResult]:
    """deleteStoredData

     Starts a new background job that will delete stored data about check results, sensor readouts etc.

    Args:
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (DeleteStoredDataQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DeleteStoredDataQueueJobResult]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
        wait=wait,
        wait_timeout=wait_timeout,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    json_body: DeleteStoredDataQueueJobParameters,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[DeleteStoredDataQueueJobResult]:
    """deleteStoredData

     Starts a new background job that will delete stored data about check results, sensor readouts etc.

    Args:
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (DeleteStoredDataQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DeleteStoredDataQueueJobResult
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
        wait=wait,
        wait_timeout=wait_timeout,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    json_body: DeleteStoredDataQueueJobParameters,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Response[DeleteStoredDataQueueJobResult]:
    """deleteStoredData

     Starts a new background job that will delete stored data about check results, sensor readouts etc.

    Args:
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (DeleteStoredDataQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DeleteStoredDataQueueJobResult]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
        wait=wait,
        wait_timeout=wait_timeout,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    json_body: DeleteStoredDataQueueJobParameters,
    wait: Union[Unset, None, bool] = UNSET,
    wait_timeout: Union[Unset, None, int] = UNSET,
) -> Optional[DeleteStoredDataQueueJobResult]:
    """deleteStoredData

     Starts a new background job that will delete stored data about check results, sensor readouts etc.

    Args:
        wait (Union[Unset, None, bool]):
        wait_timeout (Union[Unset, None, int]):
        json_body (DeleteStoredDataQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DeleteStoredDataQueueJobResult
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
            wait=wait,
            wait_timeout=wait_timeout,
        )
    ).parsed
