from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_date_values_in_future_percent_check_spec import (
        ColumnDateValuesInFuturePercentCheckSpec,
    )
    from ..models.column_datetime_value_in_range_date_percent_check_spec import (
        ColumnDatetimeValueInRangeDatePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatetimeProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnDatetimeProfilingChecksSpec:
    """
    Attributes:
        profile_date_values_in_future_percent (Union[Unset, ColumnDateValuesInFuturePercentCheckSpec]):
        profile_datetime_value_in_range_date_percent (Union[Unset, ColumnDatetimeValueInRangeDatePercentCheckSpec]):
    """

    profile_date_values_in_future_percent: Union[
        Unset, "ColumnDateValuesInFuturePercentCheckSpec"
    ] = UNSET
    profile_datetime_value_in_range_date_percent: Union[
        Unset, "ColumnDatetimeValueInRangeDatePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_date_values_in_future_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_date_values_in_future_percent, Unset):
            profile_date_values_in_future_percent = (
                self.profile_date_values_in_future_percent.to_dict()
            )

        profile_datetime_value_in_range_date_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_datetime_value_in_range_date_percent, Unset):
            profile_datetime_value_in_range_date_percent = (
                self.profile_datetime_value_in_range_date_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_date_values_in_future_percent is not UNSET:
            field_dict[
                "profile_date_values_in_future_percent"
            ] = profile_date_values_in_future_percent
        if profile_datetime_value_in_range_date_percent is not UNSET:
            field_dict[
                "profile_datetime_value_in_range_date_percent"
            ] = profile_datetime_value_in_range_date_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_date_values_in_future_percent_check_spec import (
            ColumnDateValuesInFuturePercentCheckSpec,
        )
        from ..models.column_datetime_value_in_range_date_percent_check_spec import (
            ColumnDatetimeValueInRangeDatePercentCheckSpec,
        )

        d = src_dict.copy()
        _profile_date_values_in_future_percent = d.pop(
            "profile_date_values_in_future_percent", UNSET
        )
        profile_date_values_in_future_percent: Union[
            Unset, ColumnDateValuesInFuturePercentCheckSpec
        ]
        if isinstance(_profile_date_values_in_future_percent, Unset):
            profile_date_values_in_future_percent = UNSET
        else:
            profile_date_values_in_future_percent = (
                ColumnDateValuesInFuturePercentCheckSpec.from_dict(
                    _profile_date_values_in_future_percent
                )
            )

        _profile_datetime_value_in_range_date_percent = d.pop(
            "profile_datetime_value_in_range_date_percent", UNSET
        )
        profile_datetime_value_in_range_date_percent: Union[
            Unset, ColumnDatetimeValueInRangeDatePercentCheckSpec
        ]
        if isinstance(_profile_datetime_value_in_range_date_percent, Unset):
            profile_datetime_value_in_range_date_percent = UNSET
        else:
            profile_datetime_value_in_range_date_percent = (
                ColumnDatetimeValueInRangeDatePercentCheckSpec.from_dict(
                    _profile_datetime_value_in_range_date_percent
                )
            )

        column_datetime_profiling_checks_spec = cls(
            profile_date_values_in_future_percent=profile_date_values_in_future_percent,
            profile_datetime_value_in_range_date_percent=profile_datetime_value_in_range_date_percent,
        )

        column_datetime_profiling_checks_spec.additional_properties = d
        return column_datetime_profiling_checks_spec

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
