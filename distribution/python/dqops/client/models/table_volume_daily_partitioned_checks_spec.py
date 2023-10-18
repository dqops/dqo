from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_anomaly_stationary_partition_row_count_30_days_check_spec import (
        TableAnomalyStationaryPartitionRowCount30DaysCheckSpec,
    )
    from ..models.table_anomaly_stationary_partition_row_count_check_spec import (
        TableAnomalyStationaryPartitionRowCountCheckSpec,
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
    from ..models.table_volume_daily_partitioned_checks_spec_custom_checks import (
        TableVolumeDailyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableVolumeDailyPartitionedChecksSpec")


@_attrs_define
class TableVolumeDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableVolumeDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_row_count (Union[Unset, TableRowCountCheckSpec]):
        daily_partition_row_count_change (Union[Unset, TableChangeRowCountCheckSpec]):
        daily_partition_row_count_change_yesterday (Union[Unset, TableChangeRowCountSinceYesterdayCheckSpec]):
        daily_partition_row_count_anomaly_stationary_30_days (Union[Unset,
            TableAnomalyStationaryPartitionRowCount30DaysCheckSpec]):
        daily_partition_row_count_anomaly_stationary (Union[Unset, TableAnomalyStationaryPartitionRowCountCheckSpec]):
        daily_partition_row_count_change_7_days (Union[Unset, TableChangeRowCountSince7DaysCheckSpec]):
        daily_partition_row_count_change_30_days (Union[Unset, TableChangeRowCountSince30DaysCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableVolumeDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    daily_partition_row_count_change: Union[
        Unset, "TableChangeRowCountCheckSpec"
    ] = UNSET
    daily_partition_row_count_change_yesterday: Union[
        Unset, "TableChangeRowCountSinceYesterdayCheckSpec"
    ] = UNSET
    daily_partition_row_count_anomaly_stationary_30_days: Union[
        Unset, "TableAnomalyStationaryPartitionRowCount30DaysCheckSpec"
    ] = UNSET
    daily_partition_row_count_anomaly_stationary: Union[
        Unset, "TableAnomalyStationaryPartitionRowCountCheckSpec"
    ] = UNSET
    daily_partition_row_count_change_7_days: Union[
        Unset, "TableChangeRowCountSince7DaysCheckSpec"
    ] = UNSET
    daily_partition_row_count_change_30_days: Union[
        Unset, "TableChangeRowCountSince30DaysCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count, Unset):
            daily_partition_row_count = self.daily_partition_row_count.to_dict()

        daily_partition_row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count_change, Unset):
            daily_partition_row_count_change = (
                self.daily_partition_row_count_change.to_dict()
            )

        daily_partition_row_count_change_yesterday: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count_change_yesterday, Unset):
            daily_partition_row_count_change_yesterday = (
                self.daily_partition_row_count_change_yesterday.to_dict()
            )

        daily_partition_row_count_anomaly_stationary_30_days: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_row_count_anomaly_stationary_30_days, Unset
        ):
            daily_partition_row_count_anomaly_stationary_30_days = (
                self.daily_partition_row_count_anomaly_stationary_30_days.to_dict()
            )

        daily_partition_row_count_anomaly_stationary: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_row_count_anomaly_stationary, Unset):
            daily_partition_row_count_anomaly_stationary = (
                self.daily_partition_row_count_anomaly_stationary.to_dict()
            )

        daily_partition_row_count_change_7_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count_change_7_days, Unset):
            daily_partition_row_count_change_7_days = (
                self.daily_partition_row_count_change_7_days.to_dict()
            )

        daily_partition_row_count_change_30_days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_row_count_change_30_days, Unset):
            daily_partition_row_count_change_30_days = (
                self.daily_partition_row_count_change_30_days.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_row_count is not UNSET:
            field_dict["daily_partition_row_count"] = daily_partition_row_count
        if daily_partition_row_count_change is not UNSET:
            field_dict[
                "daily_partition_row_count_change"
            ] = daily_partition_row_count_change
        if daily_partition_row_count_change_yesterday is not UNSET:
            field_dict[
                "daily_partition_row_count_change_yesterday"
            ] = daily_partition_row_count_change_yesterday
        if daily_partition_row_count_anomaly_stationary_30_days is not UNSET:
            field_dict[
                "daily_partition_row_count_anomaly_stationary_30_days"
            ] = daily_partition_row_count_anomaly_stationary_30_days
        if daily_partition_row_count_anomaly_stationary is not UNSET:
            field_dict[
                "daily_partition_row_count_anomaly_stationary"
            ] = daily_partition_row_count_anomaly_stationary
        if daily_partition_row_count_change_7_days is not UNSET:
            field_dict[
                "daily_partition_row_count_change_7_days"
            ] = daily_partition_row_count_change_7_days
        if daily_partition_row_count_change_30_days is not UNSET:
            field_dict[
                "daily_partition_row_count_change_30_days"
            ] = daily_partition_row_count_change_30_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_anomaly_stationary_partition_row_count_30_days_check_spec import (
            TableAnomalyStationaryPartitionRowCount30DaysCheckSpec,
        )
        from ..models.table_anomaly_stationary_partition_row_count_check_spec import (
            TableAnomalyStationaryPartitionRowCountCheckSpec,
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
        from ..models.table_volume_daily_partitioned_checks_spec_custom_checks import (
            TableVolumeDailyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableVolumeDailyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableVolumeDailyPartitionedChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_partition_row_count = d.pop("daily_partition_row_count", UNSET)
        daily_partition_row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_daily_partition_row_count, Unset):
            daily_partition_row_count = UNSET
        else:
            daily_partition_row_count = TableRowCountCheckSpec.from_dict(
                _daily_partition_row_count
            )

        _daily_partition_row_count_change = d.pop(
            "daily_partition_row_count_change", UNSET
        )
        daily_partition_row_count_change: Union[Unset, TableChangeRowCountCheckSpec]
        if isinstance(_daily_partition_row_count_change, Unset):
            daily_partition_row_count_change = UNSET
        else:
            daily_partition_row_count_change = TableChangeRowCountCheckSpec.from_dict(
                _daily_partition_row_count_change
            )

        _daily_partition_row_count_change_yesterday = d.pop(
            "daily_partition_row_count_change_yesterday", UNSET
        )
        daily_partition_row_count_change_yesterday: Union[
            Unset, TableChangeRowCountSinceYesterdayCheckSpec
        ]
        if isinstance(_daily_partition_row_count_change_yesterday, Unset):
            daily_partition_row_count_change_yesterday = UNSET
        else:
            daily_partition_row_count_change_yesterday = (
                TableChangeRowCountSinceYesterdayCheckSpec.from_dict(
                    _daily_partition_row_count_change_yesterday
                )
            )

        _daily_partition_row_count_anomaly_stationary_30_days = d.pop(
            "daily_partition_row_count_anomaly_stationary_30_days", UNSET
        )
        daily_partition_row_count_anomaly_stationary_30_days: Union[
            Unset, TableAnomalyStationaryPartitionRowCount30DaysCheckSpec
        ]
        if isinstance(_daily_partition_row_count_anomaly_stationary_30_days, Unset):
            daily_partition_row_count_anomaly_stationary_30_days = UNSET
        else:
            daily_partition_row_count_anomaly_stationary_30_days = (
                TableAnomalyStationaryPartitionRowCount30DaysCheckSpec.from_dict(
                    _daily_partition_row_count_anomaly_stationary_30_days
                )
            )

        _daily_partition_row_count_anomaly_stationary = d.pop(
            "daily_partition_row_count_anomaly_stationary", UNSET
        )
        daily_partition_row_count_anomaly_stationary: Union[
            Unset, TableAnomalyStationaryPartitionRowCountCheckSpec
        ]
        if isinstance(_daily_partition_row_count_anomaly_stationary, Unset):
            daily_partition_row_count_anomaly_stationary = UNSET
        else:
            daily_partition_row_count_anomaly_stationary = (
                TableAnomalyStationaryPartitionRowCountCheckSpec.from_dict(
                    _daily_partition_row_count_anomaly_stationary
                )
            )

        _daily_partition_row_count_change_7_days = d.pop(
            "daily_partition_row_count_change_7_days", UNSET
        )
        daily_partition_row_count_change_7_days: Union[
            Unset, TableChangeRowCountSince7DaysCheckSpec
        ]
        if isinstance(_daily_partition_row_count_change_7_days, Unset):
            daily_partition_row_count_change_7_days = UNSET
        else:
            daily_partition_row_count_change_7_days = (
                TableChangeRowCountSince7DaysCheckSpec.from_dict(
                    _daily_partition_row_count_change_7_days
                )
            )

        _daily_partition_row_count_change_30_days = d.pop(
            "daily_partition_row_count_change_30_days", UNSET
        )
        daily_partition_row_count_change_30_days: Union[
            Unset, TableChangeRowCountSince30DaysCheckSpec
        ]
        if isinstance(_daily_partition_row_count_change_30_days, Unset):
            daily_partition_row_count_change_30_days = UNSET
        else:
            daily_partition_row_count_change_30_days = (
                TableChangeRowCountSince30DaysCheckSpec.from_dict(
                    _daily_partition_row_count_change_30_days
                )
            )

        table_volume_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_row_count=daily_partition_row_count,
            daily_partition_row_count_change=daily_partition_row_count_change,
            daily_partition_row_count_change_yesterday=daily_partition_row_count_change_yesterday,
            daily_partition_row_count_anomaly_stationary_30_days=daily_partition_row_count_anomaly_stationary_30_days,
            daily_partition_row_count_anomaly_stationary=daily_partition_row_count_anomaly_stationary,
            daily_partition_row_count_change_7_days=daily_partition_row_count_change_7_days,
            daily_partition_row_count_change_30_days=daily_partition_row_count_change_30_days,
        )

        table_volume_daily_partitioned_checks_spec.additional_properties = d
        return table_volume_daily_partitioned_checks_spec

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
