from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_anomaly_differencing_sum_30_days_check_spec import (
        ColumnAnomalyDifferencingSum30DaysCheckSpec,
    )
    from ..models.column_anomaly_differencing_sum_check_spec import (
        ColumnAnomalyDifferencingSumCheckSpec,
    )
    from ..models.column_anomaly_profiling_checks_spec_custom_checks import (
        ColumnAnomalyProfilingChecksSpecCustomChecks,
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


T = TypeVar("T", bound="ColumnAnomalyProfilingChecksSpec")


@_attrs_define
class ColumnAnomalyProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAnomalyProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_mean_change (Union[Unset, ColumnChangeMeanCheckSpec]):
        profile_mean_change_yesterday (Union[Unset, ColumnChangeMeanSinceYesterdayCheckSpec]):
        profile_median_change (Union[Unset, ColumnChangeMedianCheckSpec]):
        profile_median_change_yesterday (Union[Unset, ColumnChangeMedianSinceYesterdayCheckSpec]):
        profile_sum_change (Union[Unset, ColumnChangeSumCheckSpec]):
        profile_sum_change_yesterday (Union[Unset, ColumnChangeSumSinceYesterdayCheckSpec]):
        profile_mean_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMean30DaysCheckSpec]):
        profile_mean_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMeanCheckSpec]):
        profile_median_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec]):
        profile_median_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryMedianCheckSpec]):
        profile_sum_anomaly_differencing_30_days (Union[Unset, ColumnAnomalyDifferencingSum30DaysCheckSpec]):
        profile_sum_anomaly_differencing (Union[Unset, ColumnAnomalyDifferencingSumCheckSpec]):
        profile_mean_change_7_days (Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]):
        profile_mean_change_30_days (Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]):
        profile_median_change_7_days (Union[Unset, ColumnChangeMedianSince7DaysCheckSpec]):
        profile_median_change_30_days (Union[Unset, ColumnChangeMedianSince30DaysCheckSpec]):
        profile_sum_change_7_days (Union[Unset, ColumnChangeSumSince7DaysCheckSpec]):
        profile_sum_change_30_days (Union[Unset, ColumnChangeSumSince30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnAnomalyProfilingChecksSpecCustomChecks"] = UNSET
    profile_mean_change: Union[Unset, "ColumnChangeMeanCheckSpec"] = UNSET
    profile_mean_change_yesterday: Union[
        Unset, "ColumnChangeMeanSinceYesterdayCheckSpec"
    ] = UNSET
    profile_median_change: Union[Unset, "ColumnChangeMedianCheckSpec"] = UNSET
    profile_median_change_yesterday: Union[
        Unset, "ColumnChangeMedianSinceYesterdayCheckSpec"
    ] = UNSET
    profile_sum_change: Union[Unset, "ColumnChangeSumCheckSpec"] = UNSET
    profile_sum_change_yesterday: Union[
        Unset, "ColumnChangeSumSinceYesterdayCheckSpec"
    ] = UNSET
    profile_mean_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryMean30DaysCheckSpec"
    ] = UNSET
    profile_mean_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryMeanCheckSpec"
    ] = UNSET
    profile_median_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryMedian30DaysCheckSpec"
    ] = UNSET
    profile_median_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryMedianCheckSpec"
    ] = UNSET
    profile_sum_anomaly_differencing_30_days: Union[
        Unset, "ColumnAnomalyDifferencingSum30DaysCheckSpec"
    ] = UNSET
    profile_sum_anomaly_differencing: Union[
        Unset, "ColumnAnomalyDifferencingSumCheckSpec"
    ] = UNSET
    profile_mean_change_7_days: Union[
        Unset, "ColumnChangeMeanSince7DaysCheckSpec"
    ] = UNSET
    profile_mean_change_30_days: Union[
        Unset, "ColumnChangeMeanSince30DaysCheckSpec"
    ] = UNSET
    profile_median_change_7_days: Union[
        Unset, "ColumnChangeMedianSince7DaysCheckSpec"
    ] = UNSET
    profile_median_change_30_days: Union[
        Unset, "ColumnChangeMedianSince30DaysCheckSpec"
    ] = UNSET
    profile_sum_change_7_days: Union[
        Unset, "ColumnChangeSumSince7DaysCheckSpec"
    ] = UNSET
    profile_sum_change_30_days: Union[
        Unset, "ColumnChangeSumSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_mean_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change, Unset):
            profile_mean_change = self.profile_mean_change.to_dict()

        profile_mean_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_yesterday, Unset):
            profile_mean_change_yesterday = self.profile_mean_change_yesterday.to_dict()

        profile_median_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change, Unset):
            profile_median_change = self.profile_median_change.to_dict()

        profile_median_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_yesterday, Unset):
            profile_median_change_yesterday = (
                self.profile_median_change_yesterday.to_dict()
            )

        profile_sum_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change, Unset):
            profile_sum_change = self.profile_sum_change.to_dict()

        profile_sum_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_change_yesterday, Unset):
            profile_sum_change_yesterday = self.profile_sum_change_yesterday.to_dict()

        profile_mean_anomaly_stationary_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_anomaly_stationary_30_days, Unset):
            profile_mean_anomaly_stationary_30_days = (
                self.profile_mean_anomaly_stationary_30_days.to_dict()
            )

        profile_mean_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_anomaly_stationary, Unset):
            profile_mean_anomaly_stationary = (
                self.profile_mean_anomaly_stationary.to_dict()
            )

        profile_median_anomaly_stationary_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_anomaly_stationary_30_days, Unset):
            profile_median_anomaly_stationary_30_days = (
                self.profile_median_anomaly_stationary_30_days.to_dict()
            )

        profile_median_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_anomaly_stationary, Unset):
            profile_median_anomaly_stationary = (
                self.profile_median_anomaly_stationary.to_dict()
            )

        profile_sum_anomaly_differencing_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_anomaly_differencing_30_days, Unset):
            profile_sum_anomaly_differencing_30_days = (
                self.profile_sum_anomaly_differencing_30_days.to_dict()
            )

        profile_sum_anomaly_differencing: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sum_anomaly_differencing, Unset):
            profile_sum_anomaly_differencing = (
                self.profile_sum_anomaly_differencing.to_dict()
            )

        profile_mean_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_7_days, Unset):
            profile_mean_change_7_days = self.profile_mean_change_7_days.to_dict()

        profile_mean_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_mean_change_30_days, Unset):
            profile_mean_change_30_days = self.profile_mean_change_30_days.to_dict()

        profile_median_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_7_days, Unset):
            profile_median_change_7_days = self.profile_median_change_7_days.to_dict()

        profile_median_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_median_change_30_days, Unset):
            profile_median_change_30_days = self.profile_median_change_30_days.to_dict()

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
        if profile_mean_change is not UNSET:
            field_dict["profile_mean_change"] = profile_mean_change
        if profile_mean_change_yesterday is not UNSET:
            field_dict["profile_mean_change_yesterday"] = profile_mean_change_yesterday
        if profile_median_change is not UNSET:
            field_dict["profile_median_change"] = profile_median_change
        if profile_median_change_yesterday is not UNSET:
            field_dict[
                "profile_median_change_yesterday"
            ] = profile_median_change_yesterday
        if profile_sum_change is not UNSET:
            field_dict["profile_sum_change"] = profile_sum_change
        if profile_sum_change_yesterday is not UNSET:
            field_dict["profile_sum_change_yesterday"] = profile_sum_change_yesterday
        if profile_mean_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "profile_mean_anomaly_stationary_30_days"
            ] = profile_mean_anomaly_stationary_30_days
        if profile_mean_anomaly_stationary is not UNSET:
            field_dict[
                "profile_mean_anomaly_stationary"
            ] = profile_mean_anomaly_stationary
        if profile_median_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "profile_median_anomaly_stationary_30_days"
            ] = profile_median_anomaly_stationary_30_days
        if profile_median_anomaly_stationary is not UNSET:
            field_dict[
                "profile_median_anomaly_stationary"
            ] = profile_median_anomaly_stationary
        if profile_sum_anomaly_differencing_30_days is not UNSET:
            field_dict[
                "profile_sum_anomaly_differencing_30_days"
            ] = profile_sum_anomaly_differencing_30_days
        if profile_sum_anomaly_differencing is not UNSET:
            field_dict[
                "profile_sum_anomaly_differencing"
            ] = profile_sum_anomaly_differencing
        if profile_mean_change_7_days is not UNSET:
            field_dict["profile_mean_change_7_days"] = profile_mean_change_7_days
        if profile_mean_change_30_days is not UNSET:
            field_dict["profile_mean_change_30_days"] = profile_mean_change_30_days
        if profile_median_change_7_days is not UNSET:
            field_dict["profile_median_change_7_days"] = profile_median_change_7_days
        if profile_median_change_30_days is not UNSET:
            field_dict["profile_median_change_30_days"] = profile_median_change_30_days
        if profile_sum_change_7_days is not UNSET:
            field_dict["profile_sum_change_7_days"] = profile_sum_change_7_days
        if profile_sum_change_30_days is not UNSET:
            field_dict["profile_sum_change_30_days"] = profile_sum_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_anomaly_differencing_sum_30_days_check_spec import (
            ColumnAnomalyDifferencingSum30DaysCheckSpec,
        )
        from ..models.column_anomaly_differencing_sum_check_spec import (
            ColumnAnomalyDifferencingSumCheckSpec,
        )
        from ..models.column_anomaly_profiling_checks_spec_custom_checks import (
            ColumnAnomalyProfilingChecksSpecCustomChecks,
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
        custom_checks: Union[Unset, ColumnAnomalyProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnAnomalyProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_mean_change = d.pop("profile_mean_change", UNSET)
        profile_mean_change: Union[Unset, ColumnChangeMeanCheckSpec]
        if isinstance(_profile_mean_change, Unset):
            profile_mean_change = UNSET
        else:
            profile_mean_change = ColumnChangeMeanCheckSpec.from_dict(
                _profile_mean_change
            )

        _profile_mean_change_yesterday = d.pop("profile_mean_change_yesterday", UNSET)
        profile_mean_change_yesterday: Union[
            Unset, ColumnChangeMeanSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_mean_change_yesterday, Unset):
            profile_mean_change_yesterday = UNSET
        else:
            profile_mean_change_yesterday = (
                ColumnChangeMeanSinceYesterdayCheckSpec.from_dict(
                    _profile_mean_change_yesterday
                )
            )

        _profile_median_change = d.pop("profile_median_change", UNSET)
        profile_median_change: Union[Unset, ColumnChangeMedianCheckSpec]
        if isinstance(_profile_median_change, Unset):
            profile_median_change = UNSET
        else:
            profile_median_change = ColumnChangeMedianCheckSpec.from_dict(
                _profile_median_change
            )

        _profile_median_change_yesterday = d.pop(
            "profile_median_change_yesterday", UNSET
        )
        profile_median_change_yesterday: Union[
            Unset, ColumnChangeMedianSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_median_change_yesterday, Unset):
            profile_median_change_yesterday = UNSET
        else:
            profile_median_change_yesterday = (
                ColumnChangeMedianSinceYesterdayCheckSpec.from_dict(
                    _profile_median_change_yesterday
                )
            )

        _profile_sum_change = d.pop("profile_sum_change", UNSET)
        profile_sum_change: Union[Unset, ColumnChangeSumCheckSpec]
        if isinstance(_profile_sum_change, Unset):
            profile_sum_change = UNSET
        else:
            profile_sum_change = ColumnChangeSumCheckSpec.from_dict(_profile_sum_change)

        _profile_sum_change_yesterday = d.pop("profile_sum_change_yesterday", UNSET)
        profile_sum_change_yesterday: Union[
            Unset, ColumnChangeSumSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_sum_change_yesterday, Unset):
            profile_sum_change_yesterday = UNSET
        else:
            profile_sum_change_yesterday = (
                ColumnChangeSumSinceYesterdayCheckSpec.from_dict(
                    _profile_sum_change_yesterday
                )
            )

        _profile_mean_anomaly_stationary_30_days = d.pop(
            "profile_mean_anomaly_stationary_30_days", UNSET
        )
        profile_mean_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryMean30DaysCheckSpec
        ]
        if isinstance(_profile_mean_anomaly_stationary_30_days, Unset):
            profile_mean_anomaly_stationary_30_days = UNSET
        else:
            profile_mean_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryMean30DaysCheckSpec.from_dict(
                    _profile_mean_anomaly_stationary_30_days
                )
            )

        _profile_mean_anomaly_stationary = d.pop(
            "profile_mean_anomaly_stationary", UNSET
        )
        profile_mean_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryMeanCheckSpec
        ]
        if isinstance(_profile_mean_anomaly_stationary, Unset):
            profile_mean_anomaly_stationary = UNSET
        else:
            profile_mean_anomaly_stationary = (
                ColumnAnomalyStationaryMeanCheckSpec.from_dict(
                    _profile_mean_anomaly_stationary
                )
            )

        _profile_median_anomaly_stationary_30_days = d.pop(
            "profile_median_anomaly_stationary_30_days", UNSET
        )
        profile_median_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryMedian30DaysCheckSpec
        ]
        if isinstance(_profile_median_anomaly_stationary_30_days, Unset):
            profile_median_anomaly_stationary_30_days = UNSET
        else:
            profile_median_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryMedian30DaysCheckSpec.from_dict(
                    _profile_median_anomaly_stationary_30_days
                )
            )

        _profile_median_anomaly_stationary = d.pop(
            "profile_median_anomaly_stationary", UNSET
        )
        profile_median_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryMedianCheckSpec
        ]
        if isinstance(_profile_median_anomaly_stationary, Unset):
            profile_median_anomaly_stationary = UNSET
        else:
            profile_median_anomaly_stationary = (
                ColumnAnomalyStationaryMedianCheckSpec.from_dict(
                    _profile_median_anomaly_stationary
                )
            )

        _profile_sum_anomaly_differencing_30_days = d.pop(
            "profile_sum_anomaly_differencing_30_days", UNSET
        )
        profile_sum_anomaly_differencing_30_days: Union[
            Unset, ColumnAnomalyDifferencingSum30DaysCheckSpec
        ]
        if isinstance(_profile_sum_anomaly_differencing_30_days, Unset):
            profile_sum_anomaly_differencing_30_days = UNSET
        else:
            profile_sum_anomaly_differencing_30_days = (
                ColumnAnomalyDifferencingSum30DaysCheckSpec.from_dict(
                    _profile_sum_anomaly_differencing_30_days
                )
            )

        _profile_sum_anomaly_differencing = d.pop(
            "profile_sum_anomaly_differencing", UNSET
        )
        profile_sum_anomaly_differencing: Union[
            Unset, ColumnAnomalyDifferencingSumCheckSpec
        ]
        if isinstance(_profile_sum_anomaly_differencing, Unset):
            profile_sum_anomaly_differencing = UNSET
        else:
            profile_sum_anomaly_differencing = (
                ColumnAnomalyDifferencingSumCheckSpec.from_dict(
                    _profile_sum_anomaly_differencing
                )
            )

        _profile_mean_change_7_days = d.pop("profile_mean_change_7_days", UNSET)
        profile_mean_change_7_days: Union[Unset, ColumnChangeMeanSince7DaysCheckSpec]
        if isinstance(_profile_mean_change_7_days, Unset):
            profile_mean_change_7_days = UNSET
        else:
            profile_mean_change_7_days = ColumnChangeMeanSince7DaysCheckSpec.from_dict(
                _profile_mean_change_7_days
            )

        _profile_mean_change_30_days = d.pop("profile_mean_change_30_days", UNSET)
        profile_mean_change_30_days: Union[Unset, ColumnChangeMeanSince30DaysCheckSpec]
        if isinstance(_profile_mean_change_30_days, Unset):
            profile_mean_change_30_days = UNSET
        else:
            profile_mean_change_30_days = (
                ColumnChangeMeanSince30DaysCheckSpec.from_dict(
                    _profile_mean_change_30_days
                )
            )

        _profile_median_change_7_days = d.pop("profile_median_change_7_days", UNSET)
        profile_median_change_7_days: Union[
            Unset, ColumnChangeMedianSince7DaysCheckSpec
        ]
        if isinstance(_profile_median_change_7_days, Unset):
            profile_median_change_7_days = UNSET
        else:
            profile_median_change_7_days = (
                ColumnChangeMedianSince7DaysCheckSpec.from_dict(
                    _profile_median_change_7_days
                )
            )

        _profile_median_change_30_days = d.pop("profile_median_change_30_days", UNSET)
        profile_median_change_30_days: Union[
            Unset, ColumnChangeMedianSince30DaysCheckSpec
        ]
        if isinstance(_profile_median_change_30_days, Unset):
            profile_median_change_30_days = UNSET
        else:
            profile_median_change_30_days = (
                ColumnChangeMedianSince30DaysCheckSpec.from_dict(
                    _profile_median_change_30_days
                )
            )

        _profile_sum_change_7_days = d.pop("profile_sum_change_7_days", UNSET)
        profile_sum_change_7_days: Union[Unset, ColumnChangeSumSince7DaysCheckSpec]
        if isinstance(_profile_sum_change_7_days, Unset):
            profile_sum_change_7_days = UNSET
        else:
            profile_sum_change_7_days = ColumnChangeSumSince7DaysCheckSpec.from_dict(
                _profile_sum_change_7_days
            )

        _profile_sum_change_30_days = d.pop("profile_sum_change_30_days", UNSET)
        profile_sum_change_30_days: Union[Unset, ColumnChangeSumSince30DaysCheckSpec]
        if isinstance(_profile_sum_change_30_days, Unset):
            profile_sum_change_30_days = UNSET
        else:
            profile_sum_change_30_days = ColumnChangeSumSince30DaysCheckSpec.from_dict(
                _profile_sum_change_30_days
            )

        column_anomaly_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_mean_change=profile_mean_change,
            profile_mean_change_yesterday=profile_mean_change_yesterday,
            profile_median_change=profile_median_change,
            profile_median_change_yesterday=profile_median_change_yesterday,
            profile_sum_change=profile_sum_change,
            profile_sum_change_yesterday=profile_sum_change_yesterday,
            profile_mean_anomaly_stationary_30_days=profile_mean_anomaly_stationary_30_days,
            profile_mean_anomaly_stationary=profile_mean_anomaly_stationary,
            profile_median_anomaly_stationary_30_days=profile_median_anomaly_stationary_30_days,
            profile_median_anomaly_stationary=profile_median_anomaly_stationary,
            profile_sum_anomaly_differencing_30_days=profile_sum_anomaly_differencing_30_days,
            profile_sum_anomaly_differencing=profile_sum_anomaly_differencing,
            profile_mean_change_7_days=profile_mean_change_7_days,
            profile_mean_change_30_days=profile_mean_change_30_days,
            profile_median_change_7_days=profile_median_change_7_days,
            profile_median_change_30_days=profile_median_change_30_days,
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
