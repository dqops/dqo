from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_container_model import CheckContainerModel
from ...types import Response


def _get_kwargs() -> Dict[str, Any]:
    pass

    return {
        "method": "get",
        "url": "api/defaults/defaultchecks/dataobservability/monitoring/monthly/table",
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CheckContainerModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckContainerModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[CheckContainerModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerModel]:
    """getDefaultDataObservabilityMonthlyMonitoringTableChecks

     Returns UI model to show and edit the default configuration of the monthly monitoring (Data
    Observability end of month scores) checks that are configured for all imported tables on a table
    level.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerModel]
    """

    kwargs = _get_kwargs()

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerModel]:
    """getDefaultDataObservabilityMonthlyMonitoringTableChecks

     Returns UI model to show and edit the default configuration of the monthly monitoring (Data
    Observability end of month scores) checks that are configured for all imported tables on a table
    level.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerModel
    """

    return sync_detailed(
        client=client,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
) -> Response[CheckContainerModel]:
    """getDefaultDataObservabilityMonthlyMonitoringTableChecks

     Returns UI model to show and edit the default configuration of the monthly monitoring (Data
    Observability end of month scores) checks that are configured for all imported tables on a table
    level.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckContainerModel]
    """

    kwargs = _get_kwargs()

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
) -> Optional[CheckContainerModel]:
    """getDefaultDataObservabilityMonthlyMonitoringTableChecks

     Returns UI model to show and edit the default configuration of the monthly monitoring (Data
    Observability end of month scores) checks that are configured for all imported tables on a table
    level.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckContainerModel
    """

    return (
        await asyncio_detailed(
            client=client,
        )
    ).parsed
