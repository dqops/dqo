from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_row_count_change_check_spec import TableRowCountChangeCheckSpec
    from ..models.table_row_count_check_spec import TableRowCountCheckSpec
    from ..models.table_volume_monthly_partitioned_checks_spec_custom_checks import (
        TableVolumeMonthlyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableVolumeMonthlyPartitionedChecksSpec")


@_attrs_define
class TableVolumeMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableVolumeMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_row_count (Union[Unset, TableRowCountCheckSpec]):
        monthly_partition_row_count_change (Union[Unset, TableRowCountChangeCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableVolumeMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    monthly_partition_row_count_change: Union[Unset, "TableRowCountChangeCheckSpec"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_row_count, Unset):
            monthly_partition_row_count = self.monthly_partition_row_count.to_dict()

        monthly_partition_row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_row_count_change, Unset):
            monthly_partition_row_count_change = (
                self.monthly_partition_row_count_change.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_row_count is not UNSET:
            field_dict["monthly_partition_row_count"] = monthly_partition_row_count
        if monthly_partition_row_count_change is not UNSET:
            field_dict["monthly_partition_row_count_change"] = (
                monthly_partition_row_count_change
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_row_count_change_check_spec import (
            TableRowCountChangeCheckSpec,
        )
        from ..models.table_row_count_check_spec import TableRowCountCheckSpec
        from ..models.table_volume_monthly_partitioned_checks_spec_custom_checks import (
            TableVolumeMonthlyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableVolumeMonthlyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableVolumeMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_row_count = d.pop("monthly_partition_row_count", UNSET)
        monthly_partition_row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_monthly_partition_row_count, Unset):
            monthly_partition_row_count = UNSET
        else:
            monthly_partition_row_count = TableRowCountCheckSpec.from_dict(
                _monthly_partition_row_count
            )

        _monthly_partition_row_count_change = d.pop(
            "monthly_partition_row_count_change", UNSET
        )
        monthly_partition_row_count_change: Union[Unset, TableRowCountChangeCheckSpec]
        if isinstance(_monthly_partition_row_count_change, Unset):
            monthly_partition_row_count_change = UNSET
        else:
            monthly_partition_row_count_change = TableRowCountChangeCheckSpec.from_dict(
                _monthly_partition_row_count_change
            )

        table_volume_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_row_count=monthly_partition_row_count,
            monthly_partition_row_count_change=monthly_partition_row_count_change,
        )

        table_volume_monthly_partitioned_checks_spec.additional_properties = d
        return table_volume_monthly_partitioned_checks_spec

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
