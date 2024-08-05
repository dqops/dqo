from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_empty_column_found_check_spec import (
        ColumnEmptyColumnFoundCheckSpec,
    )
    from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
    from ..models.column_not_nulls_percent_check_spec import (
        ColumnNotNullsPercentCheckSpec,
    )
    from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
    from ..models.column_nulls_monthly_monitoring_checks_spec_custom_checks import (
        ColumnNullsMonthlyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec


T = TypeVar("T", bound="ColumnNullsMonthlyMonitoringChecksSpec")


@_attrs_define
class ColumnNullsMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNullsMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        monthly_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        monthly_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        monthly_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
        monthly_empty_column_found (Union[Unset, ColumnEmptyColumnFoundCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnNullsMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    monthly_nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    monthly_not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = UNSET
    monthly_not_nulls_percent: Union[Unset, "ColumnNotNullsPercentCheckSpec"] = UNSET
    monthly_empty_column_found: Union[Unset, "ColumnEmptyColumnFoundCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_nulls_count, Unset):
            monthly_nulls_count = self.monthly_nulls_count.to_dict()

        monthly_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_nulls_percent, Unset):
            monthly_nulls_percent = self.monthly_nulls_percent.to_dict()

        monthly_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_not_nulls_count, Unset):
            monthly_not_nulls_count = self.monthly_not_nulls_count.to_dict()

        monthly_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_not_nulls_percent, Unset):
            monthly_not_nulls_percent = self.monthly_not_nulls_percent.to_dict()

        monthly_empty_column_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_empty_column_found, Unset):
            monthly_empty_column_found = self.monthly_empty_column_found.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_nulls_count is not UNSET:
            field_dict["monthly_nulls_count"] = monthly_nulls_count
        if monthly_nulls_percent is not UNSET:
            field_dict["monthly_nulls_percent"] = monthly_nulls_percent
        if monthly_not_nulls_count is not UNSET:
            field_dict["monthly_not_nulls_count"] = monthly_not_nulls_count
        if monthly_not_nulls_percent is not UNSET:
            field_dict["monthly_not_nulls_percent"] = monthly_not_nulls_percent
        if monthly_empty_column_found is not UNSET:
            field_dict["monthly_empty_column_found"] = monthly_empty_column_found

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_empty_column_found_check_spec import (
            ColumnEmptyColumnFoundCheckSpec,
        )
        from ..models.column_not_nulls_count_check_spec import (
            ColumnNotNullsCountCheckSpec,
        )
        from ..models.column_not_nulls_percent_check_spec import (
            ColumnNotNullsPercentCheckSpec,
        )
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_nulls_monthly_monitoring_checks_spec_custom_checks import (
            ColumnNullsMonthlyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnNullsMonthlyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnNullsMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_nulls_count = d.pop("monthly_nulls_count", UNSET)
        monthly_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_monthly_nulls_count, Unset):
            monthly_nulls_count = UNSET
        else:
            monthly_nulls_count = ColumnNullsCountCheckSpec.from_dict(
                _monthly_nulls_count
            )

        _monthly_nulls_percent = d.pop("monthly_nulls_percent", UNSET)
        monthly_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_monthly_nulls_percent, Unset):
            monthly_nulls_percent = UNSET
        else:
            monthly_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(
                _monthly_nulls_percent
            )

        _monthly_not_nulls_count = d.pop("monthly_not_nulls_count", UNSET)
        monthly_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_monthly_not_nulls_count, Unset):
            monthly_not_nulls_count = UNSET
        else:
            monthly_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(
                _monthly_not_nulls_count
            )

        _monthly_not_nulls_percent = d.pop("monthly_not_nulls_percent", UNSET)
        monthly_not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_monthly_not_nulls_percent, Unset):
            monthly_not_nulls_percent = UNSET
        else:
            monthly_not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(
                _monthly_not_nulls_percent
            )

        _monthly_empty_column_found = d.pop("monthly_empty_column_found", UNSET)
        monthly_empty_column_found: Union[Unset, ColumnEmptyColumnFoundCheckSpec]
        if isinstance(_monthly_empty_column_found, Unset):
            monthly_empty_column_found = UNSET
        else:
            monthly_empty_column_found = ColumnEmptyColumnFoundCheckSpec.from_dict(
                _monthly_empty_column_found
            )

        column_nulls_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_nulls_count=monthly_nulls_count,
            monthly_nulls_percent=monthly_nulls_percent,
            monthly_not_nulls_count=monthly_not_nulls_count,
            monthly_not_nulls_percent=monthly_not_nulls_percent,
            monthly_empty_column_found=monthly_empty_column_found,
        )

        column_nulls_monthly_monitoring_checks_spec.additional_properties = d
        return column_nulls_monthly_monitoring_checks_spec

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
