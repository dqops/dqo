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


T = TypeVar("T", bound="ColumnNullsProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnNullsProfilingChecksSpec:
    """
    Attributes:
        nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
        nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
        nulls_percent_anomaly_stationary (Union[Unset, ColumnAnomalyStationaryNullPercentCheckSpec]):
        nulls_percent_change (Union[Unset, ColumnChangeNullPercentCheckSpec]):
        nulls_percent_change_yesterday (Union[Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec]):
        not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
        not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
        nulls_percent_anomaly_stationary_30_days (Union[Unset, ColumnAnomalyStationaryNullPercent30DaysCheckSpec]):
        nulls_percent_change_7_days (Union[Unset, ColumnChangeNullPercentSince7DaysCheckSpec]):
        nulls_percent_change_30_days (Union[Unset, ColumnChangeNullPercentSince30DaysCheckSpec]):
    """

    nulls_count: Union[Unset, "ColumnNullsCountCheckSpec"] = UNSET
    nulls_percent: Union[Unset, "ColumnNullsPercentCheckSpec"] = UNSET
    nulls_percent_anomaly_stationary: Union[
        Unset, "ColumnAnomalyStationaryNullPercentCheckSpec"
    ] = UNSET
    nulls_percent_change: Union[Unset, "ColumnChangeNullPercentCheckSpec"] = UNSET
    nulls_percent_change_yesterday: Union[
        Unset, "ColumnChangeNullPercentSinceYesterdayCheckSpec"
    ] = UNSET
    not_nulls_count: Union[Unset, "ColumnNotNullsCountCheckSpec"] = UNSET
    not_nulls_percent: Union[Unset, "ColumnNotNullsPercentCheckSpec"] = UNSET
    nulls_percent_anomaly_stationary_30_days: Union[
        Unset, "ColumnAnomalyStationaryNullPercent30DaysCheckSpec"
    ] = UNSET
    nulls_percent_change_7_days: Union[
        Unset, "ColumnChangeNullPercentSince7DaysCheckSpec"
    ] = UNSET
    nulls_percent_change_30_days: Union[
        Unset, "ColumnChangeNullPercentSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_count, Unset):
            nulls_count = self.nulls_count.to_dict()

        nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent, Unset):
            nulls_percent = self.nulls_percent.to_dict()

        nulls_percent_anomaly_stationary: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_anomaly_stationary, Unset):
            nulls_percent_anomaly_stationary = (
                self.nulls_percent_anomaly_stationary.to_dict()
            )

        nulls_percent_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_change, Unset):
            nulls_percent_change = self.nulls_percent_change.to_dict()

        nulls_percent_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_change_yesterday, Unset):
            nulls_percent_change_yesterday = (
                self.nulls_percent_change_yesterday.to_dict()
            )

        not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_count, Unset):
            not_nulls_count = self.not_nulls_count.to_dict()

        not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_percent, Unset):
            not_nulls_percent = self.not_nulls_percent.to_dict()

        nulls_percent_anomaly_stationary_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_anomaly_stationary_30_days, Unset):
            nulls_percent_anomaly_stationary_30_days = (
                self.nulls_percent_anomaly_stationary_30_days.to_dict()
            )

        nulls_percent_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_change_7_days, Unset):
            nulls_percent_change_7_days = self.nulls_percent_change_7_days.to_dict()

        nulls_percent_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent_change_30_days, Unset):
            nulls_percent_change_30_days = self.nulls_percent_change_30_days.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if nulls_count is not UNSET:
            field_dict["nulls_count"] = nulls_count
        if nulls_percent is not UNSET:
            field_dict["nulls_percent"] = nulls_percent
        if nulls_percent_anomaly_stationary is not UNSET:
            field_dict[
                "nulls_percent_anomaly_stationary"
            ] = nulls_percent_anomaly_stationary
        if nulls_percent_change is not UNSET:
            field_dict["nulls_percent_change"] = nulls_percent_change
        if nulls_percent_change_yesterday is not UNSET:
            field_dict[
                "nulls_percent_change_yesterday"
            ] = nulls_percent_change_yesterday
        if not_nulls_count is not UNSET:
            field_dict["not_nulls_count"] = not_nulls_count
        if not_nulls_percent is not UNSET:
            field_dict["not_nulls_percent"] = not_nulls_percent
        if nulls_percent_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "nulls_percent_anomaly_stationary_30_days"
            ] = nulls_percent_anomaly_stationary_30_days
        if nulls_percent_change_7_days is not UNSET:
            field_dict["nulls_percent_change_7_days"] = nulls_percent_change_7_days
        if nulls_percent_change_30_days is not UNSET:
            field_dict["nulls_percent_change_30_days"] = nulls_percent_change_30_days

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
        _nulls_count = d.pop("nulls_count", UNSET)
        nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_nulls_count, Unset):
            nulls_count = UNSET
        else:
            nulls_count = ColumnNullsCountCheckSpec.from_dict(_nulls_count)

        _nulls_percent = d.pop("nulls_percent", UNSET)
        nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_nulls_percent, Unset):
            nulls_percent = UNSET
        else:
            nulls_percent = ColumnNullsPercentCheckSpec.from_dict(_nulls_percent)

        _nulls_percent_anomaly_stationary = d.pop(
            "nulls_percent_anomaly_stationary", UNSET
        )
        nulls_percent_anomaly_stationary: Union[
            Unset, ColumnAnomalyStationaryNullPercentCheckSpec
        ]
        if isinstance(_nulls_percent_anomaly_stationary, Unset):
            nulls_percent_anomaly_stationary = UNSET
        else:
            nulls_percent_anomaly_stationary = (
                ColumnAnomalyStationaryNullPercentCheckSpec.from_dict(
                    _nulls_percent_anomaly_stationary
                )
            )

        _nulls_percent_change = d.pop("nulls_percent_change", UNSET)
        nulls_percent_change: Union[Unset, ColumnChangeNullPercentCheckSpec]
        if isinstance(_nulls_percent_change, Unset):
            nulls_percent_change = UNSET
        else:
            nulls_percent_change = ColumnChangeNullPercentCheckSpec.from_dict(
                _nulls_percent_change
            )

        _nulls_percent_change_yesterday = d.pop("nulls_percent_change_yesterday", UNSET)
        nulls_percent_change_yesterday: Union[
            Unset, ColumnChangeNullPercentSinceYesterdayCheckSpec
        ]
        if isinstance(_nulls_percent_change_yesterday, Unset):
            nulls_percent_change_yesterday = UNSET
        else:
            nulls_percent_change_yesterday = (
                ColumnChangeNullPercentSinceYesterdayCheckSpec.from_dict(
                    _nulls_percent_change_yesterday
                )
            )

        _not_nulls_count = d.pop("not_nulls_count", UNSET)
        not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_not_nulls_count, Unset):
            not_nulls_count = UNSET
        else:
            not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(_not_nulls_count)

        _not_nulls_percent = d.pop("not_nulls_percent", UNSET)
        not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_not_nulls_percent, Unset):
            not_nulls_percent = UNSET
        else:
            not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(
                _not_nulls_percent
            )

        _nulls_percent_anomaly_stationary_30_days = d.pop(
            "nulls_percent_anomaly_stationary_30_days", UNSET
        )
        nulls_percent_anomaly_stationary_30_days: Union[
            Unset, ColumnAnomalyStationaryNullPercent30DaysCheckSpec
        ]
        if isinstance(_nulls_percent_anomaly_stationary_30_days, Unset):
            nulls_percent_anomaly_stationary_30_days = UNSET
        else:
            nulls_percent_anomaly_stationary_30_days = (
                ColumnAnomalyStationaryNullPercent30DaysCheckSpec.from_dict(
                    _nulls_percent_anomaly_stationary_30_days
                )
            )

        _nulls_percent_change_7_days = d.pop("nulls_percent_change_7_days", UNSET)
        nulls_percent_change_7_days: Union[
            Unset, ColumnChangeNullPercentSince7DaysCheckSpec
        ]
        if isinstance(_nulls_percent_change_7_days, Unset):
            nulls_percent_change_7_days = UNSET
        else:
            nulls_percent_change_7_days = (
                ColumnChangeNullPercentSince7DaysCheckSpec.from_dict(
                    _nulls_percent_change_7_days
                )
            )

        _nulls_percent_change_30_days = d.pop("nulls_percent_change_30_days", UNSET)
        nulls_percent_change_30_days: Union[
            Unset, ColumnChangeNullPercentSince30DaysCheckSpec
        ]
        if isinstance(_nulls_percent_change_30_days, Unset):
            nulls_percent_change_30_days = UNSET
        else:
            nulls_percent_change_30_days = (
                ColumnChangeNullPercentSince30DaysCheckSpec.from_dict(
                    _nulls_percent_change_30_days
                )
            )

        column_nulls_profiling_checks_spec = cls(
            nulls_count=nulls_count,
            nulls_percent=nulls_percent,
            nulls_percent_anomaly_stationary=nulls_percent_anomaly_stationary,
            nulls_percent_change=nulls_percent_change,
            nulls_percent_change_yesterday=nulls_percent_change_yesterday,
            not_nulls_count=not_nulls_count,
            not_nulls_percent=not_nulls_percent,
            nulls_percent_anomaly_stationary_30_days=nulls_percent_anomaly_stationary_30_days,
            nulls_percent_change_7_days=nulls_percent_change_7_days,
            nulls_percent_change_30_days=nulls_percent_change_30_days,
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
