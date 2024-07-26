from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_profiling_checks_spec_custom_checks import (
        TableAccuracyProfilingChecksSpecCustomChecks,
    )
    from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
        TableAccuracyTotalRowCountMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="TableAccuracyProfilingChecksSpec")


@_attrs_define
class TableAccuracyProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableAccuracyProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_total_row_count_match_percent (Union[Unset, TableAccuracyTotalRowCountMatchPercentCheckSpec]):
    """

    custom_checks: Union[Unset, "TableAccuracyProfilingChecksSpecCustomChecks"] = UNSET
    profile_total_row_count_match_percent: Union[
        Unset, "TableAccuracyTotalRowCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_total_row_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_total_row_count_match_percent, Unset):
            profile_total_row_count_match_percent = (
                self.profile_total_row_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_total_row_count_match_percent is not UNSET:
            field_dict["profile_total_row_count_match_percent"] = (
                profile_total_row_count_match_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_profiling_checks_spec_custom_checks import (
            TableAccuracyProfilingChecksSpecCustomChecks,
        )
        from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
            TableAccuracyTotalRowCountMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableAccuracyProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableAccuracyProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

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
            custom_checks=custom_checks,
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
