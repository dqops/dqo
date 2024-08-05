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
    from ..models.column_null_percent_anomaly_stationary_check_spec import (
        ColumnNullPercentAnomalyStationaryCheckSpec,
    )
    from ..models.column_null_percent_change_1_day_check_spec import (
        ColumnNullPercentChange1DayCheckSpec,
    )
    from ..models.column_null_percent_change_7_days_check_spec import (
        ColumnNullPercentChange7DaysCheckSpec,
    )
    from ..models.column_null_percent_change_30_days_check_spec import (
        ColumnNullPercentChange30DaysCheckSpec,
    )
    from ..models.column_null_percent_change_check_spec import (
        ColumnNullPercentChangeCheckSpec,
    )
    from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
    from ..models.column_nulls_daily_partitioned_checks_spec_custom_checks import (
        ColumnNullsDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec


T = TypeVar("T", bound="ColumnNullsDailyPartitionedChecksSpec")


@_attrs_define
class ColumnNullsDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNullsDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        daily_partition_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        daily_partition_nulls_percent_anomaly (Union[Unset, ColumnNullPercentAnomalyStationaryCheckSpec]):
        daily_partition_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        daily_partition_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
        daily_partition_empty_column_found (Union[Unset, ColumnEmptyColumnFoundCheckSpec]):
        daily_partition_nulls_percent_change (Union[Unset, ColumnNullPercentChangeCheckSpec]):
        daily_partition_nulls_percent_change_1_day (Union[Unset, ColumnNullPercentChange1DayCheckSpec]):
        daily_partition_nulls_percent_change_7_days (Union[Unset, ColumnNullPercentChange7DaysCheckSpec]):
        daily_partition_nulls_percent_change_30_days (Union[Unset, ColumnNullPercentChange30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnNullsDailyPartitionedChecksSpecCustomChecks"] = (
        UNSET
    )
    daily_partition_nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    daily_partition_nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    daily_partition_nulls_percent_anomaly: Union[
        Unset, "ColumnNullPercentAnomalyStationaryCheckSpec"
    ] = UNSET
    daily_partition_not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = (
        UNSET
    )
    daily_partition_not_nulls_percent: Union[
        Unset, "ColumnNotNullsPercentCheckSpec"
    ] = UNSET
    daily_partition_empty_column_found: Union[
        Unset, "ColumnEmptyColumnFoundCheckSpec"
    ] = UNSET
    daily_partition_nulls_percent_change: Union[
        Unset, "ColumnNullPercentChangeCheckSpec"
    ] = UNSET
    daily_partition_nulls_percent_change_1_day: Union[
        Unset, "ColumnNullPercentChange1DayCheckSpec"
    ] = UNSET
    daily_partition_nulls_percent_change_7_days: Union[
        Unset, "ColumnNullPercentChange7DaysCheckSpec"
    ] = UNSET
    daily_partition_nulls_percent_change_30_days: Union[
        Unset, "ColumnNullPercentChange30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_nulls_count, Unset):
            daily_partition_nulls_count = self.daily_partition_nulls_count.to_dict()

        daily_partition_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_nulls_percent, Unset):
            daily_partition_nulls_percent = self.daily_partition_nulls_percent.to_dict()

        daily_partition_nulls_percent_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_nulls_percent_anomaly, Unset):
            daily_partition_nulls_percent_anomaly = (
                self.daily_partition_nulls_percent_anomaly.to_dict()
            )

        daily_partition_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_not_nulls_count, Unset):
            daily_partition_not_nulls_count = (
                self.daily_partition_not_nulls_count.to_dict()
            )

        daily_partition_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_not_nulls_percent, Unset):
            daily_partition_not_nulls_percent = (
                self.daily_partition_not_nulls_percent.to_dict()
            )

        daily_partition_empty_column_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_empty_column_found, Unset):
            daily_partition_empty_column_found = (
                self.daily_partition_empty_column_found.to_dict()
            )

        daily_partition_nulls_percent_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_nulls_percent_change, Unset):
            daily_partition_nulls_percent_change = (
                self.daily_partition_nulls_percent_change.to_dict()
            )

        daily_partition_nulls_percent_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_nulls_percent_change_1_day, Unset):
            daily_partition_nulls_percent_change_1_day = (
                self.daily_partition_nulls_percent_change_1_day.to_dict()
            )

        daily_partition_nulls_percent_change_7_days: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_nulls_percent_change_7_days, Unset):
            daily_partition_nulls_percent_change_7_days = (
                self.daily_partition_nulls_percent_change_7_days.to_dict()
            )

        daily_partition_nulls_percent_change_30_days: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_partition_nulls_percent_change_30_days, Unset):
            daily_partition_nulls_percent_change_30_days = (
                self.daily_partition_nulls_percent_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_nulls_count is not UNSET:
            field_dict["daily_partition_nulls_count"] = daily_partition_nulls_count
        if daily_partition_nulls_percent is not UNSET:
            field_dict["daily_partition_nulls_percent"] = daily_partition_nulls_percent
        if daily_partition_nulls_percent_anomaly is not UNSET:
            field_dict["daily_partition_nulls_percent_anomaly"] = (
                daily_partition_nulls_percent_anomaly
            )
        if daily_partition_not_nulls_count is not UNSET:
            field_dict["daily_partition_not_nulls_count"] = (
                daily_partition_not_nulls_count
            )
        if daily_partition_not_nulls_percent is not UNSET:
            field_dict["daily_partition_not_nulls_percent"] = (
                daily_partition_not_nulls_percent
            )
        if daily_partition_empty_column_found is not UNSET:
            field_dict["daily_partition_empty_column_found"] = (
                daily_partition_empty_column_found
            )
        if daily_partition_nulls_percent_change is not UNSET:
            field_dict["daily_partition_nulls_percent_change"] = (
                daily_partition_nulls_percent_change
            )
        if daily_partition_nulls_percent_change_1_day is not UNSET:
            field_dict["daily_partition_nulls_percent_change_1_day"] = (
                daily_partition_nulls_percent_change_1_day
            )
        if daily_partition_nulls_percent_change_7_days is not UNSET:
            field_dict["daily_partition_nulls_percent_change_7_days"] = (
                daily_partition_nulls_percent_change_7_days
            )
        if daily_partition_nulls_percent_change_30_days is not UNSET:
            field_dict["daily_partition_nulls_percent_change_30_days"] = (
                daily_partition_nulls_percent_change_30_days
            )

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
        from ..models.column_null_percent_anomaly_stationary_check_spec import (
            ColumnNullPercentAnomalyStationaryCheckSpec,
        )
        from ..models.column_null_percent_change_1_day_check_spec import (
            ColumnNullPercentChange1DayCheckSpec,
        )
        from ..models.column_null_percent_change_7_days_check_spec import (
            ColumnNullPercentChange7DaysCheckSpec,
        )
        from ..models.column_null_percent_change_30_days_check_spec import (
            ColumnNullPercentChange30DaysCheckSpec,
        )
        from ..models.column_null_percent_change_check_spec import (
            ColumnNullPercentChangeCheckSpec,
        )
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_nulls_daily_partitioned_checks_spec_custom_checks import (
            ColumnNullsDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnNullsDailyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnNullsDailyPartitionedChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_partition_nulls_count = d.pop("daily_partition_nulls_count", UNSET)
        daily_partition_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_daily_partition_nulls_count, Unset):
            daily_partition_nulls_count = UNSET
        else:
            daily_partition_nulls_count = ColumnNullsCountCheckSpec.from_dict(
                _daily_partition_nulls_count
            )

        _daily_partition_nulls_percent = d.pop("daily_partition_nulls_percent", UNSET)
        daily_partition_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_daily_partition_nulls_percent, Unset):
            daily_partition_nulls_percent = UNSET
        else:
            daily_partition_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(
                _daily_partition_nulls_percent
            )

        _daily_partition_nulls_percent_anomaly = d.pop(
            "daily_partition_nulls_percent_anomaly", UNSET
        )
        daily_partition_nulls_percent_anomaly: Union[
            Unset, ColumnNullPercentAnomalyStationaryCheckSpec
        ]
        if isinstance(_daily_partition_nulls_percent_anomaly, Unset):
            daily_partition_nulls_percent_anomaly = UNSET
        else:
            daily_partition_nulls_percent_anomaly = (
                ColumnNullPercentAnomalyStationaryCheckSpec.from_dict(
                    _daily_partition_nulls_percent_anomaly
                )
            )

        _daily_partition_not_nulls_count = d.pop(
            "daily_partition_not_nulls_count", UNSET
        )
        daily_partition_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_daily_partition_not_nulls_count, Unset):
            daily_partition_not_nulls_count = UNSET
        else:
            daily_partition_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(
                _daily_partition_not_nulls_count
            )

        _daily_partition_not_nulls_percent = d.pop(
            "daily_partition_not_nulls_percent", UNSET
        )
        daily_partition_not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_daily_partition_not_nulls_percent, Unset):
            daily_partition_not_nulls_percent = UNSET
        else:
            daily_partition_not_nulls_percent = (
                ColumnNotNullsPercentCheckSpec.from_dict(
                    _daily_partition_not_nulls_percent
                )
            )

        _daily_partition_empty_column_found = d.pop(
            "daily_partition_empty_column_found", UNSET
        )
        daily_partition_empty_column_found: Union[
            Unset, ColumnEmptyColumnFoundCheckSpec
        ]
        if isinstance(_daily_partition_empty_column_found, Unset):
            daily_partition_empty_column_found = UNSET
        else:
            daily_partition_empty_column_found = (
                ColumnEmptyColumnFoundCheckSpec.from_dict(
                    _daily_partition_empty_column_found
                )
            )

        _daily_partition_nulls_percent_change = d.pop(
            "daily_partition_nulls_percent_change", UNSET
        )
        daily_partition_nulls_percent_change: Union[
            Unset, ColumnNullPercentChangeCheckSpec
        ]
        if isinstance(_daily_partition_nulls_percent_change, Unset):
            daily_partition_nulls_percent_change = UNSET
        else:
            daily_partition_nulls_percent_change = (
                ColumnNullPercentChangeCheckSpec.from_dict(
                    _daily_partition_nulls_percent_change
                )
            )

        _daily_partition_nulls_percent_change_1_day = d.pop(
            "daily_partition_nulls_percent_change_1_day", UNSET
        )
        daily_partition_nulls_percent_change_1_day: Union[
            Unset, ColumnNullPercentChange1DayCheckSpec
        ]
        if isinstance(_daily_partition_nulls_percent_change_1_day, Unset):
            daily_partition_nulls_percent_change_1_day = UNSET
        else:
            daily_partition_nulls_percent_change_1_day = (
                ColumnNullPercentChange1DayCheckSpec.from_dict(
                    _daily_partition_nulls_percent_change_1_day
                )
            )

        _daily_partition_nulls_percent_change_7_days = d.pop(
            "daily_partition_nulls_percent_change_7_days", UNSET
        )
        daily_partition_nulls_percent_change_7_days: Union[
            Unset, ColumnNullPercentChange7DaysCheckSpec
        ]
        if isinstance(_daily_partition_nulls_percent_change_7_days, Unset):
            daily_partition_nulls_percent_change_7_days = UNSET
        else:
            daily_partition_nulls_percent_change_7_days = (
                ColumnNullPercentChange7DaysCheckSpec.from_dict(
                    _daily_partition_nulls_percent_change_7_days
                )
            )

        _daily_partition_nulls_percent_change_30_days = d.pop(
            "daily_partition_nulls_percent_change_30_days", UNSET
        )
        daily_partition_nulls_percent_change_30_days: Union[
            Unset, ColumnNullPercentChange30DaysCheckSpec
        ]
        if isinstance(_daily_partition_nulls_percent_change_30_days, Unset):
            daily_partition_nulls_percent_change_30_days = UNSET
        else:
            daily_partition_nulls_percent_change_30_days = (
                ColumnNullPercentChange30DaysCheckSpec.from_dict(
                    _daily_partition_nulls_percent_change_30_days
                )
            )

        column_nulls_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_nulls_count=daily_partition_nulls_count,
            daily_partition_nulls_percent=daily_partition_nulls_percent,
            daily_partition_nulls_percent_anomaly=daily_partition_nulls_percent_anomaly,
            daily_partition_not_nulls_count=daily_partition_not_nulls_count,
            daily_partition_not_nulls_percent=daily_partition_not_nulls_percent,
            daily_partition_empty_column_found=daily_partition_empty_column_found,
            daily_partition_nulls_percent_change=daily_partition_nulls_percent_change,
            daily_partition_nulls_percent_change_1_day=daily_partition_nulls_percent_change_1_day,
            daily_partition_nulls_percent_change_7_days=daily_partition_nulls_percent_change_7_days,
            daily_partition_nulls_percent_change_30_days=daily_partition_nulls_percent_change_30_days,
        )

        column_nulls_daily_partitioned_checks_spec.additional_properties = d
        return column_nulls_daily_partitioned_checks_spec

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
