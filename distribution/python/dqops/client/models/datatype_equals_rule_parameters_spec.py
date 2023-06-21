from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="DatatypeEqualsRuleParametersSpec")


@attr.s(auto_attribs=True)
class DatatypeEqualsRuleParametersSpec:
    """
    Attributes:
        expected_datatype (Union[Unset, int]): Expected data type code, the data type codes are: 1 - integers, 2 -
            floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
    """

    expected_datatype: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        expected_datatype = self.expected_datatype

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if expected_datatype is not UNSET:
            field_dict["expected_datatype"] = expected_datatype

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        expected_datatype = d.pop("expected_datatype", UNSET)

        datatype_equals_rule_parameters_spec = cls(
            expected_datatype=expected_datatype,
        )

        datatype_equals_rule_parameters_spec.additional_properties = d
        return datatype_equals_rule_parameters_spec

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
