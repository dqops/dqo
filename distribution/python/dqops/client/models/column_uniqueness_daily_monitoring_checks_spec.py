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
    from ..models.column_uniqueness_daily_monitoring_checks_spec_custom_checks import (
        ColumnUniquenessDailyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnUniquenessDailyMonitoringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnUniquenessDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        daily_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        daily_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        daily_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnUniquenessDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    daily_distinct_percent: Union[Unset, "ColumnDistinctPercentCheckSpec"] = UNSET
    daily_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    daily_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_distinct_count, Unset):
            daily_distinct_count = self.daily_distinct_count.to_dict()

        daily_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_distinct_percent, Unset):
            daily_distinct_percent = self.daily_distinct_percent.to_dict()

        daily_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_duplicate_count, Unset):
            daily_duplicate_count = self.daily_duplicate_count.to_dict()

        daily_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_duplicate_percent, Unset):
            daily_duplicate_percent = self.daily_duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_distinct_count is not UNSET:
            field_dict["daily_distinct_count"] = daily_distinct_count
        if daily_distinct_percent is not UNSET:
            field_dict["daily_distinct_percent"] = daily_distinct_percent
        if daily_duplicate_count is not UNSET:
            field_dict["daily_duplicate_count"] = daily_duplicate_count
        if daily_duplicate_percent is not UNSET:
            field_dict["daily_duplicate_percent"] = daily_duplicate_percent

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
        from ..models.column_uniqueness_daily_monitoring_checks_spec_custom_checks import (
            ColumnUniquenessDailyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnUniquenessDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnUniquenessDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_distinct_count = d.pop("daily_distinct_count", UNSET)
        daily_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_daily_distinct_count, Unset):
            daily_distinct_count = UNSET
        else:
            daily_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _daily_distinct_count
            )

        _daily_distinct_percent = d.pop("daily_distinct_percent", UNSET)
        daily_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_daily_distinct_percent, Unset):
            daily_distinct_percent = UNSET
        else:
            daily_distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(
                _daily_distinct_percent
            )

        _daily_duplicate_count = d.pop("daily_duplicate_count", UNSET)
        daily_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_daily_duplicate_count, Unset):
            daily_duplicate_count = UNSET
        else:
            daily_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _daily_duplicate_count
            )

        _daily_duplicate_percent = d.pop("daily_duplicate_percent", UNSET)
        daily_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_daily_duplicate_percent, Unset):
            daily_duplicate_percent = UNSET
        else:
            daily_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _daily_duplicate_percent
            )

        column_uniqueness_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_distinct_count=daily_distinct_count,
            daily_distinct_percent=daily_distinct_percent,
            daily_duplicate_count=daily_duplicate_count,
            daily_duplicate_percent=daily_duplicate_percent,
        )

        column_uniqueness_daily_monitoring_checks_spec.additional_properties = d
        return column_uniqueness_daily_monitoring_checks_spec

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