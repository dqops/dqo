from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_profiling_checks_spec_custom_checks import (
        ColumnAnomalyProfilingChecksSpecCustomChecks,
    )
    from ..models.column_max_anomaly_differencing_check_spec import (
        ColumnMaxAnomalyDifferencingCheckSpec,
    )
    from ..models.column_mean_anomaly_stationary_check_spec import (
        ColumnMeanAnomalyStationaryCheckSpec,
    )
    from ..models.column_mean_change_1_day_check_spec import (
        ColumnMeanChange1DayCheckSpec,
    )
    from ..models.column_mean_change_7_days_check_spec import (
        ColumnMeanChange7DaysCheckSpec,
    )
    from ..models.column_mean_change_30_days_check_spec import (
        ColumnMeanChange30DaysCheckSpec,
    )
    from ..models.column_mean_change_check_spec import ColumnMeanChangeCheckSpec
    from ..models.column_median_anomaly_stationary_check_spec import (
        ColumnMedianAnomalyStationaryCheckSpec,
    )
    from ..models.column_median_change_1_day_check_spec import (
        ColumnMedianChange1DayCheckSpec,
    )
    from ..models.column_median_change_7_days_check_spec import (
        ColumnMedianChange7DaysCheckSpec,
    )
    from ..models.column_median_change_30_days_check_spec import (
        ColumnMedianChange30DaysCheckSpec,
    )
    from ..models.column_median_change_check_spec import ColumnMedianChangeCheckSpec
    from ..models.column_min_anomaly_differencing_check_spec import (
        ColumnMinAnomalyDifferencingCheckSpec,
    )
    from ..models.column_sum_anomaly_differencing_check_spec import (
        ColumnSumAnomalyDifferencingCheckSpec,
    )
    from ..models.column_sum_change_1_day_check_spec import ColumnSumChange1DayCheckSpec
    from ..models.column_sum_change_7_days_check_spec import (
        ColumnSumChange7DaysCheckSpec,
    )
    from ..models.column_sum_change_30_days_check_spec import (
        ColumnSumChange30DaysCheckSpec,
    )
    from ..models.column_sum_change_check_spec import ColumnSumChangeCheckSpec


T = TypeVar("T", bound="ColumnAnomalyProfilingChecksSpec")


