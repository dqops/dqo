from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...types import Response


def _get_kwargs(
    connection_name: str,
    filtered_notification_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "delete",
        "url": "api/connections/{connectionName}/filterednotifications/{filteredNotificationName}".format(
            connectionName=connection_name,
            filteredNotificationName=filtered_notification_name,
        ),
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
    connection_name: str,
    filtered_notification_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """deleteConnectionFilteredNotificationConfiguration

     Deletes a filtered notification configuration from a connection

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        filtered_notification_name=filtered_notification_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    filtered_notification_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[Any]:
    """deleteConnectionFilteredNotificationConfiguration

     Deletes a filtered notification configuration from a connection

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        filtered_notification_name=filtered_notification_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
