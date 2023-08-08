from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_datatype_date_match_format_percent_check_spec import (
        ColumnDatatypeDateMatchFormatPercentCheckSpec,
    )
    from ..models.column_datatype_string_datatype_changed_check_spec import (
        ColumnDatatypeStringDatatypeChangedCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatatypeProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnDatatypeProfilingChecksSpec:
    """
    Attributes:
        profile_date_match_format_percent (Union[Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec]):
        profile_string_datatype_changed (Union[Unset, ColumnDatatypeStringDatatypeChangedCheckSpec]):
    """

    profile_date_match_format_percent: Union[
        Unset, "ColumnDatatypeDateMatchFormatPercentCheckSpec"
    ] = UNSET
    profile_string_datatype_changed: Union[
        Unset, "ColumnDatatypeStringDatatypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_date_match_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_date_match_format_percent, Unset):
            profile_date_match_format_percent = (
                self.profile_date_match_format_percent.to_dict()
            )

        profile_string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_datatype_changed, Unset):
            profile_string_datatype_changed = (
                self.profile_string_datatype_changed.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_date_match_format_percent is not UNSET:
            field_dict[
                "profile_date_match_format_percent"
            ] = profile_date_match_format_percent
        if profile_string_datatype_changed is not UNSET:
            field_dict[
                "profile_string_datatype_changed"
            ] = profile_string_datatype_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datatype_date_match_format_percent_check_spec import (
            ColumnDatatypeDateMatchFormatPercentCheckSpec,
        )
        from ..models.column_datatype_string_datatype_changed_check_spec import (
            ColumnDatatypeStringDatatypeChangedCheckSpec,
        )

        d = src_dict.copy()
        _profile_date_match_format_percent = d.pop(
            "profile_date_match_format_percent", UNSET
        )
        profile_date_match_format_percent: Union[
            Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec
        ]
        if isinstance(_profile_date_match_format_percent, Unset):
            profile_date_match_format_percent = UNSET
        else:
            profile_date_match_format_percent = (
                ColumnDatatypeDateMatchFormatPercentCheckSpec.from_dict(
                    _profile_date_match_format_percent
                )
            )

        _profile_string_datatype_changed = d.pop(
            "profile_string_datatype_changed", UNSET
        )
        profile_string_datatype_changed: Union[
            Unset, ColumnDatatypeStringDatatypeChangedCheckSpec
        ]
        if isinstance(_profile_string_datatype_changed, Unset):
            profile_string_datatype_changed = UNSET
        else:
            profile_string_datatype_changed = (
                ColumnDatatypeStringDatatypeChangedCheckSpec.from_dict(
                    _profile_string_datatype_changed
                )
            )

        column_datatype_profiling_checks_spec = cls(
            profile_date_match_format_percent=profile_date_match_format_percent,
            profile_string_datatype_changed=profile_string_datatype_changed,
        )

        column_datatype_profiling_checks_spec.additional_properties = d
        return column_datatype_profiling_checks_spec

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
