from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_stationary_null_percent_30_days_check_spec import (
        ColumnAnomalyStationaryNullPercent30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_null_percent_check_spec import (
        ColumnAnomalyStationaryNullPercentCheckSpec,
    )
    from ..models.column_change_null_percent_check_spec import (
        ColumnChangeNullPercentCheckSpec,
    )
    from ..models.column_change_null_percent_since_7_days_check_spec import (
        ColumnChangeNullPercentSince7DaysCheckSpec,
    )
    from ..models.column_change_null_percent_since_30_days_check_spec import (
        ColumnChangeNullPercentSince30DaysCheckSpec,
    )
    from ..models.column_change_null_percent_since_yesterday_check_spec import (
        ColumnChangeNullPercentSinceYesterdayCheckSpec,
    )
    from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
    from ..models.column_not_nulls_percent_check_spec import (
        ColumnNotNullsPercentCheckSpec,
    )
    from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
    from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec


T = TypeVar("T", bound="ColumnNullsDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnNullsDailyRecurringChecksSpec:
    """
    Attributes:
        daily_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        daily_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        daily_nulls_percent_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryNullPercentCheckSpec]):
        daily_nulls_percent_change (Union[Unset, ColumnChangeNullPercentCheckSpec]):
        daily_nulls_percent_change_yesterday (Union[Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec]):
        daily_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        daily_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
        daily_nulls_percent_anomaly_stationary_30_days (Union[Unset,
            ColumnAnomalyStationaryNullPercent30DaysCheckSpec]):
        daily_nulls_percent_change_7_days (Union[Unset, ColumnChangeNullPercentSince7DaysCheckSpec]):
        daily_nulls_percent_change_30_days (Union[Unset, ColumnChangeNullPercentSince30DaysCheckSpec]):
    """

    daily_nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    daily_nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    daily_nulls_percent_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryNullPercentCheckSpec"
    ] = UNSET
    daily_nulls_percent_change: Union[Unset, "ColumnChangeNullPercentCheckSpec"] = UNSET
    daily_nulls_percent_change_yesterday: Union[
        Unset, "ColumnChangeNullPercentSinceYesterdayCheckSpec"
    ] = UNSET
    daily_not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = UNSET
    daily_not_nulls_percent: Union[Unset, "ColumnNotNullsPercentCheckSpec"] = UNSET
    daily_nulls_percent_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryNullPercent30DaysCheckSpec"
    ] = UNSET
    daily_nulls_percent_change_7_days: Union[
        Unset, "ColumnChangeNullPercentSince7DaysCheckSpec"
    ] = UNSET
    daily_nulls_percent_change_30_days: Union[
        Unset, "ColumnChangeNullPercentSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_count, Unset):
            daily_nulls_count = self.daily_nulls_count.to_dict()

        daily_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent, Unset):
            daily_nulls_percent = self.daily_nulls_percent.to_dict()

        daily_nulls_percent_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent_anomaly_stationary, Unset):
            daily_nulls_percent_anomaly_stationary = (
                self.daily_nulls_percent_anomaly_stationary.to_dict()
            )

        daily_nulls_percent_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent_change, Unset):
            daily_nulls_percent_change = self.daily_nulls_percent_change.to_dict()

        daily_nulls_percent_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent_change_yesterday, Unset):
            daily_nulls_percent_change_yesterday = (
                self.daily_nulls_percent_change_yesterday.to_dict()
            )

        daily_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_nulls_count, Unset):
            daily_not_nulls_count = self.daily_not_nulls_count.to_dict()

        daily_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_nulls_percent, Unset):
            daily_not_nulls_percent = self.daily_not_nulls_percent.to_dict()

        daily_nulls_percent_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_nulls_percent_anomaly_stationary_30_days, Unset):
            daily_nulls_percent_anomaly_stationary_30_days = (
                self.daily_nulls_percent_anomaly_stationary_30_days.to_dict()
            )

        daily_nulls_percent_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent_change_7_days, Unset):
            daily_nulls_percent_change_7_days = (
                self.daily_nulls_percent_change_7_days.to_dict()
            )

        daily_nulls_percent_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent_change_30_days, Unset):
            daily_nulls_percent_change_30_days = (
                self.daily_nulls_percent_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_nulls_count is not UNSET:
            field_dict["daily_nulls_count"] = daily_nulls_count
        if daily_nulls_percent is not UNSET:
            field_dict["daily_nulls_percent"] = daily_nulls_percent
        if daily_nulls_percent_anomaly_stationary is not UNSET:
            field_dict[
                "daily_nulls_percent_anomaly_stationary"
            ] = daily_nulls_percent_anomaly_stationary
        if daily_nulls_percent_change is not UNSET:
            field_dict["daily_nulls_percent_change"] = daily_nulls_percent_change
        if daily_nulls_percent_change_yesterday is not UNSET:
            field_dict[
                "daily_nulls_percent_change_yesterday"
            ] = daily_nulls_percent_change_yesterday
        if daily_not_nulls_count is not UNSET:
            field_dict["daily_not_nulls_count"] = daily_not_nulls_count
        if daily_not_nulls_percent is not UNSET:
            field_dict["daily_not_nulls_percent"] = daily_not_nulls_percent
        if daily_nulls_percent_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "daily_nulls_percent_anomaly_stationary_30_days"
            ] = daily_nulls_percent_anomaly_stationary_30_days
        if daily_nulls_percent_change_7_days is not UNSET:
            field_dict[
                "daily_nulls_percent_change_7_days"
            ] = daily_nulls_percent_change_7_days
        if daily_nulls_percent_change_30_days is not UNSET:
            field_dict[
                "daily_nulls_percent_change_30_days"
            ] = daily_nulls_percent_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_stationary_null_percent_30_days_check_spec import (
            ColumnAnomalyStationaryNullPercent30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_null_percent_check_spec import (
            ColumnAnomalyStationaryNullPercentCheckSpec,
        )
        from ..models.column_change_null_percent_check_spec import (
            ColumnChangeNullPercentCheckSpec,
        )
        from ..models.column_change_null_percent_since_7_days_check_spec import (
            ColumnChangeNullPercentSince7DaysCheckSpec,
        )
        from ..models.column_change_null_percent_since_30_days_check_spec import (
            ColumnChangeNullPercentSince30DaysCheckSpec,
        )
        from ..models.column_change_null_percent_since_yesterday_check_spec import (
            ColumnChangeNullPercentSinceYesterdayCheckSpec,
        )
        from ..models.column_not_nulls_count_check_spec import (
            ColumnNotNullsCountCheckSpec,
        )
        from ..models.column_not_nulls_percent_check_spec import (
            ColumnNotNullsPercentCheckSpec,
        )
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec

        d = src_dict.copy()
        _daily_nulls_count = d.pop("daily_nulls_count", UNSET)
        daily_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_daily_nulls_count, Unset):
            daily_nulls_count = UNSET
        else:
            daily_nulls_count = ColumnNullsCountCheckSpec.from_dict(_daily_nulls_count)

        _daily_nulls_percent = d.pop("daily_nulls_percent", UNSET)
        daily_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_daily_nulls_percent, Unset):
            daily_nulls_percent = UNSET
        else:
            daily_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(
                _daily_nulls_percent
            )

        _daily_nulls_percent_anomaly_stationary = d.pop(
            "daily_nulls_percent_anomaly_stationary", UNSET
        )
        daily_nulls_percent_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryNullPercentCheckSpec
        ]
        if isinstance(_daily_nulls_percent_anomaly_stationary, Unset):
            daily_nulls_percent_anomaly_stationary = UNSET
        else:
            daily_nulls_percent_anomaly_stationary = (
                ColumnAnomalyStationaryNullPercentCheckSpec.from_dict(
                    _daily_nulls_percent_anomaly_stationary
                )
            )

        _daily_nulls_percent_change = d.pop("daily_nulls_percent_change", UNSET)
        daily_nulls_percent_change: Union[Unset, ColumnChangeNullPercentCheckSpec]
        if isinstance(_daily_nulls_percent_change, Unset):
            daily_nulls_percent_change = UNSET
        else:
            daily_nulls_percent_change = ColumnChangeNullPercentCheckSpec.from_dict(
                _daily_nulls_percent_change
            )

        _daily_nulls_percent_change_yesterday = d.pop(
            "daily_nulls_percent_change_yesterday", UNSET
        )
        daily_nulls_percent_change_yesterday: Union[
            Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_nulls_percent_change_yesterday, Unset):
            daily_nulls_percent_change_yesterday = UNSET
        else:
            daily_nulls_percent_change_yesterday = (
                ColumnChangeNullPercentSinceYesterdayCheckSpec.from_dict(
                    _daily_nulls_percent_change_yesterday
                )
            )

        _daily_not_nulls_count = d.pop("daily_not_nulls_count", UNSET)
        daily_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_daily_not_nulls_count, Unset):
            daily_not_nulls_count = UNSET
        else:
            daily_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(
                _daily_not_nulls_count
            )

        _daily_not_nulls_percent = d.pop("daily_not_nulls_percent", UNSET)
        daily_not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_daily_not_nulls_percent, Unset):
            daily_not_nulls_percent = UNSET
        else:
            daily_not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(
                _daily_not_nulls_percent
            )

        _daily_nulls_percent_anomaly_stationary_30_days = d.pop(
            "daily_nulls_percent_anomaly_stationary_30_days", UNSET
        )
        daily_nulls_percent_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryNullPercent30DaysCheckSpec
        ]
        if isinstance(_daily_nulls_percent_anomaly_stationary_30_days, Unset):
            daily_nulls_percent_anomaly_stationary_30_days = UNSET
        else:
            daily_nulls_percent_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryNullPercent30DaysCheckSpec.from_dict(
                    _daily_nulls_percent_anomaly_stationary_30_days
                )
            )

        _daily_nulls_percent_change_7_days = d.pop(
            "daily_nulls_percent_change_7_days", UNSET
        )
        daily_nulls_percent_change_7_days: Union[
            Unset, ColumnChangeNullPercentSince7DaysCheckSpec
        ]
        if isinstance(_daily_nulls_percent_change_7_days, Unset):
            daily_nulls_percent_change_7_days = UNSET
        else:
            daily_nulls_percent_change_7_days = (
                ColumnChangeNullPercentSince7DaysCheckSpec.from_dict(
                    _daily_nulls_percent_change_7_days
                )
            )

        _daily_nulls_percent_change_30_days = d.pop(
            "daily_nulls_percent_change_30_days", UNSET
        )
        daily_nulls_percent_change_30_days: Union[
            Unset, ColumnChangeNullPercentSince30DaysCheckSpec
        ]
        if isinstance(_daily_nulls_percent_change_30_days, Unset):
            daily_nulls_percent_change_30_days = UNSET
        else:
            daily_nulls_percent_change_30_days = (
                ColumnChangeNullPercentSince30DaysCheckSpec.from_dict(
                    _daily_nulls_percent_change_30_days
                )
            )

        column_nulls_daily_recurring_checks_spec = cls(
            daily_nulls_count=daily_nulls_count,
            daily_nulls_percent=daily_nulls_percent,
            daily_nulls_percent_anomaly_stationary=daily_nulls_percent_anomaly_stationary,
            daily_nulls_percent_change=daily_nulls_percent_change,
            daily_nulls_percent_change_yesterday=daily_nulls_percent_change_yesterday,
            daily_not_nulls_count=daily_not_nulls_count,
            daily_not_nulls_percent=daily_not_nulls_percent,
            daily_nulls_percent_anomaly_stationary_30_days=daily_nulls_percent_anomaly_stationary_30_days,
            daily_nulls_percent_change_7_days=daily_nulls_percent_change_7_days,
            daily_nulls_percent_change_30_days=daily_nulls_percent_change_30_days,
        )

        column_nulls_daily_recurring_checks_spec.additional_properties = d
        return column_nulls_daily_recurring_checks_spec

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
