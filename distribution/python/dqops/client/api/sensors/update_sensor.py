from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_object import MonoObject
from ...models.sensor_model import SensorModel
from ...types import Response


def _get_kwargs(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Dict[str, Any]:
    url = "{}api/sensors/{fullSensorName}".format(
        client.base_url, fullSensorName=full_sensor_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[MonoObject]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoObject.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[MonoObject]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Response[MonoObject]:
    """updateSensor

     Updates an existing sensor, making a custom sensor definition if it is not present.
    Removes sensor if custom definition is same as Dqo Home sensor

    Args:
        full_sensor_name (str):
        json_body (SensorModel): Sensor model.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        client=client,
        json_body=json_body,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Optional[MonoObject]:
    """updateSensor

     Updates an existing sensor, making a custom sensor definition if it is not present.
    Removes sensor if custom definition is same as Dqo Home sensor

    Args:
        full_sensor_name (str):
        json_body (SensorModel): Sensor model.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return sync_detailed(
        full_sensor_name=full_sensor_name,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Response[MonoObject]:
    """updateSensor

     Updates an existing sensor, making a custom sensor definition if it is not present.
    Removes sensor if custom definition is same as Dqo Home sensor

    Args:
        full_sensor_name (str):
        json_body (SensorModel): Sensor model.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Optional[MonoObject]:
    """updateSensor

     Updates an existing sensor, making a custom sensor definition if it is not present.
    Removes sensor if custom definition is same as Dqo Home sensor

    Args:
        full_sensor_name (str):
        json_body (SensorModel): Sensor model.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return (
        await asyncio_detailed(
            full_sensor_name=full_sensor_name,
            client=client,
            json_body=json_body,
        )
    ).parsed
