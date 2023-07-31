from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import Client
from ...models.connection_basic_model import ConnectionBasicModel
from ...models.connection_remote_model import ConnectionRemoteModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    client: Client,
    json_body: ConnectionBasicModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/datasource/testconnection".format(client.base_url)

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["verifyNameUniqueness"] = verify_name_uniqueness

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[ConnectionRemoteModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = ConnectionRemoteModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[ConnectionRemoteModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: Client,
    json_body: ConnectionBasicModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Response[ConnectionRemoteModel]:
    """testConnection

     Checks if the given remote connection could be opened and the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionBasicModel): Basic connection model with a subset of parameters,
            excluding all nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionRemoteModel]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: Client,
    json_body: ConnectionBasicModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Optional[ConnectionRemoteModel]:
    """testConnection

     Checks if the given remote connection could be opened and the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionBasicModel): Basic connection model with a subset of parameters,
            excluding all nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionRemoteModel
    """

    return sync_detailed(
        client=client,
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    ).parsed


async def asyncio_detailed(
    *,
    client: Client,
    json_body: ConnectionBasicModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Response[ConnectionRemoteModel]:
    """testConnection

     Checks if the given remote connection could be opened and the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionBasicModel): Basic connection model with a subset of parameters,
            excluding all nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[ConnectionRemoteModel]
    """

    kwargs = _get_kwargs(
        client=client,
        json_body=json_body,
        verify_name_uniqueness=verify_name_uniqueness,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: Client,
    json_body: ConnectionBasicModel,
    verify_name_uniqueness: Union[Unset, None, bool] = UNSET,
) -> Optional[ConnectionRemoteModel]:
    """testConnection

     Checks if the given remote connection could be opened and the credentials are valid

    Args:
        verify_name_uniqueness (Union[Unset, None, bool]):
        json_body (ConnectionBasicModel): Basic connection model with a subset of parameters,
            excluding all nested objects.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        ConnectionRemoteModel
    """

    return (
        await asyncio_detailed(
            client=client,
            json_body=json_body,
            verify_name_uniqueness=verify_name_uniqueness,
        )
    ).parsed
