from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import Client
from ...models.dqo_queue_job_id import DqoQueueJobId
from ...models.synchronize_multiple_folders_dqo_queue_job_parameters import (
    SynchronizeMultipleFoldersDqoQueueJobParameters,
)
from ...types import Response


def _get_kwargs(
    *,
    client: Client,
    json_body: SynchronizeMultipleFoldersDqoQueueJobParameters,
) -> Dict[str, Any]:
    url = "{}api/jobs/synchronize".format(client.base_url)

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[DqoQueueJobId]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DqoQueueJobId.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[DqoQueueJobId]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: Client,
    json_body: SynchronizeMultipleFoldersDqoQueueJobParameters,
) -> Response[DqoQueueJobId]:
    """synchronizeFolders

     Starts multiple file synchronization jobs that will synchronize files from selected DQO User home
    folders to the DQO Cloud. The default synchronization mode is a full synchronization (upload local
    files, download new files from the cloud).

    Args:
        json_body (SynchronizeMultipleFoldersDqoQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoQueueJobId]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: Client,
    json_body: SynchronizeMultipleFoldersDqoQueueJobParameters,
) -> Optional[DqoQueueJobId]:
    """synchronizeFolders

     Starts multiple file synchronization jobs that will synchronize files from selected DQO User home
    folders to the DQO Cloud. The default synchronization mode is a full synchronization (upload local
    files, download new files from the cloud).

    Args:
        json_body (SynchronizeMultipleFoldersDqoQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoQueueJobId
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    *,
    client: Client,
    json_body: SynchronizeMultipleFoldersDqoQueueJobParameters,
) -> Response[DqoQueueJobId]:
    """synchronizeFolders

     Starts multiple file synchronization jobs that will synchronize files from selected DQO User home
    folders to the DQO Cloud. The default synchronization mode is a full synchronization (upload local
    files, download new files from the cloud).

    Args:
        json_body (SynchronizeMultipleFoldersDqoQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoQueueJobId]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: Client,
    json_body: SynchronizeMultipleFoldersDqoQueueJobParameters,
) -> Optional[DqoQueueJobId]:
    """synchronizeFolders

     Starts multiple file synchronization jobs that will synchronize files from selected DQO User home
    folders to the DQO Cloud. The default synchronization mode is a full synchronization (upload local
    files, download new files from the cloud).

    Args:
        json_body (SynchronizeMultipleFoldersDqoQueueJobParameters):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoQueueJobId
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
        )
    ).parsed
