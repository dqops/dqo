from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.dqo_cloud_user_model import DqoCloudUserModel
from ...types import Response


def _get_kwargs(
    email: str,
    *,
    client: AuthenticatedClient,
) -> Dict[str, Any]:
    url = "{}api/users/{email}".format(client.base_url, email=email)

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[DqoCloudUserModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DqoCloudUserModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[DqoCloudUserModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    email: str,
    *,
    client: AuthenticatedClient,
) -> Response[DqoCloudUserModel]:
    """getUser

     Returns the user model that describes the role of a user identified by an email

    Args:
        email (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoCloudUserModel]
    """

    kwargs = _get_kwargs(
        email=email,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    email: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DqoCloudUserModel]:
    """getUser

     Returns the user model that describes the role of a user identified by an email

    Args:
        email (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoCloudUserModel
    """

    return sync_detailed(
        email=email,
        client=client,
    ).parsed


async def asyncio_detailed(
    email: str,
    *,
    client: AuthenticatedClient,
) -> Response[DqoCloudUserModel]:
    """getUser

     Returns the user model that describes the role of a user identified by an email

    Args:
        email (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DqoCloudUserModel]
    """

    kwargs = _get_kwargs(
        email=email,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    email: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DqoCloudUserModel]:
    """getUser

     Returns the user model that describes the role of a user identified by an email

    Args:
        email (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DqoCloudUserModel
    """

    return (
        await asyncio_detailed(
            email=email,
            client=client,
        )
    ).parsed
