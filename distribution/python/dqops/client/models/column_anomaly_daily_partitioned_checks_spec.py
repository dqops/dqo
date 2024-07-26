from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_daily_partitioned_checks_spec_custom_checks import (
        ColumnAnomalyDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_max_anomaly_stationary_check_spec import (
        ColumnMaxAnomalyStationaryCheckSpec,
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
    from ..models.column_min_anomaly_stationary_check_spec import (
        ColumnMinAnomalyStationaryCheckSpec,
    )
    from ..models.column_sum_anomaly_stationary_partition_check_spec import (
        ColumnSumAnomalyStationaryPartitionCheckSpec,
    )
    from ..models.column_sum_change_1_day_check_spec import ColumnSumChange1DayCheckSpec
    from ..models.column_sum_change_7_days_check_spec import (
        ColumnSumChange7DaysCheckSpec,
    )
    from ..models.column_sum_change_30_days_check_spec import (
        ColumnSumChange30DaysCheckSpec,
    )
    from ..models.column_sum_change_check_spec import ColumnSumChangeCheckSpec


T = TypeVar("T", bound="ColumnAnomalyDailyPartitionedChecksSpec")


@_attrs_define
class ColumnAnomalyDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_sum_anomaly (Union[Unset, ColumnSumAnomalyStationaryPartitionCheckSpec]):
        daily_partition_mean_anomaly (Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]):
        daily_partition_median_anomaly (Union[Unset, ColumnMedianAnomalyStationaryCheckSpec]):
        daily_partition_min_anomaly (Union[Unset, ColumnMinAnomalyStationaryCheckSpec]):
        daily_partition_max_anomaly (Union[Unset, ColumnMaxAnomalyStationaryCheckSpec]):
        daily_partition_mean_change (Union[Unset, ColumnMeanChangeCheckSpec]):
        daily_partition_median_change (Union[Unset, ColumnMedianChangeCheckSpec]):
        daily_partition_sum_change (Union[Unset, ColumnSumChangeCheckSpec]):
        daily_partition_mean_change_1_day (Union[Unset, ColumnMeanChange1DayCheckSpec]):
        daily_partition_mean_change_7_days (Union[Unset, ColumnMeanChange7DaysCheckSpec]):
        daily_partition_mean_change_30_days (Union[Unset, ColumnMeanChange30DaysCheckSpec]):
        daily_partition_median_change_1_day (Union[Unset, ColumnMedianChange1DayCheckSpec]):
        daily_partition_median_change_7_days (Union[Unset, ColumnMedianChange7DaysCheckSpec]):
        daily_partition_median_change_30_days (Union[Unset, ColumnMedianChange30DaysCheckSpec]):
        daily_partition_sum_change_1_day (Union[Unset, ColumnSumChange1DayCheckSpec]):
        daily_partition_sum_change_7_days (Union[Unset, ColumnSumChange7DaysCheckSpec]):
        daily_partition_sum_change_30_days (Union[Unset, ColumnSumChange30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAnomalyDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_sum_anomaly: Union[
        Unset, "ColumnSumAnomalyStationaryPartitionCheckSpec"
    ] = UNSET
    daily_partition_mean_anomaly: Union[
        Unset, "ColumnMeanAnomalyStationaryCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly: Union[
        Unset, "ColumnMedianAnomalyStationaryCheckSpec"
    ] = UNSET
    daily_partition_min_anomaly: Union[Unset, "ColumnMinAnomalyStationaryCheckSpec"] = (
        UNSET
    )
    daily_partition_max_anomaly: Union[Unset, "ColumnMaxAnomalyStationaryCheckSpec"] = (
        UNSET
    )
    daily_partition_mean_change: Union[Unset, "ColumnMeanChangeCheckSpec"] = UNSET
    daily_partition_median_change: Union[Unset, "ColumnMedianChangeCheckSpec"] = UNSET
    daily_partition_sum_change: Union[Unset, "ColumnSumChangeCheckSpec"] = UNSET
    daily_partition_mean_change_1_day: Union[Unset, "ColumnMeanChange1DayCheckSpec"] = (
        UNSET
    )
    daily_partition_mean_change_7_days: Union[
        Unset, "ColumnMeanChange7DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_change_30_days: Union[
        Unset, "ColumnMeanChange30DaysCheckSpec"
    ] = UNSET
    daily_partition_median_change_1_day: Union[
        Unset, "ColumnMedianChange1DayCheckSpec"
    ] = UNSET
    daily_partition_median_change_7_days: Union[
        Unset, "ColumnMedianChange7DaysCheckSpec"
    ] = UNSET
    daily_partition_median_change_30_days: Union[
        Unset, "ColumnMedianChange30DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_change_1_day: Union[Unset, "ColumnSumChange1DayCheckSpec"] = (
        UNSET
    )
    daily_partition_sum_change_7_days: Union[Unset, "ColumnSumChange7DaysCheckSpec"] = (
        UNSET
    )
    daily_partition_sum_change_30_days: Union[
        Unset, "ColumnSumChange30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_sum_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly, Unset):
            daily_partition_sum_anomaly = self.daily_partition_sum_anomaly.to_dict()

        daily_partition_mean_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly, Unset):
            daily_partition_mean_anomaly = self.daily_partition_mean_anomaly.to_dict()

        daily_partition_median_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_anomaly, Unset):
            daily_partition_median_anomaly = (
                self.daily_partition_median_anomaly.to_dict()
            )

        daily_partition_min_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_min_anomaly, Unset):
            daily_partition_min_anomaly = self.daily_partition_min_anomaly.to_dict()

        daily_partition_max_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_max_anomaly, Unset):
            daily_partition_max_anomaly = self.daily_partition_max_anomaly.to_dict()

        daily_partition_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change, Unset):
            daily_partition_mean_change = self.daily_partition_mean_change.to_dict()

        daily_partition_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change, Unset):
            daily_partition_median_change = self.daily_partition_median_change.to_dict()

        daily_partition_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change, Unset):
            daily_partition_sum_change = self.daily_partition_sum_change.to_dict()

        daily_partition_mean_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change_1_day, Unset):
            daily_partition_mean_change_1_day = (
                self.daily_partition_mean_change_1_day.to_dict()
            )

        daily_partition_mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change_7_days, Unset):
            daily_partition_mean_change_7_days = (
                self.daily_partition_mean_change_7_days.to_dict()
            )

        daily_partition_mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change_30_days, Unset):
            daily_partition_mean_change_30_days = (
                self.daily_partition_mean_change_30_days.to_dict()
            )

        daily_partition_median_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change_1_day, Unset):
            daily_partition_median_change_1_day = (
                self.daily_partition_median_change_1_day.to_dict()
            )

        daily_partition_median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change_7_days, Unset):
            daily_partition_median_change_7_days = (
                self.daily_partition_median_change_7_days.to_dict()
            )

        daily_partition_median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change_30_days, Unset):
            daily_partition_median_change_30_days = (
                self.daily_partition_median_change_30_days.to_dict()
            )

        daily_partition_sum_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change_1_day, Unset):
            daily_partition_sum_change_1_day = (
                self.daily_partition_sum_change_1_day.to_dict()
            )

        daily_partition_sum_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change_7_days, Unset):
            daily_partition_sum_change_7_days = (
                self.daily_partition_sum_change_7_days.to_dict()
            )

        daily_partition_sum_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change_30_days, Unset):
            daily_partition_sum_change_30_days = (
                self.daily_partition_sum_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_sum_anomaly is not UNSET:
            field_dict["daily_partition_sum_anomaly"] = daily_partition_sum_anomaly
        if daily_partition_mean_anomaly is not UNSET:
            field_dict["daily_partition_mean_anomaly"] = daily_partition_mean_anomaly
        if daily_partition_median_anomaly is not UNSET:
            field_dict["daily_partition_median_anomaly"] = (
                daily_partition_median_anomaly
            )
        if daily_partition_min_anomaly is not UNSET:
            field_dict["daily_partition_min_anomaly"] = daily_partition_min_anomaly
        if daily_partition_max_anomaly is not UNSET:
            field_dict["daily_partition_max_anomaly"] = daily_partition_max_anomaly
        if daily_partition_mean_change is not UNSET:
            field_dict["daily_partition_mean_change"] = daily_partition_mean_change
        if daily_partition_median_change is not UNSET:
            field_dict["daily_partition_median_change"] = daily_partition_median_change
        if daily_partition_sum_change is not UNSET:
            field_dict["daily_partition_sum_change"] = daily_partition_sum_change
        if daily_partition_mean_change_1_day is not UNSET:
            field_dict["daily_partition_mean_change_1_day"] = (
                daily_partition_mean_change_1_day
            )
        if daily_partition_mean_change_7_days is not UNSET:
            field_dict["daily_partition_mean_change_7_days"] = (
                daily_partition_mean_change_7_days
            )
        if daily_partition_mean_change_30_days is not UNSET:
            field_dict["daily_partition_mean_change_30_days"] = (
                daily_partition_mean_change_30_days
            )
        if daily_partition_median_change_1_day is not UNSET:
            field_dict["daily_partition_median_change_1_day"] = (
                daily_partition_median_change_1_day
            )
        if daily_partition_median_change_7_days is not UNSET:
            field_dict["daily_partition_median_change_7_days"] = (
                daily_partition_median_change_7_days
            )
        if daily_partition_median_change_30_days is not UNSET:
            field_dict["daily_partition_median_change_30_days"] = (
                daily_partition_median_change_30_days
            )
        if daily_partition_sum_change_1_day is not UNSET:
            field_dict["daily_partition_sum_change_1_day"] = (
                daily_partition_sum_change_1_day
            )
        if daily_partition_sum_change_7_days is not UNSET:
            field_dict["daily_partition_sum_change_7_days"] = (
                daily_partition_sum_change_7_days
            )
        if daily_partition_sum_change_30_days is not UNSET:
            field_dict["daily_partition_sum_change_30_days"] = (
                daily_partition_sum_change_30_days
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_daily_partitioned_checks_spec_custom_checks import (
            ColumnAnomalyDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_max_anomaly_stationary_check_spec import (
            ColumnMaxAnomalyStationaryCheckSpec,
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
        from ..models.column_min_anomaly_stationary_check_spec import (
            ColumnMinAnomalyStationaryCheckSpec,
        )
        from ..models.column_sum_anomaly_stationary_partition_check_spec import (
            ColumnSumAnomalyStationaryPartitionCheckSpec,
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
        custom_checks: Union[Unset, ColumnAnomalyDailyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAnomalyDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_sum_anomaly = d.pop("daily_partition_sum_anomaly", UNSET)
        daily_partition_sum_anomaly: Union[
            Unset, ColumnSumAnomalyStationaryPartitionCheckSpec
        ]
        if isinstance(_daily_partition_sum_anomaly, Unset):
            daily_partition_sum_anomaly = UNSET
        else:
            daily_partition_sum_anomaly = (
                ColumnSumAnomalyStationaryPartitionCheckSpec.from_dict(
                    _daily_partition_sum_anomaly
                )
            )

        _daily_partition_mean_anomaly = d.pop("daily_partition_mean_anomaly", UNSET)
        daily_partition_mean_anomaly: Union[Unset, ColumnMeanAnomalyStationaryCheckSpec]
        if isinstance(_daily_partition_mean_anomaly, Unset):
            daily_partition_mean_anomaly = UNSET
        else:
            daily_partition_mean_anomaly = (
                ColumnMeanAnomalyStationaryCheckSpec.from_dict(
                    _daily_partition_mean_anomaly
                )
            )

        _daily_partition_median_anomaly = d.pop("daily_partition_median_anomaly", UNSET)
        daily_partition_median_anomaly: Union[
            Unset, ColumnMedianAnomalyStationaryCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly, Unset):
            daily_partition_median_anomaly = UNSET
        else:
            daily_partition_median_anomaly = (
                ColumnMedianAnomalyStationaryCheckSpec.from_dict(
                    _daily_partition_median_anomaly
                )
            )

        _daily_partition_min_anomaly = d.pop("daily_partition_min_anomaly", UNSET)
        daily_partition_min_anomaly: Union[Unset, ColumnMinAnomalyStationaryCheckSpec]
        if isinstance(_daily_partition_min_anomaly, Unset):
            daily_partition_min_anomaly = UNSET
        else:
            daily_partition_min_anomaly = ColumnMinAnomalyStationaryCheckSpec.from_dict(
                _daily_partition_min_anomaly
            )

        _daily_partition_max_anomaly = d.pop("daily_partition_max_anomaly", UNSET)
        daily_partition_max_anomaly: Union[Unset, ColumnMaxAnomalyStationaryCheckSpec]
        if isinstance(_daily_partition_max_anomaly, Unset):
            daily_partition_max_anomaly = UNSET
        else:
            daily_partition_max_anomaly = ColumnMaxAnomalyStationaryCheckSpec.from_dict(
                _daily_partition_max_anomaly
            )

        _daily_partition_mean_change = d.pop("daily_partition_mean_change", UNSET)
        daily_partition_mean_change: Union[Unset, ColumnMeanChangeCheckSpec]
        if isinstance(_daily_partition_mean_change, Unset):
            daily_partition_mean_change = UNSET
        else:
            daily_partition_mean_change = ColumnMeanChangeCheckSpec.from_dict(
                _daily_partition_mean_change
            )

        _daily_partition_median_change = d.pop("daily_partition_median_change", UNSET)
        daily_partition_median_change: Union[Unset, ColumnMedianChangeCheckSpec]
        if isinstance(_daily_partition_median_change, Unset):
            daily_partition_median_change = UNSET
        else:
            daily_partition_median_change = ColumnMedianChangeCheckSpec.from_dict(
                _daily_partition_median_change
            )

        _daily_partition_sum_change = d.pop("daily_partition_sum_change", UNSET)
        daily_partition_sum_change: Union[Unset, ColumnSumChangeCheckSpec]
        if isinstance(_daily_partition_sum_change, Unset):
            daily_partition_sum_change = UNSET
        else:
            daily_partition_sum_change = ColumnSumChangeCheckSpec.from_dict(
                _daily_partition_sum_change
            )

        _daily_partition_mean_change_1_day = d.pop(
            "daily_partition_mean_change_1_day", UNSET
        )
        daily_partition_mean_change_1_day: Union[Unset, ColumnMeanChange1DayCheckSpec]
        if isinstance(_daily_partition_mean_change_1_day, Unset):
            daily_partition_mean_change_1_day = UNSET
        else:
            daily_partition_mean_change_1_day = ColumnMeanChange1DayCheckSpec.from_dict(
                _daily_partition_mean_change_1_day
            )

        _daily_partition_mean_change_7_days = d.pop(
            "daily_partition_mean_change_7_days", UNSET
        )
        daily_partition_mean_change_7_days: Union[Unset, ColumnMeanChange7DaysCheckSpec]
        if isinstance(_daily_partition_mean_change_7_days, Unset):
            daily_partition_mean_change_7_days = UNSET
        else:
            daily_partition_mean_change_7_days = (
                ColumnMeanChange7DaysCheckSpec.from_dict(
                    _daily_partition_mean_change_7_days
                )
            )

        _daily_partition_mean_change_30_days = d.pop(
            "daily_partition_mean_change_30_days", UNSET
        )
        daily_partition_mean_change_30_days: Union[
            Unset, ColumnMeanChange30DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_change_30_days, Unset):
            daily_partition_mean_change_30_days = UNSET
        else:
            daily_partition_mean_change_30_days = (
                ColumnMeanChange30DaysCheckSpec.from_dict(
                    _daily_partition_mean_change_30_days
                )
            )

        _daily_partition_median_change_1_day = d.pop(
            "daily_partition_median_change_1_day", UNSET
        )
        daily_partition_median_change_1_day: Union[
            Unset, ColumnMedianChange1DayCheckSpec
        ]
        if isinstance(_daily_partition_median_change_1_day, Unset):
            daily_partition_median_change_1_day = UNSET
        else:
            daily_partition_median_change_1_day = (
                ColumnMedianChange1DayCheckSpec.from_dict(
                    _daily_partition_median_change_1_day
                )
            )

        _daily_partition_median_change_7_days = d.pop(
            "daily_partition_median_change_7_days", UNSET
        )
        daily_partition_median_change_7_days: Union[
            Unset, ColumnMedianChange7DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_change_7_days, Unset):
            daily_partition_median_change_7_days = UNSET
        else:
            daily_partition_median_change_7_days = (
                ColumnMedianChange7DaysCheckSpec.from_dict(
                    _daily_partition_median_change_7_days
                )
            )

        _daily_partition_median_change_30_days = d.pop(
            "daily_partition_median_change_30_days", UNSET
        )
        daily_partition_median_change_30_days: Union[
            Unset, ColumnMedianChange30DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_change_30_days, Unset):
            daily_partition_median_change_30_days = UNSET
        else:
            daily_partition_median_change_30_days = (
                ColumnMedianChange30DaysCheckSpec.from_dict(
                    _daily_partition_median_change_30_days
                )
            )

        _daily_partition_sum_change_1_day = d.pop(
            "daily_partition_sum_change_1_day", UNSET
        )
        daily_partition_sum_change_1_day: Union[Unset, ColumnSumChange1DayCheckSpec]
        if isinstance(_daily_partition_sum_change_1_day, Unset):
            daily_partition_sum_change_1_day = UNSET
        else:
            daily_partition_sum_change_1_day = ColumnSumChange1DayCheckSpec.from_dict(
                _daily_partition_sum_change_1_day
            )

        _daily_partition_sum_change_7_days = d.pop(
            "daily_partition_sum_change_7_days", UNSET
        )
        daily_partition_sum_change_7_days: Union[Unset, ColumnSumChange7DaysCheckSpec]
        if isinstance(_daily_partition_sum_change_7_days, Unset):
            daily_partition_sum_change_7_days = UNSET
        else:
            daily_partition_sum_change_7_days = ColumnSumChange7DaysCheckSpec.from_dict(
                _daily_partition_sum_change_7_days
            )

        _daily_partition_sum_change_30_days = d.pop(
            "daily_partition_sum_change_30_days", UNSET
        )
        daily_partition_sum_change_30_days: Union[Unset, ColumnSumChange30DaysCheckSpec]
        if isinstance(_daily_partition_sum_change_30_days, Unset):
            daily_partition_sum_change_30_days = UNSET
        else:
            daily_partition_sum_change_30_days = (
                ColumnSumChange30DaysCheckSpec.from_dict(
                    _daily_partition_sum_change_30_days
                )
            )

        column_anomaly_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_sum_anomaly=daily_partition_sum_anomaly,
            daily_partition_mean_anomaly=daily_partition_mean_anomaly,
            daily_partition_median_anomaly=daily_partition_median_anomaly,
            daily_partition_min_anomaly=daily_partition_min_anomaly,
            daily_partition_max_anomaly=daily_partition_max_anomaly,
            daily_partition_mean_change=daily_partition_mean_change,
            daily_partition_median_change=daily_partition_median_change,
            daily_partition_sum_change=daily_partition_sum_change,
            daily_partition_mean_change_1_day=daily_partition_mean_change_1_day,
            daily_partition_mean_change_7_days=daily_partition_mean_change_7_days,
            daily_partition_mean_change_30_days=daily_partition_mean_change_30_days,
            daily_partition_median_change_1_day=daily_partition_median_change_1_day,
            daily_partition_median_change_7_days=daily_partition_median_change_7_days,
            daily_partition_median_change_30_days=daily_partition_median_change_30_days,
            daily_partition_sum_change_1_day=daily_partition_sum_change_1_day,
            daily_partition_sum_change_7_days=daily_partition_sum_change_7_days,
            daily_partition_sum_change_30_days=daily_partition_sum_change_30_days,
        )

        column_anomaly_daily_partitioned_checks_spec.additional_properties = d
        return column_anomaly_daily_partitioned_checks_spec

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
