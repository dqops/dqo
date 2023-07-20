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


T = TypeVar("T", bound="ColumnSchemaMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSchemaMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_column_exists (Union[Unset, ColumnSchemaColumnExistsCheckSpec]):
        monthly_column_type_changed (Union[Unset, ColumnSchemaTypeChangedCheckSpec]):
    """

    monthly_column_exists: Union[Unset, "ColumnSchemaColumnExistsCheckSpec"] = UNSET
    monthly_column_type_changed: Union[
        Unset, "ColumnSchemaTypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_column_exists: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_exists, Unset):
            monthly_column_exists = self.monthly_column_exists.to_dict()

        monthly_column_type_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_type_changed, Unset):
            monthly_column_type_changed = self.monthly_column_type_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_column_exists is not UNSET:
            field_dict["monthly_column_exists"] = monthly_column_exists
        if monthly_column_type_changed is not UNSET:
            field_dict["monthly_column_type_changed"] = monthly_column_type_changed

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
        _monthly_column_exists = d.pop("monthly_column_exists", UNSET)
        monthly_column_exists: Union[Unset, ColumnSchemaColumnExistsCheckSpec]
        if isinstance(_monthly_column_exists, Unset):
            monthly_column_exists = UNSET
        else:
            monthly_column_exists = ColumnSchemaColumnExistsCheckSpec.from_dict(
                _monthly_column_exists
            )

        _monthly_column_type_changed = d.pop("monthly_column_type_changed", UNSET)
        monthly_column_type_changed: Union[Unset, ColumnSchemaTypeChangedCheckSpec]
        if isinstance(_monthly_column_type_changed, Unset):
            monthly_column_type_changed = UNSET
        else:
            monthly_column_type_changed = ColumnSchemaTypeChangedCheckSpec.from_dict(
                _monthly_column_type_changed
            )

        column_schema_monthly_recurring_checks_spec = cls(
            monthly_column_exists=monthly_column_exists,
            monthly_column_type_changed=monthly_column_type_changed,
        )

        column_schema_monthly_recurring_checks_spec.additional_properties = d
        return column_schema_monthly_recurring_checks_spec

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
