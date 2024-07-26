from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_daily_monitoring_checks_spec_custom_checks import (
        ColumnAccuracyDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_accuracy_total_average_match_percent_check_spec import (
        ColumnAccuracyTotalAverageMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_max_match_percent_check_spec import (
        ColumnAccuracyTotalMaxMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_min_match_percent_check_spec import (
        ColumnAccuracyTotalMinMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_not_null_count_match_percent_check_spec import (
        ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec,
    )
    from ..models.column_accuracy_total_sum_match_percent_check_spec import (
        ColumnAccuracyTotalSumMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnAccuracyDailyMonitoringChecksSpec")


@_attrs_define
class ColumnAccuracyDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAccuracyDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_total_sum_match_percent (Union[Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec]):
        daily_total_min_match_percent (Union[Unset, ColumnAccuracyTotalMinMatchPercentCheckSpec]):
        daily_total_max_match_percent (Union[Unset, ColumnAccuracyTotalMaxMatchPercentCheckSpec]):
        daily_total_average_match_percent (Union[Unset, ColumnAccuracyTotalAverageMatchPercentCheckSpec]):
        daily_total_not_null_count_match_percent (Union[Unset, ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAccuracyDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_total_sum_match_percent: Union[
        Unset, "ColumnAccuracyTotalSumMatchPercentCheckSpec"
    ] = UNSET
    daily_total_min_match_percent: Union[
        Unset, "ColumnAccuracyTotalMinMatchPercentCheckSpec"
    ] = UNSET
    daily_total_max_match_percent: Union[
        Unset, "ColumnAccuracyTotalMaxMatchPercentCheckSpec"
    ] = UNSET
    daily_total_average_match_percent: Union[
        Unset, "ColumnAccuracyTotalAverageMatchPercentCheckSpec"
    ] = UNSET
    daily_total_not_null_count_match_percent: Union[
        Unset, "ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_total_sum_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_sum_match_percent, Unset):
            daily_total_sum_match_percent = self.daily_total_sum_match_percent.to_dict()

        daily_total_min_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_min_match_percent, Unset):
            daily_total_min_match_percent = self.daily_total_min_match_percent.to_dict()

        daily_total_max_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_max_match_percent, Unset):
            daily_total_max_match_percent = self.daily_total_max_match_percent.to_dict()

        daily_total_average_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_average_match_percent, Unset):
            daily_total_average_match_percent = (
                self.daily_total_average_match_percent.to_dict()
            )

        daily_total_not_null_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_total_not_null_count_match_percent, Unset):
            daily_total_not_null_count_match_percent = (
                self.daily_total_not_null_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_total_sum_match_percent is not UNSET:
            field_dict["daily_total_sum_match_percent"] = daily_total_sum_match_percent
        if daily_total_min_match_percent is not UNSET:
            field_dict["daily_total_min_match_percent"] = daily_total_min_match_percent
        if daily_total_max_match_percent is not UNSET:
            field_dict["daily_total_max_match_percent"] = daily_total_max_match_percent
        if daily_total_average_match_percent is not UNSET:
            field_dict["daily_total_average_match_percent"] = (
                daily_total_average_match_percent
            )
        if daily_total_not_null_count_match_percent is not UNSET:
            field_dict["daily_total_not_null_count_match_percent"] = (
                daily_total_not_null_count_match_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_daily_monitoring_checks_spec_custom_checks import (
            ColumnAccuracyDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_accuracy_total_average_match_percent_check_spec import (
            ColumnAccuracyTotalAverageMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_max_match_percent_check_spec import (
            ColumnAccuracyTotalMaxMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_min_match_percent_check_spec import (
            ColumnAccuracyTotalMinMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_not_null_count_match_percent_check_spec import (
            ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec,
        )
        from ..models.column_accuracy_total_sum_match_percent_check_spec import (
            ColumnAccuracyTotalSumMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnAccuracyDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAccuracyDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_total_sum_match_percent = d.pop("daily_total_sum_match_percent", UNSET)
        daily_total_sum_match_percent: Union[
            Unset, ColumnAccuracyTotalSumMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_sum_match_percent, Unset):
            daily_total_sum_match_percent = UNSET
        else:
            daily_total_sum_match_percent = (
                ColumnAccuracyTotalSumMatchPercentCheckSpec.from_dict(
                    _daily_total_sum_match_percent
                )
            )

        _daily_total_min_match_percent = d.pop("daily_total_min_match_percent", UNSET)
        daily_total_min_match_percent: Union[
            Unset, ColumnAccuracyTotalMinMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_min_match_percent, Unset):
            daily_total_min_match_percent = UNSET
        else:
            daily_total_min_match_percent = (
                ColumnAccuracyTotalMinMatchPercentCheckSpec.from_dict(
                    _daily_total_min_match_percent
                )
            )

        _daily_total_max_match_percent = d.pop("daily_total_max_match_percent", UNSET)
        daily_total_max_match_percent: Union[
            Unset, ColumnAccuracyTotalMaxMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_max_match_percent, Unset):
            daily_total_max_match_percent = UNSET
        else:
            daily_total_max_match_percent = (
                ColumnAccuracyTotalMaxMatchPercentCheckSpec.from_dict(
                    _daily_total_max_match_percent
                )
            )

        _daily_total_average_match_percent = d.pop(
            "daily_total_average_match_percent", UNSET
        )
        daily_total_average_match_percent: Union[
            Unset, ColumnAccuracyTotalAverageMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_average_match_percent, Unset):
            daily_total_average_match_percent = UNSET
        else:
            daily_total_average_match_percent = (
                ColumnAccuracyTotalAverageMatchPercentCheckSpec.from_dict(
                    _daily_total_average_match_percent
                )
            )

        _daily_total_not_null_count_match_percent = d.pop(
            "daily_total_not_null_count_match_percent", UNSET
        )
        daily_total_not_null_count_match_percent: Union[
            Unset, ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec
        ]
        if isinstance(_daily_total_not_null_count_match_percent, Unset):
            daily_total_not_null_count_match_percent = UNSET
        else:
            daily_total_not_null_count_match_percent = (
                ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec.from_dict(
                    _daily_total_not_null_count_match_percent
                )
            )

        column_accuracy_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_total_sum_match_percent=daily_total_sum_match_percent,
            daily_total_min_match_percent=daily_total_min_match_percent,
            daily_total_max_match_percent=daily_total_max_match_percent,
            daily_total_average_match_percent=daily_total_average_match_percent,
            daily_total_not_null_count_match_percent=daily_total_not_null_count_match_percent,
        )

        column_accuracy_daily_monitoring_checks_spec.additional_properties = d
        return column_accuracy_daily_monitoring_checks_spec

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
