from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_stationary_distinct_percent_30_days_check_spec import (
        ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_distinct_percent_check_spec import (
        ColumnAnomalyStationaryDistinctPercentCheckSpec,
    )
    from ..models.column_anomaly_stationary_partition_distinct_count_30_days_check_spec import (
        ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_partition_distinct_count_check_spec import (
        ColumnAnomalyStationaryPartitionDistinctCountCheckSpec,
    )
    from ..models.column_change_distinct_count_check_spec import (
        ColumnChangeDistinctCountCheckSpec,
    )
    from ..models.column_change_distinct_count_since_7_days_check_spec import (
        ColumnChangeDistinctCountSince7DaysCheckSpec,
    )
    from ..models.column_change_distinct_count_since_30_days_check_spec import (
        ColumnChangeDistinctCountSince30DaysCheckSpec,
    )
    from ..models.column_change_distinct_count_since_yesterday_check_spec import (
        ColumnChangeDistinctCountSinceYesterdayCheckSpec,
    )
    from ..models.column_change_distinct_percent_check_spec import (
        ColumnChangeDistinctPercentCheckSpec,
    )
    from ..models.column_change_distinct_percent_since_7_days_check_spec import (
        ColumnChangeDistinctPercentSince7DaysCheckSpec,
    )
    from ..models.column_change_distinct_percent_since_30_days_check_spec import (
        ColumnChangeDistinctPercentSince30DaysCheckSpec,
    )
    from ..models.column_change_distinct_percent_since_yesterday_check_spec import (
        ColumnChangeDistinctPercentSinceYesterdayCheckSpec,
    )
    from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
    from ..models.column_distinct_percent_check_spec import (
        ColumnDistinctPercentCheckSpec,
    )
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )
    from ..models.column_uniqueness_daily_partitioned_checks_spec_custom_checks import (
        ColumnUniquenessDailyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnUniquenessDailyPartitionedChecksSpec")


@_attrs_define
class ColumnUniquenessDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnUniquenessDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        daily_partition_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        daily_partition_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        daily_partition_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
        daily_partition_anomaly_stationary_distinct_count (Union[Unset,
            ColumnAnomalyStationaryPartitionDistinctCountCheckSpec]):
        daily_partition_anomaly_stationary_distinct_percent (Union[Unset,
            ColumnAnomalyStationaryDistinctPercentCheckSpec]):
        daily_partition_change_distinct_count (Union[Unset, ColumnChangeDistinctCountCheckSpec]):
        daily_partition_change_distinct_count_since_yesterday (Union[Unset,
            ColumnChangeDistinctCountSinceYesterdayCheckSpec]):
        daily_partition_change_distinct_percent (Union[Unset, ColumnChangeDistinctPercentCheckSpec]):
        daily_partition_change_distinct_percent_since_yesterday (Union[Unset,
            ColumnChangeDistinctPercentSinceYesterdayCheckSpec]):
        daily_partition_anomaly_stationary_distinct_count_30_days (Union[Unset,
            ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec]):
        daily_partition_anomaly_stationary_distinct_percent_30_days (Union[Unset,
            ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec]):
        daily_partition_change_distinct_count_since_7_days (Union[Unset, ColumnChangeDistinctCountSince7DaysCheckSpec]):
        daily_partition_change_distinct_count_since_30_days (Union[Unset,
            ColumnChangeDistinctCountSince30DaysCheckSpec]):
        daily_partition_change_distinct_percent_since_7_days (Union[Unset,
            ColumnChangeDistinctPercentSince7DaysCheckSpec]):
        daily_partition_change_distinct_percent_since_30_days (Union[Unset,
            ColumnChangeDistinctPercentSince30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnUniquenessDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    daily_partition_distinct_percent: Union[
        Unset, "ColumnDistinctPercentCheckSpec"
    ] = UNSET
    daily_partition_duplicate_count: Union[
        Unset, "ColumnDuplicateCountCheckSpec"
    ] = UNSET
    daily_partition_duplicate_percent: Union[
        Unset, "ColumnDuplicatePercentCheckSpec"
    ] = UNSET
    daily_partition_anomaly_stationary_distinct_count: Union[
        Unset, "ColumnAnomalyStationaryPartitionDistinctCountCheckSpec"
    ] = UNSET
    daily_partition_anomaly_stationary_distinct_percent: Union[
        Unset, "ColumnAnomalyStationaryDistinctPercentCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_count: Union[
        Unset, "ColumnChangeDistinctCountCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_count_since_yesterday: Union[
        Unset, "ColumnChangeDistinctCountSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_percent: Union[
        Unset, "ColumnChangeDistinctPercentCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_percent_since_yesterday: Union[
        Unset, "ColumnChangeDistinctPercentSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_anomaly_stationary_distinct_count_30_days: Union[
        Unset, "ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec"
    ] = UNSET
    daily_partition_anomaly_stationary_distinct_percent_30_days: Union[
        Unset, "ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_count_since_7_days: Union[
        Unset, "ColumnChangeDistinctCountSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_count_since_30_days: Union[
        Unset, "ColumnChangeDistinctCountSince30DaysCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_percent_since_7_days: Union[
        Unset, "ColumnChangeDistinctPercentSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_change_distinct_percent_since_30_days: Union[
        Unset, "ColumnChangeDistinctPercentSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_distinct_count, Unset):
            daily_partition_distinct_count = (
                self.daily_partition_distinct_count.to_dict()
            )

        daily_partition_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_distinct_percent, Unset):
            daily_partition_distinct_percent = (
                self.daily_partition_distinct_percent.to_dict()
            )

        daily_partition_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_duplicate_count, Unset):
            daily_partition_duplicate_count = (
                self.daily_partition_duplicate_count.to_dict()
            )

        daily_partition_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_duplicate_percent, Unset):
            daily_partition_duplicate_percent = (
                self.daily_partition_duplicate_percent.to_dict()
            )

        daily_partition_anomaly_stationary_distinct_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_anomaly_stationary_distinct_count, Unset
        ):
            daily_partition_anomaly_stationary_distinct_count = (
                self.daily_partition_anomaly_stationary_distinct_count.to_dict()
            )

        daily_partition_anomaly_stationary_distinct_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_anomaly_stationary_distinct_percent, Unset
        ):
            daily_partition_anomaly_stationary_distinct_percent = (
                self.daily_partition_anomaly_stationary_distinct_percent.to_dict()
            )

        daily_partition_change_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_change_distinct_count, Unset):
            daily_partition_change_distinct_count = (
                self.daily_partition_change_distinct_count.to_dict()
            )

        daily_partition_change_distinct_count_since_yesterday: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_count_since_yesterday, Unset
        ):
            daily_partition_change_distinct_count_since_yesterday = (
                self.daily_partition_change_distinct_count_since_yesterday.to_dict()
            )

        daily_partition_change_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_change_distinct_percent, Unset):
            daily_partition_change_distinct_percent = (
                self.daily_partition_change_distinct_percent.to_dict()
            )

        daily_partition_change_distinct_percent_since_yesterday: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_percent_since_yesterday, Unset
        ):
            daily_partition_change_distinct_percent_since_yesterday = (
                self.daily_partition_change_distinct_percent_since_yesterday.to_dict()
            )

        daily_partition_anomaly_stationary_distinct_count_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_anomaly_stationary_distinct_count_30_days, Unset
        ):
            daily_partition_anomaly_stationary_distinct_count_30_days = (
                self.daily_partition_anomaly_stationary_distinct_count_30_days.to_dict()
            )

        daily_partition_anomaly_stationary_distinct_percent_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_anomaly_stationary_distinct_percent_30_days, Unset
        ):
            daily_partition_anomaly_stationary_distinct_percent_30_days = (
                self.daily_partition_anomaly_stationary_distinct_percent_30_days.to_dict()
            )

        daily_partition_change_distinct_count_since_7_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_count_since_7_days, Unset
        ):
            daily_partition_change_distinct_count_since_7_days = (
                self.daily_partition_change_distinct_count_since_7_days.to_dict()
            )

        daily_partition_change_distinct_count_since_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_count_since_30_days, Unset
        ):
            daily_partition_change_distinct_count_since_30_days = (
                self.daily_partition_change_distinct_count_since_30_days.to_dict()
            )

        daily_partition_change_distinct_percent_since_7_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_percent_since_7_days, Unset
        ):
            daily_partition_change_distinct_percent_since_7_days = (
                self.daily_partition_change_distinct_percent_since_7_days.to_dict()
            )

        daily_partition_change_distinct_percent_since_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_change_distinct_percent_since_30_days, Unset
        ):
            daily_partition_change_distinct_percent_since_30_days = (
                self.daily_partition_change_distinct_percent_since_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_distinct_count is not UNSET:
            field_dict[
                "daily_partition_distinct_count"
            ] = daily_partition_distinct_count
        if daily_partition_distinct_percent is not UNSET:
            field_dict[
                "daily_partition_distinct_percent"
            ] = daily_partition_distinct_percent
        if daily_partition_duplicate_count is not UNSET:
            field_dict[
                "daily_partition_duplicate_count"
            ] = daily_partition_duplicate_count
        if daily_partition_duplicate_percent is not UNSET:
            field_dict[
                "daily_partition_duplicate_percent"
            ] = daily_partition_duplicate_percent
        if daily_partition_anomaly_stationary_distinct_count is not UNSET:
            field_dict[
                "daily_partition_anomaly_stationary_distinct_count"
            ] = daily_partition_anomaly_stationary_distinct_count
        if daily_partition_anomaly_stationary_distinct_percent is not UNSET:
            field_dict[
                "daily_partition_anomaly_stationary_distinct_percent"
            ] = daily_partition_anomaly_stationary_distinct_percent
        if daily_partition_change_distinct_count is not UNSET:
            field_dict[
                "daily_partition_change_distinct_count"
            ] = daily_partition_change_distinct_count
        if daily_partition_change_distinct_count_since_yesterday is not UNSET:
            field_dict[
                "daily_partition_change_distinct_count_since_yesterday"
            ] = daily_partition_change_distinct_count_since_yesterday
        if daily_partition_change_distinct_percent is not UNSET:
            field_dict[
                "daily_partition_change_distinct_percent"
            ] = daily_partition_change_distinct_percent
        if daily_partition_change_distinct_percent_since_yesterday is not UNSET:
            field_dict[
                "daily_partition_change_distinct_percent_since_yesterday"
            ] = daily_partition_change_distinct_percent_since_yesterday
        if daily_partition_anomaly_stationary_distinct_count_30_days is not UNSET:
            field_dict[
                "daily_partition_anomaly_stationary_distinct_count_30_days"
            ] = daily_partition_anomaly_stationary_distinct_count_30_days
        if daily_partition_anomaly_stationary_distinct_percent_30_days is not UNSET:
            field_dict[
                "daily_partition_anomaly_stationary_distinct_percent_30_days"
            ] = daily_partition_anomaly_stationary_distinct_percent_30_days
        if daily_partition_change_distinct_count_since_7_days is not UNSET:
            field_dict[
                "daily_partition_change_distinct_count_since_7_days"
            ] = daily_partition_change_distinct_count_since_7_days
        if daily_partition_change_distinct_count_since_30_days is not UNSET:
            field_dict[
                "daily_partition_change_distinct_count_since_30_days"
            ] = daily_partition_change_distinct_count_since_30_days
        if daily_partition_change_distinct_percent_since_7_days is not UNSET:
            field_dict[
                "daily_partition_change_distinct_percent_since_7_days"
            ] = daily_partition_change_distinct_percent_since_7_days
        if daily_partition_change_distinct_percent_since_30_days is not UNSET:
            field_dict[
                "daily_partition_change_distinct_percent_since_30_days"
            ] = daily_partition_change_distinct_percent_since_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_stationary_distinct_percent_30_days_check_spec import (
            ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_distinct_percent_check_spec import (
            ColumnAnomalyStationaryDistinctPercentCheckSpec,
        )
        from ..models.column_anomaly_stationary_partition_distinct_count_30_days_check_spec import (
            ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_partition_distinct_count_check_spec import (
            ColumnAnomalyStationaryPartitionDistinctCountCheckSpec,
        )
        from ..models.column_change_distinct_count_check_spec import (
            ColumnChangeDistinctCountCheckSpec,
        )
        from ..models.column_change_distinct_count_since_7_days_check_spec import (
            ColumnChangeDistinctCountSince7DaysCheckSpec,
        )
        from ..models.column_change_distinct_count_since_30_days_check_spec import (
            ColumnChangeDistinctCountSince30DaysCheckSpec,
        )
        from ..models.column_change_distinct_count_since_yesterday_check_spec import (
            ColumnChangeDistinctCountSinceYesterdayCheckSpec,
        )
        from ..models.column_change_distinct_percent_check_spec import (
            ColumnChangeDistinctPercentCheckSpec,
        )
        from ..models.column_change_distinct_percent_since_7_days_check_spec import (
            ColumnChangeDistinctPercentSince7DaysCheckSpec,
        )
        from ..models.column_change_distinct_percent_since_30_days_check_spec import (
            ColumnChangeDistinctPercentSince30DaysCheckSpec,
        )
        from ..models.column_change_distinct_percent_since_yesterday_check_spec import (
            ColumnChangeDistinctPercentSinceYesterdayCheckSpec,
        )
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
        from ..models.column_uniqueness_daily_partitioned_checks_spec_custom_checks import (
            ColumnUniquenessDailyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnUniquenessDailyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnUniquenessDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_distinct_count = d.pop("daily_partition_distinct_count", UNSET)
        daily_partition_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_daily_partition_distinct_count, Unset):
            daily_partition_distinct_count = UNSET
        else:
            daily_partition_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _daily_partition_distinct_count
            )

        _daily_partition_distinct_percent = d.pop(
            "daily_partition_distinct_percent", UNSET
        )
        daily_partition_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_daily_partition_distinct_percent, Unset):
            daily_partition_distinct_percent = UNSET
        else:
            daily_partition_distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(
                _daily_partition_distinct_percent
            )

        _daily_partition_duplicate_count = d.pop(
            "daily_partition_duplicate_count", UNSET
        )
        daily_partition_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_daily_partition_duplicate_count, Unset):
            daily_partition_duplicate_count = UNSET
        else:
            daily_partition_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _daily_partition_duplicate_count
            )

        _daily_partition_duplicate_percent = d.pop(
            "daily_partition_duplicate_percent", UNSET
        )
        daily_partition_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_daily_partition_duplicate_percent, Unset):
            daily_partition_duplicate_percent = UNSET
        else:
            daily_partition_duplicate_percent = (
                ColumnDuplicatePercentCheckSpec.from_dict(
                    _daily_partition_duplicate_percent
                )
            )

        _daily_partition_anomaly_stationary_distinct_count = d.pop(
            "daily_partition_anomaly_stationary_distinct_count", UNSET
        )
        daily_partition_anomaly_stationary_distinct_count: Union[
            Unset, ColumnAnomalyStationaryPartitionDistinctCountCheckSpec
        ]
        if isinstance(_daily_partition_anomaly_stationary_distinct_count, Unset):
            daily_partition_anomaly_stationary_distinct_count = UNSET
        else:
            daily_partition_anomaly_stationary_distinct_count = (
                ColumnAnomalyStationaryPartitionDistinctCountCheckSpec.from_dict(
                    _daily_partition_anomaly_stationary_distinct_count
                )
            )

        _daily_partition_anomaly_stationary_distinct_percent = d.pop(
            "daily_partition_anomaly_stationary_distinct_percent", UNSET
        )
        daily_partition_anomaly_stationary_distinct_percent: Union[
            Unset, ColumnAnomalyStationaryDistinctPercentCheckSpec
        ]
        if isinstance(_daily_partition_anomaly_stationary_distinct_percent, Unset):
            daily_partition_anomaly_stationary_distinct_percent = UNSET
        else:
            daily_partition_anomaly_stationary_distinct_percent = (
                ColumnAnomalyStationaryDistinctPercentCheckSpec.from_dict(
                    _daily_partition_anomaly_stationary_distinct_percent
                )
            )

        _daily_partition_change_distinct_count = d.pop(
            "daily_partition_change_distinct_count", UNSET
        )
        daily_partition_change_distinct_count: Union[
            Unset, ColumnChangeDistinctCountCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_count, Unset):
            daily_partition_change_distinct_count = UNSET
        else:
            daily_partition_change_distinct_count = (
                ColumnChangeDistinctCountCheckSpec.from_dict(
                    _daily_partition_change_distinct_count
                )
            )

        _daily_partition_change_distinct_count_since_yesterday = d.pop(
            "daily_partition_change_distinct_count_since_yesterday", UNSET
        )
        daily_partition_change_distinct_count_since_yesterday: Union[
            Unset, ColumnChangeDistinctCountSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_count_since_yesterday, Unset):
            daily_partition_change_distinct_count_since_yesterday = UNSET
        else:
            daily_partition_change_distinct_count_since_yesterday = (
                ColumnChangeDistinctCountSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_change_distinct_count_since_yesterday
                )
            )

        _daily_partition_change_distinct_percent = d.pop(
            "daily_partition_change_distinct_percent", UNSET
        )
        daily_partition_change_distinct_percent: Union[
            Unset, ColumnChangeDistinctPercentCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_percent, Unset):
            daily_partition_change_distinct_percent = UNSET
        else:
            daily_partition_change_distinct_percent = (
                ColumnChangeDistinctPercentCheckSpec.from_dict(
                    _daily_partition_change_distinct_percent
                )
            )

        _daily_partition_change_distinct_percent_since_yesterday = d.pop(
            "daily_partition_change_distinct_percent_since_yesterday", UNSET
        )
        daily_partition_change_distinct_percent_since_yesterday: Union[
            Unset, ColumnChangeDistinctPercentSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_percent_since_yesterday, Unset):
            daily_partition_change_distinct_percent_since_yesterday = UNSET
        else:
            daily_partition_change_distinct_percent_since_yesterday = (
                ColumnChangeDistinctPercentSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_change_distinct_percent_since_yesterday
                )
            )

        _daily_partition_anomaly_stationary_distinct_count_30_days = d.pop(
            "daily_partition_anomaly_stationary_distinct_count_30_days", UNSET
        )
        daily_partition_anomaly_stationary_distinct_count_30_days: Union[
            Unset, ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec
        ]
        if isinstance(
            _daily_partition_anomaly_stationary_distinct_count_30_days, Unset
        ):
            daily_partition_anomaly_stationary_distinct_count_30_days = UNSET
        else:
            daily_partition_anomaly_stationary_distinct_count_30_days = (
                ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec.from_dict(
                    _daily_partition_anomaly_stationary_distinct_count_30_days
                )
            )

        _daily_partition_anomaly_stationary_distinct_percent_30_days = d.pop(
            "daily_partition_anomaly_stationary_distinct_percent_30_days", UNSET
        )
        daily_partition_anomaly_stationary_distinct_percent_30_days: Union[
            Unset, ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec
        ]
        if isinstance(
            _daily_partition_anomaly_stationary_distinct_percent_30_days, Unset
        ):
            daily_partition_anomaly_stationary_distinct_percent_30_days = UNSET
        else:
            daily_partition_anomaly_stationary_distinct_percent_30_days = (
                ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec.from_dict(
                    _daily_partition_anomaly_stationary_distinct_percent_30_days
                )
            )

        _daily_partition_change_distinct_count_since_7_days = d.pop(
            "daily_partition_change_distinct_count_since_7_days", UNSET
        )
        daily_partition_change_distinct_count_since_7_days: Union[
            Unset, ColumnChangeDistinctCountSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_count_since_7_days, Unset):
            daily_partition_change_distinct_count_since_7_days = UNSET
        else:
            daily_partition_change_distinct_count_since_7_days = (
                ColumnChangeDistinctCountSince7DaysCheckSpec.from_dict(
                    _daily_partition_change_distinct_count_since_7_days
                )
            )

        _daily_partition_change_distinct_count_since_30_days = d.pop(
            "daily_partition_change_distinct_count_since_30_days", UNSET
        )
        daily_partition_change_distinct_count_since_30_days: Union[
            Unset, ColumnChangeDistinctCountSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_count_since_30_days, Unset):
            daily_partition_change_distinct_count_since_30_days = UNSET
        else:
            daily_partition_change_distinct_count_since_30_days = (
                ColumnChangeDistinctCountSince30DaysCheckSpec.from_dict(
                    _daily_partition_change_distinct_count_since_30_days
                )
            )

        _daily_partition_change_distinct_percent_since_7_days = d.pop(
            "daily_partition_change_distinct_percent_since_7_days", UNSET
        )
        daily_partition_change_distinct_percent_since_7_days: Union[
            Unset, ColumnChangeDistinctPercentSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_percent_since_7_days, Unset):
            daily_partition_change_distinct_percent_since_7_days = UNSET
        else:
            daily_partition_change_distinct_percent_since_7_days = (
                ColumnChangeDistinctPercentSince7DaysCheckSpec.from_dict(
                    _daily_partition_change_distinct_percent_since_7_days
                )
            )

        _daily_partition_change_distinct_percent_since_30_days = d.pop(
            "daily_partition_change_distinct_percent_since_30_days", UNSET
        )
        daily_partition_change_distinct_percent_since_30_days: Union[
            Unset, ColumnChangeDistinctPercentSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_change_distinct_percent_since_30_days, Unset):
            daily_partition_change_distinct_percent_since_30_days = UNSET
        else:
            daily_partition_change_distinct_percent_since_30_days = (
                ColumnChangeDistinctPercentSince30DaysCheckSpec.from_dict(
                    _daily_partition_change_distinct_percent_since_30_days
                )
            )

        column_uniqueness_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_distinct_count=daily_partition_distinct_count,
            daily_partition_distinct_percent=daily_partition_distinct_percent,
            daily_partition_duplicate_count=daily_partition_duplicate_count,
            daily_partition_duplicate_percent=daily_partition_duplicate_percent,
            daily_partition_anomaly_stationary_distinct_count=daily_partition_anomaly_stationary_distinct_count,
            daily_partition_anomaly_stationary_distinct_percent=daily_partition_anomaly_stationary_distinct_percent,
            daily_partition_change_distinct_count=daily_partition_change_distinct_count,
            daily_partition_change_distinct_count_since_yesterday=daily_partition_change_distinct_count_since_yesterday,
            daily_partition_change_distinct_percent=daily_partition_change_distinct_percent,
            daily_partition_change_distinct_percent_since_yesterday=daily_partition_change_distinct_percent_since_yesterday,
            daily_partition_anomaly_stationary_distinct_count_30_days=daily_partition_anomaly_stationary_distinct_count_30_days,
            daily_partition_anomaly_stationary_distinct_percent_30_days=daily_partition_anomaly_stationary_distinct_percent_30_days,
            daily_partition_change_distinct_count_since_7_days=daily_partition_change_distinct_count_since_7_days,
            daily_partition_change_distinct_count_since_30_days=daily_partition_change_distinct_count_since_30_days,
            daily_partition_change_distinct_percent_since_7_days=daily_partition_change_distinct_percent_since_7_days,
            daily_partition_change_distinct_percent_since_30_days=daily_partition_change_distinct_percent_since_30_days,
        )

        column_uniqueness_daily_partitioned_checks_spec.additional_properties = d
        return column_uniqueness_daily_partitioned_checks_spec

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
