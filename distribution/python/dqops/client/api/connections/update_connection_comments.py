from http import HTTPStatus
from typing import Any, Dict, List, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.comment_spec import CommentSpec
from ...models.mono_void import MonoVoid
from ...types import Response


def _get_kwargs(
    connection_name: str,
    *,
    client: AuthenticatedClient,
    json_body: List["CommentSpec"],
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/comments".format(
        client.base_url, connectionName=connection_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    json_json_body = []
    for json_body_item_data in json_body:
        json_body_item = json_body_item_data.to_dict()

        json_json_body.append(json_body_item)

    return {
        "method": "put",
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
    connection_name: str,
    *,
    client: AuthenticatedClient,
    json_body: List["CommentSpec"],
) -> Response[MonoVoid]:
    """updateConnectionComments

     Updates (replaces) the list of comments of a connection

    Args:
        connection_name (str):
        json_body (List['CommentSpec']):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
        json_body=json_body,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    *,
    client: AuthenticatedClient,
    json_body: List["CommentSpec"],
) -> Optional[MonoVoid]:
    """updateConnectionComments

     Updates (replaces) the list of comments of a connection

    Args:
        connection_name (str):
        json_body (List['CommentSpec']):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return sync_detailed(
        connection_name=connection_name,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    *,
    client: AuthenticatedClient,
    json_body: List["CommentSpec"],
) -> Response[MonoVoid]:
    """updateConnectionComments

     Updates (replaces) the list of comments of a connection

    Args:
        connection_name (str):
        json_body (List['CommentSpec']):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    *,
    client: AuthenticatedClient,
    json_body: List["CommentSpec"],
) -> Optional[MonoVoid]:
    """updateConnectionComments

     Updates (replaces) the list of comments of a connection

    Args:
        connection_name (str):
        json_body (List['CommentSpec']):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoVoid
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            client=client,
            json_body=json_body,
        )
    ).parsed
