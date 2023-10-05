from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...types import Response


def _get_kwargs(
    credential_name: str,
    *,
    client: AuthenticatedClient,
) -> Dict[str, Any]:
    url = "{}api/credentials/{credentialName}".format(
        client.base_url, credentialName=credential_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    return {
        "method": "delete",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
    }


def _parse_response(*, client: Client, response: httpx.Response) -> Optional[MonoVoid]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoVoid.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(*, client: Client, response: httpx.Response) -> Response[MonoVoid]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    credential_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[MonoVoid]:
    """deleteSharedCredential

     Deletes a shared credential file from the DQO user's home .credentials/ folder.

    Args:
        credential_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        credential_name=credential_name,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    credential_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[MonoVoid]:
    """deleteSharedCredential

     Deletes a shared credential file from the DQO user's home .credentials/ folder.

    Args:
        credential_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return sync_detailed(
        credential_name=credential_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    credential_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[MonoVoid]:
    """deleteSharedCredential

     Deletes a shared credential file from the DQO user's home .credentials/ folder.

    Args:
        credential_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        credential_name=credential_name,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    credential_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[MonoVoid]:
    """deleteSharedCredential

     Deletes a shared credential file from the DQO user's home .credentials/ folder.

    Args:
        credential_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return (
        await asyncio_detailed(
            credential_name=credential_name,
            client=client,
        )
    ).parsed
