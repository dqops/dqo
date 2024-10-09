from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.local_data_domain_model import LocalDataDomainModel
from ...types import Response


def _get_kwargs(
    data_domain_display_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "post",
        "url": "api/domains/{dataDomainDisplayName}".format(
            dataDomainDisplayName=data_domain_display_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[LocalDataDomainModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = LocalDataDomainModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[LocalDataDomainModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    data_domain_display_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[LocalDataDomainModel]:
    """createDataDomain

     Creates a new data domain given a data domain display name.

    Args:
        data_domain_display_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[LocalDataDomainModel]
    """

    kwargs = _get_kwargs(
        data_domain_display_name=data_domain_display_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    data_domain_display_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[LocalDataDomainModel]:
    """createDataDomain

     Creates a new data domain given a data domain display name.

    Args:
        data_domain_display_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        LocalDataDomainModel
    """

    return sync_detailed(
        data_domain_display_name=data_domain_display_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    data_domain_display_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[LocalDataDomainModel]:
    """createDataDomain

     Creates a new data domain given a data domain display name.

    Args:
        data_domain_display_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[LocalDataDomainModel]
    """

    kwargs = _get_kwargs(
        data_domain_display_name=data_domain_display_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    data_domain_display_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[LocalDataDomainModel]:
    """createDataDomain

     Creates a new data domain given a data domain display name.

    Args:
        data_domain_display_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        LocalDataDomainModel
    """

    return (
        await asyncio_detailed(
            data_domain_display_name=data_domain_display_name,
            client=client,
        )
    ).parsed
