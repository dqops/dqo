from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.check_mining_proposal_model import CheckMiningProposalModel
from ...models.check_time_scale import CheckTimeScale
from ...types import Response


def _get_kwargs(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    json_body: CheckMiningProposalModel,
) -> Dict[str, Any]:

    pass

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": "api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/applyproposal".format(
            connectionName=connection_name,
            schemaName=schema_name,
            tableName=table_name,
            timeScale=time_scale,
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
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckMiningProposalModel,
) -> Response[Any]:
    """applyProposedPartitionedChecks

     Applies the proposed configuration of data quality partitioned checks on a table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckMiningProposalModel): Model that has a proposed configuration of checks on
            a table and its columns generated by a data quality check mining service.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        json_body=json_body,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


async def asyncio_detailed(
    connection_name: str,
    schema_name: str,
    table_name: str,
    time_scale: CheckTimeScale,
    *,
    client: AuthenticatedClient,
    json_body: CheckMiningProposalModel,
) -> Response[Any]:
    """applyProposedPartitionedChecks

     Applies the proposed configuration of data quality partitioned checks on a table.

    Args:
        connection_name (str):
        schema_name (str):
        table_name (str):
        time_scale (CheckTimeScale):
        json_body (CheckMiningProposalModel): Model that has a proposed configuration of checks on
            a table and its columns generated by a data quality check mining service.

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[Any]
    """

    kwargs = _get_kwargs(
        connection_name=connection_name,
        schema_name=schema_name,
        table_name=table_name,
        time_scale=time_scale,
        json_body=json_body,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)
