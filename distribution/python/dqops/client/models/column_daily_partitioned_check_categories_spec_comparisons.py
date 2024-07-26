from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.column_comparison_daily_partitioned_checks_spec import (
        ColumnComparisonDailyPartitionedChecksSpec,
    )


T = TypeVar("T", bound="ColumnDailyPartitionedCheckCategoriesSpecComparisons")


@_attrs_define
class ColumnDailyPartitionedCheckCategoriesSpecComparisons:
    """Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each
    comparison must match the name of a data comparison that is configured on the parent table.

    """

    additional_properties: Dict[str, "ColumnComparisonDailyPartitionedChecksSpec"] = (
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
        from ..models.column_comparison_daily_partitioned_checks_spec import (
            ColumnComparisonDailyPartitionedChecksSpec,
        )

        d = src_dict.copy()
        column_daily_partitioned_check_categories_spec_comparisons = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = ColumnComparisonDailyPartitionedChecksSpec.from_dict(
                prop_dict
            )

            additional_properties[prop_name] = additional_property

        column_daily_partitioned_check_categories_spec_comparisons.additional_properties = (
            additional_properties
        )
        return column_daily_partitioned_check_categories_spec_comparisons

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "ColumnComparisonDailyPartitionedChecksSpec":
        return self.additional_properties[key]

    def __setitem__(
        self, key: str, value: "ColumnComparisonDailyPartitionedChecksSpec"
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
