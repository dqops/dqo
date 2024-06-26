from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.table_comparison_monthly_monitoring_checks_spec import (
        TableComparisonMonthlyMonitoringChecksSpec,
    )


T = TypeVar("T", bound="TableMonthlyMonitoringCheckCategoriesSpecComparisons")


@_attrs_define
class TableMonthlyMonitoringCheckCategoriesSpecComparisons:
    """Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the
    name of a data comparison that is configured on the parent table.

    """

    additional_properties: Dict[str, "TableComparisonMonthlyMonitoringChecksSpec"] = (
        _attrs_field(init=False, factory=dict)
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
        from ..models.table_comparison_monthly_monitoring_checks_spec import (
            TableComparisonMonthlyMonitoringChecksSpec,
        )

        d = src_dict.copy()
        table_monthly_monitoring_check_categories_spec_comparisons = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = TableComparisonMonthlyMonitoringChecksSpec.from_dict(
                prop_dict
            )

            additional_properties[prop_name] = additional_property

        table_monthly_monitoring_check_categories_spec_comparisons.additional_properties = (
            additional_properties
        )
        return table_monthly_monitoring_check_categories_spec_comparisons

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "TableComparisonMonthlyMonitoringChecksSpec":
        return self.additional_properties[key]

    def __setitem__(
        self, key: str, value: "TableComparisonMonthlyMonitoringChecksSpec"
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
