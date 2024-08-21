from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.filtered_notification_model import FilteredNotificationModel
from ...types import Response


def _get_kwargs(
    connection_name: str,
    filtered_notification_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/filterednotifications/{filteredNotificationName}".format(
            connectionName=connection_name,
            filteredNotificationName=filtered_notification_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[FilteredNotificationModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = FilteredNotificationModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[FilteredNotificationModel]:
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
) -> Response[FilteredNotificationModel]:
    """getConnectionFilteredNotificationConfiguration

     Returns a model of the filtered notification configuration

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[FilteredNotificationModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        filtered_notification_name=filtered_notification_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    filtered_notification_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[FilteredNotificationModel]:
    """getConnectionFilteredNotificationConfiguration

     Returns a model of the filtered notification configuration

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        FilteredNotificationModel
    """

    return sync_detailed(
        connection_name=connection_name,
        filtered_notification_name=filtered_notification_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    filtered_notification_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[FilteredNotificationModel]:
    """getConnectionFilteredNotificationConfiguration

     Returns a model of the filtered notification configuration

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[FilteredNotificationModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        filtered_notification_name=filtered_notification_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    filtered_notification_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[FilteredNotificationModel]:
    """getConnectionFilteredNotificationConfiguration

     Returns a model of the filtered notification configuration

    Args:
        connection_name (str):
        filtered_notification_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        FilteredNotificationModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            filtered_notification_name=filtered_notification_name,
            client=client,
        )
    ).parsed
