from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_schema_column_count_changed_check_spec import (
        TableSchemaColumnCountChangedCheckSpec,
    )
    from ..models.table_schema_column_count_check_spec import (
        TableSchemaColumnCountCheckSpec,
    )
    from ..models.table_schema_column_list_changed_check_spec import (
        TableSchemaColumnListChangedCheckSpec,
    )
    from ..models.table_schema_column_list_or_order_changed_check_spec import (
        TableSchemaColumnListOrOrderChangedCheckSpec,
    )
    from ..models.table_schema_column_types_changed_check_spec import (
        TableSchemaColumnTypesChangedCheckSpec,
    )


T = TypeVar("T", bound="TableSchemaProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableSchemaProfilingChecksSpec:
    """
    Attributes:
        column_count (Union[Unset, TableSchemaColumnCountCheckSpec]):
        column_count_changed (Union[Unset, TableSchemaColumnCountChangedCheckSpec]):
        column_list_changed (Union[Unset, TableSchemaColumnListChangedCheckSpec]):
        column_list_or_order_changed (Union[Unset, TableSchemaColumnListOrOrderChangedCheckSpec]):
        column_types_changed (Union[Unset, TableSchemaColumnTypesChangedCheckSpec]):
    """

    column_count: Union[Unset, "TableSchemaColumnCountCheckSpec"] = UNSET
    column_count_changed: Union[Unset, "TableSchemaColumnCountChangedCheckSpec"] = UNSET
    column_list_changed: Union[Unset, "TableSchemaColumnListChangedCheckSpec"] = UNSET
    column_list_or_order_changed: Union[
        Unset, "TableSchemaColumnListOrOrderChangedCheckSpec"
    ] = UNSET
    column_types_changed: Union[Unset, "TableSchemaColumnTypesChangedCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_count, Unset):
            column_count = self.column_count.to_dict()

        column_count_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_count_changed, Unset):
            column_count_changed = self.column_count_changed.to_dict()

        column_list_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_list_changed, Unset):
            column_list_changed = self.column_list_changed.to_dict()

        column_list_or_order_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_list_or_order_changed, Unset):
            column_list_or_order_changed = self.column_list_or_order_changed.to_dict()

        column_types_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_types_changed, Unset):
            column_types_changed = self.column_types_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_count is not UNSET:
            field_dict["column_count"] = column_count
        if column_count_changed is not UNSET:
            field_dict["column_count_changed"] = column_count_changed
        if column_list_changed is not UNSET:
            field_dict["column_list_changed"] = column_list_changed
        if column_list_or_order_changed is not UNSET:
            field_dict["column_list_or_order_changed"] = column_list_or_order_changed
        if column_types_changed is not UNSET:
            field_dict["column_types_changed"] = column_types_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_schema_column_count_changed_check_spec import (
            TableSchemaColumnCountChangedCheckSpec,
        )
        from ..models.table_schema_column_count_check_spec import (
            TableSchemaColumnCountCheckSpec,
        )
        from ..models.table_schema_column_list_changed_check_spec import (
            TableSchemaColumnListChangedCheckSpec,
        )
        from ..models.table_schema_column_list_or_order_changed_check_spec import (
            TableSchemaColumnListOrOrderChangedCheckSpec,
        )
        from ..models.table_schema_column_types_changed_check_spec import (
            TableSchemaColumnTypesChangedCheckSpec,
        )

        d = src_dict.copy()
        _column_count = d.pop("column_count", UNSET)
        column_count: Union[Unset, TableSchemaColumnCountCheckSpec]
        if isinstance(_column_count, Unset):
            column_count = UNSET
        else:
            column_count = TableSchemaColumnCountCheckSpec.from_dict(_column_count)

        _column_count_changed = d.pop("column_count_changed", UNSET)
        column_count_changed: Union[Unset, TableSchemaColumnCountChangedCheckSpec]
        if isinstance(_column_count_changed, Unset):
            column_count_changed = UNSET
        else:
            column_count_changed = TableSchemaColumnCountChangedCheckSpec.from_dict(
                _column_count_changed
            )

        _column_list_changed = d.pop("column_list_changed", UNSET)
        column_list_changed: Union[Unset, TableSchemaColumnListChangedCheckSpec]
        if isinstance(_column_list_changed, Unset):
            column_list_changed = UNSET
        else:
            column_list_changed = TableSchemaColumnListChangedCheckSpec.from_dict(
                _column_list_changed
            )

        _column_list_or_order_changed = d.pop("column_list_or_order_changed", UNSET)
        column_list_or_order_changed: Union[
            Unset, TableSchemaColumnListOrOrderChangedCheckSpec
        ]
        if isinstance(_column_list_or_order_changed, Unset):
            column_list_or_order_changed = UNSET
        else:
            column_list_or_order_changed = (
                TableSchemaColumnListOrOrderChangedCheckSpec.from_dict(
                    _column_list_or_order_changed
                )
            )

        _column_types_changed = d.pop("column_types_changed", UNSET)
        column_types_changed: Union[Unset, TableSchemaColumnTypesChangedCheckSpec]
        if isinstance(_column_types_changed, Unset):
            column_types_changed = UNSET
        else:
            column_types_changed = TableSchemaColumnTypesChangedCheckSpec.from_dict(
                _column_types_changed
            )

        table_schema_profiling_checks_spec = cls(
            column_count=column_count,
            column_count_changed=column_count_changed,
            column_list_changed=column_list_changed,
            column_list_or_order_changed=column_list_or_order_changed,
            column_types_changed=column_types_changed,
        )

        table_schema_profiling_checks_spec.additional_properties = d
        return table_schema_profiling_checks_spec

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
