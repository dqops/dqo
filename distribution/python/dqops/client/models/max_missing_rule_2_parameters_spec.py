from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MaxMissingRule2ParametersSpec")


@_attrs_define
class MaxMissingRule2ParametersSpec:
    """
    Attributes:
        max_missing (Union[Unset, int]): The maximum number of values from the expected_values list that were not found
            in the column (inclusive).
    """

    max_missing: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_missing = self.max_missing

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_missing is not UNSET:
            field_dict["max_missing"] = max_missing

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_missing = d.pop("max_missing", UNSET)

        max_missing_rule_2_parameters_spec = cls(
            max_missing=max_missing,
        )

        max_missing_rule_2_parameters_spec.additional_properties = d
        return max_missing_rule_2_parameters_spec

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
