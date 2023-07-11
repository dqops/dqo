from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_mean_change_30_days_check_spec import (
        ColumnAnomalyMeanChange30DaysCheckSpec,
    )
    from ..models.column_anomaly_mean_change_60_days_check_spec import (
        ColumnAnomalyMeanChange60DaysCheckSpec,
    )
    from ..models.column_anomaly_median_change_30_days_check_spec import (
        ColumnAnomalyMedianChange30DaysCheckSpec,
    )
    from ..models.column_anomaly_median_change_60_days_check_spec import (
        ColumnAnomalyMedianChange60DaysCheckSpec,
    )
    from ..models.column_anomaly_sum_change_30_days_check_spec import (
        ColumnAnomalySumChange30DaysCheckSpec,
    )
    from ..models.column_anomaly_sum_change_60_days_check_spec import (
        ColumnAnomalySumChange60DaysCheckSpec,
    )
    from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
    from ..models.column_change_mean_since_7_days_check_spec import (
        ColumnChangeMeanSince7DaysCheckSpec,
    )
    from ..models.column_change_mean_since_30_days_check_spec import (
        ColumnChangeMeanSince30DaysCheckSpec,
    )
    from ..models.column_change_mean_since_yesterday_check_spec import (
        ColumnChangeMeanSinceYesterdayCheckSpec,
    )
    from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
    from ..models.column_change_median_since_7_days_check_spec import (
        ColumnChangeMedianSince7DaysCheckSpec,
    )
    from ..models.column_change_median_since_30_days_check_spec import (
        ColumnChangeMedianSince30DaysCheckSpec,
    )
    from ..models.column_change_median_since_yesterday_check_spec import (
        ColumnChangeMedianSinceYesterdayCheckSpec,
    )
    from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec
    from ..models.column_change_sum_since_7_days_check_spec import (
        ColumnChangeSumSince7DaysCheckSpec,
    )
    from ..models.column_change_sum_since_30_days_check_spec import (
        ColumnChangeSumSince30DaysCheckSpec,
    )
    from ..models.column_change_sum_since_yesterday_check_spec import (
        ColumnChangeSumSinceYesterdayCheckSpec,
    )


