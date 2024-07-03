from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.dqo_job_queue_incremental_snapshot_model import (
    DqoJobQueueIncrementalSnapshotModel,
)
from ...types import Response


def _get_kwargs(
    sequence_number: int,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/jobs/jobchangessince/{sequenceNumber}".format(
            sequenceNumber=sequence_number,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[DqoJobQueueIncrementalSnapshotModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DqoJobQueueIncrementalSnapshotModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[DqoJobQueueIncrementalSnapshotModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    sequence_number: int,
    *,
    client: AuthenticatedClient,
) -> Response[DqoJobQueueIncrementalSnapshotModel]:
    """getJobChangesSince

     Retrieves an incremental list of job changes (new jobs or job status changes)

    Args:
        sequence_number (int):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoJobQueueIncrementalSnapshotModel]
    """

    kwargs = _get_kwargs(
        sequence_number=sequence_number,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    sequence_number: int,
    *,
    client: AuthenticatedClient,
) -> Optional[DqoJobQueueIncrementalSnapshotModel]:
    """getJobChangesSince

     Retrieves an incremental list of job changes (new jobs or job status changes)

    Args:
        sequence_number (int):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoJobQueueIncrementalSnapshotModel
    """

    return sync_detailed(
        sequence_number=sequence_number,
        client=client,
    ).parsed


async def asyncio_detailed(
    sequence_number: int,
    *,
    client: AuthenticatedClient,
) -> Response[DqoJobQueueIncrementalSnapshotModel]:
    """getJobChangesSince

     Retrieves an incremental list of job changes (new jobs or job status changes)

    Args:
        sequence_number (int):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoJobQueueIncrementalSnapshotModel]
    """

    kwargs = _get_kwargs(
        sequence_number=sequence_number,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    sequence_number: int,
    *,
    client: AuthenticatedClient,
) -> Optional[DqoJobQueueIncrementalSnapshotModel]:
    """getJobChangesSince

     Retrieves an incremental list of job changes (new jobs or job status changes)

    Args:
        sequence_number (int):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoJobQueueIncrementalSnapshotModel
    """

    return (
        await asyncio_detailed(
            sequence_number=sequence_number,
            client=client,
        )
    ).parsed
