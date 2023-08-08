from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import Client
from ...models.sensor_model import SensorModel
from ...types import Response


def _get_kwargs(
    full_sensor_name: str,
    *,
    client: Client,
) -> Dict[str, Any]:
    url = "{}api/sensors/{fullSensorName}".format(
        client.base_url, fullSensorName=full_sensor_name
    )

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
) -> Optional[SensorModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = SensorModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[SensorModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    full_sensor_name: str,
    *,
    client: Client,
) -> Response[SensorModel]:
    """getSensor

     Returns a sensor model

    Args:
        full_sensor_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[SensorModel]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        client=client,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_sensor_name: str,
    *,
    client: Client,
) -> Optional[SensorModel]:
    """getSensor

     Returns a sensor model

    Args:
        full_sensor_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        SensorModel
    """

    return sync_detailed(
        full_sensor_name=full_sensor_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    full_sensor_name: str,
    *,
    client: Client,
) -> Response[SensorModel]:
    """getSensor

     Returns a sensor model

    Args:
        full_sensor_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[SensorModel]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        client=client,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_sensor_name: str,
    *,
    client: Client,
) -> Optional[SensorModel]:
    """getSensor

     Returns a sensor model

    Args:
        full_sensor_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        SensorModel
    """

    return (
        await asyncio_detailed(
            full_sensor_name=full_sensor_name,
            client=client,
        )
    ).parsed
