from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.sensor_model import SensorModel
from ...types import Response


def _get_kwargs(
    full_sensor_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/sensors/{fullSensorName}".format(
            fullSensorName=full_sensor_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[SensorModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = SensorModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
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
    client: AuthenticatedClient,
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
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
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
    client: AuthenticatedClient,
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
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_sensor_name: str,
    *,
    client: AuthenticatedClient,
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
