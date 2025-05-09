from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.custom_check_spec import CustomCheckSpec


T = TypeVar("T", bound="TableUniquenessDailyMonitoringChecksSpecCustomChecks")


@_attrs_define
class TableUniquenessDailyMonitoringChecksSpecCustomChecks:
    """Dictionary of additional custom checks within this category. The keys are check names defined in the definition
    section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom
    check.

    """

    additional_properties: Dict[str, "CustomCheckSpec"] = _attrs_field(
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
        from ..models.custom_check_spec import CustomCheckSpec

        d = src_dict.copy()
        table_uniqueness_daily_monitoring_checks_spec_custom_checks = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = CustomCheckSpec.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        table_uniqueness_daily_monitoring_checks_spec_custom_checks.additional_properties = (
            additional_properties
        )
        return table_uniqueness_daily_monitoring_checks_spec_custom_checks

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "CustomCheckSpec":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "CustomCheckSpec") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
