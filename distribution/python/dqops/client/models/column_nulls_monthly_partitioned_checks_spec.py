from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
    from ..models.column_not_nulls_percent_check_spec import (
        ColumnNotNullsPercentCheckSpec,
    )
    from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
    from ..models.column_nulls_monthly_partitioned_checks_spec_custom_checks import (
        ColumnNullsMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec


T = TypeVar("T", bound="ColumnNullsMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnNullsMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNullsMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        monthly_partition_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        monthly_partition_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        monthly_partition_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnNullsMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    monthly_partition_nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    monthly_partition_not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = (
        UNSET
    )
    monthly_partition_not_nulls_percent: Union[
        Unset, "ColumnNotNullsPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_nulls_count, Unset):
            monthly_partition_nulls_count = self.monthly_partition_nulls_count.to_dict()

        monthly_partition_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_nulls_percent, Unset):
            monthly_partition_nulls_percent = (
                self.monthly_partition_nulls_percent.to_dict()
            )

        monthly_partition_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_not_nulls_count, Unset):
            monthly_partition_not_nulls_count = (
                self.monthly_partition_not_nulls_count.to_dict()
            )

        monthly_partition_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_not_nulls_percent, Unset):
            monthly_partition_not_nulls_percent = (
                self.monthly_partition_not_nulls_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_nulls_count is not UNSET:
            field_dict["monthly_partition_nulls_count"] = monthly_partition_nulls_count
        if monthly_partition_nulls_percent is not UNSET:
            field_dict["monthly_partition_nulls_percent"] = (
                monthly_partition_nulls_percent
            )
        if monthly_partition_not_nulls_count is not UNSET:
            field_dict["monthly_partition_not_nulls_count"] = (
                monthly_partition_not_nulls_count
            )
        if monthly_partition_not_nulls_percent is not UNSET:
            field_dict["monthly_partition_not_nulls_percent"] = (
                monthly_partition_not_nulls_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_not_nulls_count_check_spec import (
            ColumnNotNullsCountCheckSpec,
        )
        from ..models.column_not_nulls_percent_check_spec import (
            ColumnNotNullsPercentCheckSpec,
        )
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_nulls_monthly_partitioned_checks_spec_custom_checks import (
            ColumnNullsMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnNullsMonthlyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnNullsMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_nulls_count = d.pop("monthly_partition_nulls_count", UNSET)
        monthly_partition_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_monthly_partition_nulls_count, Unset):
            monthly_partition_nulls_count = UNSET
        else:
            monthly_partition_nulls_count = ColumnNullsCountCheckSpec.from_dict(
                _monthly_partition_nulls_count
            )

        _monthly_partition_nulls_percent = d.pop(
            "monthly_partition_nulls_percent", UNSET
        )
        monthly_partition_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_monthly_partition_nulls_percent, Unset):
            monthly_partition_nulls_percent = UNSET
        else:
            monthly_partition_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(
                _monthly_partition_nulls_percent
            )

        _monthly_partition_not_nulls_count = d.pop(
            "monthly_partition_not_nulls_count", UNSET
        )
        monthly_partition_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_monthly_partition_not_nulls_count, Unset):
            monthly_partition_not_nulls_count = UNSET
        else:
            monthly_partition_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(
                _monthly_partition_not_nulls_count
            )

        _monthly_partition_not_nulls_percent = d.pop(
            "monthly_partition_not_nulls_percent", UNSET
        )
        monthly_partition_not_nulls_percent: Union[
            Unset, ColumnNotNullsPercentCheckSpec
        ]
        if isinstance(_monthly_partition_not_nulls_percent, Unset):
            monthly_partition_not_nulls_percent = UNSET
        else:
            monthly_partition_not_nulls_percent = (
                ColumnNotNullsPercentCheckSpec.from_dict(
                    _monthly_partition_not_nulls_percent
                )
            )

        column_nulls_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_nulls_count=monthly_partition_nulls_count,
            monthly_partition_nulls_percent=monthly_partition_nulls_percent,
            monthly_partition_not_nulls_count=monthly_partition_not_nulls_count,
            monthly_partition_not_nulls_percent=monthly_partition_not_nulls_percent,
        )

        column_nulls_monthly_partitioned_checks_spec.additional_properties = d
        return column_nulls_monthly_partitioned_checks_spec

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
