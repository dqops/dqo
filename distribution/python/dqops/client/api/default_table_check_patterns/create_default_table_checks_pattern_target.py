from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.default_table_checks_pattern_list_model import (
    DefaultTableChecksPatternListModel,
)
from ...types import Response


def _get_kwargs(
    pattern_name: str,
    *,
    json_body: DefaultTableChecksPatternListModel,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "post",
        "url": "api/default/checks/table/{patternName}/target".format(
            patternName=pattern_name,
        ),
        "json": json_json_body,
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[Any]:
    if response.status_code == HTTPStatus.CREATED:
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
    pattern_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DefaultTableChecksPatternListModel,
) -> Response[Any]:
    """createDefaultTableChecksPatternTarget

     Creates (adds) a new default table-level checks pattern configuration.

    Args:
        pattern_name (str):
        json_body (DefaultTableChecksPatternListModel): Default table-level checks pattern list
            model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        pattern_name=pattern_name,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    pattern_name: str,
    *,
    client: AuthenticatedClient,
    json_body: DefaultTableChecksPatternListModel,
) -> Response[Any]:
    """createDefaultTableChecksPatternTarget

     Creates (adds) a new default table-level checks pattern configuration.

    Args:
        pattern_name (str):
        json_body (DefaultTableChecksPatternListModel): Default table-level checks pattern list
            model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        pattern_name=pattern_name,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