T = TypeVar("T", bound="ColumnAnomalyDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAnomalyDailyRecurringChecksSpec:
    """
    Attributes:
        daily_mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
        daily_mean_change_yesterday (Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]):
        daily_median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
        daily_median_change_yesterday (Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]):
        daily_sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
        daily_sum_change_yesterday (Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]):
        daily_mean_anomaly_30_days (Union[Unset, ColumnAnomalyMeanChange30DaysCheckSpec]):
        daily_mean_anomaly_60_days (Union[Unset, ColumnAnomalyMeanChange60DaysCheckSpec]):
        daily_median_anomaly_30_days (Union[Unset, ColumnAnomalyMedianChange30DaysCheckSpec]):
        daily_median_anomaly_60_days (Union[Unset, ColumnAnomalyMedianChange60DaysCheckSpec]):
        daily_sum_anomaly_30_days (Union[Unset, ColumnAnomalySumChange30DaysCheckSpec]):
        daily_sum_anomaly_60_days (Union[Unset, ColumnAnomalySumChange60DaysCheckSpec]):
        daily_mean_change_7_days (Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]):
        daily_mean_change_30_days (Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]):
        daily_median_change_7_days (Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]):
        daily_median_change_30_days (Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]):
        daily_sum_change_7_days (Union[Unset, ColumnChangeSumSince7DaysCheckSpec]):
        daily_sum_change_30_days (Union[Unset, ColumnChangeSumSince30DaysCheckSpec]):
    """

    daily_mean_change: Union[Unset, "ColumnChangeMeanCheckSpec"] = UNSET
    daily_mean_change_yesterday: Union[
        Unset, "ColumnChangeMeanSinceYesterdayCheckSpec"
    ] = UNSET
    daily_median_change: Union[Unset, "ColumnChangeMedianCheckSpec"] = UNSET
    daily_median_change_yesterday: Union[
        Unset, "ColumnChangeMedianSinceYesterdayCheckSpec"
    ] = UNSET
    daily_sum_change: Union[Unset, "ColumnChangeSumCheckSpec"] = UNSET
    daily_sum_change_yesterday: Union[
        Unset, "ColumnChangeSumSinceYesterdayCheckSpec"
    ] = UNSET
    daily_mean_anomaly_30_days: Union[
        Unset, "ColumnAnomalyMeanChange30DaysCheckSpec"
    ] = UNSET
    daily_mean_anomaly_60_days: Union[
        Unset, "ColumnAnomalyMeanChange60DaysCheckSpec"
    ] = UNSET
    daily_median_anomaly_30_days: Union[
        Unset, "ColumnAnomalyMedianChange30DaysCheckSpec"
    ] = UNSET
    daily_median_anomaly_60_days: Union[
        Unset, "ColumnAnomalyMedianChange60DaysCheckSpec"
    ] = UNSET
    daily_sum_anomaly_30_days: Union[
        Unset, "ColumnAnomalySumChange30DaysCheckSpec"
    ] = UNSET
    daily_sum_anomaly_60_days: Union[
        Unset, "ColumnAnomalySumChange60DaysCheckSpec"
    ] = UNSET
    daily_mean_change_7_days: Union[
        Unset, "ColumnChangeMeanSince7DaysCheckSpec"
    ] = UNSET
    daily_mean_change_30_days: Union[
        Unset, "ColumnChangeMeanSince30DaysCheckSpec"
    ] = UNSET
    daily_median_change_7_days: Union[
        Unset, "ColumnChangeMedianSince7DaysCheckSpec"
    ] = UNSET
    daily_median_change_30_days: Union[
        Unset, "ColumnChangeMedianSince30DaysCheckSpec"
    ] = UNSET
    daily_sum_change_7_days: Union[Unset, "ColumnChangeSumSince7DaysCheckSpec"] = UNSET
    daily_sum_change_30_days: Union[
        Unset, "ColumnChangeSumSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change, Unset):
            daily_mean_change = self.daily_mean_change.to_dict()

        daily_mean_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_yesterday, Unset):
            daily_mean_change_yesterday = self.daily_mean_change_yesterday.to_dict()

        daily_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change, Unset):
            daily_median_change = self.daily_median_change.to_dict()

        daily_median_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_yesterday, Unset):
            daily_median_change_yesterday = self.daily_median_change_yesterday.to_dict()

        daily_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change, Unset):
            daily_sum_change = self.daily_sum_change.to_dict()

        daily_sum_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_yesterday, Unset):
            daily_sum_change_yesterday = self.daily_sum_change_yesterday.to_dict()

        daily_mean_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_anomaly_30_days, Unset):
            daily_mean_anomaly_30_days = self.daily_mean_anomaly_30_days.to_dict()

        daily_mean_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_anomaly_60_days, Unset):
            daily_mean_anomaly_60_days = self.daily_mean_anomaly_60_days.to_dict()

        daily_median_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_anomaly_30_days, Unset):
            daily_median_anomaly_30_days = self.daily_median_anomaly_30_days.to_dict()

        daily_median_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_anomaly_60_days, Unset):
            daily_median_anomaly_60_days = self.daily_median_anomaly_60_days.to_dict()

        daily_sum_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_anomaly_30_days, Unset):
            daily_sum_anomaly_30_days = self.daily_sum_anomaly_30_days.to_dict()

        daily_sum_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_anomaly_60_days, Unset):
            daily_sum_anomaly_60_days = self.daily_sum_anomaly_60_days.to_dict()

        daily_mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_7_days, Unset):
            daily_mean_change_7_days = self.daily_mean_change_7_days.to_dict()

        daily_mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_mean_change_30_days, Unset):
            daily_mean_change_30_days = self.daily_mean_change_30_days.to_dict()

        daily_median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_7_days, Unset):
            daily_median_change_7_days = self.daily_median_change_7_days.to_dict()

        daily_median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_median_change_30_days, Unset):
            daily_median_change_30_days = self.daily_median_change_30_days.to_dict()

        daily_sum_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_7_days, Unset):
            daily_sum_change_7_days = self.daily_sum_change_7_days.to_dict()

        daily_sum_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sum_change_30_days, Unset):
            daily_sum_change_30_days = self.daily_sum_change_30_days.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_mean_change is not UNSET:
            field_dict["daily_mean_change"] = daily_mean_change
        if daily_mean_change_yesterday is not UNSET:
            field_dict["daily_mean_change_yesterday"] = daily_mean_change_yesterday
        if daily_median_change is not UNSET:
            field_dict["daily_median_change"] = daily_median_change
        if daily_median_change_yesterday is not UNSET:
            field_dict["daily_median_change_yesterday"] = daily_median_change_yesterday
        if daily_sum_change is not UNSET:
            field_dict["daily_sum_change"] = daily_sum_change
        if daily_sum_change_yesterday is not UNSET:
            field_dict["daily_sum_change_yesterday"] = daily_sum_change_yesterday
        if daily_mean_anomaly_30_days is not UNSET:
            field_dict["daily_mean_anomaly_30_days"] = daily_mean_anomaly_30_days
        if daily_mean_anomaly_60_days is not UNSET:
            field_dict["daily_mean_anomaly_60_days"] = daily_mean_anomaly_60_days
        if daily_median_anomaly_30_days is not UNSET:
            field_dict["daily_median_anomaly_30_days"] = daily_median_anomaly_30_days
        if daily_median_anomaly_60_days is not UNSET:
            field_dict["daily_median_anomaly_60_days"] = daily_median_anomaly_60_days
        if daily_sum_anomaly_30_days is not UNSET:
            field_dict["daily_sum_anomaly_30_days"] = daily_sum_anomaly_30_days
        if daily_sum_anomaly_60_days is not UNSET:
            field_dict["daily_sum_anomaly_60_days"] = daily_sum_anomaly_60_days
        if daily_mean_change_7_days is not UNSET:
            field_dict["daily_mean_change_7_days"] = daily_mean_change_7_days
        if daily_mean_change_30_days is not UNSET:
            field_dict["daily_mean_change_30_days"] = daily_mean_change_30_days
        if daily_median_change_7_days is not UNSET:
            field_dict["daily_median_change_7_days"] = daily_median_change_7_days
        if daily_median_change_30_days is not UNSET:
            field_dict["daily_median_change_30_days"] = daily_median_change_30_days
        if daily_sum_change_7_days is not UNSET:
            field_dict["daily_sum_change_7_days"] = daily_sum_change_7_days
        if daily_sum_change_30_days is not UNSET:
            field_dict["daily_sum_change_30_days"] = daily_sum_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_mean_change_30_days_check_spec import (
            ColumnAnomalyMeanChange30DaysCheckSpec,
        )
        from ..models.column_anomaly_mean_change_60_days_check_spec import (
            ColumnAnomalyMeanChange60DaysCheckSpec,
        )
        from ..models.column_anomaly_median_change_30_days_check_spec import (
            ColumnAnomalyMedianChange30DaysCheckSpec,
        )
        from ..models.column_anomaly_median_change_60_days_check_spec import (
            ColumnAnomalyMedianChange60DaysCheckSpec,
        )
        from ..models.column_anomaly_sum_change_30_days_check_spec import (
            ColumnAnomalySumChange30DaysCheckSpec,
        )
        from ..models.column_anomaly_sum_change_60_days_check_spec import (
            ColumnAnomalySumChange60DaysCheckSpec,
        )
        from ..models.column_change_mean_check_spec import ColumnChangeMeanCheckSpec
        from ..models.column_change_mean_since_7_days_check_spec import (
            ColumnChangeMeanSince7DaysCheckSpec,
        )
        from ..models.column_change_mean_since_30_days_check_spec import (
            ColumnChangeMeanSince30DaysCheckSpec,
        )
        from ..models.column_change_mean_since_yesterday_check_spec import (
            ColumnChangeMeanSinceYesterdayCheckSpec,
        )
        from ..models.column_change_median_check_spec import ColumnChangeMedianCheckSpec
        from ..models.column_change_median_since_7_days_check_spec import (
            ColumnChangeMedianSince7DaysCheckSpec,
        )
        from ..models.column_change_median_since_30_days_check_spec import (
            ColumnChangeMedianSince30DaysCheckSpec,
        )
        from ..models.column_change_median_since_yesterday_check_spec import (
            ColumnChangeMedianSinceYesterdayCheckSpec,
        )
        from ..models.column_change_sum_check_spec import ColumnChangeSumCheckSpec
        from ..models.column_change_sum_since_7_days_check_spec import (
            ColumnChangeSumSince7DaysCheckSpec,
        )
        from ..models.column_change_sum_since_30_days_check_spec import (
            ColumnChangeSumSince30DaysCheckSpec,
        )
        from ..models.column_change_sum_since_yesterday_check_spec import (
            ColumnChangeSumSinceYesterdayCheckSpec,
        )

        d = src_dict.copy()
        _daily_mean_change = d.pop("daily_mean_change", UNSET)
        daily_mean_change: Union[Unset, ColumnChangeMeanCheckSpec]
        if isinstance(_daily_mean_change, Unset):
            daily_mean_change = UNSET
        else:
            daily_mean_change = ColumnChangeMeanCheckSpec.from_dict(_daily_mean_change)

        _daily_mean_change_yesterday = d.pop("daily_mean_change_yesterday", UNSET)
        daily_mean_change_yesterday: Union[
            Unset, ColumnChangeMeanSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_mean_change_yesterday, Unset):
            daily_mean_change_yesterday = UNSET
        else:
            daily_mean_change_yesterday = (
                ColumnChangeMeanSinceYesterdayCheckSpec.from_dict(
                    _daily_mean_change_yesterday
                )
            )

        _daily_median_change = d.pop("daily_median_change", UNSET)
        daily_median_change: Union[Unset, ColumnChangeMedianCheckSpec]
        if isinstance(_daily_median_change, Unset):
            daily_median_change = UNSET
        else:
            daily_median_change = ColumnChangeMedianCheckSpec.from_dict(
                _daily_median_change
            )

        _daily_median_change_yesterday = d.pop("daily_median_change_yesterday", UNSET)
        daily_median_change_yesterday: Union[
            Unset, ColumnChangeMedianSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_median_change_yesterday, Unset):
            daily_median_change_yesterday = UNSET
        else:
            daily_median_change_yesterday = (
                ColumnChangeMedianSinceYesterdayCheckSpec.from_dict(
                    _daily_median_change_yesterday
                )
            )

        _daily_sum_change = d.pop("daily_sum_change", UNSET)
        daily_sum_change: Union[Unset, ColumnChangeSumCheckSpec]
        if isinstance(_daily_sum_change, Unset):
            daily_sum_change = UNSET
        else:
            daily_sum_change = ColumnChangeSumCheckSpec.from_dict(_daily_sum_change)

        _daily_sum_change_yesterday = d.pop("daily_sum_change_yesterday", UNSET)
        daily_sum_change_yesterday: Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]
        if isinstance(_daily_sum_change_yesterday, Unset):
            daily_sum_change_yesterday = UNSET
        else:
            daily_sum_change_yesterday = (
                ColumnChangeSumSinceYesterdayCheckSpec.from_dict(
                    _daily_sum_change_yesterday
                )
            )

        _daily_mean_anomaly_30_days = d.pop("daily_mean_anomaly_30_days", UNSET)
        daily_mean_anomaly_30_days: Union[Unset, ColumnAnomalyMeanChange30DaysCheckSpec]
        if isinstance(_daily_mean_anomaly_30_days, Unset):
            daily_mean_anomaly_30_days = UNSET
        else:
            daily_mean_anomaly_30_days = (
                ColumnAnomalyMeanChange30DaysCheckSpec.from_dict(
                    _daily_mean_anomaly_30_days
                )
            )

        _daily_mean_anomaly_60_days = d.pop("daily_mean_anomaly_60_days", UNSET)
        daily_mean_anomaly_60_days: Union[Unset, ColumnAnomalyMeanChange60DaysCheckSpec]
        if isinstance(_daily_mean_anomaly_60_days, Unset):
            daily_mean_anomaly_60_days = UNSET
        else:
            daily_mean_anomaly_60_days = (
                ColumnAnomalyMeanChange60DaysCheckSpec.from_dict(
                    _daily_mean_anomaly_60_days
                )
            )

        _daily_median_anomaly_30_days = d.pop("daily_median_anomaly_30_days", UNSET)
        daily_median_anomaly_30_days: Union[
            Unset, ColumnAnomalyMedianChange30DaysCheckSpec
        ]
        if isinstance(_daily_median_anomaly_30_days, Unset):
            daily_median_anomaly_30_days = UNSET
        else:
            daily_median_anomaly_30_days = (
                ColumnAnomalyMedianChange30DaysCheckSpec.from_dict(
                    _daily_median_anomaly_30_days
                )
            )

        _daily_median_anomaly_60_days = d.pop("daily_median_anomaly_60_days", UNSET)
        daily_median_anomaly_60_days: Union[
            Unset, ColumnAnomalyMedianChange60DaysCheckSpec
        ]
        if isinstance(_daily_median_anomaly_60_days, Unset):
            daily_median_anomaly_60_days = UNSET
        else:
            daily_median_anomaly_60_days = (
                ColumnAnomalyMedianChange60DaysCheckSpec.from_dict(
                    _daily_median_anomaly_60_days
                )
            )

        _daily_sum_anomaly_30_days = d.pop("daily_sum_anomaly_30_days", UNSET)
        daily_sum_anomaly_30_days: Union[Unset, ColumnAnomalySumChange30DaysCheckSpec]
        if isinstance(_daily_sum_anomaly_30_days, Unset):
            daily_sum_anomaly_30_days = UNSET
        else:
            daily_sum_anomaly_30_days = ColumnAnomalySumChange30DaysCheckSpec.from_dict(
                _daily_sum_anomaly_30_days
            )

        _daily_sum_anomaly_60_days = d.pop("daily_sum_anomaly_60_days", UNSET)
        daily_sum_anomaly_60_days: Union[Unset, ColumnAnomalySumChange60DaysCheckSpec]
        if isinstance(_daily_sum_anomaly_60_days, Unset):
            daily_sum_anomaly_60_days = UNSET
        else:
            daily_sum_anomaly_60_days = ColumnAnomalySumChange60DaysCheckSpec.from_dict(
                _daily_sum_anomaly_60_days
            )

        _daily_mean_change_7_days = d.pop("daily_mean_change_7_days", UNSET)
        daily_mean_change_7_days: Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]
        if isinstance(_daily_mean_change_7_days, Unset):
            daily_mean_change_7_days = UNSET
        else:
            daily_mean_change_7_days = ColumnChangeMeanSince7DaysCheckSpec.from_dict(
                _daily_mean_change_7_days
            )

        _daily_mean_change_30_days = d.pop("daily_mean_change_30_days", UNSET)
        daily_mean_change_30_days: Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]
        if isinstance(_daily_mean_change_30_days, Unset):
            daily_mean_change_30_days = UNSET
        else:
            daily_mean_change_30_days = ColumnChangeMeanSince30DaysCheckSpec.from_dict(
                _daily_mean_change_30_days
            )

        _daily_median_change_7_days = d.pop("daily_median_change_7_days", UNSET)
        daily_median_change_7_days: Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]
        if isinstance(_daily_median_change_7_days, Unset):
            daily_median_change_7_days = UNSET
        else:
            daily_median_change_7_days = (
                ColumnChangeMedianSince7DaysCheckSpec.from_dict(
                    _daily_median_change_7_days
                )
            )

        _daily_median_change_30_days = d.pop("daily_median_change_30_days", UNSET)
        daily_median_change_30_days: Union[
            Unset, ColumnChangeMedianSince30DaysCheckSpec
        ]
        if isinstance(_daily_median_change_30_days, Unset):
            daily_median_change_30_days = UNSET
        else:
            daily_median_change_30_days = (
                ColumnChangeMedianSince30DaysCheckSpec.from_dict(
                    _daily_median_change_30_days
                )
            )

        _daily_sum_change_7_days = d.pop("daily_sum_change_7_days", UNSET)
        daily_sum_change_7_days: Union[Unset, ColumnChangeSumSince7DaysCheckSpec]
        if isinstance(_daily_sum_change_7_days, Unset):
            daily_sum_change_7_days = UNSET
        else:
            daily_sum_change_7_days = ColumnChangeSumSince7DaysCheckSpec.from_dict(
                _daily_sum_change_7_days
            )

        _daily_sum_change_30_days = d.pop("daily_sum_change_30_days", UNSET)
        daily_sum_change_30_days: Union[Unset, ColumnChangeSumSince30DaysCheckSpec]
        if isinstance(_daily_sum_change_30_days, Unset):
            daily_sum_change_30_days = UNSET
        else:
            daily_sum_change_30_days = ColumnChangeSumSince30DaysCheckSpec.from_dict(
                _daily_sum_change_30_days
            )

        column_anomaly_daily_recurring_checks_spec = cls(
            daily_mean_change=daily_mean_change,
            daily_mean_change_yesterday=daily_mean_change_yesterday,
            daily_median_change=daily_median_change,
            daily_median_change_yesterday=daily_median_change_yesterday,
            daily_sum_change=daily_sum_change,
            daily_sum_change_yesterday=daily_sum_change_yesterday,
            daily_mean_anomaly_30_days=daily_mean_anomaly_30_days,
            daily_mean_anomaly_60_days=daily_mean_anomaly_60_days,
            daily_median_anomaly_30_days=daily_median_anomaly_30_days,
            daily_median_anomaly_60_days=daily_median_anomaly_60_days,
            daily_sum_anomaly_30_days=daily_sum_anomaly_30_days,
            daily_sum_anomaly_60_days=daily_sum_anomaly_60_days,
            daily_mean_change_7_days=daily_mean_change_7_days,
            daily_mean_change_30_days=daily_mean_change_30_days,
            daily_median_change_7_days=daily_median_change_7_days,
            daily_median_change_30_days=daily_median_change_30_days,
            daily_sum_change_7_days=daily_sum_change_7_days,
            daily_sum_change_30_days=daily_sum_change_30_days,
        )

        column_anomaly_daily_recurring_checks_spec.additional_properties = d
        return column_anomaly_daily_recurring_checks_spec

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
