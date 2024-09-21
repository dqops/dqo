from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_duplicate_record_count_check_spec import (
        TableDuplicateRecordCountCheckSpec,
    )
    from ..models.table_duplicate_record_percent_check_spec import (
        TableDuplicateRecordPercentCheckSpec,
    )
    from ..models.table_uniqueness_profiling_checks_spec_custom_checks import (
        TableUniquenessProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableUniquenessProfilingChecksSpec")


@_attrs_define
class TableUniquenessProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableUniquenessProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_duplicate_record_count (Union[Unset, TableDuplicateRecordCountCheckSpec]):
        profile_duplicate_record_percent (Union[Unset, TableDuplicateRecordPercentCheckSpec]):
    """

    custom_checks: Union[Unset, "TableUniquenessProfilingChecksSpecCustomChecks"] = (
        UNSET
    )
    profile_duplicate_record_count: Union[
        Unset, "TableDuplicateRecordCountCheckSpec"
    ] = UNSET
    profile_duplicate_record_percent: Union[
        Unset, "TableDuplicateRecordPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_duplicate_record_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_record_count, Unset):
            profile_duplicate_record_count = (
                self.profile_duplicate_record_count.to_dict()
            )

        profile_duplicate_record_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_duplicate_record_percent, Unset):
            profile_duplicate_record_percent = (
                self.profile_duplicate_record_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_duplicate_record_count is not UNSET:
            field_dict["profile_duplicate_record_count"] = (
                profile_duplicate_record_count
            )
        if profile_duplicate_record_percent is not UNSET:
            field_dict["profile_duplicate_record_percent"] = (
                profile_duplicate_record_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_duplicate_record_count_check_spec import (
            TableDuplicateRecordCountCheckSpec,
        )
        from ..models.table_duplicate_record_percent_check_spec import (
            TableDuplicateRecordPercentCheckSpec,
        )
        from ..models.table_uniqueness_profiling_checks_spec_custom_checks import (
            TableUniquenessProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableUniquenessProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableUniquenessProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_duplicate_record_count = d.pop("profile_duplicate_record_count", UNSET)
        profile_duplicate_record_count: Union[Unset, TableDuplicateRecordCountCheckSpec]
        if isinstance(_profile_duplicate_record_count, Unset):
            profile_duplicate_record_count = UNSET
        else:
            profile_duplicate_record_count = (
                TableDuplicateRecordCountCheckSpec.from_dict(
                    _profile_duplicate_record_count
                )
            )

        _profile_duplicate_record_percent = d.pop(
            "profile_duplicate_record_percent", UNSET
        )
        profile_duplicate_record_percent: Union[
            Unset, TableDuplicateRecordPercentCheckSpec
        ]
        if isinstance(_profile_duplicate_record_percent, Unset):
            profile_duplicate_record_percent = UNSET
        else:
            profile_duplicate_record_percent = (
                TableDuplicateRecordPercentCheckSpec.from_dict(
                    _profile_duplicate_record_percent
                )
            )

        table_uniqueness_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_duplicate_record_count=profile_duplicate_record_count,
            profile_duplicate_record_percent=profile_duplicate_record_percent,
        )

        table_uniqueness_profiling_checks_spec.additional_properties = d
        return table_uniqueness_profiling_checks_spec

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
