from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_differencing_distinct_count_30_days_check_spec import (
        ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec,
    )
    from ..models.column_anomaly_differencing_distinct_count_check_spec import (
        ColumnAnomalyDifferencingDistinctCountCheckSpec,
    )
    from ..models.column_anomaly_stationary_distinct_percent_30_days_check_spec import (
        ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec,
    )
    from ..models.column_anomaly_stationary_distinct_percent_check_spec import (
        ColumnAnomalyStationaryDistinctPercentCheckSpec,
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
    from ..models.column_uniqueness_profiling_checks_spec_custom_checks import (
        ColumnUniquenessProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnUniquenessProfilingChecksSpec")


@_attrs_define
class ColumnUniquenessProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnUniquenessProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        profile_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        profile_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        profile_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
        profile_anomaly_differencing_distinct_count (Union[Unset, ColumnAnomalyDifferencingDistinctCountCheckSpec]):
        profile_anomaly_stationary_distinct_percent (Union[Unset, ColumnAnomalyStationaryDistinctPercentCheckSpec]):
        profile_change_distinct_count (Union[Unset, ColumnChangeDistinctCountCheckSpec]):
        profile_change_distinct_count_since_yesterday (Union[Unset, ColumnChangeDistinctCountSinceYesterdayCheckSpec]):
        profile_change_distinct_percent (Union[Unset, ColumnChangeDistinctPercentCheckSpec]):
        profile_change_distinct_percent_since_yesterday (Union[Unset,
            ColumnChangeDistinctPercentSinceYesterdayCheckSpec]):
        profile_anomaly_differencing_distinct_count_30_days (Union[Unset,
            ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec]):
        profile_anomaly_stationary_distinct_percent_30_days (Union[Unset,
            ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec]):
        profile_change_distinct_count_since_7_days (Union[Unset, ColumnChangeDistinctCountSince7DaysCheckSpec]):
        profile_change_distinct_count_since_30_days (Union[Unset, ColumnChangeDistinctCountSince30DaysCheckSpec]):
        profile_change_distinct_percent_since_7_days (Union[Unset, ColumnChangeDistinctPercentSince7DaysCheckSpec]):
        profile_change_distinct_percent_since_30_days (Union[Unset, ColumnChangeDistinctPercentSince30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnUniquenessProfilingChecksSpecCustomChecks"
    ] = UNSET
    profile_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    profile_distinct_percent: Union[Unset, "ColumnDistinctPercentCheckSpec"] = UNSET
    profile_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    profile_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    profile_anomaly_differencing_distinct_count: Union[
        Unset, "ColumnAnomalyDifferencingDistinctCountCheckSpec"
    ] = UNSET
    profile_anomaly_stationary_distinct_percent: Union[
        Unset, "ColumnAnomalyStationaryDistinctPercentCheckSpec"
    ] = UNSET
    profile_change_distinct_count: Union[
        Unset, "ColumnChangeDistinctCountCheckSpec"
    ] = UNSET
    profile_change_distinct_count_since_yesterday: Union[
        Unset, "ColumnChangeDistinctCountSinceYesterdayCheckSpec"
    ] = UNSET
    profile_change_distinct_percent: Union[
        Unset, "ColumnChangeDistinctPercentCheckSpec"
    ] = UNSET
    profile_change_distinct_percent_since_yesterday: Union[
        Unset, "ColumnChangeDistinctPercentSinceYesterdayCheckSpec"
    ] = UNSET
    profile_anomaly_differencing_distinct_count_30_days: Union[
        Unset, "ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec"
    ] = UNSET
    profile_anomaly_stationary_distinct_percent_30_days: Union[
        Unset, "ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec"
    ] = UNSET
    profile_change_distinct_count_since_7_days: Union[
        Unset, "ColumnChangeDistinctCountSince7DaysCheckSpec"
    ] = UNSET
    profile_change_distinct_count_since_30_days: Union[
        Unset, "ColumnChangeDistinctCountSince30DaysCheckSpec"
    ] = UNSET
    profile_change_distinct_percent_since_7_days: Union[
        Unset, "ColumnChangeDistinctPercentSince7DaysCheckSpec"
    ] = UNSET
    profile_change_distinct_percent_since_30_days: Union[
        Unset, "ColumnChangeDistinctPercentSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count, Unset):
            profile_distinct_count = self.profile_distinct_count.to_dict()

        profile_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent, Unset):
            profile_distinct_percent = self.profile_distinct_percent.to_dict()

        profile_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_count, Unset):
            profile_duplicate_count = self.profile_duplicate_count.to_dict()

        profile_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_percent, Unset):
            profile_duplicate_percent = self.profile_duplicate_percent.to_dict()

        profile_anomaly_differencing_distinct_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_anomaly_differencing_distinct_count, Unset):
            profile_anomaly_differencing_distinct_count = (
                self.profile_anomaly_differencing_distinct_count.to_dict()
            )

        profile_anomaly_stationary_distinct_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_anomaly_stationary_distinct_percent, Unset):
            profile_anomaly_stationary_distinct_percent = (
                self.profile_anomaly_stationary_distinct_percent.to_dict()
            )

        profile_change_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_change_distinct_count, Unset):
            profile_change_distinct_count = self.profile_change_distinct_count.to_dict()

        profile_change_distinct_count_since_yesterday: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_change_distinct_count_since_yesterday, Unset):
            profile_change_distinct_count_since_yesterday = (
                self.profile_change_distinct_count_since_yesterday.to_dict()
            )

        profile_change_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_change_distinct_percent, Unset):
            profile_change_distinct_percent = (
                self.profile_change_distinct_percent.to_dict()
            )

        profile_change_distinct_percent_since_yesterday: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_change_distinct_percent_since_yesterday, Unset):
            profile_change_distinct_percent_since_yesterday = (
                self.profile_change_distinct_percent_since_yesterday.to_dict()
            )

        profile_anomaly_differencing_distinct_count_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.profile_anomaly_differencing_distinct_count_30_days, Unset
        ):
            profile_anomaly_differencing_distinct_count_30_days = (
                self.profile_anomaly_differencing_distinct_count_30_days.to_dict()
            )

        profile_anomaly_stationary_distinct_percent_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.profile_anomaly_stationary_distinct_percent_30_days, Unset
        ):
            profile_anomaly_stationary_distinct_percent_30_days = (
                self.profile_anomaly_stationary_distinct_percent_30_days.to_dict()
            )

        profile_change_distinct_count_since_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_change_distinct_count_since_7_days, Unset):
            profile_change_distinct_count_since_7_days = (
                self.profile_change_distinct_count_since_7_days.to_dict()
            )

        profile_change_distinct_count_since_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_change_distinct_count_since_30_days, Unset):
            profile_change_distinct_count_since_30_days = (
                self.profile_change_distinct_count_since_30_days.to_dict()
            )

        profile_change_distinct_percent_since_7_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_change_distinct_percent_since_7_days, Unset):
            profile_change_distinct_percent_since_7_days = (
                self.profile_change_distinct_percent_since_7_days.to_dict()
            )

        profile_change_distinct_percent_since_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_change_distinct_percent_since_30_days, Unset):
            profile_change_distinct_percent_since_30_days = (
                self.profile_change_distinct_percent_since_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_distinct_count is not UNSET:
            field_dict["profile_distinct_count"] = profile_distinct_count
        if profile_distinct_percent is not UNSET:
            field_dict["profile_distinct_percent"] = profile_distinct_percent
        if profile_duplicate_count is not UNSET:
            field_dict["profile_duplicate_count"] = profile_duplicate_count
        if profile_duplicate_percent is not UNSET:
            field_dict["profile_duplicate_percent"] = profile_duplicate_percent
        if profile_anomaly_differencing_distinct_count is not UNSET:
            field_dict[
                "profile_anomaly_differencing_distinct_count"
            ] = profile_anomaly_differencing_distinct_count
        if profile_anomaly_stationary_distinct_percent is not UNSET:
            field_dict[
                "profile_anomaly_stationary_distinct_percent"
            ] = profile_anomaly_stationary_distinct_percent
        if profile_change_distinct_count is not UNSET:
            field_dict["profile_change_distinct_count"] = profile_change_distinct_count
        if profile_change_distinct_count_since_yesterday is not UNSET:
            field_dict[
                "profile_change_distinct_count_since_yesterday"
            ] = profile_change_distinct_count_since_yesterday
        if profile_change_distinct_percent is not UNSET:
            field_dict[
                "profile_change_distinct_percent"
            ] = profile_change_distinct_percent
        if profile_change_distinct_percent_since_yesterday is not UNSET:
            field_dict[
                "profile_change_distinct_percent_since_yesterday"
            ] = profile_change_distinct_percent_since_yesterday
        if profile_anomaly_differencing_distinct_count_30_days is not UNSET:
            field_dict[
                "profile_anomaly_differencing_distinct_count_30_days"
            ] = profile_anomaly_differencing_distinct_count_30_days
        if profile_anomaly_stationary_distinct_percent_30_days is not UNSET:
            field_dict[
                "profile_anomaly_stationary_distinct_percent_30_days"
            ] = profile_anomaly_stationary_distinct_percent_30_days
        if profile_change_distinct_count_since_7_days is not UNSET:
            field_dict[
                "profile_change_distinct_count_since_7_days"
            ] = profile_change_distinct_count_since_7_days
        if profile_change_distinct_count_since_30_days is not UNSET:
            field_dict[
                "profile_change_distinct_count_since_30_days"
            ] = profile_change_distinct_count_since_30_days
        if profile_change_distinct_percent_since_7_days is not UNSET:
            field_dict[
                "profile_change_distinct_percent_since_7_days"
            ] = profile_change_distinct_percent_since_7_days
        if profile_change_distinct_percent_since_30_days is not UNSET:
            field_dict[
                "profile_change_distinct_percent_since_30_days"
            ] = profile_change_distinct_percent_since_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_differencing_distinct_count_30_days_check_spec import (
            ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec,
        )
        from ..models.column_anomaly_differencing_distinct_count_check_spec import (
            ColumnAnomalyDifferencingDistinctCountCheckSpec,
        )
        from ..models.column_anomaly_stationary_distinct_percent_30_days_check_spec import (
            ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec,
        )
        from ..models.column_anomaly_stationary_distinct_percent_check_spec import (
            ColumnAnomalyStationaryDistinctPercentCheckSpec,
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
        from ..models.column_uniqueness_profiling_checks_spec_custom_checks import (
            ColumnUniquenessProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnUniquenessProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnUniquenessProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_distinct_count = d.pop("profile_distinct_count", UNSET)
        profile_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_profile_distinct_count, Unset):
            profile_distinct_count = UNSET
        else:
            profile_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _profile_distinct_count
            )

        _profile_distinct_percent = d.pop("profile_distinct_percent", UNSET)
        profile_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_profile_distinct_percent, Unset):
            profile_distinct_percent = UNSET
        else:
            profile_distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(
                _profile_distinct_percent
            )

        _profile_duplicate_count = d.pop("profile_duplicate_count", UNSET)
        profile_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_profile_duplicate_count, Unset):
            profile_duplicate_count = UNSET
        else:
            profile_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _profile_duplicate_count
            )

        _profile_duplicate_percent = d.pop("profile_duplicate_percent", UNSET)
        profile_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_profile_duplicate_percent, Unset):
            profile_duplicate_percent = UNSET
        else:
            profile_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _profile_duplicate_percent
            )

        _profile_anomaly_differencing_distinct_count = d.pop(
            "profile_anomaly_differencing_distinct_count", UNSET
        )
        profile_anomaly_differencing_distinct_count: Union[
            Unset, ColumnAnomalyDifferencingDistinctCountCheckSpec
        ]
        if isinstance(_profile_anomaly_differencing_distinct_count, Unset):
            profile_anomaly_differencing_distinct_count = UNSET
        else:
            profile_anomaly_differencing_distinct_count = (
                ColumnAnomalyDifferencingDistinctCountCheckSpec.from_dict(
                    _profile_anomaly_differencing_distinct_count
                )
            )

        _profile_anomaly_stationary_distinct_percent = d.pop(
            "profile_anomaly_stationary_distinct_percent", UNSET
        )
        profile_anomaly_stationary_distinct_percent: Union[
            Unset, ColumnAnomalyStationaryDistinctPercentCheckSpec
        ]
        if isinstance(_profile_anomaly_stationary_distinct_percent, Unset):
            profile_anomaly_stationary_distinct_percent = UNSET
        else:
            profile_anomaly_stationary_distinct_percent = (
                ColumnAnomalyStationaryDistinctPercentCheckSpec.from_dict(
                    _profile_anomaly_stationary_distinct_percent
                )
            )

        _profile_change_distinct_count = d.pop("profile_change_distinct_count", UNSET)
        profile_change_distinct_count: Union[Unset, ColumnChangeDistinctCountCheckSpec]
        if isinstance(_profile_change_distinct_count, Unset):
            profile_change_distinct_count = UNSET
        else:
            profile_change_distinct_count = (
                ColumnChangeDistinctCountCheckSpec.from_dict(
                    _profile_change_distinct_count
                )
            )

        _profile_change_distinct_count_since_yesterday = d.pop(
            "profile_change_distinct_count_since_yesterday", UNSET
        )
        profile_change_distinct_count_since_yesterday: Union[
            Unset, ColumnChangeDistinctCountSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_change_distinct_count_since_yesterday, Unset):
            profile_change_distinct_count_since_yesterday = UNSET
        else:
            profile_change_distinct_count_since_yesterday = (
                ColumnChangeDistinctCountSinceYesterdayCheckSpec.from_dict(
                    _profile_change_distinct_count_since_yesterday
                )
            )

        _profile_change_distinct_percent = d.pop(
            "profile_change_distinct_percent", UNSET
        )
        profile_change_distinct_percent: Union[
            Unset, ColumnChangeDistinctPercentCheckSpec
        ]
        if isinstance(_profile_change_distinct_percent, Unset):
            profile_change_distinct_percent = UNSET
        else:
            profile_change_distinct_percent = (
                ColumnChangeDistinctPercentCheckSpec.from_dict(
                    _profile_change_distinct_percent
                )
            )

        _profile_change_distinct_percent_since_yesterday = d.pop(
            "profile_change_distinct_percent_since_yesterday", UNSET
        )
        profile_change_distinct_percent_since_yesterday: Union[
            Unset, ColumnChangeDistinctPercentSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_change_distinct_percent_since_yesterday, Unset):
            profile_change_distinct_percent_since_yesterday = UNSET
        else:
            profile_change_distinct_percent_since_yesterday = (
                ColumnChangeDistinctPercentSinceYesterdayCheckSpec.from_dict(
                    _profile_change_distinct_percent_since_yesterday
                )
            )

        _profile_anomaly_differencing_distinct_count_30_days = d.pop(
            "profile_anomaly_differencing_distinct_count_30_days", UNSET
        )
        profile_anomaly_differencing_distinct_count_30_days: Union[
            Unset, ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec
        ]
        if isinstance(_profile_anomaly_differencing_distinct_count_30_days, Unset):
            profile_anomaly_differencing_distinct_count_30_days = UNSET
        else:
            profile_anomaly_differencing_distinct_count_30_days = (
                ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec.from_dict(
                    _profile_anomaly_differencing_distinct_count_30_days
                )
            )

        _profile_anomaly_stationary_distinct_percent_30_days = d.pop(
            "profile_anomaly_stationary_distinct_percent_30_days", UNSET
        )
        profile_anomaly_stationary_distinct_percent_30_days: Union[
            Unset, ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec
        ]
        if isinstance(_profile_anomaly_stationary_distinct_percent_30_days, Unset):
            profile_anomaly_stationary_distinct_percent_30_days = UNSET
        else:
            profile_anomaly_stationary_distinct_percent_30_days = (
                ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec.from_dict(
                    _profile_anomaly_stationary_distinct_percent_30_days
                )
            )

        _profile_change_distinct_count_since_7_days = d.pop(
            "profile_change_distinct_count_since_7_days", UNSET
        )
        profile_change_distinct_count_since_7_days: Union[
            Unset, ColumnChangeDistinctCountSince7DaysCheckSpec
        ]
        if isinstance(_profile_change_distinct_count_since_7_days, Unset):
            profile_change_distinct_count_since_7_days = UNSET
        else:
            profile_change_distinct_count_since_7_days = (
                ColumnChangeDistinctCountSince7DaysCheckSpec.from_dict(
                    _profile_change_distinct_count_since_7_days
                )
            )

        _profile_change_distinct_count_since_30_days = d.pop(
            "profile_change_distinct_count_since_30_days", UNSET
        )
        profile_change_distinct_count_since_30_days: Union[
            Unset, ColumnChangeDistinctCountSince30DaysCheckSpec
        ]
        if isinstance(_profile_change_distinct_count_since_30_days, Unset):
            profile_change_distinct_count_since_30_days = UNSET
        else:
            profile_change_distinct_count_since_30_days = (
                ColumnChangeDistinctCountSince30DaysCheckSpec.from_dict(
                    _profile_change_distinct_count_since_30_days
                )
            )

        _profile_change_distinct_percent_since_7_days = d.pop(
            "profile_change_distinct_percent_since_7_days", UNSET
        )
        profile_change_distinct_percent_since_7_days: Union[
            Unset, ColumnChangeDistinctPercentSince7DaysCheckSpec
        ]
        if isinstance(_profile_change_distinct_percent_since_7_days, Unset):
            profile_change_distinct_percent_since_7_days = UNSET
        else:
            profile_change_distinct_percent_since_7_days = (
                ColumnChangeDistinctPercentSince7DaysCheckSpec.from_dict(
                    _profile_change_distinct_percent_since_7_days
                )
            )

        _profile_change_distinct_percent_since_30_days = d.pop(
            "profile_change_distinct_percent_since_30_days", UNSET
        )
        profile_change_distinct_percent_since_30_days: Union[
            Unset, ColumnChangeDistinctPercentSince30DaysCheckSpec
        ]
        if isinstance(_profile_change_distinct_percent_since_30_days, Unset):
            profile_change_distinct_percent_since_30_days = UNSET
        else:
            profile_change_distinct_percent_since_30_days = (
                ColumnChangeDistinctPercentSince30DaysCheckSpec.from_dict(
                    _profile_change_distinct_percent_since_30_days
                )
            )

        column_uniqueness_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_distinct_count=profile_distinct_count,
            profile_distinct_percent=profile_distinct_percent,
            profile_duplicate_count=profile_duplicate_count,
            profile_duplicate_percent=profile_duplicate_percent,
            profile_anomaly_differencing_distinct_count=profile_anomaly_differencing_distinct_count,
            profile_anomaly_stationary_distinct_percent=profile_anomaly_stationary_distinct_percent,
            profile_change_distinct_count=profile_change_distinct_count,
            profile_change_distinct_count_since_yesterday=profile_change_distinct_count_since_yesterday,
            profile_change_distinct_percent=profile_change_distinct_percent,
            profile_change_distinct_percent_since_yesterday=profile_change_distinct_percent_since_yesterday,
            profile_anomaly_differencing_distinct_count_30_days=profile_anomaly_differencing_distinct_count_30_days,
            profile_anomaly_stationary_distinct_percent_30_days=profile_anomaly_stationary_distinct_percent_30_days,
            profile_change_distinct_count_since_7_days=profile_change_distinct_count_since_7_days,
            profile_change_distinct_count_since_30_days=profile_change_distinct_count_since_30_days,
            profile_change_distinct_percent_since_7_days=profile_change_distinct_percent_since_7_days,
            profile_change_distinct_percent_since_30_days=profile_change_distinct_percent_since_30_days,
        )

        column_uniqueness_profiling_checks_spec.additional_properties = d
        return column_uniqueness_profiling_checks_spec

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
