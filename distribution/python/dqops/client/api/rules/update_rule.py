from http import HTTPStatus
from typing import Any, Dict, Optional

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.mono_object import MonoObject
from ...models.rule_model import RuleModel
from ...types import Response


def _get_kwargs(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
    json_body: RuleModel,
) -> Dict[str, Any]:
    url = "{}api/rules/{fullRuleName}".format(
        client.base_url, fullRuleName=full_rule_name
    )

    headers: Dict[str, str] = client.get_headers()
    cookies: Dict[str, Any] = client.get_cookies()

    json_json_body = json_body.to_dict()

    return {
        "method": "put",
        "url": url,
        "headers": headers,
        "cookies": cookies,
        "timeout": client.get_timeout(),
        "follow_redirects": client.follow_redirects,
        "json": json_json_body,
    }


def _parse_response(
    *, client: Client, response: httpx.Response
) -> Optional[MonoObject]:
    if response.status_code == HTTPStatus.OK:
        response_200 = MonoObject.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Client, response: httpx.Response
) -> Response[MonoObject]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
    json_body: RuleModel,
) -> Response[MonoObject]:
    """updateRule

     Updates an existing rule, making a custom rule definition if it is not present

    Args:
        full_rule_name (str):
        json_body (RuleModel): Rule model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        full_rule_name=full_rule_name,
        client=client,
        json_body=json_body,
    )

    response = httpx.request(
        verify=client.verify_ssl,
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
    json_body: RuleModel,
) -> Optional[MonoObject]:
    """updateRule

     Updates an existing rule, making a custom rule definition if it is not present

    Args:
        full_rule_name (str):
        json_body (RuleModel): Rule model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return sync_detailed(
        full_rule_name=full_rule_name,
        client=client,
        json_body=json_body,
    ).parsed


async def asyncio_detailed(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
    json_body: RuleModel,
) -> Response[MonoObject]:
    """updateRule

     Updates an existing rule, making a custom rule definition if it is not present

    Args:
        full_rule_name (str):
        json_body (RuleModel): Rule model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[MonoObject]
    """

    kwargs = _get_kwargs(
        full_rule_name=full_rule_name,
        client=client,
        json_body=json_body,
    )

    async with httpx.AsyncClient(verify=client.verify_ssl) as _client:
        response = await _client.request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    full_rule_name: str,
    *,
    client: AuthenticatedClient,
    json_body: RuleModel,
) -> Optional[MonoObject]:
    """updateRule

     Updates an existing rule, making a custom rule definition if it is not present

    Args:
        full_rule_name (str):
        json_body (RuleModel): Rule model

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        MonoObject
    """

    return (
        await asyncio_detailed(
            full_rule_name=full_rule_name,
            client=client,
            json_body=json_body,
        )
    ).parsed
