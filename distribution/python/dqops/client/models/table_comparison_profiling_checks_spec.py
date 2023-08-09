from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_comparison_row_count_match_check_spec import (
        TableComparisonRowCountMatchCheckSpec,
    )


T = TypeVar("T", bound="TableComparisonProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableComparisonProfilingChecksSpec:
    """
    Attributes:
        profile_row_count_match (Union[Unset, TableComparisonRowCountMatchCheckSpec]):
    """

    profile_row_count_match: Union[
        Unset, "TableComparisonRowCountMatchCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_row_count_match: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_row_count_match, Unset):
            profile_row_count_match = self.profile_row_count_match.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_row_count_match is not UNSET:
            field_dict["profile_row_count_match"] = profile_row_count_match

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_comparison_row_count_match_check_spec import (
            TableComparisonRowCountMatchCheckSpec,
        )

        d = src_dict.copy()
        _profile_row_count_match = d.pop("profile_row_count_match", UNSET)
        profile_row_count_match: Union[Unset, TableComparisonRowCountMatchCheckSpec]
        if isinstance(_profile_row_count_match, Unset):
            profile_row_count_match = UNSET
        else:
            profile_row_count_match = TableComparisonRowCountMatchCheckSpec.from_dict(
                _profile_row_count_match
            )

        table_comparison_profiling_checks_spec = cls(
            profile_row_count_match=profile_row_count_match,
        )

        table_comparison_profiling_checks_spec.additional_properties = d
        return table_comparison_profiling_checks_spec

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
