from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...types import Response


def _get_kwargs(
    data_domain_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/domains/{dataDomainName}/switch".format(
            dataDomainName=data_domain_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.SEE_OTHER:
        return None
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Any]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    data_domain_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """switchToDataDomain

     Switches to a different data domain. This operation sends a special cookie and redirects the user to
    the home screen.

    Args:
        data_domain_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        data_domain_name=data_domain_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    data_domain_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """switchToDataDomain

     Switches to a different data domain. This operation sends a special cookie and redirects the user to
    the home screen.

    Args:
        data_domain_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        data_domain_name=data_domain_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
