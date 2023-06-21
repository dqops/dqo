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


T = TypeVar("T", bound="TableSchemaMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableSchemaMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_column_count (Union[Unset, TableSchemaColumnCountCheckSpec]):
        monthly_column_count_changed (Union[Unset, TableSchemaColumnCountChangedCheckSpec]):
        monthly_column_list_changed (Union[Unset, TableSchemaColumnListChangedCheckSpec]):
        monthly_column_list_or_order_changed (Union[Unset, TableSchemaColumnListOrOrderChangedCheckSpec]):
        monthly_column_types_changed (Union[Unset, TableSchemaColumnTypesChangedCheckSpec]):
    """

    monthly_column_count: Union[Unset, "TableSchemaColumnCountCheckSpec"] = UNSET
    monthly_column_count_changed: Union[
        Unset, "TableSchemaColumnCountChangedCheckSpec"
    ] = UNSET
    monthly_column_list_changed: Union[
        Unset, "TableSchemaColumnListChangedCheckSpec"
    ] = UNSET
    monthly_column_list_or_order_changed: Union[
        Unset, "TableSchemaColumnListOrOrderChangedCheckSpec"
    ] = UNSET
    monthly_column_types_changed: Union[
        Unset, "TableSchemaColumnTypesChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_column_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_count, Unset):
            monthly_column_count = self.monthly_column_count.to_dict()

        monthly_column_count_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_count_changed, Unset):
            monthly_column_count_changed = self.monthly_column_count_changed.to_dict()

        monthly_column_list_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_list_changed, Unset):
            monthly_column_list_changed = self.monthly_column_list_changed.to_dict()

        monthly_column_list_or_order_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_list_or_order_changed, Unset):
            monthly_column_list_or_order_changed = (
                self.monthly_column_list_or_order_changed.to_dict()
            )

        monthly_column_types_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_column_types_changed, Unset):
            monthly_column_types_changed = self.monthly_column_types_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_column_count is not UNSET:
            field_dict["monthly_column_count"] = monthly_column_count
        if monthly_column_count_changed is not UNSET:
            field_dict["monthly_column_count_changed"] = monthly_column_count_changed
        if monthly_column_list_changed is not UNSET:
            field_dict["monthly_column_list_changed"] = monthly_column_list_changed
        if monthly_column_list_or_order_changed is not UNSET:
            field_dict[
                "monthly_column_list_or_order_changed"
            ] = monthly_column_list_or_order_changed
        if monthly_column_types_changed is not UNSET:
            field_dict["monthly_column_types_changed"] = monthly_column_types_changed

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
        _monthly_column_count = d.pop("monthly_column_count", UNSET)
        monthly_column_count: Union[Unset, TableSchemaColumnCountCheckSpec]
        if isinstance(_monthly_column_count, Unset):
            monthly_column_count = UNSET
        else:
            monthly_column_count = TableSchemaColumnCountCheckSpec.from_dict(
                _monthly_column_count
            )

        _monthly_column_count_changed = d.pop("monthly_column_count_changed", UNSET)
        monthly_column_count_changed: Union[
            Unset, TableSchemaColumnCountChangedCheckSpec
        ]
        if isinstance(_monthly_column_count_changed, Unset):
            monthly_column_count_changed = UNSET
        else:
            monthly_column_count_changed = (
                TableSchemaColumnCountChangedCheckSpec.from_dict(
                    _monthly_column_count_changed
                )
            )

        _monthly_column_list_changed = d.pop("monthly_column_list_changed", UNSET)
        monthly_column_list_changed: Union[Unset, TableSchemaColumnListChangedCheckSpec]
        if isinstance(_monthly_column_list_changed, Unset):
            monthly_column_list_changed = UNSET
        else:
            monthly_column_list_changed = (
                TableSchemaColumnListChangedCheckSpec.from_dict(
                    _monthly_column_list_changed
                )
            )

        _monthly_column_list_or_order_changed = d.pop(
            "monthly_column_list_or_order_changed", UNSET
        )
        monthly_column_list_or_order_changed: Union[
            Unset, TableSchemaColumnListOrOrderChangedCheckSpec
        ]
        if isinstance(_monthly_column_list_or_order_changed, Unset):
            monthly_column_list_or_order_changed = UNSET
        else:
            monthly_column_list_or_order_changed = (
                TableSchemaColumnListOrOrderChangedCheckSpec.from_dict(
                    _monthly_column_list_or_order_changed
                )
            )

        _monthly_column_types_changed = d.pop("monthly_column_types_changed", UNSET)
        monthly_column_types_changed: Union[
            Unset, TableSchemaColumnTypesChangedCheckSpec
        ]
        if isinstance(_monthly_column_types_changed, Unset):
            monthly_column_types_changed = UNSET
        else:
            monthly_column_types_changed = (
                TableSchemaColumnTypesChangedCheckSpec.from_dict(
                    _monthly_column_types_changed
                )
            )

        table_schema_monthly_recurring_checks_spec = cls(
            monthly_column_count=monthly_column_count,
            monthly_column_count_changed=monthly_column_count_changed,
            monthly_column_list_changed=monthly_column_list_changed,
            monthly_column_list_or_order_changed=monthly_column_list_or_order_changed,
            monthly_column_types_changed=monthly_column_types_changed,
        )

        table_schema_monthly_recurring_checks_spec.additional_properties = d
        return table_schema_monthly_recurring_checks_spec

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
