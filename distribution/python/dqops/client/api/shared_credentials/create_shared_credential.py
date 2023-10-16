from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...models.shared_credential_model import SharedCredentialModel
from ...types import Response


def _get_kwargs(
    *,
    json_body: SharedCredentialModel,
) -> Dict[str, Any]:
    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": "api/credentials",
        "json": json_json_body,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[MonoVoid]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoVoid.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[MonoVoid]:
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

     Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/
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
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Optional[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/
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

     Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/
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
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Optional[MonoVoid]:
    """createSharedCredential

     Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/
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
