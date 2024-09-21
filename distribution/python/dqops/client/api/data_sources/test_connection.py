from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.connection_model import ConnectionModel
from ...models.connection_test_model import ConnectionTestModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    json_body: ConnectionModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["verifyNameUniqueness"] = verify_name_uniqueness

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": "api/datasource/testconnection",
        "json": json_json_body,
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[ConnectionTestModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = ConnectionTestModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[ConnectionTestModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    json_body: ConnectionModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Response[ConnectionTestModel]:
    """testConnection

     Checks if the given remote connection can be opened and if the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionModel): Connection model with a subset of parameters, excluding all
            nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionTestModel]
    """

    kwargs = _get_kwargs(
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    json_body: ConnectionModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Optional[ConnectionTestModel]:
    """testConnection

     Checks if the given remote connection can be opened and if the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionModel): Connection model with a subset of parameters, excluding all
            nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionTestModel
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    json_body: ConnectionModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Response[ConnectionTestModel]:
    """testConnection

     Checks if the given remote connection can be opened and if the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionModel): Connection model with a subset of parameters, excluding all
            nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionTestModel]
    """

    kwargs = _get_kwargs(
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    json_body: ConnectionModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Optional[ConnectionTestModel]:
    """testConnection

     Checks if the given remote connection can be opened and if the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionModel): Connection model with a subset of parameters, excluding all
            nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionTestModel
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
            verify_name_uniqueness=verify_name_uniqueness,
        )
    ).parsed
