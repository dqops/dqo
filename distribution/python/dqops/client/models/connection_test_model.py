from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.connection_test_status import ConnectionTestStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="ConnectionTestModel")


@_attrs_define
class ConnectionTestModel:
    """Connection test status result

    Attributes:
        connection_test_result (Union[Unset, ConnectionTestStatus]):
        error_message (Union[Unset, str]): Optional error message when the status is not "SUCCESS"
    """

    connection_test_result: Union[Unset, ConnectionTestStatus] = UNSET
    error_message: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_test_result: Union[Unset, str] = UNSET
        if not isinstance(self.connection_test_result, Unset):
            connection_test_result = self.connection_test_result.value

        error_message = self.error_message

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_test_result is not UNSET:
            field_dict["connectionTestResult"] = connection_test_result
        if error_message is not UNSET:
            field_dict["errorMessage"] = error_message

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _connection_test_result = d.pop("connectionTestResult", UNSET)
        connection_test_result: Union[Unset, ConnectionTestStatus]
        if isinstance(_connection_test_result, Unset):
            connection_test_result = UNSET
        else:
            connection_test_result = ConnectionTestStatus(_connection_test_result)

        error_message = d.pop("errorMessage", UNSET)

        connection_test_model = cls(
            connection_test_result=connection_test_result,
            error_message=error_message,
        )

        connection_test_model.additional_properties = d
        return connection_test_model

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
