from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.detected_datatype_category import DetectedDatatypeCategory
from ..types import UNSET, Unset

T = TypeVar("T", bound="DetectedDatatypeEqualsRuleParametersSpec")


@_attrs_define
class DetectedDatatypeEqualsRuleParametersSpec:
    """
    Attributes:
        expected_datatype (Union[Unset, DetectedDatatypeCategory]):
    """

    expected_datatype: Union[Unset, DetectedDatatypeCategory] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        expected_datatype: Union[Unset, str] = UNSET
        if not isinstance(self.expected_datatype, Unset):
            expected_datatype = self.expected_datatype.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if expected_datatype is not UNSET:
            field_dict["expected_datatype"] = expected_datatype

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _expected_datatype = d.pop("expected_datatype", UNSET)
        expected_datatype: Union[Unset, DetectedDatatypeCategory]
        if isinstance(_expected_datatype, Unset):
            expected_datatype = UNSET
        else:
            expected_datatype = DetectedDatatypeCategory(_expected_datatype)

        detected_datatype_equals_rule_parameters_spec = cls(
            expected_datatype=expected_datatype,
        )

        detected_datatype_equals_rule_parameters_spec.additional_properties = d
        return detected_datatype_equals_rule_parameters_spec

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
