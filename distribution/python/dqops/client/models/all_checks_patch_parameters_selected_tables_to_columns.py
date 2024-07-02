from typing import Any, Dict, List, Type, TypeVar, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

T = TypeVar("T", bound="AllChecksPatchParametersSelectedTablesToColumns")


@_attrs_define
class AllChecksPatchParametersSelectedTablesToColumns:
    """List of concrete table and column names which will be the target. Column mappings are ignored for table level
    checks. This filter is applied at the end.

    """

    additional_properties: Dict[str, List[str]] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        all_checks_patch_parameters_selected_tables_to_columns = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = cast(List[str], prop_dict)

            additional_properties[prop_name] = additional_property

        all_checks_patch_parameters_selected_tables_to_columns.additional_properties = (
            additional_properties
        )
        return all_checks_patch_parameters_selected_tables_to_columns

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> List[str]:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: List[str]) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
