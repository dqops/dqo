from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_date_values_in_future_percent_check_spec import (
        ColumnDateValuesInFuturePercentCheckSpec,
    )
    from ..models.column_datetime_daily_partitioned_checks_spec_custom_checks import (
        ColumnDatetimeDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_datetime_date_match_format_percent_check_spec import (
        ColumnDatetimeDateMatchFormatPercentCheckSpec,
    )
    from ..models.column_datetime_value_in_range_date_percent_check_spec import (
        ColumnDatetimeValueInRangeDatePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatetimeDailyPartitionedChecksSpec")


@_attrs_define
class ColumnDatetimeDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnDatetimeDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_date_match_format_percent (Union[Unset, ColumnDatetimeDateMatchFormatPercentCheckSpec]):
        daily_partition_date_values_in_future_percent (Union[Unset, ColumnDateValuesInFuturePercentCheckSpec]):
        daily_partition_datetime_value_in_range_date_percent (Union[Unset,
            ColumnDatetimeValueInRangeDatePercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnDatetimeDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_date_match_format_percent: Union[
        Unset, "ColumnDatetimeDateMatchFormatPercentCheckSpec"
    ] = UNSET
    daily_partition_date_values_in_future_percent: Union[
        Unset, "ColumnDateValuesInFuturePercentCheckSpec"
    ] = UNSET
    daily_partition_datetime_value_in_range_date_percent: Union[
        Unset, "ColumnDatetimeValueInRangeDatePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_date_match_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_date_match_format_percent, Unset):
            daily_partition_date_match_format_percent = (
                self.daily_partition_date_match_format_percent.to_dict()
            )

        daily_partition_date_values_in_future_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_date_values_in_future_percent, Unset):
            daily_partition_date_values_in_future_percent = (
                self.daily_partition_date_values_in_future_percent.to_dict()
            )

        daily_partition_datetime_value_in_range_date_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.daily_partition_datetime_value_in_range_date_percent, Unset
        ):
            daily_partition_datetime_value_in_range_date_percent = (
                self.daily_partition_datetime_value_in_range_date_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_date_match_format_percent is not UNSET:
            field_dict[
                "daily_partition_date_match_format_percent"
            ] = daily_partition_date_match_format_percent
        if daily_partition_date_values_in_future_percent is not UNSET:
            field_dict[
                "daily_partition_date_values_in_future_percent"
            ] = daily_partition_date_values_in_future_percent
        if daily_partition_datetime_value_in_range_date_percent is not UNSET:
            field_dict[
                "daily_partition_datetime_value_in_range_date_percent"
            ] = daily_partition_datetime_value_in_range_date_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_date_values_in_future_percent_check_spec import (
            ColumnDateValuesInFuturePercentCheckSpec,
        )
        from ..models.column_datetime_daily_partitioned_checks_spec_custom_checks import (
            ColumnDatetimeDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_datetime_date_match_format_percent_check_spec import (
            ColumnDatetimeDateMatchFormatPercentCheckSpec,
        )
        from ..models.column_datetime_value_in_range_date_percent_check_spec import (
            ColumnDatetimeValueInRangeDatePercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnDatetimeDailyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnDatetimeDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_date_match_format_percent = d.pop(
            "daily_partition_date_match_format_percent", UNSET
        )
        daily_partition_date_match_format_percent: Union[
            Unset, ColumnDatetimeDateMatchFormatPercentCheckSpec
        ]
        if isinstance(_daily_partition_date_match_format_percent, Unset):
            daily_partition_date_match_format_percent = UNSET
        else:
            daily_partition_date_match_format_percent = (
                ColumnDatetimeDateMatchFormatPercentCheckSpec.from_dict(
                    _daily_partition_date_match_format_percent
                )
            )

        _daily_partition_date_values_in_future_percent = d.pop(
            "daily_partition_date_values_in_future_percent", UNSET
        )
        daily_partition_date_values_in_future_percent: Union[
            Unset, ColumnDateValuesInFuturePercentCheckSpec
        ]
        if isinstance(_daily_partition_date_values_in_future_percent, Unset):
            daily_partition_date_values_in_future_percent = UNSET
        else:
            daily_partition_date_values_in_future_percent = (
                ColumnDateValuesInFuturePercentCheckSpec.from_dict(
                    _daily_partition_date_values_in_future_percent
                )
            )

        _daily_partition_datetime_value_in_range_date_percent = d.pop(
            "daily_partition_datetime_value_in_range_date_percent", UNSET
        )
        daily_partition_datetime_value_in_range_date_percent: Union[
            Unset, ColumnDatetimeValueInRangeDatePercentCheckSpec
        ]
        if isinstance(_daily_partition_datetime_value_in_range_date_percent, Unset):
            daily_partition_datetime_value_in_range_date_percent = UNSET
        else:
            daily_partition_datetime_value_in_range_date_percent = (
                ColumnDatetimeValueInRangeDatePercentCheckSpec.from_dict(
                    _daily_partition_datetime_value_in_range_date_percent
                )
            )

        column_datetime_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_date_match_format_percent=daily_partition_date_match_format_percent,
            daily_partition_date_values_in_future_percent=daily_partition_date_values_in_future_percent,
            daily_partition_datetime_value_in_range_date_percent=daily_partition_datetime_value_in_range_date_percent,
        )

        column_datetime_daily_partitioned_checks_spec.additional_properties = d
        return column_datetime_daily_partitioned_checks_spec

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
