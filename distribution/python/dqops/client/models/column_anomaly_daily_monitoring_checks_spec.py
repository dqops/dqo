from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_daily_monitoring_checks_spec_custom_checks import (
        ColumnAnomalyDailyMonitoringChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnAnomalyDailyMonitoringChecksSpec")


@_attrs_define
class ColumnAnomalyDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_sum_anomaly (Union[Unset, ColumnSumAnomalyDifferencingCheckSpec]):
        daily_mean_anomaly (Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]):
        daily_median_anomaly (Union[Unset, ColumnMedianAnomalyStationaryCheckSpec]):
        daily_min_anomaly (Union[Unset, ColumnMinAnomalyDifferencingCheckSpec]):
        daily_max_anomaly (Union[Unset, ColumnMaxAnomalyDifferencingCheckSpec]):
        daily_mean_change (Union[Unset, ColumnMeanChangeCheckSpec]):
        daily_median_change (Union[Unset, ColumnMedianChangeCheckSpec]):
        daily_sum_change (Union[Unset, ColumnSumChangeCheckSpec]):
        daily_mean_change_1_day (Union[Unset, ColumnMeanChange1DayCheckSpec]):
        daily_mean_change_7_days (Union[Unset, ColumnMeanChange7DaysCheckSpec]):
        daily_mean_change_30_days (Union[Unset, ColumnMeanChange30DaysCheckSpec]):
        daily_median_change_1_day (Union[Unset, ColumnMedianChange1DayCheckSpec]):
        daily_median_change_7_days (Union[Unset, ColumnMedianChange7DaysCheckSpec]):
        daily_median_change_30_days (Union[Unset, ColumnMedianChange30DaysCheckSpec]):
        daily_sum_change_1_day (Union[Unset, ColumnSumChange1DayCheckSpec]):
        daily_sum_change_7_days (Union[Unset, ColumnSumChange7DaysCheckSpec]):
        daily_sum_change_30_days (Union[Unset, ColumnSumChange30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAnomalyDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_sum_anomaly: Union[Unset, "ColumnSumAnomalyDifferencingCheckSpec"] = UNSET
    daily_mean_anomaly: Union[Unset, "ColumnMeanAnomalyStationaryCheckSpec"] = UNSET
    daily_median_anomaly: Union[Unset, "ColumnMedianAnomalyStationaryCheckSpec"] = UNSET
    daily_min_anomaly: Union[Unset, "ColumnMinAnomalyDifferencingCheckSpec"] = UNSET
    daily_max_anomaly: Union[Unset, "ColumnMaxAnomalyDifferencingCheckSpec"] = UNSET
    daily_mean_change: Union[Unset, "ColumnMeanChangeCheckSpec"] = UNSET
    daily_median_change: Union[Unset, "ColumnMedianChangeCheckSpec"] = UNSET
    daily_sum_change: Union[Unset, "ColumnSumChangeCheckSpec"] = UNSET
    daily_mean_change_1_day: Union[Unset, "ColumnMeanChange1DayCheckSpec"] = UNSET
    daily_mean_change_7_days: Union[Unset, "ColumnMeanChange7DaysCheckSpec"] = UNSET
    daily_mean_change_30_days: Union[Unset, "ColumnMeanChange30DaysCheckSpec"] = UNSET
    daily_median_change_1_day: Union[Unset, "ColumnMedianChange1DayCheckSpec"] = UNSET
    daily_median_change_7_days: Union[Unset, "ColumnMedianChange7DaysCheckSpec"] = UNSET
    daily_median_change_30_days: Union[Unset, "ColumnMedianChange30DaysCheckSpec"] = (
        UNSET
    )
    daily_sum_change_1_day: Union[Unset, "ColumnSumChange1DayCheckSpec"] = UNSET
    daily_sum_change_7_days: Union[Unset, "ColumnSumChange7DaysCheckSpec"] = UNSET
    daily_sum_change_30_days: Union[Unset, "ColumnSumChange30DaysCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_sum_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_anomaly, Unset):
            daily_sum_anomaly = self.daily_sum_anomaly.to_dict()

        daily_mean_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_anomaly, Unset):
            daily_mean_anomaly = self.daily_mean_anomaly.to_dict()

        daily_median_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_anomaly, Unset):
            daily_median_anomaly = self.daily_median_anomaly.to_dict()

        daily_min_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_min_anomaly, Unset):
            daily_min_anomaly = self.daily_min_anomaly.to_dict()

        daily_max_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_max_anomaly, Unset):
            daily_max_anomaly = self.daily_max_anomaly.to_dict()

        daily_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change, Unset):
            daily_mean_change = self.daily_mean_change.to_dict()

        daily_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change, Unset):
            daily_median_change = self.daily_median_change.to_dict()

        daily_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change, Unset):
            daily_sum_change = self.daily_sum_change.to_dict()

        daily_mean_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_1_day, Unset):
            daily_mean_change_1_day = self.daily_mean_change_1_day.to_dict()

        daily_mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_7_days, Unset):
            daily_mean_change_7_days = self.daily_mean_change_7_days.to_dict()

        daily_mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_30_days, Unset):
            daily_mean_change_30_days = self.daily_mean_change_30_days.to_dict()

        daily_median_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_1_day, Unset):
            daily_median_change_1_day = self.daily_median_change_1_day.to_dict()

        daily_median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_7_days, Unset):
            daily_median_change_7_days = self.daily_median_change_7_days.to_dict()

        daily_median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_30_days, Unset):
            daily_median_change_30_days = self.daily_median_change_30_days.to_dict()

        daily_sum_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_1_day, Unset):
            daily_sum_change_1_day = self.daily_sum_change_1_day.to_dict()

        daily_sum_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_7_days, Unset):
            daily_sum_change_7_days = self.daily_sum_change_7_days.to_dict()

        daily_sum_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_30_days, Unset):
            daily_sum_change_30_days = self.daily_sum_change_30_days.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_sum_anomaly is not UNSET:
            field_dict["daily_sum_anomaly"] = daily_sum_anomaly
        if daily_mean_anomaly is not UNSET:
            field_dict["daily_mean_anomaly"] = daily_mean_anomaly
        if daily_median_anomaly is not UNSET:
            field_dict["daily_median_anomaly"] = daily_median_anomaly
        if daily_min_anomaly is not UNSET:
            field_dict["daily_min_anomaly"] = daily_min_anomaly
        if daily_max_anomaly is not UNSET:
            field_dict["daily_max_anomaly"] = daily_max_anomaly
        if daily_mean_change is not UNSET:
            field_dict["daily_mean_change"] = daily_mean_change
        if daily_median_change is not UNSET:
            field_dict["daily_median_change"] = daily_median_change
        if daily_sum_change is not UNSET:
            field_dict["daily_sum_change"] = daily_sum_change
        if daily_mean_change_1_day is not UNSET:
            field_dict["daily_mean_change_1_day"] = daily_mean_change_1_day
        if daily_mean_change_7_days is not UNSET:
            field_dict["daily_mean_change_7_days"] = daily_mean_change_7_days
        if daily_mean_change_30_days is not UNSET:
            field_dict["daily_mean_change_30_days"] = daily_mean_change_30_days
        if daily_median_change_1_day is not UNSET:
            field_dict["daily_median_change_1_day"] = daily_median_change_1_day
        if daily_median_change_7_days is not UNSET:
            field_dict["daily_median_change_7_days"] = daily_median_change_7_days
        if daily_median_change_30_days is not UNSET:
            field_dict["daily_median_change_30_days"] = daily_median_change_30_days
        if daily_sum_change_1_day is not UNSET:
            field_dict["daily_sum_change_1_day"] = daily_sum_change_1_day
        if daily_sum_change_7_days is not UNSET:
            field_dict["daily_sum_change_7_days"] = daily_sum_change_7_days
        if daily_sum_change_30_days is not UNSET:
            field_dict["daily_sum_change_30_days"] = daily_sum_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_daily_monitoring_checks_spec_custom_checks import (
            ColumnAnomalyDailyMonitoringChecksSpecCustomChecks,
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
        custom_checks: Union[Unset, ColumnAnomalyDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAnomalyDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_sum_anomaly = d.pop("daily_sum_anomaly", UNSET)
        daily_sum_anomaly: Union[Unset, ColumnSumAnomalyDifferencingCheckSpec]
        if isinstance(_daily_sum_anomaly, Unset):
            daily_sum_anomaly = UNSET
        else:
            daily_sum_anomaly = ColumnSumAnomalyDifferencingCheckSpec.from_dict(
                _daily_sum_anomaly
            )

        _daily_mean_anomaly = d.pop("daily_mean_anomaly", UNSET)
        daily_mean_anomaly: Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]
        if isinstance(_daily_mean_anomaly, Unset):
            daily_mean_anomaly = UNSET
        else:
            daily_mean_anomaly = ColumnMeanAnomalyStationaryCheckSpec.from_dict(
                _daily_mean_anomaly
            )

        _daily_median_anomaly = d.pop("daily_median_anomaly", UNSET)
        daily_median_anomaly: Union[Unset, ColumnMedianAnomalyStationaryCheckSpec]
        if isinstance(_daily_median_anomaly, Unset):
            daily_median_anomaly = UNSET
        else:
            daily_median_anomaly = ColumnMedianAnomalyStationaryCheckSpec.from_dict(
                _daily_median_anomaly
            )

        _daily_min_anomaly = d.pop("daily_min_anomaly", UNSET)
        daily_min_anomaly: Union[Unset, ColumnMinAnomalyDifferencingCheckSpec]
        if isinstance(_daily_min_anomaly, Unset):
            daily_min_anomaly = UNSET
        else:
            daily_min_anomaly = ColumnMinAnomalyDifferencingCheckSpec.from_dict(
                _daily_min_anomaly
            )

        _daily_max_anomaly = d.pop("daily_max_anomaly", UNSET)
        daily_max_anomaly: Union[Unset, ColumnMaxAnomalyDifferencingCheckSpec]
        if isinstance(_daily_max_anomaly, Unset):
            daily_max_anomaly = UNSET
        else:
            daily_max_anomaly = ColumnMaxAnomalyDifferencingCheckSpec.from_dict(
                _daily_max_anomaly
            )

        _daily_mean_change = d.pop("daily_mean_change", UNSET)
        daily_mean_change: Union[Unset, ColumnMeanChangeCheckSpec]
        if isinstance(_daily_mean_change, Unset):
            daily_mean_change = UNSET
        else:
            daily_mean_change = ColumnMeanChangeCheckSpec.from_dict(_daily_mean_change)

        _daily_median_change = d.pop("daily_median_change", UNSET)
        daily_median_change: Union[Unset, ColumnMedianChangeCheckSpec]
        if isinstance(_daily_median_change, Unset):
            daily_median_change = UNSET
        else:
            daily_median_change = ColumnMedianChangeCheckSpec.from_dict(
                _daily_median_change
            )

        _daily_sum_change = d.pop("daily_sum_change", UNSET)
        daily_sum_change: Union[Unset, ColumnSumChangeCheckSpec]
        if isinstance(_daily_sum_change, Unset):
            daily_sum_change = UNSET
        else:
            daily_sum_change = ColumnSumChangeCheckSpec.from_dict(_daily_sum_change)

        _daily_mean_change_1_day = d.pop("daily_mean_change_1_day", UNSET)
        daily_mean_change_1_day: Union[Unset, ColumnMeanChange1DayCheckSpec]
        if isinstance(_daily_mean_change_1_day, Unset):
            daily_mean_change_1_day = UNSET
        else:
            daily_mean_change_1_day = ColumnMeanChange1DayCheckSpec.from_dict(
                _daily_mean_change_1_day
            )

        _daily_mean_change_7_days = d.pop("daily_mean_change_7_days", UNSET)
        daily_mean_change_7_days: Union[Unset, ColumnMeanChange7DaysCheckSpec]
        if isinstance(_daily_mean_change_7_days, Unset):
            daily_mean_change_7_days = UNSET
        else:
            daily_mean_change_7_days = ColumnMeanChange7DaysCheckSpec.from_dict(
                _daily_mean_change_7_days
            )

        _daily_mean_change_30_days = d.pop("daily_mean_change_30_days", UNSET)
        daily_mean_change_30_days: Union[Unset, ColumnMeanChange30DaysCheckSpec]
        if isinstance(_daily_mean_change_30_days, Unset):
            daily_mean_change_30_days = UNSET
        else:
            daily_mean_change_30_days = ColumnMeanChange30DaysCheckSpec.from_dict(
                _daily_mean_change_30_days
            )

        _daily_median_change_1_day = d.pop("daily_median_change_1_day", UNSET)
        daily_median_change_1_day: Union[Unset, ColumnMedianChange1DayCheckSpec]
        if isinstance(_daily_median_change_1_day, Unset):
            daily_median_change_1_day = UNSET
        else:
            daily_median_change_1_day = ColumnMedianChange1DayCheckSpec.from_dict(
                _daily_median_change_1_day
            )

        _daily_median_change_7_days = d.pop("daily_median_change_7_days", UNSET)
        daily_median_change_7_days: Union[Unset, ColumnMedianChange7DaysCheckSpec]
        if isinstance(_daily_median_change_7_days, Unset):
            daily_median_change_7_days = UNSET
        else:
            daily_median_change_7_days = ColumnMedianChange7DaysCheckSpec.from_dict(
                _daily_median_change_7_days
            )

        _daily_median_change_30_days = d.pop("daily_median_change_30_days", UNSET)
        daily_median_change_30_days: Union[Unset, ColumnMedianChange30DaysCheckSpec]
        if isinstance(_daily_median_change_30_days, Unset):
            daily_median_change_30_days = UNSET
        else:
            daily_median_change_30_days = ColumnMedianChange30DaysCheckSpec.from_dict(
                _daily_median_change_30_days
            )

        _daily_sum_change_1_day = d.pop("daily_sum_change_1_day", UNSET)
        daily_sum_change_1_day: Union[Unset, ColumnSumChange1DayCheckSpec]
        if isinstance(_daily_sum_change_1_day, Unset):
            daily_sum_change_1_day = UNSET
        else:
            daily_sum_change_1_day = ColumnSumChange1DayCheckSpec.from_dict(
                _daily_sum_change_1_day
            )

        _daily_sum_change_7_days = d.pop("daily_sum_change_7_days", UNSET)
        daily_sum_change_7_days: Union[Unset, ColumnSumChange7DaysCheckSpec]
        if isinstance(_daily_sum_change_7_days, Unset):
            daily_sum_change_7_days = UNSET
        else:
            daily_sum_change_7_days = ColumnSumChange7DaysCheckSpec.from_dict(
                _daily_sum_change_7_days
            )

        _daily_sum_change_30_days = d.pop("daily_sum_change_30_days", UNSET)
        daily_sum_change_30_days: Union[Unset, ColumnSumChange30DaysCheckSpec]
        if isinstance(_daily_sum_change_30_days, Unset):
            daily_sum_change_30_days = UNSET
        else:
            daily_sum_change_30_days = ColumnSumChange30DaysCheckSpec.from_dict(
                _daily_sum_change_30_days
            )

        column_anomaly_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_sum_anomaly=daily_sum_anomaly,
            daily_mean_anomaly=daily_mean_anomaly,
            daily_median_anomaly=daily_median_anomaly,
            daily_min_anomaly=daily_min_anomaly,
            daily_max_anomaly=daily_max_anomaly,
            daily_mean_change=daily_mean_change,
            daily_median_change=daily_median_change,
            daily_sum_change=daily_sum_change,
            daily_mean_change_1_day=daily_mean_change_1_day,
            daily_mean_change_7_days=daily_mean_change_7_days,
            daily_mean_change_30_days=daily_mean_change_30_days,
            daily_median_change_1_day=daily_median_change_1_day,
            daily_median_change_7_days=daily_median_change_7_days,
            daily_median_change_30_days=daily_median_change_30_days,
            daily_sum_change_1_day=daily_sum_change_1_day,
            daily_sum_change_7_days=daily_sum_change_7_days,
            daily_sum_change_30_days=daily_sum_change_30_days,
        )

        column_anomaly_daily_monitoring_checks_spec.additional_properties = d
        return column_anomaly_daily_monitoring_checks_spec

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
