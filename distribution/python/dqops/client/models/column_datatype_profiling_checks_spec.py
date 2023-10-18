from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_datatype_profiling_checks_spec_custom_checks import (
        ColumnDatatypeProfilingChecksSpecCustomChecks,
    )
    from ..models.column_datatype_string_datatype_changed_check_spec import (
        ColumnDatatypeStringDatatypeChangedCheckSpec,
    )
    from ..models.column_datatype_string_datatype_detected_check_spec import (
        ColumnDatatypeStringDatatypeDetectedCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatatypeProfilingChecksSpec")


@_attrs_define
class ColumnDatatypeProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnDatatypeProfilingChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        profile_string_datatype_detected (Union[Unset, ColumnDatatypeStringDatatypeDetectedCheckSpec]):
        profile_string_datatype_changed (Union[Unset, ColumnDatatypeStringDatatypeChangedCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnDatatypeProfilingChecksSpecCustomChecks"] = UNSET
    profile_string_datatype_detected: Union[
        Unset, "ColumnDatatypeStringDatatypeDetectedCheckSpec"
    ] = UNSET
    profile_string_datatype_changed: Union[
        Unset, "ColumnDatatypeStringDatatypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_string_datatype_detected: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_datatype_detected, Unset):
            profile_string_datatype_detected = (
                self.profile_string_datatype_detected.to_dict()
            )

        profile_string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_string_datatype_changed, Unset):
            profile_string_datatype_changed = (
                self.profile_string_datatype_changed.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_string_datatype_detected is not UNSET:
            field_dict[
                "profile_string_datatype_detected"
            ] = profile_string_datatype_detected
        if profile_string_datatype_changed is not UNSET:
            field_dict[
                "profile_string_datatype_changed"
            ] = profile_string_datatype_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datatype_profiling_checks_spec_custom_checks import (
            ColumnDatatypeProfilingChecksSpecCustomChecks,
        )
        from ..models.column_datatype_string_datatype_changed_check_spec import (
            ColumnDatatypeStringDatatypeChangedCheckSpec,
        )
        from ..models.column_datatype_string_datatype_detected_check_spec import (
            ColumnDatatypeStringDatatypeDetectedCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnDatatypeProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnDatatypeProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_string_datatype_detected = d.pop(
            "profile_string_datatype_detected", UNSET
        )
        profile_string_datatype_detected: Union[
            Unset, ColumnDatatypeStringDatatypeDetectedCheckSpec
        ]
        if isinstance(_profile_string_datatype_detected, Unset):
            profile_string_datatype_detected = UNSET
        else:
            profile_string_datatype_detected = (
                ColumnDatatypeStringDatatypeDetectedCheckSpec.from_dict(
                    _profile_string_datatype_detected
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
            custom_checks=custom_checks,
            profile_string_datatype_detected=profile_string_datatype_detected,
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
