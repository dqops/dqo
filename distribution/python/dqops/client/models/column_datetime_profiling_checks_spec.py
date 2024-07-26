from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_date_in_range_percent_check_spec import (
        ColumnDateInRangePercentCheckSpec,
    )
    from ..models.column_date_values_in_future_percent_check_spec import (
        ColumnDateValuesInFuturePercentCheckSpec,
    )
    from ..models.column_datetime_profiling_checks_spec_custom_checks import (
        ColumnDatetimeProfilingChecksSpecCustomChecks,
    )
    from ..models.column_text_match_date_format_percent_check_spec import (
        ColumnTextMatchDateFormatPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatetimeProfilingChecksSpec")


@_attrs_define
class ColumnDatetimeProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnDatetimeProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_date_values_in_future_percent (Union[Unset, ColumnDateValuesInFuturePercentCheckSpec]):
        profile_date_in_range_percent (Union[Unset, ColumnDateInRangePercentCheckSpec]):
        profile_text_match_date_format_percent (Union[Unset, ColumnTextMatchDateFormatPercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnDatetimeProfilingChecksSpecCustomChecks"] = UNSET
    profile_date_values_in_future_percent: Union[
        Unset, "ColumnDateValuesInFuturePercentCheckSpec"
    ] = UNSET
    profile_date_in_range_percent: Union[Unset, "ColumnDateInRangePercentCheckSpec"] = (
        UNSET
    )
    profile_text_match_date_format_percent: Union[
        Unset, "ColumnTextMatchDateFormatPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_date_values_in_future_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_date_values_in_future_percent, Unset):
            profile_date_values_in_future_percent = (
                self.profile_date_values_in_future_percent.to_dict()
            )

        profile_date_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_date_in_range_percent, Unset):
            profile_date_in_range_percent = self.profile_date_in_range_percent.to_dict()

        profile_text_match_date_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_match_date_format_percent, Unset):
            profile_text_match_date_format_percent = (
                self.profile_text_match_date_format_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_date_values_in_future_percent is not UNSET:
            field_dict["profile_date_values_in_future_percent"] = (
                profile_date_values_in_future_percent
            )
        if profile_date_in_range_percent is not UNSET:
            field_dict["profile_date_in_range_percent"] = profile_date_in_range_percent
        if profile_text_match_date_format_percent is not UNSET:
            field_dict["profile_text_match_date_format_percent"] = (
                profile_text_match_date_format_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_date_in_range_percent_check_spec import (
            ColumnDateInRangePercentCheckSpec,
        )
        from ..models.column_date_values_in_future_percent_check_spec import (
            ColumnDateValuesInFuturePercentCheckSpec,
        )
        from ..models.column_datetime_profiling_checks_spec_custom_checks import (
            ColumnDatetimeProfilingChecksSpecCustomChecks,
        )
        from ..models.column_text_match_date_format_percent_check_spec import (
            ColumnTextMatchDateFormatPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnDatetimeProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnDatetimeProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

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

        _profile_date_in_range_percent = d.pop("profile_date_in_range_percent", UNSET)
        profile_date_in_range_percent: Union[Unset, ColumnDateInRangePercentCheckSpec]
        if isinstance(_profile_date_in_range_percent, Unset):
            profile_date_in_range_percent = UNSET
        else:
            profile_date_in_range_percent = ColumnDateInRangePercentCheckSpec.from_dict(
                _profile_date_in_range_percent
            )

        _profile_text_match_date_format_percent = d.pop(
            "profile_text_match_date_format_percent", UNSET
        )
        profile_text_match_date_format_percent: Union[
            Unset, ColumnTextMatchDateFormatPercentCheckSpec
        ]
        if isinstance(_profile_text_match_date_format_percent, Unset):
            profile_text_match_date_format_percent = UNSET
        else:
            profile_text_match_date_format_percent = (
                ColumnTextMatchDateFormatPercentCheckSpec.from_dict(
                    _profile_text_match_date_format_percent
                )
            )

        column_datetime_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_date_values_in_future_percent=profile_date_values_in_future_percent,
            profile_date_in_range_percent=profile_date_in_range_percent,
            profile_text_match_date_format_percent=profile_text_match_date_format_percent,
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
