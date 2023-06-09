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
        column_exists (Union[Unset, ColumnSchemaColumnExistsCheckSpec]):
        column_type_changed (Union[Unset, ColumnSchemaTypeChangedCheckSpec]):
    """

    column_exists: Union[Unset, "ColumnSchemaColumnExistsCheckSpec"] = UNSET
    column_type_changed: Union[Unset, "ColumnSchemaTypeChangedCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_exists: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_exists, Unset):
            column_exists = self.column_exists.to_dict()

        column_type_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_type_changed, Unset):
            column_type_changed = self.column_type_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_exists is not UNSET:
            field_dict["column_exists"] = column_exists
        if column_type_changed is not UNSET:
            field_dict["column_type_changed"] = column_type_changed

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
        _column_exists = d.pop("column_exists", UNSET)
        column_exists: Union[Unset, ColumnSchemaColumnExistsCheckSpec]
        if isinstance(_column_exists, Unset):
            column_exists = UNSET
        else:
            column_exists = ColumnSchemaColumnExistsCheckSpec.from_dict(_column_exists)

        _column_type_changed = d.pop("column_type_changed", UNSET)
        column_type_changed: Union[Unset, ColumnSchemaTypeChangedCheckSpec]
        if isinstance(_column_type_changed, Unset):
            column_type_changed = UNSET
        else:
            column_type_changed = ColumnSchemaTypeChangedCheckSpec.from_dict(
                _column_type_changed
            )

        column_schema_profiling_checks_spec = cls(
            column_exists=column_exists,
            column_type_changed=column_type_changed,
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
