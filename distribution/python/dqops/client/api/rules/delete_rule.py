from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...types import Response


def _get_kwargs(
    full_rule_name: str,
) -> Dict[str, Any]:
    pass

    return {
        "method": "delete",
        "url": "api/rules/{fullRuleName}".format(
            fullRuleName=full_rule_name,
        ),
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
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[MonoVoid]:
    """deleteRule

     Deletes a custom rule definition

    Args:
        full_rule_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        full_rule_name=full_rule_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[MonoVoid]:
    """deleteRule

     Deletes a custom rule definition

    Args:
        full_rule_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return sync_detailed(
        full_rule_name=full_rule_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[MonoVoid]:
    """deleteRule

     Deletes a custom rule definition

    Args:
        full_rule_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        full_rule_name=full_rule_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[MonoVoid]:
    """deleteRule

     Deletes a custom rule definition

    Args:
        full_rule_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return (
        await asyncio_detailed(
            full_rule_name=full_rule_name,
            client=client,
        )
    ).parsed
