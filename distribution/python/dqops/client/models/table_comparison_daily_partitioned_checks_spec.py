from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_comparison_daily_partitioned_checks_spec_custom_checks import (
        TableComparisonDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.table_comparison_row_count_match_check_spec import (
        TableComparisonRowCountMatchCheckSpec,
    )


T = TypeVar("T", bound="TableComparisonDailyPartitionedChecksSpec")


@_attrs_define
class TableComparisonDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableComparisonDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_row_count_match (Union[Unset, TableComparisonRowCountMatchCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableComparisonDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_row_count_match: Union[
        Unset, "TableComparisonRowCountMatchCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_row_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count_match, Unset):
            daily_partition_row_count_match = (
                self.daily_partition_row_count_match.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_row_count_match is not UNSET:
            field_dict["daily_partition_row_count_match"] = (
                daily_partition_row_count_match
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_comparison_daily_partitioned_checks_spec_custom_checks import (
            TableComparisonDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.table_comparison_row_count_match_check_spec import (
            TableComparisonRowCountMatchCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, TableComparisonDailyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableComparisonDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_row_count_match = d.pop(
            "daily_partition_row_count_match", UNSET
        )
        daily_partition_row_count_match: Union[
            Unset, TableComparisonRowCountMatchCheckSpec
        ]
        if isinstance(_daily_partition_row_count_match, Unset):
            daily_partition_row_count_match = UNSET
        else:
            daily_partition_row_count_match = (
                TableComparisonRowCountMatchCheckSpec.from_dict(
                    _daily_partition_row_count_match
                )
            )

        table_comparison_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_row_count_match=daily_partition_row_count_match,
        )

        table_comparison_daily_partitioned_checks_spec.additional_properties = d
        return table_comparison_daily_partitioned_checks_spec

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
