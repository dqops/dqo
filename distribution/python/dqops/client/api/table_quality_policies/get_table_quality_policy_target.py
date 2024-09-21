from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.table_quality_policy_list_model import TableQualityPolicyListModel
from ...types import Response


def _get_kwargs(
    pattern_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/policies/checks/table/{patternName}/target".format(
            patternName=pattern_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[TableQualityPolicyListModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = TableQualityPolicyListModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[TableQualityPolicyListModel]:
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
) -> Response[TableQualityPolicyListModel]:
    """getTableQualityPolicyTarget

     Returns a default checks pattern definition (a data quality policy)

    Args:
        pattern_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableQualityPolicyListModel]
    """

    kwargs = _get_kwargs(
        pattern_name=pattern_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    pattern_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[TableQualityPolicyListModel]:
    """getTableQualityPolicyTarget

     Returns a default checks pattern definition (a data quality policy)

    Args:
        pattern_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableQualityPolicyListModel
    """

    return sync_detailed(
        pattern_name=pattern_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    pattern_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[TableQualityPolicyListModel]:
    """getTableQualityPolicyTarget

     Returns a default checks pattern definition (a data quality policy)

    Args:
        pattern_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[TableQualityPolicyListModel]
    """

    kwargs = _get_kwargs(
        pattern_name=pattern_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    pattern_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[TableQualityPolicyListModel]:
    """getTableQualityPolicyTarget

     Returns a default checks pattern definition (a data quality policy)

    Args:
        pattern_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        TableQualityPolicyListModel
    """

    return (
        await asyncio_detailed(
            pattern_name=pattern_name,
            client=client,
        )
    ).parsed
