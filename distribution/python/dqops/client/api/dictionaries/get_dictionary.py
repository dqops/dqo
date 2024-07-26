from http import HTTPStatus
from typing import Any, Dict, Optional, Union

import httpx

from ... import errors
from ...client import AuthenticatedClient, Client
from ...models.data_dictionary_model import DataDictionaryModel
from ...types import Response


def _get_kwargs(
    dictionary_name: str,
) -> Dict[str, Any]:

    pass

    return {
        "method": "get",
        "url": "api/dictionaries/{dictionaryName}".format(
            dictionaryName=dictionary_name,
        ),
    }


def _parse_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Optional[DataDictionaryModel]:
    if response.status_code == HTTPStatus.OK:
        response_200 = DataDictionaryModel.from_dict(response.json())

        return response_200
    if client.raise_on_unexpected_status:
        raise errors.UnexpectedStatus(response.status_code, response.content)
    else:
        return None


def _build_response(
    *, client: Union[AuthenticatedClient, Client], response: httpx.Response
) -> Response[DataDictionaryModel]:
    return Response(
        status_code=HTTPStatus(response.status_code),
        content=response.content,
        headers=response.headers,
        parsed=_parse_response(client=client, response=response),
    )


def sync_detailed(
    dictionary_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[DataDictionaryModel]:
    """getDictionary

     Returns the content of a data dictionary CSV file as a model object

    Args:
        dictionary_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DataDictionaryModel]
    """

    kwargs = _get_kwargs(
        dictionary_name=dictionary_name,
    )

    response = client.get_httpx_client().request(
        **kwargs,
    )

    return _build_response(client=client, response=response)


def sync(
    dictionary_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DataDictionaryModel]:
    """getDictionary

     Returns the content of a data dictionary CSV file as a model object

    Args:
        dictionary_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DataDictionaryModel
    """

    return sync_detailed(
        dictionary_name=dictionary_name,
        client=client,
    ).parsed


async def asyncio_detailed(
    dictionary_name: str,
    *,
    client: AuthenticatedClient,
) -> Response[DataDictionaryModel]:
    """getDictionary

     Returns the content of a data dictionary CSV file as a model object

    Args:
        dictionary_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        Response[DataDictionaryModel]
    """

    kwargs = _get_kwargs(
        dictionary_name=dictionary_name,
    )

    response = await client.get_async_httpx_client().request(**kwargs)

    return _build_response(client=client, response=response)


async def asyncio(
    dictionary_name: str,
    *,
    client: AuthenticatedClient,
) -> Optional[DataDictionaryModel]:
    """getDictionary

     Returns the content of a data dictionary CSV file as a model object

    Args:
        dictionary_name (str):

    Raises:
        errors.UnexpectedStatus: If the server returns an undocumented status code and Client.raise_on_unexpected_status is True.
        httpx.TimeoutException: If the request takes longer than Client.timeout.

    Returns:
        DataDictionaryModel
    """

    return (
        await asyncio_detailed(
            dictionary_name=dictionary_name,
            client=client,
        )
    ).parsed
