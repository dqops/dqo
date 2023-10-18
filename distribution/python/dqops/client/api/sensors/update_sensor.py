from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_void import MonoVoid
from ...models.sensor_model import SensorModel
from ...types import Response


def _get_kwargs(
    full_sensor_name: str,
    *,
    json_body: SensorModel,
) -> Dict[str, Any]:
    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/sensors/{fullSensorName}".format(
            fullSensorName=full_sensor_name,
        ),
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
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Response[MonoVoid]:
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
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Optional[MonoVoid]:
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
        MonoVoid
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
) -> Response[MonoVoid]:
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
        Response[MonoVoid]
    """

    kwargs = _get_kwargs(
        full_sensor_name=full_sensor_name,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
    json_body: SensorModel,
) -> Optional[MonoVoid]:
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
        MonoVoid
    """

    return (
        await asyncio_detailed(
            full_sensor_name=full_sensor_name,
            client=client,
            json_body=json_body,
        )
    ).parsed
