from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_blanks_empty_text_found_check_spec import (
        ColumnBlanksEmptyTextFoundCheckSpec,
    )
    from ..models.column_blanks_empty_text_percent_check_spec import (
        ColumnBlanksEmptyTextPercentCheckSpec,
    )
    from ..models.column_blanks_null_placeholder_text_found_check_spec import (
        ColumnBlanksNullPlaceholderTextFoundCheckSpec,
    )
    from ..models.column_blanks_null_placeholder_text_percent_check_spec import (
        ColumnBlanksNullPlaceholderTextPercentCheckSpec,
    )
    from ..models.column_blanks_profiling_checks_spec_custom_checks import (
        ColumnBlanksProfilingChecksSpecCustomChecks,
    )
    from ..models.column_blanks_whitespace_text_found_check_spec import (
        ColumnBlanksWhitespaceTextFoundCheckSpec,
    )
    from ..models.column_blanks_whitespace_text_percent_check_spec import (
        ColumnBlanksWhitespaceTextPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnBlanksProfilingChecksSpec")


@_attrs_define
class ColumnBlanksProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnBlanksProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_empty_text_found (Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]):
        profile_whitespace_text_found (Union[Unset, ColumnBlanksWhitespaceTextFoundCheckSpec]):
        profile_null_placeholder_text_found (Union[Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec]):
        profile_empty_text_percent (Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]):
        profile_whitespace_text_percent (Union[Unset, ColumnBlanksWhitespaceTextPercentCheckSpec]):
        profile_null_placeholder_text_percent (Union[Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnBlanksProfilingChecksSpecCustomChecks"] = UNSET
    profile_empty_text_found: Union[
        Unset, "ColumnBlanksEmptyTextFoundCheckSpec"
    ] = UNSET
    profile_whitespace_text_found: Union[
        Unset, "ColumnBlanksWhitespaceTextFoundCheckSpec"
    ] = UNSET
    profile_null_placeholder_text_found: Union[
        Unset, "ColumnBlanksNullPlaceholderTextFoundCheckSpec"
    ] = UNSET
    profile_empty_text_percent: Union[
        Unset, "ColumnBlanksEmptyTextPercentCheckSpec"
    ] = UNSET
    profile_whitespace_text_percent: Union[
        Unset, "ColumnBlanksWhitespaceTextPercentCheckSpec"
    ] = UNSET
    profile_null_placeholder_text_percent: Union[
        Unset, "ColumnBlanksNullPlaceholderTextPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_empty_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_empty_text_found, Unset):
            profile_empty_text_found = self.profile_empty_text_found.to_dict()

        profile_whitespace_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_whitespace_text_found, Unset):
            profile_whitespace_text_found = self.profile_whitespace_text_found.to_dict()

        profile_null_placeholder_text_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_null_placeholder_text_found, Unset):
            profile_null_placeholder_text_found = (
                self.profile_null_placeholder_text_found.to_dict()
            )

        profile_empty_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_empty_text_percent, Unset):
            profile_empty_text_percent = self.profile_empty_text_percent.to_dict()

        profile_whitespace_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_whitespace_text_percent, Unset):
            profile_whitespace_text_percent = (
                self.profile_whitespace_text_percent.to_dict()
            )

        profile_null_placeholder_text_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_null_placeholder_text_percent, Unset):
            profile_null_placeholder_text_percent = (
                self.profile_null_placeholder_text_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_empty_text_found is not UNSET:
            field_dict["profile_empty_text_found"] = profile_empty_text_found
        if profile_whitespace_text_found is not UNSET:
            field_dict["profile_whitespace_text_found"] = profile_whitespace_text_found
        if profile_null_placeholder_text_found is not UNSET:
            field_dict[
                "profile_null_placeholder_text_found"
            ] = profile_null_placeholder_text_found
        if profile_empty_text_percent is not UNSET:
            field_dict["profile_empty_text_percent"] = profile_empty_text_percent
        if profile_whitespace_text_percent is not UNSET:
            field_dict[
                "profile_whitespace_text_percent"
            ] = profile_whitespace_text_percent
        if profile_null_placeholder_text_percent is not UNSET:
            field_dict[
                "profile_null_placeholder_text_percent"
            ] = profile_null_placeholder_text_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_blanks_empty_text_found_check_spec import (
            ColumnBlanksEmptyTextFoundCheckSpec,
        )
        from ..models.column_blanks_empty_text_percent_check_spec import (
            ColumnBlanksEmptyTextPercentCheckSpec,
        )
        from ..models.column_blanks_null_placeholder_text_found_check_spec import (
            ColumnBlanksNullPlaceholderTextFoundCheckSpec,
        )
        from ..models.column_blanks_null_placeholder_text_percent_check_spec import (
            ColumnBlanksNullPlaceholderTextPercentCheckSpec,
        )
        from ..models.column_blanks_profiling_checks_spec_custom_checks import (
            ColumnBlanksProfilingChecksSpecCustomChecks,
        )
        from ..models.column_blanks_whitespace_text_found_check_spec import (
            ColumnBlanksWhitespaceTextFoundCheckSpec,
        )
        from ..models.column_blanks_whitespace_text_percent_check_spec import (
            ColumnBlanksWhitespaceTextPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnBlanksProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnBlanksProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_empty_text_found = d.pop("profile_empty_text_found", UNSET)
        profile_empty_text_found: Union[Unset, ColumnBlanksEmptyTextFoundCheckSpec]
        if isinstance(_profile_empty_text_found, Unset):
            profile_empty_text_found = UNSET
        else:
            profile_empty_text_found = ColumnBlanksEmptyTextFoundCheckSpec.from_dict(
                _profile_empty_text_found
            )

        _profile_whitespace_text_found = d.pop("profile_whitespace_text_found", UNSET)
        profile_whitespace_text_found: Union[
            Unset, ColumnBlanksWhitespaceTextFoundCheckSpec
        ]
        if isinstance(_profile_whitespace_text_found, Unset):
            profile_whitespace_text_found = UNSET
        else:
            profile_whitespace_text_found = (
                ColumnBlanksWhitespaceTextFoundCheckSpec.from_dict(
                    _profile_whitespace_text_found
                )
            )

        _profile_null_placeholder_text_found = d.pop(
            "profile_null_placeholder_text_found", UNSET
        )
        profile_null_placeholder_text_found: Union[
            Unset, ColumnBlanksNullPlaceholderTextFoundCheckSpec
        ]
        if isinstance(_profile_null_placeholder_text_found, Unset):
            profile_null_placeholder_text_found = UNSET
        else:
            profile_null_placeholder_text_found = (
                ColumnBlanksNullPlaceholderTextFoundCheckSpec.from_dict(
                    _profile_null_placeholder_text_found
                )
            )

        _profile_empty_text_percent = d.pop("profile_empty_text_percent", UNSET)
        profile_empty_text_percent: Union[Unset, ColumnBlanksEmptyTextPercentCheckSpec]
        if isinstance(_profile_empty_text_percent, Unset):
            profile_empty_text_percent = UNSET
        else:
            profile_empty_text_percent = (
                ColumnBlanksEmptyTextPercentCheckSpec.from_dict(
                    _profile_empty_text_percent
                )
            )

        _profile_whitespace_text_percent = d.pop(
            "profile_whitespace_text_percent", UNSET
        )
        profile_whitespace_text_percent: Union[
            Unset, ColumnBlanksWhitespaceTextPercentCheckSpec
        ]
        if isinstance(_profile_whitespace_text_percent, Unset):
            profile_whitespace_text_percent = UNSET
        else:
            profile_whitespace_text_percent = (
                ColumnBlanksWhitespaceTextPercentCheckSpec.from_dict(
                    _profile_whitespace_text_percent
                )
            )

        _profile_null_placeholder_text_percent = d.pop(
            "profile_null_placeholder_text_percent", UNSET
        )
        profile_null_placeholder_text_percent: Union[
            Unset, ColumnBlanksNullPlaceholderTextPercentCheckSpec
        ]
        if isinstance(_profile_null_placeholder_text_percent, Unset):
            profile_null_placeholder_text_percent = UNSET
        else:
            profile_null_placeholder_text_percent = (
                ColumnBlanksNullPlaceholderTextPercentCheckSpec.from_dict(
                    _profile_null_placeholder_text_percent
                )
            )

        column_blanks_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_empty_text_found=profile_empty_text_found,
            profile_whitespace_text_found=profile_whitespace_text_found,
            profile_null_placeholder_text_found=profile_null_placeholder_text_found,
            profile_empty_text_percent=profile_empty_text_percent,
            profile_whitespace_text_percent=profile_whitespace_text_percent,
            profile_null_placeholder_text_percent=profile_null_placeholder_text_percent,
        )

        column_blanks_profiling_checks_spec.additional_properties = d
        return column_blanks_profiling_checks_spec

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
