from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="SpringErrorPayload")


@_attrs_define
class SpringErrorPayload:
    """Spring error payload that identifies the fields in the error returned by the REST API in case of unexpected errors
    (exceptions).

        Attributes:
            timestamp (Union[Unset, int]): Error timestamp as an epoch timestamp.
            status (Union[Unset, int]): Optional status code.
            error (Union[Unset, str]): Error name.
            exception (Union[Unset, str]): Optional exception.
            message (Union[Unset, str]): Exception's message.
            path (Union[Unset, str]): Exception's stack trace (optional).
    """

    timestamp: Union[Unset, int] = UNSET
    status: Union[Unset, int] = UNSET
    error: Union[Unset, str] = UNSET
    exception: Union[Unset, str] = UNSET
    message: Union[Unset, str] = UNSET
    path: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        timestamp = self.timestamp
        status = self.status
        error = self.error
        exception = self.exception
        message = self.message
        path = self.path

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if timestamp is not UNSET:
            field_dict["timestamp"] = timestamp
        if status is not UNSET:
            field_dict["status"] = status
        if error is not UNSET:
            field_dict["error"] = error
        if exception is not UNSET:
            field_dict["exception"] = exception
        if message is not UNSET:
            field_dict["message"] = message
        if path is not UNSET:
            field_dict["path"] = path

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        timestamp = d.pop("timestamp", UNSET)

        status = d.pop("status", UNSET)

        error = d.pop("error", UNSET)

        exception = d.pop("exception", UNSET)

        message = d.pop("message", UNSET)

        path = d.pop("path", UNSET)

        spring_error_payload = cls(
            timestamp=timestamp,
            status=status,
            error=error,
            exception=exception,
            message=message,
            path=path,
        )

        spring_error_payload.additional_properties = d
        return spring_error_payload

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
