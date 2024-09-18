from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.local_data_domain_model import LocalDataDomainModel
from ...types import Response


def _get_kwargs() -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/domains/",
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["LocalDataDomainModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = LocalDataDomainModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["LocalDataDomainModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[List["LocalDataDomainModel"]]:
    """getLocalDataDomains

     Returns a list of local data domains that this instance is maintaining. Data domains are supported
    only in an ENTERPRISE versions of DQOps.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['LocalDataDomainModel']]
    """

    kwargs = _get_kwargs()

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
) -> Optional[List["LocalDataDomainModel"]]:
    """getLocalDataDomains

     Returns a list of local data domains that this instance is maintaining. Data domains are supported
    only in an ENTERPRISE versions of DQOps.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['LocalDataDomainModel']
    """

    return sync_detailed(
        client=client,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[List["LocalDataDomainModel"]]:
    """getLocalDataDomains

     Returns a list of local data domains that this instance is maintaining. Data domains are supported
    only in an ENTERPRISE versions of DQOps.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['LocalDataDomainModel']]
    """

    kwargs = _get_kwargs()

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
) -> Optional[List["LocalDataDomainModel"]]:
    """getLocalDataDomains

     Returns a list of local data domains that this instance is maintaining. Data domains are supported
    only in an ENTERPRISE versions of DQOps.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['LocalDataDomainModel']
    """

    return (
        await asyncio_detailed(
            client=client,
        )
    ).parsed
