from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.authenticated_dashboard_model import AuthenticatedDashboardModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    folder1: str,
    folder2: str,
    dashboard_name: str,
    *,
    client: AuthenticatedClient,
    window_location_origin: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/dashboards/{folder1}/{folder2}/{dashboardName}".format(
        client.base_url, folder1=folder1, folder2=folder2, dashboardName=dashboard_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["windowLocationOrigin"] = window_location_origin

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[AuthenticatedDashboardModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = AuthenticatedDashboardModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[AuthenticatedDashboardModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    folder1: str,
    folder2: str,
    dashboard_name: str,
    *,
    client: AuthenticatedClient,
    window_location_origin: Union[Unset, None, str] = UNSET,
) -> Response[AuthenticatedDashboardModel]:
    """getDashboardLevel2

     Returns a single dashboard in the tree of folders with a temporary authenticated url

    Args:
        folder1 (str):
        folder2 (str):
        dashboard_name (str):
        window_location_origin (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[AuthenticatedDashboardModel]
    """

    kwargs = _get_kwargs(
        folder1=folder1,
        folder2=folder2,
        dashboard_name=dashboard_name,
        client=client,
        window_location_origin=window_location_origin,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    folder1: str,
    folder2: str,
    dashboard_name: str,
    *,
    client: AuthenticatedClient,
    window_location_origin: Union[Unset, None, str] = UNSET,
) -> Optional[AuthenticatedDashboardModel]:
    """getDashboardLevel2

     Returns a single dashboard in the tree of folders with a temporary authenticated url

    Args:
        folder1 (str):
        folder2 (str):
        dashboard_name (str):
        window_location_origin (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        AuthenticatedDashboardModel
    """

    return sync_detailed(
        folder1=folder1,
        folder2=folder2,
        dashboard_name=dashboard_name,
        client=client,
        window_location_origin=window_location_origin,
    ).parsed


async def asyncio_detailed(
    folder1: str,
    folder2: str,
    dashboard_name: str,
    *,
    client: AuthenticatedClient,
    window_location_origin: Union[Unset, None, str] = UNSET,
) -> Response[AuthenticatedDashboardModel]:
    """getDashboardLevel2

     Returns a single dashboard in the tree of folders with a temporary authenticated url

    Args:
        folder1 (str):
        folder2 (str):
        dashboard_name (str):
        window_location_origin (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[AuthenticatedDashboardModel]
    """

    kwargs = _get_kwargs(
        folder1=folder1,
        folder2=folder2,
        dashboard_name=dashboard_name,
        client=client,
        window_location_origin=window_location_origin,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    folder1: str,
    folder2: str,
    dashboard_name: str,
    *,
    client: AuthenticatedClient,
    window_location_origin: Union[Unset, None, str] = UNSET,
) -> Optional[AuthenticatedDashboardModel]:
    """getDashboardLevel2

     Returns a single dashboard in the tree of folders with a temporary authenticated url

    Args:
        folder1 (str):
        folder2 (str):
        dashboard_name (str):
        window_location_origin (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        AuthenticatedDashboardModel
    """

    return (
        await asyncio_detailed(
            folder1=folder1,
            folder2=folder2,
            dashboard_name=dashboard_name,
            client=client,
            window_location_origin=window_location_origin,
        )
    ).parsed
