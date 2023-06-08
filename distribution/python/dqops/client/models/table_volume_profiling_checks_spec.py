from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_anomaly_row_count_change_7_days_check_spec import (
        TableAnomalyRowCountChange7DaysCheckSpec,
    )
    from ..models.table_anomaly_row_count_change_30_days_check_spec import (
        TableAnomalyRowCountChange30DaysCheckSpec,
    )
    from ..models.table_anomaly_row_count_change_60_days_check_spec import (
        TableAnomalyRowCountChange60DaysCheckSpec,
    )
    from ..models.table_change_row_count_check_spec import TableChangeRowCountCheckSpec
    from ..models.table_change_row_count_since_7_days_check_spec import (
        TableChangeRowCountSince7DaysCheckSpec,
    )
    from ..models.table_change_row_count_since_30_days_check_spec import (
        TableChangeRowCountSince30DaysCheckSpec,
    )
    from ..models.table_change_row_count_since_yesterday_check_spec import (
        TableChangeRowCountSinceYesterdayCheckSpec,
    )
    from ..models.table_row_count_check_spec import TableRowCountCheckSpec


T = TypeVar("T", bound="TableVolumeProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableVolumeProfilingChecksSpec:
    """
    Attributes:
        row_count (Union[Unset, TableRowCountCheckSpec]):
        row_count_change (Union[Unset, TableChangeRowCountCheckSpec]):
        row_count_change_yesterday (Union[Unset, TableChangeRowCountSinceYesterdayCheckSpec]):
        row_count_anomaly_7_days (Union[Unset, TableAnomalyRowCountChange7DaysCheckSpec]):
        row_count_anomaly_30_days (Union[Unset, TableAnomalyRowCountChange30DaysCheckSpec]):
        row_count_anomaly_60_days (Union[Unset, TableAnomalyRowCountChange60DaysCheckSpec]):
        row_count_change_7_days (Union[Unset, TableChangeRowCountSince7DaysCheckSpec]):
        row_count_change_30_days (Union[Unset, TableChangeRowCountSince30DaysCheckSpec]):
    """

    row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    row_count_change: Union[Unset, "TableChangeRowCountCheckSpec"] = UNSET
    row_count_change_yesterday: Union[
        Unset, "TableChangeRowCountSinceYesterdayCheckSpec"
    ] = UNSET
    row_count_anomaly_7_days: Union[
        Unset, "TableAnomalyRowCountChange7DaysCheckSpec"
    ] = UNSET
    row_count_anomaly_30_days: Union[
        Unset, "TableAnomalyRowCountChange30DaysCheckSpec"
    ] = UNSET
    row_count_anomaly_60_days: Union[
        Unset, "TableAnomalyRowCountChange60DaysCheckSpec"
    ] = UNSET
    row_count_change_7_days: Union[
        Unset, "TableChangeRowCountSince7DaysCheckSpec"
    ] = UNSET
    row_count_change_30_days: Union[
        Unset, "TableChangeRowCountSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count, Unset):
            row_count = self.row_count.to_dict()

        row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_change, Unset):
            row_count_change = self.row_count_change.to_dict()

        row_count_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_change_yesterday, Unset):
            row_count_change_yesterday = self.row_count_change_yesterday.to_dict()

        row_count_anomaly_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_anomaly_7_days, Unset):
            row_count_anomaly_7_days = self.row_count_anomaly_7_days.to_dict()

        row_count_anomaly_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_anomaly_30_days, Unset):
            row_count_anomaly_30_days = self.row_count_anomaly_30_days.to_dict()

        row_count_anomaly_60_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_anomaly_60_days, Unset):
            row_count_anomaly_60_days = self.row_count_anomaly_60_days.to_dict()

        row_count_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_change_7_days, Unset):
            row_count_change_7_days = self.row_count_change_7_days.to_dict()

        row_count_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count_change_30_days, Unset):
            row_count_change_30_days = self.row_count_change_30_days.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if row_count is not UNSET:
            field_dict["row_count"] = row_count
        if row_count_change is not UNSET:
            field_dict["row_count_change"] = row_count_change
        if row_count_change_yesterday is not UNSET:
            field_dict["row_count_change_yesterday"] = row_count_change_yesterday
        if row_count_anomaly_7_days is not UNSET:
            field_dict["row_count_anomaly_7_days"] = row_count_anomaly_7_days
        if row_count_anomaly_30_days is not UNSET:
            field_dict["row_count_anomaly_30_days"] = row_count_anomaly_30_days
        if row_count_anomaly_60_days is not UNSET:
            field_dict["row_count_anomaly_60_days"] = row_count_anomaly_60_days
        if row_count_change_7_days is not UNSET:
            field_dict["row_count_change_7_days"] = row_count_change_7_days
        if row_count_change_30_days is not UNSET:
            field_dict["row_count_change_30_days"] = row_count_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_anomaly_row_count_change_7_days_check_spec import (
            TableAnomalyRowCountChange7DaysCheckSpec,
        )
        from ..models.table_anomaly_row_count_change_30_days_check_spec import (
            TableAnomalyRowCountChange30DaysCheckSpec,
        )
        from ..models.table_anomaly_row_count_change_60_days_check_spec import (
            TableAnomalyRowCountChange60DaysCheckSpec,
        )
        from ..models.table_change_row_count_check_spec import (
            TableChangeRowCountCheckSpec,
        )
        from ..models.table_change_row_count_since_7_days_check_spec import (
            TableChangeRowCountSince7DaysCheckSpec,
        )
        from ..models.table_change_row_count_since_30_days_check_spec import (
            TableChangeRowCountSince30DaysCheckSpec,
        )
        from ..models.table_change_row_count_since_yesterday_check_spec import (
            TableChangeRowCountSinceYesterdayCheckSpec,
        )
        from ..models.table_row_count_check_spec import TableRowCountCheckSpec

        d = src_dict.copy()
        _row_count = d.pop("row_count", UNSET)
        row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_row_count, Unset):
            row_count = UNSET
        else:
            row_count = TableRowCountCheckSpec.from_dict(_row_count)

        _row_count_change = d.pop("row_count_change", UNSET)
        row_count_change: Union[Unset, TableChangeRowCountCheckSpec]
        if isinstance(_row_count_change, Unset):
            row_count_change = UNSET
        else:
            row_count_change = TableChangeRowCountCheckSpec.from_dict(_row_count_change)

        _row_count_change_yesterday = d.pop("row_count_change_yesterday", UNSET)
        row_count_change_yesterday: Union[
            Unset, TableChangeRowCountSinceYesterdayCheckSpec
        ]
        if isinstance(_row_count_change_yesterday, Unset):
            row_count_change_yesterday = UNSET
        else:
            row_count_change_yesterday = (
                TableChangeRowCountSinceYesterdayCheckSpec.from_dict(
                    _row_count_change_yesterday
                )
            )

        _row_count_anomaly_7_days = d.pop("row_count_anomaly_7_days", UNSET)
        row_count_anomaly_7_days: Union[Unset, TableAnomalyRowCountChange7DaysCheckSpec]
        if isinstance(_row_count_anomaly_7_days, Unset):
            row_count_anomaly_7_days = UNSET
        else:
            row_count_anomaly_7_days = (
                TableAnomalyRowCountChange7DaysCheckSpec.from_dict(
                    _row_count_anomaly_7_days
                )
            )

        _row_count_anomaly_30_days = d.pop("row_count_anomaly_30_days", UNSET)
        row_count_anomaly_30_days: Union[
            Unset, TableAnomalyRowCountChange30DaysCheckSpec
        ]
        if isinstance(_row_count_anomaly_30_days, Unset):
            row_count_anomaly_30_days = UNSET
        else:
            row_count_anomaly_30_days = (
                TableAnomalyRowCountChange30DaysCheckSpec.from_dict(
                    _row_count_anomaly_30_days
                )
            )

        _row_count_anomaly_60_days = d.pop("row_count_anomaly_60_days", UNSET)
        row_count_anomaly_60_days: Union[
            Unset, TableAnomalyRowCountChange60DaysCheckSpec
        ]
        if isinstance(_row_count_anomaly_60_days, Unset):
            row_count_anomaly_60_days = UNSET
        else:
            row_count_anomaly_60_days = (
                TableAnomalyRowCountChange60DaysCheckSpec.from_dict(
                    _row_count_anomaly_60_days
                )
            )

        _row_count_change_7_days = d.pop("row_count_change_7_days", UNSET)
        row_count_change_7_days: Union[Unset, TableChangeRowCountSince7DaysCheckSpec]
        if isinstance(_row_count_change_7_days, Unset):
            row_count_change_7_days = UNSET
        else:
            row_count_change_7_days = TableChangeRowCountSince7DaysCheckSpec.from_dict(
                _row_count_change_7_days
            )

        _row_count_change_30_days = d.pop("row_count_change_30_days", UNSET)
        row_count_change_30_days: Union[Unset, TableChangeRowCountSince30DaysCheckSpec]
        if isinstance(_row_count_change_30_days, Unset):
            row_count_change_30_days = UNSET
        else:
            row_count_change_30_days = (
                TableChangeRowCountSince30DaysCheckSpec.from_dict(
                    _row_count_change_30_days
                )
            )

        table_volume_profiling_checks_spec = cls(
            row_count=row_count,
            row_count_change=row_count_change,
            row_count_change_yesterday=row_count_change_yesterday,
            row_count_anomaly_7_days=row_count_anomaly_7_days,
            row_count_anomaly_30_days=row_count_anomaly_30_days,
            row_count_anomaly_60_days=row_count_anomaly_60_days,
            row_count_change_7_days=row_count_change_7_days,
            row_count_change_30_days=row_count_change_30_days,
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
