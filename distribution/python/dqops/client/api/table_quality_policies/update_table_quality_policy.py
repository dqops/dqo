from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.table_quality_policy_model import TableQualityPolicyModel
from ...types import Response


def _get_kwargs(
    pattern_name: str,
    *,
    json_body: TableQualityPolicyModel,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/policies/checks/table/{patternName}".format(
            patternName=pattern_name,
        ),
        "json": json_json_body,
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
    pattern_name: str,
    *,
    client: AuthenticatedClient,
    json_body: TableQualityPolicyModel,
) -> Response[Any]:
    """updateTableQualityPolicy

     Updates an default table-level checks pattern (data quality policy) by saving a full specification
    object

    Args:
        pattern_name (str):
        json_body (TableQualityPolicyModel): Default table-level checks pattern (data quality
            policy) model

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
    json_body: TableQualityPolicyModel,
) -> Response[Any]:
    """updateTableQualityPolicy

     Updates an default table-level checks pattern (data quality policy) by saving a full specification
    object

    Args:
        pattern_name (str):
        json_body (TableQualityPolicyModel): Default table-level checks pattern (data quality
            policy) model

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