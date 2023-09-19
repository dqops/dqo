from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_time_scale import CheckTimeScale
from ...models.check_type import CheckType
from ...models.table_data_quality_status_model import TableDataQualityStatusModel
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Dict[str, Any]:
    url = "{}api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status".format(
        client.base_url,
        connectionName=connection_name,
        schemaName=schema_name,
        tableName=table_name,
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    params: Dict[str, Any] = {}
    params["months"] = months

    json_check_type: Union[Unset, None, str] = UNSET
    if not isinstance(check_type, Unset):
        json_check_type = check_type.value if check_type else None

    params["checkType"] = json_check_type

    json_check_time_scale: Union[Unset, None, str] = UNSET
    if not isinstance(check_time_scale, Unset):
        json_check_time_scale = check_time_scale.value if check_time_scale else None

    params["checkTimeScale"] = json_check_time_scale

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "params": params,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[TableDataQualityStatusModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TableDataQualityStatusModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[TableDataQualityStatusModel]:
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
    months: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Response[TableDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableDataQualityStatusModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        months=months,
        check_type=check_type,
        check_time_scale=check_time_scale,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Optional[TableDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableDataQualityStatusModel
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        months=months,
        check_type=check_type,
        check_time_scale=check_time_scale,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Response[TableDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableDataQualityStatusModel]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        client=client,
        months=months,
        check_type=check_type,
        check_time_scale=check_time_scale,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    table_name: str,
    *,
    client: AuthenticatedClient,
    months: Union[Unset, None, int] = UNSET,
    check_type: Union[Unset, None, CheckType] = UNSET,
    check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
) -> Optional[TableDataQualityStatusModel]:
    """getTableDataQualityStatus

     Read the most recent results of executed data quality checks on the table and return the current
    table's data quality status - the number of failed data quality checks if the table has active data
    quality issues. Also returns the names of data quality checks that did not pass most recently. This
    operation verifies only the status of the most recently executed data quality checks. Previous data
    quality issues are not counted.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        months (Union[Unset, None, int]):
        check_type (Union[Unset, None, CheckType]):
        check_time_scale (Union[Unset, None, CheckTimeScale]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableDataQualityStatusModel
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            client=client,
            months=months,
            check_type=check_type,
            check_time_scale=check_time_scale,
        )
    ).parsed
