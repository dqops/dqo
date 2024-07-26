from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_row_count_anomaly_differencing_check_spec import (
        TableRowCountAnomalyDifferencingCheckSpec,
    )
    from ..models.table_row_count_change_1_day_check_spec import (
        TableRowCountChange1DayCheckSpec,
    )
    from ..models.table_row_count_change_7_days_check_spec import (
        TableRowCountChange7DaysCheckSpec,
    )
    from ..models.table_row_count_change_30_days_check_spec import (
        TableRowCountChange30DaysCheckSpec,
    )
    from ..models.table_row_count_change_check_spec import TableRowCountChangeCheckSpec
    from ..models.table_row_count_check_spec import TableRowCountCheckSpec
    from ..models.table_volume_profiling_checks_spec_custom_checks import (
        TableVolumeProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableVolumeProfilingChecksSpec")


@_attrs_define
class TableVolumeProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableVolumeProfilingChecksSpecCustomChecks]): Dictionary of additional custom checks
            within this category. The keys are check names defined in the definition section. The sensor parameters and
            rules should match the type of the configured sensor and rule for the custom check.
        profile_row_count (Union[Unset, TableRowCountCheckSpec]):
        profile_row_count_anomaly (Union[Unset, TableRowCountAnomalyDifferencingCheckSpec]):
        profile_row_count_change (Union[Unset, TableRowCountChangeCheckSpec]):
        profile_row_count_change_1_day (Union[Unset, TableRowCountChange1DayCheckSpec]):
        profile_row_count_change_7_days (Union[Unset, TableRowCountChange7DaysCheckSpec]):
        profile_row_count_change_30_days (Union[Unset, TableRowCountChange30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "TableVolumeProfilingChecksSpecCustomChecks"] = UNSET
    profile_row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    profile_row_count_anomaly: Union[
        Unset, "TableRowCountAnomalyDifferencingCheckSpec"
    ] = UNSET
    profile_row_count_change: Union[Unset, "TableRowCountChangeCheckSpec"] = UNSET
    profile_row_count_change_1_day: Union[Unset, "TableRowCountChange1DayCheckSpec"] = (
        UNSET
    )
    profile_row_count_change_7_days: Union[
        Unset, "TableRowCountChange7DaysCheckSpec"
    ] = UNSET
    profile_row_count_change_30_days: Union[
        Unset, "TableRowCountChange30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count, Unset):
            profile_row_count = self.profile_row_count.to_dict()

        profile_row_count_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_anomaly, Unset):
            profile_row_count_anomaly = self.profile_row_count_anomaly.to_dict()

        profile_row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_change, Unset):
            profile_row_count_change = self.profile_row_count_change.to_dict()

        profile_row_count_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_change_1_day, Unset):
            profile_row_count_change_1_day = (
                self.profile_row_count_change_1_day.to_dict()
            )

        profile_row_count_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_change_7_days, Unset):
            profile_row_count_change_7_days = (
                self.profile_row_count_change_7_days.to_dict()
            )

        profile_row_count_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_change_30_days, Unset):
            profile_row_count_change_30_days = (
                self.profile_row_count_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_row_count is not UNSET:
            field_dict["profile_row_count"] = profile_row_count
        if profile_row_count_anomaly is not UNSET:
            field_dict["profile_row_count_anomaly"] = profile_row_count_anomaly
        if profile_row_count_change is not UNSET:
            field_dict["profile_row_count_change"] = profile_row_count_change
        if profile_row_count_change_1_day is not UNSET:
            field_dict["profile_row_count_change_1_day"] = (
                profile_row_count_change_1_day
            )
        if profile_row_count_change_7_days is not UNSET:
            field_dict["profile_row_count_change_7_days"] = (
                profile_row_count_change_7_days
            )
        if profile_row_count_change_30_days is not UNSET:
            field_dict["profile_row_count_change_30_days"] = (
                profile_row_count_change_30_days
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_row_count_anomaly_differencing_check_spec import (
            TableRowCountAnomalyDifferencingCheckSpec,
        )
        from ..models.table_row_count_change_1_day_check_spec import (
            TableRowCountChange1DayCheckSpec,
        )
        from ..models.table_row_count_change_7_days_check_spec import (
            TableRowCountChange7DaysCheckSpec,
        )
        from ..models.table_row_count_change_30_days_check_spec import (
            TableRowCountChange30DaysCheckSpec,
        )
        from ..models.table_row_count_change_check_spec import (
            TableRowCountChangeCheckSpec,
        )
        from ..models.table_row_count_check_spec import TableRowCountCheckSpec
        from ..models.table_volume_profiling_checks_spec_custom_checks import (
            TableVolumeProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableVolumeProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableVolumeProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_row_count = d.pop("profile_row_count", UNSET)
        profile_row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_profile_row_count, Unset):
            profile_row_count = UNSET
        else:
            profile_row_count = TableRowCountCheckSpec.from_dict(_profile_row_count)

        _profile_row_count_anomaly = d.pop("profile_row_count_anomaly", UNSET)
        profile_row_count_anomaly: Union[
            Unset, TableRowCountAnomalyDifferencingCheckSpec
        ]
        if isinstance(_profile_row_count_anomaly, Unset):
            profile_row_count_anomaly = UNSET
        else:
            profile_row_count_anomaly = (
                TableRowCountAnomalyDifferencingCheckSpec.from_dict(
                    _profile_row_count_anomaly
                )
            )

        _profile_row_count_change = d.pop("profile_row_count_change", UNSET)
        profile_row_count_change: Union[Unset, TableRowCountChangeCheckSpec]
        if isinstance(_profile_row_count_change, Unset):
            profile_row_count_change = UNSET
        else:
            profile_row_count_change = TableRowCountChangeCheckSpec.from_dict(
                _profile_row_count_change
            )

        _profile_row_count_change_1_day = d.pop("profile_row_count_change_1_day", UNSET)
        profile_row_count_change_1_day: Union[Unset, TableRowCountChange1DayCheckSpec]
        if isinstance(_profile_row_count_change_1_day, Unset):
            profile_row_count_change_1_day = UNSET
        else:
            profile_row_count_change_1_day = TableRowCountChange1DayCheckSpec.from_dict(
                _profile_row_count_change_1_day
            )

        _profile_row_count_change_7_days = d.pop(
            "profile_row_count_change_7_days", UNSET
        )
        profile_row_count_change_7_days: Union[Unset, TableRowCountChange7DaysCheckSpec]
        if isinstance(_profile_row_count_change_7_days, Unset):
            profile_row_count_change_7_days = UNSET
        else:
            profile_row_count_change_7_days = (
                TableRowCountChange7DaysCheckSpec.from_dict(
                    _profile_row_count_change_7_days
                )
            )

        _profile_row_count_change_30_days = d.pop(
            "profile_row_count_change_30_days", UNSET
        )
        profile_row_count_change_30_days: Union[
            Unset, TableRowCountChange30DaysCheckSpec
        ]
        if isinstance(_profile_row_count_change_30_days, Unset):
            profile_row_count_change_30_days = UNSET
        else:
            profile_row_count_change_30_days = (
                TableRowCountChange30DaysCheckSpec.from_dict(
                    _profile_row_count_change_30_days
                )
            )

        table_volume_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_row_count=profile_row_count,
            profile_row_count_anomaly=profile_row_count_anomaly,
            profile_row_count_change=profile_row_count_change,
            profile_row_count_change_1_day=profile_row_count_change_1_day,
            profile_row_count_change_7_days=profile_row_count_change_7_days,
            profile_row_count_change_30_days=profile_row_count_change_30_days,
        )

        table_volume_profiling_checks_spec.additional_properties = d
        return table_volume_profiling_checks_spec

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
