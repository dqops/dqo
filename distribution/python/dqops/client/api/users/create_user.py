from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.dqo_cloud_user_model import DqoCloudUserModel
from ...models.mono_void import MonoVoid
from ...types import Response


def _get_kwargs(
    *,
    json_body: DqoCloudUserModel,
) -> Dict[str, Any]:
    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": "api/users",
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
    json_body: DqoCloudUserModel,
) -> Response[MonoVoid]:
    """createUser

     Creates (adds) a new user to a multi-user account.

    Args:
        json_body (DqoCloudUserModel): DQOps Cloud user model - identifies a user in a multi-user
            DQOps deployment.

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
    json_body: DqoCloudUserModel,
) -> Optional[MonoVoid]:
    """createUser

     Creates (adds) a new user to a multi-user account.

    Args:
        json_body (DqoCloudUserModel): DQOps Cloud user model - identifies a user in a multi-user
            DQOps deployment.

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
    json_body: DqoCloudUserModel,
) -> Response[MonoVoid]:
    """createUser

     Creates (adds) a new user to a multi-user account.

    Args:
        json_body (DqoCloudUserModel): DQOps Cloud user model - identifies a user in a multi-user
            DQOps deployment.

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
    json_body: DqoCloudUserModel,
) -> Optional[MonoVoid]:
    """createUser

     Creates (adds) a new user to a multi-user account.

    Args:
        json_body (DqoCloudUserModel): DQOps Cloud user model - identifies a user in a multi-user
            DQOps deployment.

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
