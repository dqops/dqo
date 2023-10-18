from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ExternalLogEntry")


@_attrs_define
class ExternalLogEntry:
    """External log entry

    Attributes:
        window_location (Union[Unset, str]): window.location value at the time when the log entry was reported.
        message (Union[Unset, str]): Log message that should be logged.
    """

    window_location: Union[Unset, str] = UNSET
    message: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        window_location = self.window_location
        message = self.message

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if window_location is not UNSET:
            field_dict["window_location"] = window_location
        if message is not UNSET:
            field_dict["message"] = message

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        window_location = d.pop("window_location", UNSET)

        message = d.pop("message", UNSET)

        external_log_entry = cls(
            window_location=window_location,
            message=message,
        )

        external_log_entry.additional_properties = d
        return external_log_entry

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
