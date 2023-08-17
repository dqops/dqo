from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_schema_column_exists_check_spec import (
        ColumnSchemaColumnExistsCheckSpec,
    )
    from ..models.column_schema_type_changed_check_spec import (
        ColumnSchemaTypeChangedCheckSpec,
    )


T = TypeVar("T", bound="ColumnSchemaProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSchemaProfilingChecksSpec:
    """
    Attributes:
        profile_column_exists (Union[Unset, ColumnSchemaColumnExistsCheckSpec]):
        profile_column_type_changed (Union[Unset, ColumnSchemaTypeChangedCheckSpec]):
    """

    profile_column_exists: Union[Unset, "ColumnSchemaColumnExistsCheckSpec"] = UNSET
    profile_column_type_changed: Union[
        Unset, "ColumnSchemaTypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_column_exists: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_exists, Unset):
            profile_column_exists = self.profile_column_exists.to_dict()

        profile_column_type_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_type_changed, Unset):
            profile_column_type_changed = self.profile_column_type_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_column_exists is not UNSET:
            field_dict["profile_column_exists"] = profile_column_exists
        if profile_column_type_changed is not UNSET:
            field_dict["profile_column_type_changed"] = profile_column_type_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_schema_column_exists_check_spec import (
            ColumnSchemaColumnExistsCheckSpec,
        )
        from ..models.column_schema_type_changed_check_spec import (
            ColumnSchemaTypeChangedCheckSpec,
        )

        d = src_dict.copy()
        _profile_column_exists = d.pop("profile_column_exists", UNSET)
        profile_column_exists: Union[Unset, ColumnSchemaColumnExistsCheckSpec]
        if isinstance(_profile_column_exists, Unset):
            profile_column_exists = UNSET
        else:
            profile_column_exists = ColumnSchemaColumnExistsCheckSpec.from_dict(
                _profile_column_exists
            )

        _profile_column_type_changed = d.pop("profile_column_type_changed", UNSET)
        profile_column_type_changed: Union[Unset, ColumnSchemaTypeChangedCheckSpec]
        if isinstance(_profile_column_type_changed, Unset):
            profile_column_type_changed = UNSET
        else:
            profile_column_type_changed = ColumnSchemaTypeChangedCheckSpec.from_dict(
                _profile_column_type_changed
            )

        column_schema_profiling_checks_spec = cls(
            profile_column_exists=profile_column_exists,
            profile_column_type_changed=profile_column_type_changed,
        )

        column_schema_profiling_checks_spec.additional_properties = d
        return column_schema_profiling_checks_spec

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
