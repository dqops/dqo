from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.column_spec import ColumnSpec


T = TypeVar("T", bound="TableSpecColumns")


@_attrs_define
class TableSpecColumns:
    """Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data
    type and a list of column level data quality checks that are enabled for a column.

    """

    additional_properties: Dict[str, "ColumnSpec"] = _attrs_field(
        init=False, factory=dict
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_spec import ColumnSpec

        d = src_dict.copy()
        table_spec_columns = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = ColumnSpec.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        table_spec_columns.additional_properties = additional_properties
        return table_spec_columns

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "ColumnSpec":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "ColumnSpec") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
