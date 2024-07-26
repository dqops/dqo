from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.shared_credential_model import SharedCredentialModel
from ...types import Response


def _get_kwargs(
    credential_name: str,
    *,
    json_body: SharedCredentialModel,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/credential/{credentialName}".format(
            credentialName=credential_name,
        ),
        "json": json_json_body,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.NO_CONTENT:
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
    credential_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Response[Any]:
    """updateSharedCredential

     Updates an existing shared credential, replacing the credential's file content.

    Args:
        credential_name (str):
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        credential_name=credential_name,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    credential_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SharedCredentialModel,
) -> Response[Any]:
    """updateSharedCredential

     Updates an existing shared credential, replacing the credential's file content.

    Args:
        credential_name (str):
        json_body (SharedCredentialModel): Shared credentials full model used to create and update
            the credential. Contains one of two forms of the credential's value: a text or a base64
            binary value.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        credential_name=credential_name,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
