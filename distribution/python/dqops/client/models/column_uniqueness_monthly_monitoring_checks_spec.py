from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
    from ..models.column_distinct_percent_check_spec import (
        ColumnDistinctPercentCheckSpec,
    )
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )
    from ..models.column_uniqueness_monthly_monitoring_checks_spec_custom_checks import (
        ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnUniquenessMonthlyMonitoringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        monthly_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        monthly_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        monthly_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    monthly_distinct_percent: Union[Unset, "ColumnDistinctPercentCheckSpec"] = UNSET
    monthly_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    monthly_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_distinct_count, Unset):
            monthly_distinct_count = self.monthly_distinct_count.to_dict()

        monthly_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_distinct_percent, Unset):
            monthly_distinct_percent = self.monthly_distinct_percent.to_dict()

        monthly_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_duplicate_count, Unset):
            monthly_duplicate_count = self.monthly_duplicate_count.to_dict()

        monthly_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_duplicate_percent, Unset):
            monthly_duplicate_percent = self.monthly_duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_distinct_count is not UNSET:
            field_dict["monthly_distinct_count"] = monthly_distinct_count
        if monthly_distinct_percent is not UNSET:
            field_dict["monthly_distinct_percent"] = monthly_distinct_percent
        if monthly_duplicate_count is not UNSET:
            field_dict["monthly_duplicate_count"] = monthly_duplicate_count
        if monthly_duplicate_percent is not UNSET:
            field_dict["monthly_duplicate_percent"] = monthly_duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_distinct_count_check_spec import (
            ColumnDistinctCountCheckSpec,
        )
        from ..models.column_distinct_percent_check_spec import (
            ColumnDistinctPercentCheckSpec,
        )
        from ..models.column_duplicate_count_check_spec import (
            ColumnDuplicateCountCheckSpec,
        )
        from ..models.column_duplicate_percent_check_spec import (
            ColumnDuplicatePercentCheckSpec,
        )
        from ..models.column_uniqueness_monthly_monitoring_checks_spec_custom_checks import (
            ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnUniquenessMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_distinct_count = d.pop("monthly_distinct_count", UNSET)
        monthly_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_monthly_distinct_count, Unset):
            monthly_distinct_count = UNSET
        else:
            monthly_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _monthly_distinct_count
            )

        _monthly_distinct_percent = d.pop("monthly_distinct_percent", UNSET)
        monthly_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_monthly_distinct_percent, Unset):
            monthly_distinct_percent = UNSET
        else:
            monthly_distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(
                _monthly_distinct_percent
            )

        _monthly_duplicate_count = d.pop("monthly_duplicate_count", UNSET)
        monthly_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_monthly_duplicate_count, Unset):
            monthly_duplicate_count = UNSET
        else:
            monthly_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _monthly_duplicate_count
            )

        _monthly_duplicate_percent = d.pop("monthly_duplicate_percent", UNSET)
        monthly_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_monthly_duplicate_percent, Unset):
            monthly_duplicate_percent = UNSET
        else:
            monthly_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _monthly_duplicate_percent
            )

        column_uniqueness_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_distinct_count=monthly_distinct_count,
            monthly_distinct_percent=monthly_distinct_percent,
            monthly_duplicate_count=monthly_duplicate_count,
            monthly_duplicate_percent=monthly_duplicate_percent,
        )

        column_uniqueness_monthly_monitoring_checks_spec.additional_properties = d
        return column_uniqueness_monthly_monitoring_checks_spec

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