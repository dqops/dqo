from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.column_nulls_profiling_checks_spec_custom_checks import (
        ColumnNullsProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnNullsProfilingChecksSpec")


@_attrs_define
class ColumnNullsProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnNullsProfilingChecksSpecCustomChecks]): Dictionary of additional custom checks
            within this category. The keys are check names defined in the definition section. The sensor parameters and
            rules should match the type of the configured sensor and rule for the custom check.
        profile_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        profile_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        profile_nulls_percent_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryNullPercentCheckSpec]):
        profile_nulls_percent_change (Union[Unset, ColumnChangeNullPercentCheckSpec]):
        profile_nulls_percent_change_yesterday (Union[Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec]):
        profile_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        profile_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
        profile_nulls_percent_change30_days (Union[Unset, ColumnChangeNullPercentSince30DaysCheckSpec]):
        profile_nulls_percent_anomaly_stationary_30_days (Union[Unset,
            ColumnAnomalyStationaryNullPercent30DaysCheckSpec]):
        profile_nulls_percent_change_7_days (Union[Unset, ColumnChangeNullPercentSince7DaysCheckSpec]):
        profile_nulls_percent_change_30_days (Union[Unset, ColumnChangeNullPercentSince30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnNullsProfilingChecksSpecCustomChecks"] = UNSET
    profile_nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    profile_nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    profile_nulls_percent_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryNullPercentCheckSpec"
    ] = UNSET
    profile_nulls_percent_change: Union[
        Unset, "ColumnChangeNullPercentCheckSpec"
    ] = UNSET
    profile_nulls_percent_change_yesterday: Union[
        Unset, "ColumnChangeNullPercentSinceYesterdayCheckSpec"
    ] = UNSET
    profile_not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = UNSET
    profile_not_nulls_percent: Union[Unset, "ColumnNotNullsPercentCheckSpec"] = UNSET
    profile_nulls_percent_change30_days: Union[
        Unset, "ColumnChangeNullPercentSince30DaysCheckSpec"
    ] = UNSET
    profile_nulls_percent_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryNullPercent30DaysCheckSpec"
    ] = UNSET
    profile_nulls_percent_change_7_days: Union[
        Unset, "ColumnChangeNullPercentSince7DaysCheckSpec"
    ] = UNSET
    profile_nulls_percent_change_30_days: Union[
        Unset, "ColumnChangeNullPercentSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_count, Unset):
            profile_nulls_count = self.profile_nulls_count.to_dict()

        profile_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent, Unset):
            profile_nulls_percent = self.profile_nulls_percent.to_dict()

        profile_nulls_percent_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_anomaly_stationary, Unset):
            profile_nulls_percent_anomaly_stationary = (
                self.profile_nulls_percent_anomaly_stationary.to_dict()
            )

        profile_nulls_percent_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_change, Unset):
            profile_nulls_percent_change = self.profile_nulls_percent_change.to_dict()

        profile_nulls_percent_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_change_yesterday, Unset):
            profile_nulls_percent_change_yesterday = (
                self.profile_nulls_percent_change_yesterday.to_dict()
            )

        profile_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_not_nulls_count, Unset):
            profile_not_nulls_count = self.profile_not_nulls_count.to_dict()

        profile_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_not_nulls_percent, Unset):
            profile_not_nulls_percent = self.profile_not_nulls_percent.to_dict()

        profile_nulls_percent_change30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_change30_days, Unset):
            profile_nulls_percent_change30_days = (
                self.profile_nulls_percent_change30_days.to_dict()
            )

        profile_nulls_percent_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_nulls_percent_anomaly_stationary_30_days, Unset):
            profile_nulls_percent_anomaly_stationary_30_days = (
                self.profile_nulls_percent_anomaly_stationary_30_days.to_dict()
            )

        profile_nulls_percent_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_change_7_days, Unset):
            profile_nulls_percent_change_7_days = (
                self.profile_nulls_percent_change_7_days.to_dict()
            )

        profile_nulls_percent_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_nulls_percent_change_30_days, Unset):
            profile_nulls_percent_change_30_days = (
                self.profile_nulls_percent_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_nulls_count is not UNSET:
            field_dict["profile_nulls_count"] = profile_nulls_count
        if profile_nulls_percent is not UNSET:
            field_dict["profile_nulls_percent"] = profile_nulls_percent
        if profile_nulls_percent_anomaly_stationary is not UNSET:
            field_dict[
                "profile_nulls_percent_anomaly_stationary"
            ] = profile_nulls_percent_anomaly_stationary
        if profile_nulls_percent_change is not UNSET:
            field_dict["profile_nulls_percent_change"] = profile_nulls_percent_change
        if profile_nulls_percent_change_yesterday is not UNSET:
            field_dict[
                "profile_nulls_percent_change_yesterday"
            ] = profile_nulls_percent_change_yesterday
        if profile_not_nulls_count is not UNSET:
            field_dict["profile_not_nulls_count"] = profile_not_nulls_count
        if profile_not_nulls_percent is not UNSET:
            field_dict["profile_not_nulls_percent"] = profile_not_nulls_percent
        if profile_nulls_percent_change30_days is not UNSET:
            field_dict[
                "profile_nulls_percent_change30_days"
            ] = profile_nulls_percent_change30_days
        if profile_nulls_percent_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "profile_nulls_percent_anomaly_stationary_30_days"
            ] = profile_nulls_percent_anomaly_stationary_30_days
        if profile_nulls_percent_change_7_days is not UNSET:
            field_dict[
                "profile_nulls_percent_change_7_days"
            ] = profile_nulls_percent_change_7_days
        if profile_nulls_percent_change_30_days is not UNSET:
            field_dict[
                "profile_nulls_percent_change_30_days"
            ] = profile_nulls_percent_change_30_days

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
        from ..models.column_nulls_profiling_checks_spec_custom_checks import (
            ColumnNullsProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnNullsProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnNullsProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_nulls_count = d.pop("profile_nulls_count", UNSET)
        profile_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_profile_nulls_count, Unset):
            profile_nulls_count = UNSET
        else:
            profile_nulls_count = ColumnNullsCountCheckSpec.from_dict(
                _profile_nulls_count
            )

        _profile_nulls_percent = d.pop("profile_nulls_percent", UNSET)
        profile_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_profile_nulls_percent, Unset):
            profile_nulls_percent = UNSET
        else:
            profile_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(
                _profile_nulls_percent
            )

        _profile_nulls_percent_anomaly_stationary = d.pop(
            "profile_nulls_percent_anomaly_stationary", UNSET
        )
        profile_nulls_percent_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryNullPercentCheckSpec
        ]
        if isinstance(_profile_nulls_percent_anomaly_stationary, Unset):
            profile_nulls_percent_anomaly_stationary = UNSET
        else:
            profile_nulls_percent_anomaly_stationary = (
                ColumnAnomalyStationaryNullPercentCheckSpec.from_dict(
                    _profile_nulls_percent_anomaly_stationary
                )
            )

        _profile_nulls_percent_change = d.pop("profile_nulls_percent_change", UNSET)
        profile_nulls_percent_change: Union[Unset, ColumnChangeNullPercentCheckSpec]
        if isinstance(_profile_nulls_percent_change, Unset):
            profile_nulls_percent_change = UNSET
        else:
            profile_nulls_percent_change = ColumnChangeNullPercentCheckSpec.from_dict(
                _profile_nulls_percent_change
            )

        _profile_nulls_percent_change_yesterday = d.pop(
            "profile_nulls_percent_change_yesterday", UNSET
        )
        profile_nulls_percent_change_yesterday: Union[
            Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec
        ]
        if isinstance(_profile_nulls_percent_change_yesterday, Unset):
            profile_nulls_percent_change_yesterday = UNSET
        else:
            profile_nulls_percent_change_yesterday = (
                ColumnChangeNullPercentSinceYesterdayCheckSpec.from_dict(
                    _profile_nulls_percent_change_yesterday
                )
            )

        _profile_not_nulls_count = d.pop("profile_not_nulls_count", UNSET)
        profile_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_profile_not_nulls_count, Unset):
            profile_not_nulls_count = UNSET
        else:
            profile_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(
                _profile_not_nulls_count
            )

        _profile_not_nulls_percent = d.pop("profile_not_nulls_percent", UNSET)
        profile_not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_profile_not_nulls_percent, Unset):
            profile_not_nulls_percent = UNSET
        else:
            profile_not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(
                _profile_not_nulls_percent
            )

        _profile_nulls_percent_change30_days = d.pop(
            "profile_nulls_percent_change30_days", UNSET
        )
        profile_nulls_percent_change30_days: Union[
            Unset, ColumnChangeNullPercentSince30DaysCheckSpec
        ]
        if isinstance(_profile_nulls_percent_change30_days, Unset):
            profile_nulls_percent_change30_days = UNSET
        else:
            profile_nulls_percent_change30_days = (
                ColumnChangeNullPercentSince30DaysCheckSpec.from_dict(
                    _profile_nulls_percent_change30_days
                )
            )

        _profile_nulls_percent_anomaly_stationary_30_days = d.pop(
            "profile_nulls_percent_anomaly_stationary_30_days", UNSET
        )
        profile_nulls_percent_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryNullPercent30DaysCheckSpec
        ]
        if isinstance(_profile_nulls_percent_anomaly_stationary_30_days, Unset):
            profile_nulls_percent_anomaly_stationary_30_days = UNSET
        else:
            profile_nulls_percent_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryNullPercent30DaysCheckSpec.from_dict(
                    _profile_nulls_percent_anomaly_stationary_30_days
                )
            )

        _profile_nulls_percent_change_7_days = d.pop(
            "profile_nulls_percent_change_7_days", UNSET
        )
        profile_nulls_percent_change_7_days: Union[
            Unset, ColumnChangeNullPercentSince7DaysCheckSpec
        ]
        if isinstance(_profile_nulls_percent_change_7_days, Unset):
            profile_nulls_percent_change_7_days = UNSET
        else:
            profile_nulls_percent_change_7_days = (
                ColumnChangeNullPercentSince7DaysCheckSpec.from_dict(
                    _profile_nulls_percent_change_7_days
                )
            )

        _profile_nulls_percent_change_30_days = d.pop(
            "profile_nulls_percent_change_30_days", UNSET
        )
        profile_nulls_percent_change_30_days: Union[
            Unset, ColumnChangeNullPercentSince30DaysCheckSpec
        ]
        if isinstance(_profile_nulls_percent_change_30_days, Unset):
            profile_nulls_percent_change_30_days = UNSET
        else:
            profile_nulls_percent_change_30_days = (
                ColumnChangeNullPercentSince30DaysCheckSpec.from_dict(
                    _profile_nulls_percent_change_30_days
                )
            )

        column_nulls_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_nulls_count=profile_nulls_count,
            profile_nulls_percent=profile_nulls_percent,
            profile_nulls_percent_anomaly_stationary=profile_nulls_percent_anomaly_stationary,
            profile_nulls_percent_change=profile_nulls_percent_change,
            profile_nulls_percent_change_yesterday=profile_nulls_percent_change_yesterday,
            profile_not_nulls_count=profile_not_nulls_count,
            profile_not_nulls_percent=profile_not_nulls_percent,
            profile_nulls_percent_change30_days=profile_nulls_percent_change30_days,
            profile_nulls_percent_anomaly_stationary_30_days=profile_nulls_percent_anomaly_stationary_30_days,
            profile_nulls_percent_change_7_days=profile_nulls_percent_change_7_days,
            profile_nulls_percent_change_30_days=profile_nulls_percent_change_30_days,
        )

        column_nulls_profiling_checks_spec.additional_properties = d
        return column_nulls_profiling_checks_spec

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
