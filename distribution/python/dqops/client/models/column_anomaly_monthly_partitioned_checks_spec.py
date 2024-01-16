from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_monthly_partitioned_checks_spec_custom_checks import (
        ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_mean_change_check_spec import ColumnMeanChangeCheckSpec
    from ..models.column_median_change_check_spec import ColumnMedianChangeCheckSpec
    from ..models.column_sum_change_check_spec import ColumnSumChangeCheckSpec


T = TypeVar("T", bound="ColumnAnomalyMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnAnomalyMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_sum_change (Union[Unset, ColumnSumChangeCheckSpec]):
        monthly_partition_mean_change (Union[Unset, ColumnMeanChangeCheckSpec]):
        monthly_partition_median_change (Union[Unset, ColumnMedianChangeCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_sum_change: Union[Unset, "ColumnSumChangeCheckSpec"] = UNSET
    monthly_partition_mean_change: Union[Unset, "ColumnMeanChangeCheckSpec"] = UNSET
    monthly_partition_median_change: Union[Unset, "ColumnMedianChangeCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_sum_change, Unset):
            monthly_partition_sum_change = self.monthly_partition_sum_change.to_dict()

        monthly_partition_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_mean_change, Unset):
            monthly_partition_mean_change = self.monthly_partition_mean_change.to_dict()

        monthly_partition_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_median_change, Unset):
            monthly_partition_median_change = (
                self.monthly_partition_median_change.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_sum_change is not UNSET:
            field_dict["monthly_partition_sum_change"] = monthly_partition_sum_change
        if monthly_partition_mean_change is not UNSET:
            field_dict["monthly_partition_mean_change"] = monthly_partition_mean_change
        if monthly_partition_median_change is not UNSET:
            field_dict[
                "monthly_partition_median_change"
            ] = monthly_partition_median_change

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_monthly_partitioned_checks_spec_custom_checks import (
            ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_mean_change_check_spec import ColumnMeanChangeCheckSpec
        from ..models.column_median_change_check_spec import ColumnMedianChangeCheckSpec
        from ..models.column_sum_change_check_spec import ColumnSumChangeCheckSpec

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAnomalyMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_sum_change = d.pop("monthly_partition_sum_change", UNSET)
        monthly_partition_sum_change: Union[Unset, ColumnSumChangeCheckSpec]
        if isinstance(_monthly_partition_sum_change, Unset):
            monthly_partition_sum_change = UNSET
        else:
            monthly_partition_sum_change = ColumnSumChangeCheckSpec.from_dict(
                _monthly_partition_sum_change
            )

        _monthly_partition_mean_change = d.pop("monthly_partition_mean_change", UNSET)
        monthly_partition_mean_change: Union[Unset, ColumnMeanChangeCheckSpec]
        if isinstance(_monthly_partition_mean_change, Unset):
            monthly_partition_mean_change = UNSET
        else:
            monthly_partition_mean_change = ColumnMeanChangeCheckSpec.from_dict(
                _monthly_partition_mean_change
            )

        _monthly_partition_median_change = d.pop(
            "monthly_partition_median_change", UNSET
        )
        monthly_partition_median_change: Union[Unset, ColumnMedianChangeCheckSpec]
        if isinstance(_monthly_partition_median_change, Unset):
            monthly_partition_median_change = UNSET
        else:
            monthly_partition_median_change = ColumnMedianChangeCheckSpec.from_dict(
                _monthly_partition_median_change
            )

        column_anomaly_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_sum_change=monthly_partition_sum_change,
            monthly_partition_mean_change=monthly_partition_mean_change,
            monthly_partition_median_change=monthly_partition_median_change,
        )

        column_anomaly_monthly_partitioned_checks_spec.additional_properties = d
        return column_anomaly_monthly_partitioned_checks_spec

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
