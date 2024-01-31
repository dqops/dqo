from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MaxFailuresRule1ParametersSpec")


@_attrs_define
class MaxFailuresRule1ParametersSpec:
    """
    Attributes:
        max_failures (Union[Unset, int]): Maximum number of consecutive days with check failures. A check is failed when
            a sensor query fails due to a connection error, missing or corrupted table.
    """

    max_failures: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_failures = self.max_failures

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_failures is not UNSET:
            field_dict["max_failures"] = max_failures

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_failures = d.pop("max_failures", UNSET)

        max_failures_rule_1_parameters_spec = cls(
            max_failures=max_failures,
        )

        max_failures_rule_1_parameters_spec.additional_properties = d
        return max_failures_rule_1_parameters_spec

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
