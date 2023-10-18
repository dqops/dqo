from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_daily_partitioned_checks_spec_custom_checks import (
        ColumnAnomalyDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_anomaly_stationary_mean_30_days_check_spec import (
        ColumnAnomalyStationaryMean30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_mean_check_spec import (
        ColumnAnomalyStationaryMeanCheckSpec,
    )
    from ..models.column_anomaly_stationary_median_30_days_check_spec import (
        ColumnAnomalyStationaryMedian30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_median_check_spec import (
        ColumnAnomalyStationaryMedianCheckSpec,
    )
    from ..models.column_anomaly_stationary_partition_sum_30_days_check_spec import (
        ColumnAnomalyStationaryPartitionSum30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_partition_sum_check_spec import (
        ColumnAnomalyStationaryPartitionSumCheckSpec,
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


@_attrs_define
class ColumnAnomalyDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
        daily_partition_mean_change_yesterday (Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]):
        daily_partition_median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
        daily_partition_median_change_yesterday (Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]):
        daily_partition_sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
        daily_partition_sum_change_yesterday (Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]):
        daily_partition_mean_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMean30DaysCheckSpec]):
        daily_partition_mean_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMeanCheckSpec]):
        daily_partition_median_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec]):
        daily_partition_median_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMedianCheckSpec]):
        daily_partition_sum_anomaly_stationary_30_days (Union[Unset,
            ColumnAnomalyStationaryPartitionSum30DaysCheckSpec]):
        daily_partition_sum_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryPartitionSumCheckSpec]):
        daily_partition_mean_change_7_days (Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]):
        daily_partition_mean_change_30_days (Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]):
        daily_partition_median_change_7_days (Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]):
        daily_partition_median_change_30_days (Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]):
        daily_partition_sum_change_7_days (Union[Unset, ColumnChangeSumSince7DaysCheckSpec]):
        daily_partition_sum_change_30_days (Union[Unset, ColumnChangeSumSince30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnAnomalyDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
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
    daily_partition_mean_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryMean30DaysCheckSpec"
    ] = UNSET
    daily_partition_mean_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryMeanCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryMedian30DaysCheckSpec"
    ] = UNSET
    daily_partition_median_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryMedianCheckSpec"
    ] = UNSET
    daily_partition_sum_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryPartitionSum30DaysCheckSpec"
    ] = UNSET
    daily_partition_sum_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryPartitionSumCheckSpec"
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
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

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

        daily_partition_mean_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly_stationary_30_days, Unset):
            daily_partition_mean_anomaly_stationary_30_days = (
                self.daily_partition_mean_anomaly_stationary_30_days.to_dict()
            )

        daily_partition_mean_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_mean_anomaly_stationary, Unset):
            daily_partition_mean_anomaly_stationary = (
                self.daily_partition_mean_anomaly_stationary.to_dict()
            )

        daily_partition_median_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_median_anomaly_stationary_30_days, Unset
        ):
            daily_partition_median_anomaly_stationary_30_days = (
                self.daily_partition_median_anomaly_stationary_30_days.to_dict()
            )

        daily_partition_median_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_median_anomaly_stationary, Unset):
            daily_partition_median_anomaly_stationary = (
                self.daily_partition_median_anomaly_stationary.to_dict()
            )

        daily_partition_sum_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly_stationary_30_days, Unset):
            daily_partition_sum_anomaly_stationary_30_days = (
                self.daily_partition_sum_anomaly_stationary_30_days.to_dict()
            )

        daily_partition_sum_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_sum_anomaly_stationary, Unset):
            daily_partition_sum_anomaly_stationary = (
                self.daily_partition_sum_anomaly_stationary.to_dict()
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
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
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
        if daily_partition_mean_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "daily_partition_mean_anomaly_stationary_30_days"
            ] = daily_partition_mean_anomaly_stationary_30_days
        if daily_partition_mean_anomaly_stationary is not UNSET:
            field_dict[
                "daily_partition_mean_anomaly_stationary"
            ] = daily_partition_mean_anomaly_stationary
        if daily_partition_median_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "daily_partition_median_anomaly_stationary_30_days"
            ] = daily_partition_median_anomaly_stationary_30_days
        if daily_partition_median_anomaly_stationary is not UNSET:
            field_dict[
                "daily_partition_median_anomaly_stationary"
            ] = daily_partition_median_anomaly_stationary
        if daily_partition_sum_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "daily_partition_sum_anomaly_stationary_30_days"
            ] = daily_partition_sum_anomaly_stationary_30_days
        if daily_partition_sum_anomaly_stationary is not UNSET:
            field_dict[
                "daily_partition_sum_anomaly_stationary"
            ] = daily_partition_sum_anomaly_stationary
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
        from ..models.column_anomaly_daily_partitioned_checks_spec_custom_checks import (
            ColumnAnomalyDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_anomaly_stationary_mean_30_days_check_spec import (
            ColumnAnomalyStationaryMean30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_mean_check_spec import (
            ColumnAnomalyStationaryMeanCheckSpec,
        )
        from ..models.column_anomaly_stationary_median_30_days_check_spec import (
            ColumnAnomalyStationaryMedian30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_median_check_spec import (
            ColumnAnomalyStationaryMedianCheckSpec,
        )
        from ..models.column_anomaly_stationary_partition_sum_30_days_check_spec import (
            ColumnAnomalyStationaryPartitionSum30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_partition_sum_check_spec import (
            ColumnAnomalyStationaryPartitionSumCheckSpec,
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

        _daily_partition_mean_anomaly_stationary_30_days = d.pop(
            "daily_partition_mean_anomaly_stationary_30_days", UNSET
        )
        daily_partition_mean_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryMean30DaysCheckSpec
        ]
        if isinstance(_daily_partition_mean_anomaly_stationary_30_days, Unset):
            daily_partition_mean_anomaly_stationary_30_days = UNSET
        else:
            daily_partition_mean_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryMean30DaysCheckSpec.from_dict(
                    _daily_partition_mean_anomaly_stationary_30_days
                )
            )

        _daily_partition_mean_anomaly_stationary = d.pop(
            "daily_partition_mean_anomaly_stationary", UNSET
        )
        daily_partition_mean_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryMeanCheckSpec
        ]
        if isinstance(_daily_partition_mean_anomaly_stationary, Unset):
            daily_partition_mean_anomaly_stationary = UNSET
        else:
            daily_partition_mean_anomaly_stationary = (
                ColumnAnomalyStationaryMeanCheckSpec.from_dict(
                    _daily_partition_mean_anomaly_stationary
                )
            )

        _daily_partition_median_anomaly_stationary_30_days = d.pop(
            "daily_partition_median_anomaly_stationary_30_days", UNSET
        )
        daily_partition_median_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly_stationary_30_days, Unset):
            daily_partition_median_anomaly_stationary_30_days = UNSET
        else:
            daily_partition_median_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryMedian30DaysCheckSpec.from_dict(
                    _daily_partition_median_anomaly_stationary_30_days
                )
            )

        _daily_partition_median_anomaly_stationary = d.pop(
            "daily_partition_median_anomaly_stationary", UNSET
        )
        daily_partition_median_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryMedianCheckSpec
        ]
        if isinstance(_daily_partition_median_anomaly_stationary, Unset):
            daily_partition_median_anomaly_stationary = UNSET
        else:
            daily_partition_median_anomaly_stationary = (
                ColumnAnomalyStationaryMedianCheckSpec.from_dict(
                    _daily_partition_median_anomaly_stationary
                )
            )

        _daily_partition_sum_anomaly_stationary_30_days = d.pop(
            "daily_partition_sum_anomaly_stationary_30_days", UNSET
        )
        daily_partition_sum_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryPartitionSum30DaysCheckSpec
        ]
        if isinstance(_daily_partition_sum_anomaly_stationary_30_days, Unset):
            daily_partition_sum_anomaly_stationary_30_days = UNSET
        else:
            daily_partition_sum_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryPartitionSum30DaysCheckSpec.from_dict(
                    _daily_partition_sum_anomaly_stationary_30_days
                )
            )

        _daily_partition_sum_anomaly_stationary = d.pop(
            "daily_partition_sum_anomaly_stationary", UNSET
        )
        daily_partition_sum_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryPartitionSumCheckSpec
        ]
        if isinstance(_daily_partition_sum_anomaly_stationary, Unset):
            daily_partition_sum_anomaly_stationary = UNSET
        else:
            daily_partition_sum_anomaly_stationary = (
                ColumnAnomalyStationaryPartitionSumCheckSpec.from_dict(
                    _daily_partition_sum_anomaly_stationary
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
            custom_checks=custom_checks,
            daily_partition_mean_change=daily_partition_mean_change,
            daily_partition_mean_change_yesterday=daily_partition_mean_change_yesterday,
            daily_partition_median_change=daily_partition_median_change,
            daily_partition_median_change_yesterday=daily_partition_median_change_yesterday,
            daily_partition_sum_change=daily_partition_sum_change,
            daily_partition_sum_change_yesterday=daily_partition_sum_change_yesterday,
            daily_partition_mean_anomaly_stationary_30_days=daily_partition_mean_anomaly_stationary_30_days,
            daily_partition_mean_anomaly_stationary=daily_partition_mean_anomaly_stationary,
            daily_partition_median_anomaly_stationary_30_days=daily_partition_median_anomaly_stationary_30_days,
            daily_partition_median_anomaly_stationary=daily_partition_median_anomaly_stationary,
            daily_partition_sum_anomaly_stationary_30_days=daily_partition_sum_anomaly_stationary_30_days,
            daily_partition_sum_anomaly_stationary=daily_partition_sum_anomaly_stationary,
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
