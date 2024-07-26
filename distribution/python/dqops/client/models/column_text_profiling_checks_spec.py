from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_text_length_above_max_length_check_spec import (
        ColumnTextLengthAboveMaxLengthCheckSpec,
    )
    from ..models.column_text_length_above_max_length_percent_check_spec import (
        ColumnTextLengthAboveMaxLengthPercentCheckSpec,
    )
    from ..models.column_text_length_below_min_length_check_spec import (
        ColumnTextLengthBelowMinLengthCheckSpec,
    )
    from ..models.column_text_length_below_min_length_percent_check_spec import (
        ColumnTextLengthBelowMinLengthPercentCheckSpec,
    )
    from ..models.column_text_length_in_range_percent_check_spec import (
        ColumnTextLengthInRangePercentCheckSpec,
    )
    from ..models.column_text_max_length_check_spec import ColumnTextMaxLengthCheckSpec
    from ..models.column_text_mean_length_check_spec import (
        ColumnTextMeanLengthCheckSpec,
    )
    from ..models.column_text_min_length_check_spec import ColumnTextMinLengthCheckSpec
    from ..models.column_text_profiling_checks_spec_custom_checks import (
        ColumnTextProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnTextProfilingChecksSpec")


@_attrs_define
class ColumnTextProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnTextProfilingChecksSpecCustomChecks]): Dictionary of additional custom checks
            within this category. The keys are check names defined in the definition section. The sensor parameters and
            rules should match the type of the configured sensor and rule for the custom check.
        profile_text_min_length (Union[Unset, ColumnTextMinLengthCheckSpec]):
        profile_text_max_length (Union[Unset, ColumnTextMaxLengthCheckSpec]):
        profile_text_mean_length (Union[Unset, ColumnTextMeanLengthCheckSpec]):
        profile_text_length_below_min_length (Union[Unset, ColumnTextLengthBelowMinLengthCheckSpec]):
        profile_text_length_below_min_length_percent (Union[Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec]):
        profile_text_length_above_max_length (Union[Unset, ColumnTextLengthAboveMaxLengthCheckSpec]):
        profile_text_length_above_max_length_percent (Union[Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec]):
        profile_text_length_in_range_percent (Union[Unset, ColumnTextLengthInRangePercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnTextProfilingChecksSpecCustomChecks"] = UNSET
    profile_text_min_length: Union[Unset, "ColumnTextMinLengthCheckSpec"] = UNSET
    profile_text_max_length: Union[Unset, "ColumnTextMaxLengthCheckSpec"] = UNSET
    profile_text_mean_length: Union[Unset, "ColumnTextMeanLengthCheckSpec"] = UNSET
    profile_text_length_below_min_length: Union[
        Unset, "ColumnTextLengthBelowMinLengthCheckSpec"
    ] = UNSET
    profile_text_length_below_min_length_percent: Union[
        Unset, "ColumnTextLengthBelowMinLengthPercentCheckSpec"
    ] = UNSET
    profile_text_length_above_max_length: Union[
        Unset, "ColumnTextLengthAboveMaxLengthCheckSpec"
    ] = UNSET
    profile_text_length_above_max_length_percent: Union[
        Unset, "ColumnTextLengthAboveMaxLengthPercentCheckSpec"
    ] = UNSET
    profile_text_length_in_range_percent: Union[
        Unset, "ColumnTextLengthInRangePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_text_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_min_length, Unset):
            profile_text_min_length = self.profile_text_min_length.to_dict()

        profile_text_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_max_length, Unset):
            profile_text_max_length = self.profile_text_max_length.to_dict()

        profile_text_mean_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_mean_length, Unset):
            profile_text_mean_length = self.profile_text_mean_length.to_dict()

        profile_text_length_below_min_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_length_below_min_length, Unset):
            profile_text_length_below_min_length = (
                self.profile_text_length_below_min_length.to_dict()
            )

        profile_text_length_below_min_length_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.profile_text_length_below_min_length_percent, Unset):
            profile_text_length_below_min_length_percent = (
                self.profile_text_length_below_min_length_percent.to_dict()
            )

        profile_text_length_above_max_length: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_length_above_max_length, Unset):
            profile_text_length_above_max_length = (
                self.profile_text_length_above_max_length.to_dict()
            )

        profile_text_length_above_max_length_percent: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.profile_text_length_above_max_length_percent, Unset):
            profile_text_length_above_max_length_percent = (
                self.profile_text_length_above_max_length_percent.to_dict()
            )

        profile_text_length_in_range_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_text_length_in_range_percent, Unset):
            profile_text_length_in_range_percent = (
                self.profile_text_length_in_range_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_text_min_length is not UNSET:
            field_dict["profile_text_min_length"] = profile_text_min_length
        if profile_text_max_length is not UNSET:
            field_dict["profile_text_max_length"] = profile_text_max_length
        if profile_text_mean_length is not UNSET:
            field_dict["profile_text_mean_length"] = profile_text_mean_length
        if profile_text_length_below_min_length is not UNSET:
            field_dict["profile_text_length_below_min_length"] = (
                profile_text_length_below_min_length
            )
        if profile_text_length_below_min_length_percent is not UNSET:
            field_dict["profile_text_length_below_min_length_percent"] = (
                profile_text_length_below_min_length_percent
            )
        if profile_text_length_above_max_length is not UNSET:
            field_dict["profile_text_length_above_max_length"] = (
                profile_text_length_above_max_length
            )
        if profile_text_length_above_max_length_percent is not UNSET:
            field_dict["profile_text_length_above_max_length_percent"] = (
                profile_text_length_above_max_length_percent
            )
        if profile_text_length_in_range_percent is not UNSET:
            field_dict["profile_text_length_in_range_percent"] = (
                profile_text_length_in_range_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_text_length_above_max_length_check_spec import (
            ColumnTextLengthAboveMaxLengthCheckSpec,
        )
        from ..models.column_text_length_above_max_length_percent_check_spec import (
            ColumnTextLengthAboveMaxLengthPercentCheckSpec,
        )
        from ..models.column_text_length_below_min_length_check_spec import (
            ColumnTextLengthBelowMinLengthCheckSpec,
        )
        from ..models.column_text_length_below_min_length_percent_check_spec import (
            ColumnTextLengthBelowMinLengthPercentCheckSpec,
        )
        from ..models.column_text_length_in_range_percent_check_spec import (
            ColumnTextLengthInRangePercentCheckSpec,
        )
        from ..models.column_text_max_length_check_spec import (
            ColumnTextMaxLengthCheckSpec,
        )
        from ..models.column_text_mean_length_check_spec import (
            ColumnTextMeanLengthCheckSpec,
        )
        from ..models.column_text_min_length_check_spec import (
            ColumnTextMinLengthCheckSpec,
        )
        from ..models.column_text_profiling_checks_spec_custom_checks import (
            ColumnTextProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnTextProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnTextProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_text_min_length = d.pop("profile_text_min_length", UNSET)
        profile_text_min_length: Union[Unset, ColumnTextMinLengthCheckSpec]
        if isinstance(_profile_text_min_length, Unset):
            profile_text_min_length = UNSET
        else:
            profile_text_min_length = ColumnTextMinLengthCheckSpec.from_dict(
                _profile_text_min_length
            )

        _profile_text_max_length = d.pop("profile_text_max_length", UNSET)
        profile_text_max_length: Union[Unset, ColumnTextMaxLengthCheckSpec]
        if isinstance(_profile_text_max_length, Unset):
            profile_text_max_length = UNSET
        else:
            profile_text_max_length = ColumnTextMaxLengthCheckSpec.from_dict(
                _profile_text_max_length
            )

        _profile_text_mean_length = d.pop("profile_text_mean_length", UNSET)
        profile_text_mean_length: Union[Unset, ColumnTextMeanLengthCheckSpec]
        if isinstance(_profile_text_mean_length, Unset):
            profile_text_mean_length = UNSET
        else:
            profile_text_mean_length = ColumnTextMeanLengthCheckSpec.from_dict(
                _profile_text_mean_length
            )

        _profile_text_length_below_min_length = d.pop(
            "profile_text_length_below_min_length", UNSET
        )
        profile_text_length_below_min_length: Union[
            Unset, ColumnTextLengthBelowMinLengthCheckSpec
        ]
        if isinstance(_profile_text_length_below_min_length, Unset):
            profile_text_length_below_min_length = UNSET
        else:
            profile_text_length_below_min_length = (
                ColumnTextLengthBelowMinLengthCheckSpec.from_dict(
                    _profile_text_length_below_min_length
                )
            )

        _profile_text_length_below_min_length_percent = d.pop(
            "profile_text_length_below_min_length_percent", UNSET
        )
        profile_text_length_below_min_length_percent: Union[
            Unset, ColumnTextLengthBelowMinLengthPercentCheckSpec
        ]
        if isinstance(_profile_text_length_below_min_length_percent, Unset):
            profile_text_length_below_min_length_percent = UNSET
        else:
            profile_text_length_below_min_length_percent = (
                ColumnTextLengthBelowMinLengthPercentCheckSpec.from_dict(
                    _profile_text_length_below_min_length_percent
                )
            )

        _profile_text_length_above_max_length = d.pop(
            "profile_text_length_above_max_length", UNSET
        )
        profile_text_length_above_max_length: Union[
            Unset, ColumnTextLengthAboveMaxLengthCheckSpec
        ]
        if isinstance(_profile_text_length_above_max_length, Unset):
            profile_text_length_above_max_length = UNSET
        else:
            profile_text_length_above_max_length = (
                ColumnTextLengthAboveMaxLengthCheckSpec.from_dict(
                    _profile_text_length_above_max_length
                )
            )

        _profile_text_length_above_max_length_percent = d.pop(
            "profile_text_length_above_max_length_percent", UNSET
        )
        profile_text_length_above_max_length_percent: Union[
            Unset, ColumnTextLengthAboveMaxLengthPercentCheckSpec
        ]
        if isinstance(_profile_text_length_above_max_length_percent, Unset):
            profile_text_length_above_max_length_percent = UNSET
        else:
            profile_text_length_above_max_length_percent = (
                ColumnTextLengthAboveMaxLengthPercentCheckSpec.from_dict(
                    _profile_text_length_above_max_length_percent
                )
            )

        _profile_text_length_in_range_percent = d.pop(
            "profile_text_length_in_range_percent", UNSET
        )
        profile_text_length_in_range_percent: Union[
            Unset, ColumnTextLengthInRangePercentCheckSpec
        ]
        if isinstance(_profile_text_length_in_range_percent, Unset):
            profile_text_length_in_range_percent = UNSET
        else:
            profile_text_length_in_range_percent = (
                ColumnTextLengthInRangePercentCheckSpec.from_dict(
                    _profile_text_length_in_range_percent
                )
            )

        column_text_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_text_min_length=profile_text_min_length,
            profile_text_max_length=profile_text_max_length,
            profile_text_mean_length=profile_text_mean_length,
            profile_text_length_below_min_length=profile_text_length_below_min_length,
            profile_text_length_below_min_length_percent=profile_text_length_below_min_length_percent,
            profile_text_length_above_max_length=profile_text_length_above_max_length,
            profile_text_length_above_max_length_percent=profile_text_length_above_max_length_percent,
            profile_text_length_in_range_percent=profile_text_length_in_range_percent,
        )

        column_text_profiling_checks_spec.additional_properties = d
        return column_text_profiling_checks_spec

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
