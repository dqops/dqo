from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_type import CheckType
from ...models.column_list_model import ColumnListModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    data_quality_status: Union[Unset, None, bool] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    params["dataQualityStatus"] = data_quality_status

    json_check_type: Union[Unset, None, str] = UNSET
    if not isinstance(check_type, Unset):
        json_check_type = check_type.value if check_type else None

    params["checkType"] = json_check_type

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["ColumnListModel"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = ColumnListModel.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["ColumnListModel"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_quality_status: Union[Unset, None, bool] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[List["ColumnListModel"]]:
    """getColumns

     Returns a list of columns inside a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_quality_status (Union[Unset, None, bool]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['ColumnListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        data_quality_status=data_quality_status,
        check_type=check_type,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_quality_status: Union[Unset, None, bool] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[List["ColumnListModel"]]:
    """getColumns

     Returns a list of columns inside a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_quality_status (Union[Unset, None, bool]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['ColumnListModel']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        data_quality_status=data_quality_status,
        check_type=check_type,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_quality_status: Union[Unset, None, bool] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Response[List["ColumnListModel"]]:
    """getColumns

     Returns a list of columns inside a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_quality_status (Union[Unset, None, bool]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['ColumnListModel']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        data_quality_status=data_quality_status,
        check_type=check_type,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    data_quality_status: Union[Unset, None, bool] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
) -> Optional[List["ColumnListModel"]]:
    """getColumns

     Returns a list of columns inside a table

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        data_quality_status (Union[Unset, None, bool]):
        check_type (Union[Unset, None, CheckType]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['ColumnListModel']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            data_quality_status=data_quality_status,
            check_type=check_type,
        )
    ).parsed
