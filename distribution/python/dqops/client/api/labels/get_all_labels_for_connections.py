from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.label_model import LabelModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    prefix: Union[Unset, None, str] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:
    pass

    params: Dict[str, Any] = {}
    params["page"] = page

    params["limit"] = limit

    params["prefix"] = prefix

    params["filter"] = filter_

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/labels/connections",
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["LabelModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = LabelModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["LabelModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    *,
    client: AuthenticatedClient,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    prefix: Union[Unset, None, str] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
) -> Response[List["LabelModel"]]:
    """getAllLabelsForConnections

     Returns a list of all labels applied to the connections to data sources, including the count of
    assignments to these data assets.

    Args:
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        prefix (Union[Unset, None, str]):
        filter_ (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['LabelModel']]
    """

    kwargs = _get_kwargs(
        page=page,
        limit=limit,
        prefix=prefix,
        filter_=filter_,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    prefix: Union[Unset, None, str] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
) -> Optional[List["LabelModel"]]:
    """getAllLabelsForConnections

     Returns a list of all labels applied to the connections to data sources, including the count of
    assignments to these data assets.

    Args:
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        prefix (Union[Unset, None, str]):
        filter_ (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['LabelModel']
    """

    return sync_detailed(
        client=client,
        page=page,
        limit=limit,
        prefix=prefix,
        filter_=filter_,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    prefix: Union[Unset, None, str] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
) -> Response[List["LabelModel"]]:
    """getAllLabelsForConnections

     Returns a list of all labels applied to the connections to data sources, including the count of
    assignments to these data assets.

    Args:
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        prefix (Union[Unset, None, str]):
        filter_ (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['LabelModel']]
    """

    kwargs = _get_kwargs(
        page=page,
        limit=limit,
        prefix=prefix,
        filter_=filter_,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    prefix: Union[Unset, None, str] = UNSET,
    filter_: Union[Unset, None, str] = UNSET,
) -> Optional[List["LabelModel"]]:
    """getAllLabelsForConnections

     Returns a list of all labels applied to the connections to data sources, including the count of
    assignments to these data assets.

    Args:
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        prefix (Union[Unset, None, str]):
        filter_ (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['LabelModel']
    """

    return (
        await asyncio_detailed(
            client=client,
            page=page,
            limit=limit,
            prefix=prefix,
            filter_=filter_,
        )
    ).parsed
