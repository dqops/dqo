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
        date_match_format_percent (Union[Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec]):
        string_datatype_changed (Union[Unset, ColumnDatatypeStringDatatypeChangedCheckSpec]):
    """

    date_match_format_percent: Union[
        Unset, "ColumnDatatypeDateMatchFormatPercentCheckSpec"
    ] = UNSET
    string_datatype_changed: Union[
        Unset, "ColumnDatatypeStringDatatypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        date_match_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.date_match_format_percent, Unset):
            date_match_format_percent = self.date_match_format_percent.to_dict()

        string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.string_datatype_changed, Unset):
            string_datatype_changed = self.string_datatype_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if date_match_format_percent is not UNSET:
            field_dict["date_match_format_percent"] = date_match_format_percent
        if string_datatype_changed is not UNSET:
            field_dict["string_datatype_changed"] = string_datatype_changed

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
        _date_match_format_percent = d.pop("date_match_format_percent", UNSET)
        date_match_format_percent: Union[
            Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec
        ]
        if isinstance(_date_match_format_percent, Unset):
            date_match_format_percent = UNSET
        else:
            date_match_format_percent = (
                ColumnDatatypeDateMatchFormatPercentCheckSpec.from_dict(
                    _date_match_format_percent
                )
            )

        _string_datatype_changed = d.pop("string_datatype_changed", UNSET)
        string_datatype_changed: Union[
            Unset, ColumnDatatypeStringDatatypeChangedCheckSpec
        ]
        if isinstance(_string_datatype_changed, Unset):
            string_datatype_changed = UNSET
        else:
            string_datatype_changed = (
                ColumnDatatypeStringDatatypeChangedCheckSpec.from_dict(
                    _string_datatype_changed
                )
            )

        column_datatype_profiling_checks_spec = cls(
            date_match_format_percent=date_match_format_percent,
            string_datatype_changed=string_datatype_changed,
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