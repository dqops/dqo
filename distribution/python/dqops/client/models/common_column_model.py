from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CommonColumnModel")


@_attrs_define
class CommonColumnModel:
    """Common column model that describes a column name that is frequently used in tables within a connection

    Attributes:
        column_name (Union[Unset, str]): Column name.
        tables_count (Union[Unset, int]): Count of tables that are have a column with this name.
    """

    column_name: Union[Unset, str] = UNSET
    tables_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_name = self.column_name
        tables_count = self.tables_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if tables_count is not UNSET:
            field_dict["tables_count"] = tables_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        column_name = d.pop("column_name", UNSET)

        tables_count = d.pop("tables_count", UNSET)

        common_column_model = cls(
            column_name=column_name,
            tables_count=tables_count,
        )

        common_column_model.additional_properties = d
        return common_column_model

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
