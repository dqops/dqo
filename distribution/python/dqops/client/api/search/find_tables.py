from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_type import CheckType
from ...models.table_list_model import TableListModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    *,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
    label: Union[Unset, None, List[str]] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["connection"] = connection

    params["schema"] = schema

    params["table"] = table

    json_label: Union[Unset, None, List[str]] = UNSET
    if not isinstance(label, Unset):
        if label is None:
            json_label = None
        else:
            json_label = label

    params["label"] = json_label

    params["page"] = page

    params["limit"] = limit

    json_check_type: Union[Unset, None, str] = UNSET
    if not isinstance(check_type, Unset):
        json_check_type = check_type.value if check_type else None

    params["checkType"] = json_check_type

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/search/tables",
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["TableListModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = TableListModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["TableListModel"]]:
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
    label: Union[Unset, None, List[str]] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[List["TableListModel"]]:
    """findTables

     Finds tables in any data source and schema

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):
        label (Union[Unset, None, List[str]]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['TableListModel']]
    """

    kwargs = _get_kwargs(
        connection=connection,
        schema=schema,
        table=table,
        label=label,
        page=page,
        limit=limit,
        check_type=check_type,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    *,
    client: AuthenticatedClient,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
    label: Union[Unset, None, List[str]] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[List["TableListModel"]]:
    """findTables

     Finds tables in any data source and schema

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):
        label (Union[Unset, None, List[str]]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['TableListModel']
    """

    return sync_detailed(
        client=client,
        connection=connection,
        schema=schema,
        table=table,
        label=label,
        page=page,
        limit=limit,
        check_type=check_type,
    ).parsed


async def asyncio_detailed(
    *,
    client: AuthenticatedClient,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
    label: Union[Unset, None, List[str]] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[List["TableListModel"]]:
    """findTables

     Finds tables in any data source and schema

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):
        label (Union[Unset, None, List[str]]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['TableListModel']]
    """

    kwargs = _get_kwargs(
        connection=connection,
        schema=schema,
        table=table,
        label=label,
        page=page,
        limit=limit,
        check_type=check_type,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    *,
    client: AuthenticatedClient,
    connection: Union[Unset, None, str] = UNSET,
    schema: Union[Unset, None, str] = UNSET,
    table: Union[Unset, None, str] = UNSET,
    label: Union[Unset, None, List[str]] = UNSET,
    page: Union[Unset, None, int] = UNSET,
    limit: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[List["TableListModel"]]:
    """findTables

     Finds tables in any data source and schema

    Args:
        connection (Union[Unset, None, str]):
        schema (Union[Unset, None, str]):
        table (Union[Unset, None, str]):
        label (Union[Unset, None, List[str]]):
        page (Union[Unset, None, int]):
        limit (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['TableListModel']
    """

    return (
        await asyncio_detailed(
            client=client,
            connection=connection,
            schema=schema,
            table=table,
            label=label,
            page=page,
            limit=limit,
            check_type=check_type,
        )
    ).parsed
