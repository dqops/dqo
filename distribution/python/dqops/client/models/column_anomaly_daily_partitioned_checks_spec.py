from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_mean_7_days_check_spec import (
        ColumnAnomalyMean7DaysCheckSpec,
    )
    from ..models.column_anomaly_mean_30_days_check_spec import (
        ColumnAnomalyMean30DaysCheckSpec,
    )
    from ..models.column_anomaly_mean_60_days_check_spec import (
        ColumnAnomalyMean60DaysCheckSpec,
    )
    from ..models.column_anomaly_median_7_days_check_spec import (
        ColumnAnomalyMedian7DaysCheckSpec,
    )
    from ..models.column_anomaly_median_30_days_check_spec import (
        ColumnAnomalyMedian30DaysCheckSpec,
    )
    from ..models.column_anomaly_median_60_days_check_spec import (
        ColumnAnomalyMedian60DaysCheckSpec,
    )
    from ..models.column_anomaly_sum_7_days_check_spec import (
        ColumnAnomalySum7DaysCheckSpec,
    )
    from ..models.column_anomaly_sum_30_days_check_spec import (
        ColumnAnomalySum30DaysCheckSpec,
    )
    from ..models.column_anomaly_sum_60_days_check_spec import (
        ColumnAnomalySum60DaysCheckSpec,
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


T = TypeVar("T", bound="ColumnAnomalyDailyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnAnomalyDailyPartitionedChecksSpec:
    """
    Attributes:
        daily_partition_mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
        daily_partition_mean_change_yesterday (Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]):
        daily_partition_median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
        daily_partition_median_change_yesterday (Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]):
        daily_partition_sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
        daily_partition_sum_change_yesterday (Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]):
        daily_partition_mean_anomaly_7_days (Union[Unset, ColumnAnomalyMean7DaysCheckSpec]):
        daily_partition_mean_anomaly_30_days (Union[Unset, ColumnAnomalyMean30DaysCheckSpec]):
        daily_partition_mean_anomaly_60_days (Union[Unset, ColumnAnomalyMean60DaysCheckSpec]):
        daily_partition_median_anomaly_7_days (Union[Unset, ColumnAnomalyMedian7DaysCheckSpec]):
        daily_partition_median_anomaly_30_days (Union[Unset, ColumnAnomalyMedian30DaysCheckSpec]):
        daily_partition_median_anomaly_60_days (Union[Unset, ColumnAnomalyMedian60DaysCheckSpec]):
        daily_partition_sum_anomaly_7_days (Union[Unset, ColumnAnomalySum7DaysCheckSpec]):
        daily_partition_sum_anomaly_30_days (Union[Unset, ColumnAnomalySum30DaysCheckSpec]):
        daily_partition_sum_anomaly_60_days (Union[Unset, ColumnAnomalySum60DaysCheckSpec]):
        daily_partition_mean_change_7_days (Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]):
        daily_partition_mean_change_30_days (Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]):
        daily_partition_median_change_7_days (Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]):
        daily_partition_median_change_30_days (Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]):
        daily_partition_sum_change_7_days (Union[Unset, ColumnChangeSumSince7DaysCheckSpec]):
        daily_partition_sum_change_30_days (Union[Unset, ColumnChangeSumSince30DaysCheckSpec]):
    """

    daily_partition_mean_change: Union[Unset, "ColumnChangeMeanCheckSpec"] = UNSET
    daily_partition_mean_change_yesterday: Union[
        Unset, "ColumnChangeMeanSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_median_change: Union[Unset, "ColumnChangeMedianCheckSpec"] = UNSET
    daily_partition_median_change_yesterday: Union[
        Unset, "ColumnChangeMedianSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_sum_change: Union[Unset, "ColumnChangeSumCheckSpec"] = UNSET
    daily_partition_sum_change_yesterday: Union[
        Unset, "ColumnChangeSumSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_mean_anomaly_7_days: Union[
        Unset, "ColumnAnomalyMean7DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_anomaly_30_days: Union[
        Unset, "ColumnAnomalyMean30DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_anomaly_60_days: Union[
        Unset, "ColumnAnomalyMean60DaysCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly_7_days: Union[
        Unset, "ColumnAnomalyMedian7DaysCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly_30_days: Union[
        Unset, "ColumnAnomalyMedian30DaysCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly_60_days: Union[
        Unset, "ColumnAnomalyMedian60DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_anomaly_7_days: Union[
        Unset, "ColumnAnomalySum7DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_anomaly_30_days: Union[
        Unset, "ColumnAnomalySum30DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_anomaly_60_days: Union[
        Unset, "ColumnAnomalySum60DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_change_7_days: Union[
        Unset, "ColumnChangeMeanSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_change_30_days: Union[
        Unset, "ColumnChangeMeanSince30DaysCheckSpec"
    ] = UNSET
    daily_partition_median_change_7_days: Union[
        Unset, "ColumnChangeMedianSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_median_change_30_days: Union[
        Unset, "ColumnChangeMedianSince30DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_change_7_days: Union[
        Unset, "ColumnChangeSumSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_change_30_days: Union[
        Unset, "ColumnChangeSumSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partition_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change, Unset):
            daily_partition_mean_change = self.daily_partition_mean_change.to_dict()

        daily_partition_mean_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_change_yesterday, Unset):
            daily_partition_mean_change_yesterday = (
                self.daily_partition_mean_change_yesterday.to_dict()
            )

        daily_partition_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change, Unset):
            daily_partition_median_change = self.daily_partition_median_change.to_dict()

        daily_partition_median_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_change_yesterday, Unset):
            daily_partition_median_change_yesterday = (
                self.daily_partition_median_change_yesterday.to_dict()
            )

        daily_partition_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change, Unset):
            daily_partition_sum_change = self.daily_partition_sum_change.to_dict()

        daily_partition_sum_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_change_yesterday, Unset):
            daily_partition_sum_change_yesterday = (
                self.daily_partition_sum_change_yesterday.to_dict()
            )

        daily_partition_mean_anomaly_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly_7_days, Unset):
            daily_partition_mean_anomaly_7_days = (
                self.daily_partition_mean_anomaly_7_days.to_dict()
            )

        daily_partition_mean_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly_30_days, Unset):
            daily_partition_mean_anomaly_30_days = (
                self.daily_partition_mean_anomaly_30_days.to_dict()
            )

        daily_partition_mean_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly_60_days, Unset):
            daily_partition_mean_anomaly_60_days = (
                self.daily_partition_mean_anomaly_60_days.to_dict()
            )

        daily_partition_median_anomaly_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_anomaly_7_days, Unset):
            daily_partition_median_anomaly_7_days = (
                self.daily_partition_median_anomaly_7_days.to_dict()
            )

        daily_partition_median_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_anomaly_30_days, Unset):
            daily_partition_median_anomaly_30_days = (
                self.daily_partition_median_anomaly_30_days.to_dict()
            )

        daily_partition_median_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_anomaly_60_days, Unset):
            daily_partition_median_anomaly_60_days = (
                self.daily_partition_median_anomaly_60_days.to_dict()
            )

        daily_partition_sum_anomaly_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly_7_days, Unset):
            daily_partition_sum_anomaly_7_days = (
                self.daily_partition_sum_anomaly_7_days.to_dict()
            )

        daily_partition_sum_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly_30_days, Unset):
            daily_partition_sum_anomaly_30_days = (
                self.daily_partition_sum_anomaly_30_days.to_dict()
            )

        daily_partition_sum_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly_60_days, Unset):
            daily_partition_sum_anomaly_60_days = (
                self.daily_partition_sum_anomaly_60_days.to_dict()
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
        if daily_partition_mean_change is not UNSET:
            field_dict["daily_partition_mean_change"] = daily_partition_mean_change
        if daily_partition_mean_change_yesterday is not UNSET:
            field_dict[
                "daily_partition_mean_change_yesterday"
            ] = daily_partition_mean_change_yesterday
        if daily_partition_median_change is not UNSET:
            field_dict["daily_partition_median_change"] = daily_partition_median_change
        if daily_partition_median_change_yesterday is not UNSET:
            field_dict[
                "daily_partition_median_change_yesterday"
            ] = daily_partition_median_change_yesterday
        if daily_partition_sum_change is not UNSET:
            field_dict["daily_partition_sum_change"] = daily_partition_sum_change
        if daily_partition_sum_change_yesterday is not UNSET:
            field_dict[
                "daily_partition_sum_change_yesterday"
            ] = daily_partition_sum_change_yesterday
        if daily_partition_mean_anomaly_7_days is not UNSET:
            field_dict[
                "daily_partition_mean_anomaly_7_days"
            ] = daily_partition_mean_anomaly_7_days
        if daily_partition_mean_anomaly_30_days is not UNSET:
            field_dict[
                "daily_partition_mean_anomaly_30_days"
            ] = daily_partition_mean_anomaly_30_days
        if daily_partition_mean_anomaly_60_days is not UNSET:
            field_dict[
                "daily_partition_mean_anomaly_60_days"
            ] = daily_partition_mean_anomaly_60_days
        if daily_partition_median_anomaly_7_days is not UNSET:
            field_dict[
                "daily_partition_median_anomaly_7_days"
            ] = daily_partition_median_anomaly_7_days
        if daily_partition_median_anomaly_30_days is not UNSET:
            field_dict[
                "daily_partition_median_anomaly_30_days"
            ] = daily_partition_median_anomaly_30_days
        if daily_partition_median_anomaly_60_days is not UNSET:
            field_dict[
                "daily_partition_median_anomaly_60_days"
            ] = daily_partition_median_anomaly_60_days
        if daily_partition_sum_anomaly_7_days is not UNSET:
            field_dict[
                "daily_partition_sum_anomaly_7_days"
            ] = daily_partition_sum_anomaly_7_days
        if daily_partition_sum_anomaly_30_days is not UNSET:
            field_dict[
                "daily_partition_sum_anomaly_30_days"
            ] = daily_partition_sum_anomaly_30_days
        if daily_partition_sum_anomaly_60_days is not UNSET:
            field_dict[
                "daily_partition_sum_anomaly_60_days"
            ] = daily_partition_sum_anomaly_60_days
        if daily_partition_mean_change_7_days is not UNSET:
            field_dict[
                "daily_partition_mean_change_7_days"
            ] = daily_partition_mean_change_7_days
        if daily_partition_mean_change_30_days is not UNSET:
            field_dict[
                "daily_partition_mean_change_30_days"
            ] = daily_partition_mean_change_30_days
        if daily_partition_median_change_7_days is not UNSET:
            field_dict[
                "daily_partition_median_change_7_days"
            ] = daily_partition_median_change_7_days
        if daily_partition_median_change_30_days is not UNSET:
            field_dict[
                "daily_partition_median_change_30_days"
            ] = daily_partition_median_change_30_days
        if daily_partition_sum_change_7_days is not UNSET:
            field_dict[
                "daily_partition_sum_change_7_days"
            ] = daily_partition_sum_change_7_days
        if daily_partition_sum_change_30_days is not UNSET:
            field_dict[
                "daily_partition_sum_change_30_days"
            ] = daily_partition_sum_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_mean_7_days_check_spec import (
            ColumnAnomalyMean7DaysCheckSpec,
        )
        from ..models.column_anomaly_mean_30_days_check_spec import (
            ColumnAnomalyMean30DaysCheckSpec,
        )
        from ..models.column_anomaly_mean_60_days_check_spec import (
            ColumnAnomalyMean60DaysCheckSpec,
        )
        from ..models.column_anomaly_median_7_days_check_spec import (
            ColumnAnomalyMedian7DaysCheckSpec,
        )
        from ..models.column_anomaly_median_30_days_check_spec import (
            ColumnAnomalyMedian30DaysCheckSpec,
        )
        from ..models.column_anomaly_median_60_days_check_spec import (
            ColumnAnomalyMedian60DaysCheckSpec,
        )
        from ..models.column_anomaly_sum_7_days_check_spec import (
            ColumnAnomalySum7DaysCheckSpec,
        )
        from ..models.column_anomaly_sum_30_days_check_spec import (
            ColumnAnomalySum30DaysCheckSpec,
        )
        from ..models.column_anomaly_sum_60_days_check_spec import (
            ColumnAnomalySum60DaysCheckSpec,
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
        _daily_partition_mean_change = d.pop("daily_partition_mean_change", UNSET)
        daily_partition_mean_change: Union[Unset, ColumnChangeMeanCheckSpec]
        if isinstance(_daily_partition_mean_change, Unset):
            daily_partition_mean_change = UNSET
        else:
            daily_partition_mean_change = ColumnChangeMeanCheckSpec.from_dict(
                _daily_partition_mean_change
            )

        _daily_partition_mean_change_yesterday = d.pop(
            "daily_partition_mean_change_yesterday", UNSET
        )
        daily_partition_mean_change_yesterday: Union[
            Unset, ColumnChangeMeanSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_mean_change_yesterday, Unset):
            daily_partition_mean_change_yesterday = UNSET
        else:
            daily_partition_mean_change_yesterday = (
                ColumnChangeMeanSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_mean_change_yesterday
                )
            )

        _daily_partition_median_change = d.pop("daily_partition_median_change", UNSET)
        daily_partition_median_change: Union[Unset, ColumnChangeMedianCheckSpec]
        if isinstance(_daily_partition_median_change, Unset):
            daily_partition_median_change = UNSET
        else:
            daily_partition_median_change = ColumnChangeMedianCheckSpec.from_dict(
                _daily_partition_median_change
            )

        _daily_partition_median_change_yesterday = d.pop(
            "daily_partition_median_change_yesterday", UNSET
        )
        daily_partition_median_change_yesterday: Union[
            Unset, ColumnChangeMedianSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_median_change_yesterday, Unset):
            daily_partition_median_change_yesterday = UNSET
        else:
            daily_partition_median_change_yesterday = (
                ColumnChangeMedianSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_median_change_yesterday
                )
            )

        _daily_partition_sum_change = d.pop("daily_partition_sum_change", UNSET)
        daily_partition_sum_change: Union[Unset, ColumnChangeSumCheckSpec]
        if isinstance(_daily_partition_sum_change, Unset):
            daily_partition_sum_change = UNSET
        else:
            daily_partition_sum_change = ColumnChangeSumCheckSpec.from_dict(
                _daily_partition_sum_change
            )

        _daily_partition_sum_change_yesterday = d.pop(
            "daily_partition_sum_change_yesterday", UNSET
        )
        daily_partition_sum_change_yesterday: Union[
            Unset, ColumnChangeSumSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_sum_change_yesterday, Unset):
            daily_partition_sum_change_yesterday = UNSET
        else:
            daily_partition_sum_change_yesterday = (
                ColumnChangeSumSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_sum_change_yesterday
                )
            )

        _daily_partition_mean_anomaly_7_days = d.pop(
            "daily_partition_mean_anomaly_7_days", UNSET
        )
        daily_partition_mean_anomaly_7_days: Union[
            Unset, ColumnAnomalyMean7DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_anomaly_7_days, Unset):
            daily_partition_mean_anomaly_7_days = UNSET
        else:
            daily_partition_mean_anomaly_7_days = (
                ColumnAnomalyMean7DaysCheckSpec.from_dict(
                    _daily_partition_mean_anomaly_7_days
                )
            )

        _daily_partition_mean_anomaly_30_days = d.pop(
            "daily_partition_mean_anomaly_30_days", UNSET
        )
        daily_partition_mean_anomaly_30_days: Union[
            Unset, ColumnAnomalyMean30DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_anomaly_30_days, Unset):
            daily_partition_mean_anomaly_30_days = UNSET
        else:
            daily_partition_mean_anomaly_30_days = (
                ColumnAnomalyMean30DaysCheckSpec.from_dict(
                    _daily_partition_mean_anomaly_30_days
                )
            )

        _daily_partition_mean_anomaly_60_days = d.pop(
            "daily_partition_mean_anomaly_60_days", UNSET
        )
        daily_partition_mean_anomaly_60_days: Union[
            Unset, ColumnAnomalyMean60DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_anomaly_60_days, Unset):
            daily_partition_mean_anomaly_60_days = UNSET
        else:
            daily_partition_mean_anomaly_60_days = (
                ColumnAnomalyMean60DaysCheckSpec.from_dict(
                    _daily_partition_mean_anomaly_60_days
                )
            )

        _daily_partition_median_anomaly_7_days = d.pop(
            "daily_partition_median_anomaly_7_days", UNSET
        )
        daily_partition_median_anomaly_7_days: Union[
            Unset, ColumnAnomalyMedian7DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly_7_days, Unset):
            daily_partition_median_anomaly_7_days = UNSET
        else:
            daily_partition_median_anomaly_7_days = (
                ColumnAnomalyMedian7DaysCheckSpec.from_dict(
                    _daily_partition_median_anomaly_7_days
                )
            )

        _daily_partition_median_anomaly_30_days = d.pop(
            "daily_partition_median_anomaly_30_days", UNSET
        )
        daily_partition_median_anomaly_30_days: Union[
            Unset, ColumnAnomalyMedian30DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly_30_days, Unset):
            daily_partition_median_anomaly_30_days = UNSET
        else:
            daily_partition_median_anomaly_30_days = (
                ColumnAnomalyMedian30DaysCheckSpec.from_dict(
                    _daily_partition_median_anomaly_30_days
                )
            )

        _daily_partition_median_anomaly_60_days = d.pop(
            "daily_partition_median_anomaly_60_days", UNSET
        )
        daily_partition_median_anomaly_60_days: Union[
            Unset, ColumnAnomalyMedian60DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly_60_days, Unset):
            daily_partition_median_anomaly_60_days = UNSET
        else:
            daily_partition_median_anomaly_60_days = (
                ColumnAnomalyMedian60DaysCheckSpec.from_dict(
                    _daily_partition_median_anomaly_60_days
                )
            )

        _daily_partition_sum_anomaly_7_days = d.pop(
            "daily_partition_sum_anomaly_7_days", UNSET
        )
        daily_partition_sum_anomaly_7_days: Union[Unset, ColumnAnomalySum7DaysCheckSpec]
        if isinstance(_daily_partition_sum_anomaly_7_days, Unset):
            daily_partition_sum_anomaly_7_days = UNSET
        else:
            daily_partition_sum_anomaly_7_days = (
                ColumnAnomalySum7DaysCheckSpec.from_dict(
                    _daily_partition_sum_anomaly_7_days
                )
            )

        _daily_partition_sum_anomaly_30_days = d.pop(
            "daily_partition_sum_anomaly_30_days", UNSET
        )
        daily_partition_sum_anomaly_30_days: Union[
            Unset, ColumnAnomalySum30DaysCheckSpec
        ]
        if isinstance(_daily_partition_sum_anomaly_30_days, Unset):
            daily_partition_sum_anomaly_30_days = UNSET
        else:
            daily_partition_sum_anomaly_30_days = (
                ColumnAnomalySum30DaysCheckSpec.from_dict(
                    _daily_partition_sum_anomaly_30_days
                )
            )

        _daily_partition_sum_anomaly_60_days = d.pop(
            "daily_partition_sum_anomaly_60_days", UNSET
        )
        daily_partition_sum_anomaly_60_days: Union[
            Unset, ColumnAnomalySum60DaysCheckSpec
        ]
        if isinstance(_daily_partition_sum_anomaly_60_days, Unset):
            daily_partition_sum_anomaly_60_days = UNSET
        else:
            daily_partition_sum_anomaly_60_days = (
                ColumnAnomalySum60DaysCheckSpec.from_dict(
                    _daily_partition_sum_anomaly_60_days
                )
            )

        _daily_partition_mean_change_7_days = d.pop(
            "daily_partition_mean_change_7_days", UNSET
        )
        daily_partition_mean_change_7_days: Union[
            Unset, ColumnChangeMeanSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_change_7_days, Unset):
            daily_partition_mean_change_7_days = UNSET
        else:
            daily_partition_mean_change_7_days = (
                ColumnChangeMeanSince7DaysCheckSpec.from_dict(
                    _daily_partition_mean_change_7_days
                )
            )

        _daily_partition_mean_change_30_days = d.pop(
            "daily_partition_mean_change_30_days", UNSET
        )
        daily_partition_mean_change_30_days: Union[
            Unset, ColumnChangeMeanSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_change_30_days, Unset):
            daily_partition_mean_change_30_days = UNSET
        else:
            daily_partition_mean_change_30_days = (
                ColumnChangeMeanSince30DaysCheckSpec.from_dict(
                    _daily_partition_mean_change_30_days
                )
            )

        _daily_partition_median_change_7_days = d.pop(
            "daily_partition_median_change_7_days", UNSET
        )
        daily_partition_median_change_7_days: Union[
            Unset, ColumnChangeMedianSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_change_7_days, Unset):
            daily_partition_median_change_7_days = UNSET
        else:
            daily_partition_median_change_7_days = (
                ColumnChangeMedianSince7DaysCheckSpec.from_dict(
                    _daily_partition_median_change_7_days
                )
            )

        _daily_partition_median_change_30_days = d.pop(
            "daily_partition_median_change_30_days", UNSET
        )
        daily_partition_median_change_30_days: Union[
            Unset, ColumnChangeMedianSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_change_30_days, Unset):
            daily_partition_median_change_30_days = UNSET
        else:
            daily_partition_median_change_30_days = (
                ColumnChangeMedianSince30DaysCheckSpec.from_dict(
                    _daily_partition_median_change_30_days
                )
            )

        _daily_partition_sum_change_7_days = d.pop(
            "daily_partition_sum_change_7_days", UNSET
        )
        daily_partition_sum_change_7_days: Union[
            Unset, ColumnChangeSumSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_sum_change_7_days, Unset):
            daily_partition_sum_change_7_days = UNSET
        else:
            daily_partition_sum_change_7_days = (
                ColumnChangeSumSince7DaysCheckSpec.from_dict(
                    _daily_partition_sum_change_7_days
                )
            )

        _daily_partition_sum_change_30_days = d.pop(
            "daily_partition_sum_change_30_days", UNSET
        )
        daily_partition_sum_change_30_days: Union[
            Unset, ColumnChangeSumSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_sum_change_30_days, Unset):
            daily_partition_sum_change_30_days = UNSET
        else:
            daily_partition_sum_change_30_days = (
                ColumnChangeSumSince30DaysCheckSpec.from_dict(
                    _daily_partition_sum_change_30_days
                )
            )

        column_anomaly_daily_partitioned_checks_spec = cls(
            daily_partition_mean_change=daily_partition_mean_change,
            daily_partition_mean_change_yesterday=daily_partition_mean_change_yesterday,
            daily_partition_median_change=daily_partition_median_change,
            daily_partition_median_change_yesterday=daily_partition_median_change_yesterday,
            daily_partition_sum_change=daily_partition_sum_change,
            daily_partition_sum_change_yesterday=daily_partition_sum_change_yesterday,
            daily_partition_mean_anomaly_7_days=daily_partition_mean_anomaly_7_days,
            daily_partition_mean_anomaly_30_days=daily_partition_mean_anomaly_30_days,
            daily_partition_mean_anomaly_60_days=daily_partition_mean_anomaly_60_days,
            daily_partition_median_anomaly_7_days=daily_partition_median_anomaly_7_days,
            daily_partition_median_anomaly_30_days=daily_partition_median_anomaly_30_days,
            daily_partition_median_anomaly_60_days=daily_partition_median_anomaly_60_days,
            daily_partition_sum_anomaly_7_days=daily_partition_sum_anomaly_7_days,
            daily_partition_sum_anomaly_30_days=daily_partition_sum_anomaly_30_days,
            daily_partition_sum_anomaly_60_days=daily_partition_sum_anomaly_60_days,
            daily_partition_mean_change_7_days=daily_partition_mean_change_7_days,
            daily_partition_mean_change_30_days=daily_partition_mean_change_30_days,
            daily_partition_median_change_7_days=daily_partition_median_change_7_days,
            daily_partition_median_change_30_days=daily_partition_median_change_30_days,
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
