from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_integrity_foreign_key_match_percent_check_spec import (
        ColumnIntegrityForeignKeyMatchPercentCheckSpec,
    )
    from ..models.column_integrity_lookup_key_not_found_count_check_spec import (
        ColumnIntegrityLookupKeyNotFoundCountCheckSpec,
    )
    from ..models.column_integrity_profiling_checks_spec_custom_checks import (
        ColumnIntegrityProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnIntegrityProfilingChecksSpec")


@_attrs_define
class ColumnIntegrityProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnIntegrityProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_lookup_key_not_found (Union[Unset, ColumnIntegrityLookupKeyNotFoundCountCheckSpec]):
        profile_lookup_key_found_percent (Union[Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnIntegrityProfilingChecksSpecCustomChecks"] = (
        UNSET
    )
    profile_lookup_key_not_found: Union[
        Unset, "ColumnIntegrityLookupKeyNotFoundCountCheckSpec"
    ] = UNSET
    profile_lookup_key_found_percent: Union[
        Unset, "ColumnIntegrityForeignKeyMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_lookup_key_not_found: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_lookup_key_not_found, Unset):
            profile_lookup_key_not_found = self.profile_lookup_key_not_found.to_dict()

        profile_lookup_key_found_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_lookup_key_found_percent, Unset):
            profile_lookup_key_found_percent = (
                self.profile_lookup_key_found_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_lookup_key_not_found is not UNSET:
            field_dict["profile_lookup_key_not_found"] = profile_lookup_key_not_found
        if profile_lookup_key_found_percent is not UNSET:
            field_dict["profile_lookup_key_found_percent"] = (
                profile_lookup_key_found_percent
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_integrity_foreign_key_match_percent_check_spec import (
            ColumnIntegrityForeignKeyMatchPercentCheckSpec,
        )
        from ..models.column_integrity_lookup_key_not_found_count_check_spec import (
            ColumnIntegrityLookupKeyNotFoundCountCheckSpec,
        )
        from ..models.column_integrity_profiling_checks_spec_custom_checks import (
            ColumnIntegrityProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnIntegrityProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnIntegrityProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_lookup_key_not_found = d.pop("profile_lookup_key_not_found", UNSET)
        profile_lookup_key_not_found: Union[
            Unset, ColumnIntegrityLookupKeyNotFoundCountCheckSpec
        ]
        if isinstance(_profile_lookup_key_not_found, Unset):
            profile_lookup_key_not_found = UNSET
        else:
            profile_lookup_key_not_found = (
                ColumnIntegrityLookupKeyNotFoundCountCheckSpec.from_dict(
                    _profile_lookup_key_not_found
                )
            )

        _profile_lookup_key_found_percent = d.pop(
            "profile_lookup_key_found_percent", UNSET
        )
        profile_lookup_key_found_percent: Union[
            Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec
        ]
        if isinstance(_profile_lookup_key_found_percent, Unset):
            profile_lookup_key_found_percent = UNSET
        else:
            profile_lookup_key_found_percent = (
                ColumnIntegrityForeignKeyMatchPercentCheckSpec.from_dict(
                    _profile_lookup_key_found_percent
                )
            )

        column_integrity_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_lookup_key_not_found=profile_lookup_key_not_found,
            profile_lookup_key_found_percent=profile_lookup_key_found_percent,
        )

        column_integrity_profiling_checks_spec.additional_properties = d
        return column_integrity_profiling_checks_spec

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
