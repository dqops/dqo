from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_distinct_count_anomaly_differencing_check_spec import (
        ColumnDistinctCountAnomalyDifferencingCheckSpec,
    )
    from ..models.column_distinct_count_change_1_day_check_spec import (
        ColumnDistinctCountChange1DayCheckSpec,
    )
    from ..models.column_distinct_count_change_7_days_check_spec import (
        ColumnDistinctCountChange7DaysCheckSpec,
    )
    from ..models.column_distinct_count_change_30_days_check_spec import (
        ColumnDistinctCountChange30DaysCheckSpec,
    )
    from ..models.column_distinct_count_change_check_spec import (
        ColumnDistinctCountChangeCheckSpec,
    )
    from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
    from ..models.column_distinct_percent_anomaly_stationary_check_spec import (
        ColumnDistinctPercentAnomalyStationaryCheckSpec,
    )
    from ..models.column_distinct_percent_change_1_day_check_spec import (
        ColumnDistinctPercentChange1DayCheckSpec,
    )
    from ..models.column_distinct_percent_change_7_days_check_spec import (
        ColumnDistinctPercentChange7DaysCheckSpec,
    )
    from ..models.column_distinct_percent_change_30_days_check_spec import (
        ColumnDistinctPercentChange30DaysCheckSpec,
    )
    from ..models.column_distinct_percent_change_check_spec import (
        ColumnDistinctPercentChangeCheckSpec,
    )
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
        profile_distinct_count_anomaly (Union[Unset, ColumnDistinctCountAnomalyDifferencingCheckSpec]):
        profile_distinct_percent_anomaly (Union[Unset, ColumnDistinctPercentAnomalyStationaryCheckSpec]):
        profile_distinct_count_change (Union[Unset, ColumnDistinctCountChangeCheckSpec]):
        profile_distinct_percent_change (Union[Unset, ColumnDistinctPercentChangeCheckSpec]):
        profile_distinct_count_change_1_day (Union[Unset, ColumnDistinctCountChange1DayCheckSpec]):
        profile_distinct_count_change_7_days (Union[Unset, ColumnDistinctCountChange7DaysCheckSpec]):
        profile_distinct_count_change_30_days (Union[Unset, ColumnDistinctCountChange30DaysCheckSpec]):
        profile_distinct_percent_change_1_day (Union[Unset, ColumnDistinctPercentChange1DayCheckSpec]):
        profile_distinct_percent_change_7_days (Union[Unset, ColumnDistinctPercentChange7DaysCheckSpec]):
        profile_distinct_percent_change_30_days (Union[Unset, ColumnDistinctPercentChange30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnUniquenessProfilingChecksSpecCustomChecks"] = (
        UNSET
    )
    profile_distinct_count: Union[Unset, "ColumnDistinctCountCheckSpec"] = UNSET
    profile_distinct_percent: Union[Unset, "ColumnDistinctPercentCheckSpec"] = UNSET
    profile_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    profile_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    profile_distinct_count_anomaly: Union[
        Unset, "ColumnDistinctCountAnomalyDifferencingCheckSpec"
    ] = UNSET
    profile_distinct_percent_anomaly: Union[
        Unset, "ColumnDistinctPercentAnomalyStationaryCheckSpec"
    ] = UNSET
    profile_distinct_count_change: Union[
        Unset, "ColumnDistinctCountChangeCheckSpec"
    ] = UNSET
    profile_distinct_percent_change: Union[
        Unset, "ColumnDistinctPercentChangeCheckSpec"
    ] = UNSET
    profile_distinct_count_change_1_day: Union[
        Unset, "ColumnDistinctCountChange1DayCheckSpec"
    ] = UNSET
    profile_distinct_count_change_7_days: Union[
        Unset, "ColumnDistinctCountChange7DaysCheckSpec"
    ] = UNSET
    profile_distinct_count_change_30_days: Union[
        Unset, "ColumnDistinctCountChange30DaysCheckSpec"
    ] = UNSET
    profile_distinct_percent_change_1_day: Union[
        Unset, "ColumnDistinctPercentChange1DayCheckSpec"
    ] = UNSET
    profile_distinct_percent_change_7_days: Union[
        Unset, "ColumnDistinctPercentChange7DaysCheckSpec"
    ] = UNSET
    profile_distinct_percent_change_30_days: Union[
        Unset, "ColumnDistinctPercentChange30DaysCheckSpec"
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

        profile_distinct_count_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count_anomaly, Unset):
            profile_distinct_count_anomaly = (
                self.profile_distinct_count_anomaly.to_dict()
            )

        profile_distinct_percent_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent_anomaly, Unset):
            profile_distinct_percent_anomaly = (
                self.profile_distinct_percent_anomaly.to_dict()
            )

        profile_distinct_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count_change, Unset):
            profile_distinct_count_change = self.profile_distinct_count_change.to_dict()

        profile_distinct_percent_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent_change, Unset):
            profile_distinct_percent_change = (
                self.profile_distinct_percent_change.to_dict()
            )

        profile_distinct_count_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count_change_1_day, Unset):
            profile_distinct_count_change_1_day = (
                self.profile_distinct_count_change_1_day.to_dict()
            )

        profile_distinct_count_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count_change_7_days, Unset):
            profile_distinct_count_change_7_days = (
                self.profile_distinct_count_change_7_days.to_dict()
            )

        profile_distinct_count_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_count_change_30_days, Unset):
            profile_distinct_count_change_30_days = (
                self.profile_distinct_count_change_30_days.to_dict()
            )

        profile_distinct_percent_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent_change_1_day, Unset):
            profile_distinct_percent_change_1_day = (
                self.profile_distinct_percent_change_1_day.to_dict()
            )

        profile_distinct_percent_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent_change_7_days, Unset):
            profile_distinct_percent_change_7_days = (
                self.profile_distinct_percent_change_7_days.to_dict()
            )

        profile_distinct_percent_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_distinct_percent_change_30_days, Unset):
            profile_distinct_percent_change_30_days = (
                self.profile_distinct_percent_change_30_days.to_dict()
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
        if profile_distinct_count_anomaly is not UNSET:
            field_dict["profile_distinct_count_anomaly"] = (
                profile_distinct_count_anomaly
            )
        if profile_distinct_percent_anomaly is not UNSET:
            field_dict["profile_distinct_percent_anomaly"] = (
                profile_distinct_percent_anomaly
            )
        if profile_distinct_count_change is not UNSET:
            field_dict["profile_distinct_count_change"] = profile_distinct_count_change
        if profile_distinct_percent_change is not UNSET:
            field_dict["profile_distinct_percent_change"] = (
                profile_distinct_percent_change
            )
        if profile_distinct_count_change_1_day is not UNSET:
            field_dict["profile_distinct_count_change_1_day"] = (
                profile_distinct_count_change_1_day
            )
        if profile_distinct_count_change_7_days is not UNSET:
            field_dict["profile_distinct_count_change_7_days"] = (
                profile_distinct_count_change_7_days
            )
        if profile_distinct_count_change_30_days is not UNSET:
            field_dict["profile_distinct_count_change_30_days"] = (
                profile_distinct_count_change_30_days
            )
        if profile_distinct_percent_change_1_day is not UNSET:
            field_dict["profile_distinct_percent_change_1_day"] = (
                profile_distinct_percent_change_1_day
            )
        if profile_distinct_percent_change_7_days is not UNSET:
            field_dict["profile_distinct_percent_change_7_days"] = (
                profile_distinct_percent_change_7_days
            )
        if profile_distinct_percent_change_30_days is not UNSET:
            field_dict["profile_distinct_percent_change_30_days"] = (
                profile_distinct_percent_change_30_days
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_distinct_count_anomaly_differencing_check_spec import (
            ColumnDistinctCountAnomalyDifferencingCheckSpec,
        )
        from ..models.column_distinct_count_change_1_day_check_spec import (
            ColumnDistinctCountChange1DayCheckSpec,
        )
        from ..models.column_distinct_count_change_7_days_check_spec import (
            ColumnDistinctCountChange7DaysCheckSpec,
        )
        from ..models.column_distinct_count_change_30_days_check_spec import (
            ColumnDistinctCountChange30DaysCheckSpec,
        )
        from ..models.column_distinct_count_change_check_spec import (
            ColumnDistinctCountChangeCheckSpec,
        )
        from ..models.column_distinct_count_check_spec import (
            ColumnDistinctCountCheckSpec,
        )
        from ..models.column_distinct_percent_anomaly_stationary_check_spec import (
            ColumnDistinctPercentAnomalyStationaryCheckSpec,
        )
        from ..models.column_distinct_percent_change_1_day_check_spec import (
            ColumnDistinctPercentChange1DayCheckSpec,
        )
        from ..models.column_distinct_percent_change_7_days_check_spec import (
            ColumnDistinctPercentChange7DaysCheckSpec,
        )
        from ..models.column_distinct_percent_change_30_days_check_spec import (
            ColumnDistinctPercentChange30DaysCheckSpec,
        )
        from ..models.column_distinct_percent_change_check_spec import (
            ColumnDistinctPercentChangeCheckSpec,
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

        _profile_distinct_count_anomaly = d.pop("profile_distinct_count_anomaly", UNSET)
        profile_distinct_count_anomaly: Union[
            Unset, ColumnDistinctCountAnomalyDifferencingCheckSpec
        ]
        if isinstance(_profile_distinct_count_anomaly, Unset):
            profile_distinct_count_anomaly = UNSET
        else:
            profile_distinct_count_anomaly = (
                ColumnDistinctCountAnomalyDifferencingCheckSpec.from_dict(
                    _profile_distinct_count_anomaly
                )
            )

        _profile_distinct_percent_anomaly = d.pop(
            "profile_distinct_percent_anomaly", UNSET
        )
        profile_distinct_percent_anomaly: Union[
            Unset, ColumnDistinctPercentAnomalyStationaryCheckSpec
        ]
        if isinstance(_profile_distinct_percent_anomaly, Unset):
            profile_distinct_percent_anomaly = UNSET
        else:
            profile_distinct_percent_anomaly = (
                ColumnDistinctPercentAnomalyStationaryCheckSpec.from_dict(
                    _profile_distinct_percent_anomaly
                )
            )

        _profile_distinct_count_change = d.pop("profile_distinct_count_change", UNSET)
        profile_distinct_count_change: Union[Unset, ColumnDistinctCountChangeCheckSpec]
        if isinstance(_profile_distinct_count_change, Unset):
            profile_distinct_count_change = UNSET
        else:
            profile_distinct_count_change = (
                ColumnDistinctCountChangeCheckSpec.from_dict(
                    _profile_distinct_count_change
                )
            )

        _profile_distinct_percent_change = d.pop(
            "profile_distinct_percent_change", UNSET
        )
        profile_distinct_percent_change: Union[
            Unset, ColumnDistinctPercentChangeCheckSpec
        ]
        if isinstance(_profile_distinct_percent_change, Unset):
            profile_distinct_percent_change = UNSET
        else:
            profile_distinct_percent_change = (
                ColumnDistinctPercentChangeCheckSpec.from_dict(
                    _profile_distinct_percent_change
                )
            )

        _profile_distinct_count_change_1_day = d.pop(
            "profile_distinct_count_change_1_day", UNSET
        )
        profile_distinct_count_change_1_day: Union[
            Unset, ColumnDistinctCountChange1DayCheckSpec
        ]
        if isinstance(_profile_distinct_count_change_1_day, Unset):
            profile_distinct_count_change_1_day = UNSET
        else:
            profile_distinct_count_change_1_day = (
                ColumnDistinctCountChange1DayCheckSpec.from_dict(
                    _profile_distinct_count_change_1_day
                )
            )

        _profile_distinct_count_change_7_days = d.pop(
            "profile_distinct_count_change_7_days", UNSET
        )
        profile_distinct_count_change_7_days: Union[
            Unset, ColumnDistinctCountChange7DaysCheckSpec
        ]
        if isinstance(_profile_distinct_count_change_7_days, Unset):
            profile_distinct_count_change_7_days = UNSET
        else:
            profile_distinct_count_change_7_days = (
                ColumnDistinctCountChange7DaysCheckSpec.from_dict(
                    _profile_distinct_count_change_7_days
                )
            )

        _profile_distinct_count_change_30_days = d.pop(
            "profile_distinct_count_change_30_days", UNSET
        )
        profile_distinct_count_change_30_days: Union[
            Unset, ColumnDistinctCountChange30DaysCheckSpec
        ]
        if isinstance(_profile_distinct_count_change_30_days, Unset):
            profile_distinct_count_change_30_days = UNSET
        else:
            profile_distinct_count_change_30_days = (
                ColumnDistinctCountChange30DaysCheckSpec.from_dict(
                    _profile_distinct_count_change_30_days
                )
            )

        _profile_distinct_percent_change_1_day = d.pop(
            "profile_distinct_percent_change_1_day", UNSET
        )
        profile_distinct_percent_change_1_day: Union[
            Unset, ColumnDistinctPercentChange1DayCheckSpec
        ]
        if isinstance(_profile_distinct_percent_change_1_day, Unset):
            profile_distinct_percent_change_1_day = UNSET
        else:
            profile_distinct_percent_change_1_day = (
                ColumnDistinctPercentChange1DayCheckSpec.from_dict(
                    _profile_distinct_percent_change_1_day
                )
            )

        _profile_distinct_percent_change_7_days = d.pop(
            "profile_distinct_percent_change_7_days", UNSET
        )
        profile_distinct_percent_change_7_days: Union[
            Unset, ColumnDistinctPercentChange7DaysCheckSpec
        ]
        if isinstance(_profile_distinct_percent_change_7_days, Unset):
            profile_distinct_percent_change_7_days = UNSET
        else:
            profile_distinct_percent_change_7_days = (
                ColumnDistinctPercentChange7DaysCheckSpec.from_dict(
                    _profile_distinct_percent_change_7_days
                )
            )

        _profile_distinct_percent_change_30_days = d.pop(
            "profile_distinct_percent_change_30_days", UNSET
        )
        profile_distinct_percent_change_30_days: Union[
            Unset, ColumnDistinctPercentChange30DaysCheckSpec
        ]
        if isinstance(_profile_distinct_percent_change_30_days, Unset):
            profile_distinct_percent_change_30_days = UNSET
        else:
            profile_distinct_percent_change_30_days = (
                ColumnDistinctPercentChange30DaysCheckSpec.from_dict(
                    _profile_distinct_percent_change_30_days
                )
            )

        column_uniqueness_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_distinct_count=profile_distinct_count,
            profile_distinct_percent=profile_distinct_percent,
            profile_duplicate_count=profile_duplicate_count,
            profile_duplicate_percent=profile_duplicate_percent,
            profile_distinct_count_anomaly=profile_distinct_count_anomaly,
            profile_distinct_percent_anomaly=profile_distinct_percent_anomaly,
            profile_distinct_count_change=profile_distinct_count_change,
            profile_distinct_percent_change=profile_distinct_percent_change,
            profile_distinct_count_change_1_day=profile_distinct_count_change_1_day,
            profile_distinct_count_change_7_days=profile_distinct_count_change_7_days,
            profile_distinct_count_change_30_days=profile_distinct_count_change_30_days,
            profile_distinct_percent_change_1_day=profile_distinct_percent_change_1_day,
            profile_distinct_percent_change_7_days=profile_distinct_percent_change_7_days,
            profile_distinct_percent_change_30_days=profile_distinct_percent_change_30_days,
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
