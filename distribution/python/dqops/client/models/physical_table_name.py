from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="PhysicalTableName")


@attr.s(auto_attribs=True)
class PhysicalTableName:
    """
    Attributes:
        schema_name (Union[Unset, str]): Schema name
        table_name (Union[Unset, str]): Table name
    """

    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schema_name = self.schema_name
        table_name = self.table_name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        physical_table_name = cls(
            schema_name=schema_name,
            table_name=table_name,
        )

        physical_table_name.additional_properties = d
        return physical_table_name

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
