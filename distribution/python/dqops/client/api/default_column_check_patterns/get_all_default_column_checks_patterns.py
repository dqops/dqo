from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.default_column_checks_pattern_list_model import (
    DefaultColumnChecksPatternListModel,
)
from ...types import Response


def _get_kwargs() -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/default/checks/column",
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["DefaultColumnChecksPatternListModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = DefaultColumnChecksPatternListModel.from_dict(
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
) -> Response[List["DefaultColumnChecksPatternListModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[List["DefaultColumnChecksPatternListModel"]]:
    """getAllDefaultColumnChecksPatterns

     Returns a flat list of all column-level default check patterns configured for this instance. Default
    checks are applied on columns dynamically.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['DefaultColumnChecksPatternListModel']]
    """

    kwargs = _get_kwargs()

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
) -> Optional[List["DefaultColumnChecksPatternListModel"]]:
    """getAllDefaultColumnChecksPatterns

     Returns a flat list of all column-level default check patterns configured for this instance. Default
    checks are applied on columns dynamically.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['DefaultColumnChecksPatternListModel']
    """

    return sync_detailed(
        client=client,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[List["DefaultColumnChecksPatternListModel"]]:
    """getAllDefaultColumnChecksPatterns

     Returns a flat list of all column-level default check patterns configured for this instance. Default
    checks are applied on columns dynamically.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['DefaultColumnChecksPatternListModel']]
    """

    kwargs = _get_kwargs()

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
) -> Optional[List["DefaultColumnChecksPatternListModel"]]:
    """getAllDefaultColumnChecksPatterns

     Returns a flat list of all column-level default check patterns configured for this instance. Default
    checks are applied on columns dynamically.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['DefaultColumnChecksPatternListModel']
    """

    return (
        await asyncio_detailed(
            client=client,
        )
    ).parsed
