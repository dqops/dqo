from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_definition_model import CheckDefinitionModel
from ...types import Response


def _get_kwargs(
    full_check_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/checks/{fullCheckName}".format(
            fullCheckName=full_check_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[CheckDefinitionModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = CheckDefinitionModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[CheckDefinitionModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    full_check_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[CheckDefinitionModel]:
    """getCheck

     Returns a check definition

    Args:
        full_check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckDefinitionModel]
    """

    kwargs = _get_kwargs(
        full_check_name=full_check_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_check_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckDefinitionModel]:
    """getCheck

     Returns a check definition

    Args:
        full_check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckDefinitionModel
    """

    return sync_detailed(
        full_check_name=full_check_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    full_check_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[CheckDefinitionModel]:
    """getCheck

     Returns a check definition

    Args:
        full_check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[CheckDefinitionModel]
    """

    kwargs = _get_kwargs(
        full_check_name=full_check_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_check_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[CheckDefinitionModel]:
    """getCheck

     Returns a check definition

    Args:
        full_check_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        CheckDefinitionModel
    """

    return (
        await asyncio_detailed(
            full_check_name=full_check_name,
            client=client,
        )
    ).parsed
