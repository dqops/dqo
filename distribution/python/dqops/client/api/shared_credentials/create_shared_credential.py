from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...models.shared_credential_model import SharedCredentialModel
from ...types import Response


def _get_kwargs(
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Dict[str, Any]:
    url = "{}api/credentials".format(client.base_url)

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
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Response[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQO user's home .credentials/
    folder named as the credential and with the content that is provided in this call.

    Args:
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
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
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Optional[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQO user's home .credentials/
    folder named as the credential and with the content that is provided in this call.

    Args:
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Response[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQO user's home .credentials/
    folder named as the credential and with the content that is provided in this call.

    Args:
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
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
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Optional[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQO user's home .credentials/
    folder named as the credential and with the content that is provided in this call.

    Args:
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
        )
    ).parsed
