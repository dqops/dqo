from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.connection_remote_model_connection_status import (
    ConnectionRemoteModelConnectionStatus,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="ConnectionRemoteModel")


@attr.s(auto_attribs=True)
class ConnectionRemoteModel:
    """Connection status remote management

    Attributes:
        connection_status (Union[Unset, ConnectionRemoteModelConnectionStatus]): Connection status
        error_message (Union[Unset, str]): Error message
    """

    connection_status: Union[Unset, ConnectionRemoteModelConnectionStatus] = UNSET
    error_message: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_status: Union[Unset, str] = UNSET
        if not isinstance(self.connection_status, Unset):
            connection_status = self.connection_status.value

        error_message = self.error_message

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_status is not UNSET:
            field_dict["connectionStatus"] = connection_status
        if error_message is not UNSET:
            field_dict["errorMessage"] = error_message

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _connection_status = d.pop("connectionStatus", UNSET)
        connection_status: Union[Unset, ConnectionRemoteModelConnectionStatus]
        if isinstance(_connection_status, Unset):
            connection_status = UNSET
        else:
            connection_status = ConnectionRemoteModelConnectionStatus(
                _connection_status
            )

        error_message = d.pop("errorMessage", UNSET)

        connection_remote_model = cls(
            connection_status=connection_status,
            error_message=error_message,
        )

        connection_remote_model.additional_properties = d
        return connection_remote_model

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> Any:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: Any) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
