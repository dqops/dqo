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
    from ..models.table_volume_daily_monitoring_checks_spec_custom_checks import (
        TableVolumeDailyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableVolumeDailyMonitoringChecksSpec")


@_attrs_define
class TableVolumeDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableVolumeDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_row_count (Union[Unset, TableRowCountCheckSpec]):
        daily_row_count_anomaly (Union[Unset, TableRowCountAnomalyDifferencingCheckSpec]):
        daily_row_count_change (Union[Unset, TableRowCountChangeCheckSpec]):
        daily_row_count_change_1_day (Union[Unset, TableRowCountChange1DayCheckSpec]):
        daily_row_count_change_7_days (Union[Unset, TableRowCountChange7DaysCheckSpec]):
        daily_row_count_change_30_days (Union[Unset, TableRowCountChange30DaysCheckSpec]):
    """

    custom_checks: Union[Unset, "TableVolumeDailyMonitoringChecksSpecCustomChecks"] = (
        UNSET
    )
    daily_row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    daily_row_count_anomaly: Union[
        Unset, "TableRowCountAnomalyDifferencingCheckSpec"
    ] = UNSET
    daily_row_count_change: Union[Unset, "TableRowCountChangeCheckSpec"] = UNSET
    daily_row_count_change_1_day: Union[Unset, "TableRowCountChange1DayCheckSpec"] = (
        UNSET
    )
    daily_row_count_change_7_days: Union[Unset, "TableRowCountChange7DaysCheckSpec"] = (
        UNSET
    )
    daily_row_count_change_30_days: Union[
        Unset, "TableRowCountChange30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count, Unset):
            daily_row_count = self.daily_row_count.to_dict()

        daily_row_count_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count_anomaly, Unset):
            daily_row_count_anomaly = self.daily_row_count_anomaly.to_dict()

        daily_row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count_change, Unset):
            daily_row_count_change = self.daily_row_count_change.to_dict()

        daily_row_count_change_1_day: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count_change_1_day, Unset):
            daily_row_count_change_1_day = self.daily_row_count_change_1_day.to_dict()

        daily_row_count_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count_change_7_days, Unset):
            daily_row_count_change_7_days = self.daily_row_count_change_7_days.to_dict()

        daily_row_count_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_row_count_change_30_days, Unset):
            daily_row_count_change_30_days = (
                self.daily_row_count_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_row_count is not UNSET:
            field_dict["daily_row_count"] = daily_row_count
        if daily_row_count_anomaly is not UNSET:
            field_dict["daily_row_count_anomaly"] = daily_row_count_anomaly
        if daily_row_count_change is not UNSET:
            field_dict["daily_row_count_change"] = daily_row_count_change
        if daily_row_count_change_1_day is not UNSET:
            field_dict["daily_row_count_change_1_day"] = daily_row_count_change_1_day
        if daily_row_count_change_7_days is not UNSET:
            field_dict["daily_row_count_change_7_days"] = daily_row_count_change_7_days
        if daily_row_count_change_30_days is not UNSET:
            field_dict["daily_row_count_change_30_days"] = (
                daily_row_count_change_30_days
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
        from ..models.table_volume_daily_monitoring_checks_spec_custom_checks import (
            TableVolumeDailyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableVolumeDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableVolumeDailyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_row_count = d.pop("daily_row_count", UNSET)
        daily_row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_daily_row_count, Unset):
            daily_row_count = UNSET
        else:
            daily_row_count = TableRowCountCheckSpec.from_dict(_daily_row_count)

        _daily_row_count_anomaly = d.pop("daily_row_count_anomaly", UNSET)
        daily_row_count_anomaly: Union[Unset, TableRowCountAnomalyDifferencingCheckSpec]
        if isinstance(_daily_row_count_anomaly, Unset):
            daily_row_count_anomaly = UNSET
        else:
            daily_row_count_anomaly = (
                TableRowCountAnomalyDifferencingCheckSpec.from_dict(
                    _daily_row_count_anomaly
                )
            )

        _daily_row_count_change = d.pop("daily_row_count_change", UNSET)
        daily_row_count_change: Union[Unset, TableRowCountChangeCheckSpec]
        if isinstance(_daily_row_count_change, Unset):
            daily_row_count_change = UNSET
        else:
            daily_row_count_change = TableRowCountChangeCheckSpec.from_dict(
                _daily_row_count_change
            )

        _daily_row_count_change_1_day = d.pop("daily_row_count_change_1_day", UNSET)
        daily_row_count_change_1_day: Union[Unset, TableRowCountChange1DayCheckSpec]
        if isinstance(_daily_row_count_change_1_day, Unset):
            daily_row_count_change_1_day = UNSET
        else:
            daily_row_count_change_1_day = TableRowCountChange1DayCheckSpec.from_dict(
                _daily_row_count_change_1_day
            )

        _daily_row_count_change_7_days = d.pop("daily_row_count_change_7_days", UNSET)
        daily_row_count_change_7_days: Union[Unset, TableRowCountChange7DaysCheckSpec]
        if isinstance(_daily_row_count_change_7_days, Unset):
            daily_row_count_change_7_days = UNSET
        else:
            daily_row_count_change_7_days = TableRowCountChange7DaysCheckSpec.from_dict(
                _daily_row_count_change_7_days
            )

        _daily_row_count_change_30_days = d.pop("daily_row_count_change_30_days", UNSET)
        daily_row_count_change_30_days: Union[Unset, TableRowCountChange30DaysCheckSpec]
        if isinstance(_daily_row_count_change_30_days, Unset):
            daily_row_count_change_30_days = UNSET
        else:
            daily_row_count_change_30_days = (
                TableRowCountChange30DaysCheckSpec.from_dict(
                    _daily_row_count_change_30_days
                )
            )

        table_volume_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_row_count=daily_row_count,
            daily_row_count_anomaly=daily_row_count_anomaly,
            daily_row_count_change=daily_row_count_change,
            daily_row_count_change_1_day=daily_row_count_change_1_day,
            daily_row_count_change_7_days=daily_row_count_change_7_days,
            daily_row_count_change_30_days=daily_row_count_change_30_days,
        )

        table_volume_daily_monitoring_checks_spec.additional_properties = d
        return table_volume_daily_monitoring_checks_spec

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
