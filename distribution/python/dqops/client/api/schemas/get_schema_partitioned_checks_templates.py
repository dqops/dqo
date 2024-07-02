from http import HTTPStatus
from typing import Any, Dict, List, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_target import CheckTarget
from ...models.check_template import CheckTemplate
from ...models.check_time_scale import CheckTimeScale
from ...types import UNSET, Response, Unset


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
) -> Dict[str, Any]:

    pass

    params: Dict[str, Any] = {}
    json_check_target: Union[Unset, None, str] = UNSET
    if not isinstance(check_target, Unset):
        json_check_target = check_target.value if check_target else None

    params["checkTarget"] = json_check_target

    params["checkCategory"] = check_category

    params["checkName"] = check_name

    params = {k: v for k, v in params.items() if v is not UNSET and v is not None}

    return {
        "method": "get",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/bulkenable/partitioned/{timeScale}".format(
            connectionName=connection_name,
            schemaName=schema_name,
            timeScale=time_scale,
        ),
        "params": params,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[List["CheckTemplate"]]:
    if response.status_code == HTTPStatus.OK:
        response_200 = []
        _response_200 = response.json()
        for response_200_item_data in _response_200:
            response_200_item = CheckTemplate.from_dict(response_200_item_data)

            response_200.append(response_200_item)

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[List["CheckTemplate"]]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
) -> Response[List["CheckTemplate"]]:
    """getSchemaPartitionedChecksTemplates

     Return available data quality checks on a requested schema.

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckTemplate']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
) -> Optional[List["CheckTemplate"]]:
    """getSchemaPartitionedChecksTemplates

     Return available data quality checks on a requested schema.

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckTemplate']
    """

    return sync_detailed(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        client=client,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
    ).parsed


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
) -> Response[List["CheckTemplate"]]:
    """getSchemaPartitionedChecksTemplates

     Return available data quality checks on a requested schema.

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[List['CheckTemplate']]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        time_scale=time_scale,
        check_target=check_target,
        check_category=check_category,
        check_name=check_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    connection_name: str,
    schema_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    check_target: Union[Unset, None, CheckTarget] = UNSET,
    check_category: Union[Unset, None, str] = UNSET,
    check_name: Union[Unset, None, str] = UNSET,
) -> Optional[List["CheckTemplate"]]:
    """getSchemaPartitionedChecksTemplates

     Return available data quality checks on a requested schema.

    Args:
        connection_name (str):
        schema_name (str):
        time_scale (CheckTimeScale):
        check_target (Union[Unset, None, CheckTarget]):
        check_category (Union[Unset, None, str]):
        check_name (Union[Unset, None, str]):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        List['CheckTemplate']
    """

    return (
        await asyncio_detailed(
            connection_name=connection_name,
            schema_name=schema_name,
            time_scale=time_scale,
            client=client,
            check_target=check_target,
            check_category=check_category,
            check_name=check_name,
        )
    ).parsed