@_attrs_define
class ColumnAnomalyProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_sum_anomaly (Union[Unset, ColumnSumAnomalyDifferencingCheckSpec]):
        profile_mean_anomaly (Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]):
        profile_median_anomaly (Union[Unset, ColumnMedianAnomalyStationaryCheckSpec]):
        profile_min_anomaly (Union[Unset, ColumnMinAnomalyDifferencingCheckSpec]):
        profile_max_anomaly (Union[Unset, ColumnMaxAnomalyDifferencingCheckSpec]):
        profile_mean_change (Union[Unset, ColumnMeanChangeCheckSpec]):
        profile_median_change (Union[Unset, ColumnMedianChangeCheckSpec]):
        profile_sum_change (Union[Unset, ColumnSumChangeCheckSpec]):
        profile_mean_change_1_day (Union[Unset, ColumnMeanChange1DayCheckSpec]):
        profile_mean_change_7_days (Union[Unset, ColumnMeanChange7DaysCheckSpec]):
        profile_mean_change_30_days (Union[Unset, ColumnMeanChange30DaysCheckSpec]):
        profile_median_change_1_day (Union[Unset, ColumnMedianChange1DayCheckSpec]):
        profile_median_change_7_days (Union[Unset, ColumnMedianChange7DaysCheckSpec]):
        profile_median_change_30_days (Union[Unset, ColumnMedianChange30DaysCheckSpec]):
        profile_sum_change_1_day (Union[Unset, ColumnSumChange1DayCheckSpec]):
        profile_sum_change_7_days (Union[Unset, ColumnSumChange7DaysCheckSpec]):
        profile_sum_change_30_days (Union[Unset, ColumnSumChange30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnAnomalyProfilingChecksSpecCustomChecks"] = UNSET
    profile_sum_anomaly: Union[Unset, "ColumnSumAnomalyDifferencingCheckSpec"] = UNSET
    profile_mean_anomaly: Union[Unset, "ColumnMeanAnomalyStationaryCheckSpec"] = UNSET
    profile_median_anomaly: Union[Unset, "ColumnMedianAnomalyStationaryCheckSpec"] = (
        UNSET
    )
    profile_min_anomaly: Union[Unset, "ColumnMinAnomalyDifferencingCheckSpec"] = UNSET
    profile_max_anomaly: Union[Unset, "ColumnMaxAnomalyDifferencingCheckSpec"] = UNSET
    profile_mean_change: Union[Unset, "ColumnMeanChangeCheckSpec"] = UNSET
    profile_median_change: Union[Unset, "ColumnMedianChangeCheckSpec"] = UNSET
    profile_sum_change: Union[Unset, "ColumnSumChangeCheckSpec"] = UNSET
    profile_mean_change_1_day: Union[Unset, "ColumnMeanChange1DayCheckSpec"] = UNSET
    profile_mean_change_7_days: Union[Unset, "ColumnMeanChange7DaysCheckSpec"] = UNSET
    profile_mean_change_30_days: Union[Unset, "ColumnMeanChange30DaysCheckSpec"] = UNSET
    profile_median_change_1_day: Union[Unset, "ColumnMedianChange1DayCheckSpec"] = UNSET
    profile_median_change_7_days: Union[Unset, "ColumnMedianChange7DaysCheckSpec"] = (
        UNSET
    )
    profile_median_change_30_days: Union[Unset, "ColumnMedianChange30DaysCheckSpec"] = (
        UNSET
    )
    profile_sum_change_1_day: Union[Unset, "ColumnSumChange1DayCheckSpec"] = UNSET
    profile_sum_change_7_days: Union[Unset, "ColumnSumChange7DaysCheckSpec"] = UNSET
    profile_sum_change_30_days: Union[Unset, "ColumnSumChange30DaysCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_sum_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_anomaly, Unset):
            profile_sum_anomaly = self.profile_sum_anomaly.to_dict()

        profile_mean_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_anomaly, Unset):
            profile_mean_anomaly = self.profile_mean_anomaly.to_dict()

        profile_median_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_anomaly, Unset):
            profile_median_anomaly = self.profile_median_anomaly.to_dict()

        profile_min_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_min_anomaly, Unset):
            profile_min_anomaly = self.profile_min_anomaly.to_dict()

        profile_max_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_max_anomaly, Unset):
            profile_max_anomaly = self.profile_max_anomaly.to_dict()

        profile_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change, Unset):
            profile_mean_change = self.profile_mean_change.to_dict()

        profile_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change, Unset):
            profile_median_change = self.profile_median_change.to_dict()

        profile_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change, Unset):
            profile_sum_change = self.profile_sum_change.to_dict()

        profile_mean_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_1_day, Unset):
            profile_mean_change_1_day = self.profile_mean_change_1_day.to_dict()

        profile_mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_7_days, Unset):
            profile_mean_change_7_days = self.profile_mean_change_7_days.to_dict()

        profile_mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_30_days, Unset):
            profile_mean_change_30_days = self.profile_mean_change_30_days.to_dict()

        profile_median_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_1_day, Unset):
            profile_median_change_1_day = self.profile_median_change_1_day.to_dict()

        profile_median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_7_days, Unset):
            profile_median_change_7_days = self.profile_median_change_7_days.to_dict()

        profile_median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_30_days, Unset):
            profile_median_change_30_days = self.profile_median_change_30_days.to_dict()

        profile_sum_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change_1_day, Unset):
            profile_sum_change_1_day = self.profile_sum_change_1_day.to_dict()

        profile_sum_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change_7_days, Unset):
            profile_sum_change_7_days = self.profile_sum_change_7_days.to_dict()

        profile_sum_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change_30_days, Unset):
            profile_sum_change_30_days = self.profile_sum_change_30_days.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_sum_anomaly is not UNSET:
            field_dict["profile_sum_anomaly"] = profile_sum_anomaly
        if profile_mean_anomaly is not UNSET:
            field_dict["profile_mean_anomaly"] = profile_mean_anomaly
        if profile_median_anomaly is not UNSET:
            field_dict["profile_median_anomaly"] = profile_median_anomaly
        if profile_min_anomaly is not UNSET:
            field_dict["profile_min_anomaly"] = profile_min_anomaly
        if profile_max_anomaly is not UNSET:
            field_dict["profile_max_anomaly"] = profile_max_anomaly
        if profile_mean_change is not UNSET:
            field_dict["profile_mean_change"] = profile_mean_change
        if profile_median_change is not UNSET:
            field_dict["profile_median_change"] = profile_median_change
        if profile_sum_change is not UNSET:
            field_dict["profile_sum_change"] = profile_sum_change
        if profile_mean_change_1_day is not UNSET:
            field_dict["profile_mean_change_1_day"] = profile_mean_change_1_day
        if profile_mean_change_7_days is not UNSET:
            field_dict["profile_mean_change_7_days"] = profile_mean_change_7_days
        if profile_mean_change_30_days is not UNSET:
            field_dict["profile_mean_change_30_days"] = profile_mean_change_30_days
        if profile_median_change_1_day is not UNSET:
            field_dict["profile_median_change_1_day"] = profile_median_change_1_day
        if profile_median_change_7_days is not UNSET:
            field_dict["profile_median_change_7_days"] = profile_median_change_7_days
        if profile_median_change_30_days is not UNSET:
            field_dict["profile_median_change_30_days"] = profile_median_change_30_days
        if profile_sum_change_1_day is not UNSET:
            field_dict["profile_sum_change_1_day"] = profile_sum_change_1_day
        if profile_sum_change_7_days is not UNSET:
            field_dict["profile_sum_change_7_days"] = profile_sum_change_7_days
        if profile_sum_change_30_days is not UNSET:
            field_dict["profile_sum_change_30_days"] = profile_sum_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_profiling_checks_spec_custom_checks import (
            ColumnAnomalyProfilingChecksSpecCustomChecks,
        )
        from ..models.column_max_anomaly_differencing_check_spec import (
            ColumnMaxAnomalyDifferencingCheckSpec,
        )
        from ..models.column_mean_anomaly_stationary_check_spec import (
            ColumnMeanAnomalyStationaryCheckSpec,
        )
        from ..models.column_mean_change_1_day_check_spec import (
            ColumnMeanChange1DayCheckSpec,
        )
        from ..models.column_mean_change_7_days_check_spec import (
            ColumnMeanChange7DaysCheckSpec,
        )
        from ..models.column_mean_change_30_days_check_spec import (
            ColumnMeanChange30DaysCheckSpec,
        )
        from ..models.column_mean_change_check_spec import ColumnMeanChangeCheckSpec
        from ..models.column_median_anomaly_stationary_check_spec import (
            ColumnMedianAnomalyStationaryCheckSpec,
        )
        from ..models.column_median_change_1_day_check_spec import (
            ColumnMedianChange1DayCheckSpec,
        )
        from ..models.column_median_change_7_days_check_spec import (
            ColumnMedianChange7DaysCheckSpec,
        )
        from ..models.column_median_change_30_days_check_spec import (
            ColumnMedianChange30DaysCheckSpec,
        )
        from ..models.column_median_change_check_spec import ColumnMedianChangeCheckSpec
        from ..models.column_min_anomaly_differencing_check_spec import (
            ColumnMinAnomalyDifferencingCheckSpec,
        )
        from ..models.column_sum_anomaly_differencing_check_spec import (
            ColumnSumAnomalyDifferencingCheckSpec,
        )
        from ..models.column_sum_change_1_day_check_spec import (
            ColumnSumChange1DayCheckSpec,
        )
        from ..models.column_sum_change_7_days_check_spec import (
            ColumnSumChange7DaysCheckSpec,
        )
        from ..models.column_sum_change_30_days_check_spec import (
            ColumnSumChange30DaysCheckSpec,
        )
        from ..models.column_sum_change_check_spec import ColumnSumChangeCheckSpec

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnAnomalyProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnAnomalyProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_sum_anomaly = d.pop("profile_sum_anomaly", UNSET)
        profile_sum_anomaly: Union[Unset, ColumnSumAnomalyDifferencingCheckSpec]
        if isinstance(_profile_sum_anomaly, Unset):
            profile_sum_anomaly = UNSET
        else:
            profile_sum_anomaly = ColumnSumAnomalyDifferencingCheckSpec.from_dict(
                _profile_sum_anomaly
            )

        _profile_mean_anomaly = d.pop("profile_mean_anomaly", UNSET)
        profile_mean_anomaly: Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]
        if isinstance(_profile_mean_anomaly, Unset):
            profile_mean_anomaly = UNSET
        else:
            profile_mean_anomaly = ColumnMeanAnomalyStationaryCheckSpec.from_dict(
                _profile_mean_anomaly
            )

        _profile_median_anomaly = d.pop("profile_median_anomaly", UNSET)
        profile_median_anomaly: Union[Unset, ColumnMedianAnomalyStationaryCheckSpec]
        if isinstance(_profile_median_anomaly, Unset):
            profile_median_anomaly = UNSET
        else:
            profile_median_anomaly = ColumnMedianAnomalyStationaryCheckSpec.from_dict(
                _profile_median_anomaly
            )

        _profile_min_anomaly = d.pop("profile_min_anomaly", UNSET)
        profile_min_anomaly: Union[Unset, ColumnMinAnomalyDifferencingCheckSpec]
        if isinstance(_profile_min_anomaly, Unset):
            profile_min_anomaly = UNSET
        else:
            profile_min_anomaly = ColumnMinAnomalyDifferencingCheckSpec.from_dict(
                _profile_min_anomaly
            )

        _profile_max_anomaly = d.pop("profile_max_anomaly", UNSET)
        profile_max_anomaly: Union[Unset, ColumnMaxAnomalyDifferencingCheckSpec]
        if isinstance(_profile_max_anomaly, Unset):
            profile_max_anomaly = UNSET
        else:
            profile_max_anomaly = ColumnMaxAnomalyDifferencingCheckSpec.from_dict(
                _profile_max_anomaly
            )

        _profile_mean_change = d.pop("profile_mean_change", UNSET)
        profile_mean_change: Union[Unset, ColumnMeanChangeCheckSpec]
        if isinstance(_profile_mean_change, Unset):
            profile_mean_change = UNSET
        else:
            profile_mean_change = ColumnMeanChangeCheckSpec.from_dict(
                _profile_mean_change
            )

        _profile_median_change = d.pop("profile_median_change", UNSET)
        profile_median_change: Union[Unset, ColumnMedianChangeCheckSpec]
        if isinstance(_profile_median_change, Unset):
            profile_median_change = UNSET
        else:
            profile_median_change = ColumnMedianChangeCheckSpec.from_dict(
                _profile_median_change
            )

        _profile_sum_change = d.pop("profile_sum_change", UNSET)
        profile_sum_change: Union[Unset, ColumnSumChangeCheckSpec]
        if isinstance(_profile_sum_change, Unset):
            profile_sum_change = UNSET
        else:
            profile_sum_change = ColumnSumChangeCheckSpec.from_dict(_profile_sum_change)

        _profile_mean_change_1_day = d.pop("profile_mean_change_1_day", UNSET)
        profile_mean_change_1_day: Union[Unset, ColumnMeanChange1DayCheckSpec]
        if isinstance(_profile_mean_change_1_day, Unset):
            profile_mean_change_1_day = UNSET
        else:
            profile_mean_change_1_day = ColumnMeanChange1DayCheckSpec.from_dict(
                _profile_mean_change_1_day
            )

        _profile_mean_change_7_days = d.pop("profile_mean_change_7_days", UNSET)
        profile_mean_change_7_days: Union[Unset, ColumnMeanChange7DaysCheckSpec]
        if isinstance(_profile_mean_change_7_days, Unset):
            profile_mean_change_7_days = UNSET
        else:
            profile_mean_change_7_days = ColumnMeanChange7DaysCheckSpec.from_dict(
                _profile_mean_change_7_days
            )

        _profile_mean_change_30_days = d.pop("profile_mean_change_30_days", UNSET)
        profile_mean_change_30_days: Union[Unset, ColumnMeanChange30DaysCheckSpec]
        if isinstance(_profile_mean_change_30_days, Unset):
            profile_mean_change_30_days = UNSET
        else:
            profile_mean_change_30_days = ColumnMeanChange30DaysCheckSpec.from_dict(
                _profile_mean_change_30_days
            )

        _profile_median_change_1_day = d.pop("profile_median_change_1_day", UNSET)
        profile_median_change_1_day: Union[Unset, ColumnMedianChange1DayCheckSpec]
        if isinstance(_profile_median_change_1_day, Unset):
            profile_median_change_1_day = UNSET
        else:
            profile_median_change_1_day = ColumnMedianChange1DayCheckSpec.from_dict(
                _profile_median_change_1_day
            )

        _profile_median_change_7_days = d.pop("profile_median_change_7_days", UNSET)
        profile_median_change_7_days: Union[Unset, ColumnMedianChange7DaysCheckSpec]
        if isinstance(_profile_median_change_7_days, Unset):
            profile_median_change_7_days = UNSET
        else:
            profile_median_change_7_days = ColumnMedianChange7DaysCheckSpec.from_dict(
                _profile_median_change_7_days
            )

        _profile_median_change_30_days = d.pop("profile_median_change_30_days", UNSET)
        profile_median_change_30_days: Union[Unset, ColumnMedianChange30DaysCheckSpec]
        if isinstance(_profile_median_change_30_days, Unset):
            profile_median_change_30_days = UNSET
        else:
            profile_median_change_30_days = ColumnMedianChange30DaysCheckSpec.from_dict(
                _profile_median_change_30_days
            )

        _profile_sum_change_1_day = d.pop("profile_sum_change_1_day", UNSET)
        profile_sum_change_1_day: Union[Unset, ColumnSumChange1DayCheckSpec]
        if isinstance(_profile_sum_change_1_day, Unset):
            profile_sum_change_1_day = UNSET
        else:
            profile_sum_change_1_day = ColumnSumChange1DayCheckSpec.from_dict(
                _profile_sum_change_1_day
            )

        _profile_sum_change_7_days = d.pop("profile_sum_change_7_days", UNSET)
        profile_sum_change_7_days: Union[Unset, ColumnSumChange7DaysCheckSpec]
        if isinstance(_profile_sum_change_7_days, Unset):
            profile_sum_change_7_days = UNSET
        else:
            profile_sum_change_7_days = ColumnSumChange7DaysCheckSpec.from_dict(
                _profile_sum_change_7_days
            )

        _profile_sum_change_30_days = d.pop("profile_sum_change_30_days", UNSET)
        profile_sum_change_30_days: Union[Unset, ColumnSumChange30DaysCheckSpec]
        if isinstance(_profile_sum_change_30_days, Unset):
            profile_sum_change_30_days = UNSET
        else:
            profile_sum_change_30_days = ColumnSumChange30DaysCheckSpec.from_dict(
                _profile_sum_change_30_days
            )

        column_anomaly_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_sum_anomaly=profile_sum_anomaly,
            profile_mean_anomaly=profile_mean_anomaly,
            profile_median_anomaly=profile_median_anomaly,
            profile_min_anomaly=profile_min_anomaly,
            profile_max_anomaly=profile_max_anomaly,
            profile_mean_change=profile_mean_change,
            profile_median_change=profile_median_change,
            profile_sum_change=profile_sum_change,
            profile_mean_change_1_day=profile_mean_change_1_day,
            profile_mean_change_7_days=profile_mean_change_7_days,
            profile_mean_change_30_days=profile_mean_change_30_days,
            profile_median_change_1_day=profile_median_change_1_day,
            profile_median_change_7_days=profile_median_change_7_days,
            profile_median_change_30_days=profile_median_change_30_days,
            profile_sum_change_1_day=profile_sum_change_1_day,
            profile_sum_change_7_days=profile_sum_change_7_days,
            profile_sum_change_30_days=profile_sum_change_30_days,
        )

        column_anomaly_profiling_checks_spec.additional_properties = d
        return column_anomaly_profiling_checks_spec

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
