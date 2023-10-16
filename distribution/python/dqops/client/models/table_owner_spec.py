from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TableOwnerSpec")


@_attrs_define
class TableOwnerSpec:
    """
    Attributes:
        data_steward (Union[Unset, str]): Data steward name
        application (Union[Unset, str]): Business application name
    """

    data_steward: Union[Unset, str] = UNSET
    application: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        data_steward = self.data_steward
        application = self.application

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if data_steward is not UNSET:
            field_dict["data_steward"] = data_steward
        if application is not UNSET:
            field_dict["application"] = application

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        data_steward = d.pop("data_steward", UNSET)

        application = d.pop("application", UNSET)

        table_owner_spec = cls(
            data_steward=data_steward,
            application=application,
        )

        table_owner_spec.additional_properties = d
        return table_owner_spec

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
