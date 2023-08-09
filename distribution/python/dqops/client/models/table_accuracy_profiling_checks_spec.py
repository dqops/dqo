from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
        TableAccuracyTotalRowCountMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="TableAccuracyProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableAccuracyProfilingChecksSpec:
    """
    Attributes:
        profile_total_row_count_match_percent (Union[Unset, TableAccuracyTotalRowCountMatchPercentCheckSpec]):
    """

    profile_total_row_count_match_percent: Union[
        Unset, "TableAccuracyTotalRowCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_total_row_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_row_count_match_percent, Unset):
            profile_total_row_count_match_percent = (
                self.profile_total_row_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_total_row_count_match_percent is not UNSET:
            field_dict[
                "profile_total_row_count_match_percent"
            ] = profile_total_row_count_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
            TableAccuracyTotalRowCountMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _profile_total_row_count_match_percent = d.pop(
            "profile_total_row_count_match_percent", UNSET
        )
        profile_total_row_count_match_percent: Union[
            Unset, TableAccuracyTotalRowCountMatchPercentCheckSpec
        ]
        if isinstance(_profile_total_row_count_match_percent, Unset):
            profile_total_row_count_match_percent = UNSET
        else:
            profile_total_row_count_match_percent = (
                TableAccuracyTotalRowCountMatchPercentCheckSpec.from_dict(
                    _profile_total_row_count_match_percent
                )
            )

        table_accuracy_profiling_checks_spec = cls(
            profile_total_row_count_match_percent=profile_total_row_count_match_percent,
        )

        table_accuracy_profiling_checks_spec.additional_properties = d
        return table_accuracy_profiling_checks_spec

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
