from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["connection"] = connection

    params["schema"] = schema

    params["table"] = table

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "put",
        "url": "api/datacatalog/sync/pushdatahealth",
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.NO_CONTENT:
        return None
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[Any]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
) -> Response[Any]:
    """pushDataQualityStatusToDataCatalog

     Pushes the data quality status of tables matching the search filters to the data catalog.

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection=connection,
        schema=schema,
        table=table,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
) -> Response[Any]:
    """pushDataQualityStatusToDataCatalog

     Pushes the data quality status of tables matching the search filters to the data catalog.

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection=connection,
        schema=schema,
        table=table,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
