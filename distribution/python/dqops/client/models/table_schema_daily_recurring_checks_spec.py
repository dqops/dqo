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


T = TypeVar("T", bound="TableSchemaDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableSchemaDailyRecurringChecksSpec:
    """
    Attributes:
        daily_column_count (Union[Unset, TableSchemaColumnCountCheckSpec]):
        daily_column_count_changed (Union[Unset, TableSchemaColumnCountChangedCheckSpec]):
        daily_column_list_changed (Union[Unset, TableSchemaColumnListChangedCheckSpec]):
        daily_column_list_or_order_changed (Union[Unset, TableSchemaColumnListOrOrderChangedCheckSpec]):
        daily_column_types_changed (Union[Unset, TableSchemaColumnTypesChangedCheckSpec]):
    """

    daily_column_count: Union[Unset, "TableSchemaColumnCountCheckSpec"] = UNSET
    daily_column_count_changed: Union[
        Unset, "TableSchemaColumnCountChangedCheckSpec"
    ] = UNSET
    daily_column_list_changed: Union[
        Unset, "TableSchemaColumnListChangedCheckSpec"
    ] = UNSET
    daily_column_list_or_order_changed: Union[
        Unset, "TableSchemaColumnListOrOrderChangedCheckSpec"
    ] = UNSET
    daily_column_types_changed: Union[
        Unset, "TableSchemaColumnTypesChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_column_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_count, Unset):
            daily_column_count = self.daily_column_count.to_dict()

        daily_column_count_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_count_changed, Unset):
            daily_column_count_changed = self.daily_column_count_changed.to_dict()

        daily_column_list_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_list_changed, Unset):
            daily_column_list_changed = self.daily_column_list_changed.to_dict()

        daily_column_list_or_order_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_list_or_order_changed, Unset):
            daily_column_list_or_order_changed = (
                self.daily_column_list_or_order_changed.to_dict()
            )

        daily_column_types_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_types_changed, Unset):
            daily_column_types_changed = self.daily_column_types_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_column_count is not UNSET:
            field_dict["daily_column_count"] = daily_column_count
        if daily_column_count_changed is not UNSET:
            field_dict["daily_column_count_changed"] = daily_column_count_changed
        if daily_column_list_changed is not UNSET:
            field_dict["daily_column_list_changed"] = daily_column_list_changed
        if daily_column_list_or_order_changed is not UNSET:
            field_dict[
                "daily_column_list_or_order_changed"
            ] = daily_column_list_or_order_changed
        if daily_column_types_changed is not UNSET:
            field_dict["daily_column_types_changed"] = daily_column_types_changed

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
        _daily_column_count = d.pop("daily_column_count", UNSET)
        daily_column_count: Union[Unset, TableSchemaColumnCountCheckSpec]
        if isinstance(_daily_column_count, Unset):
            daily_column_count = UNSET
        else:
            daily_column_count = TableSchemaColumnCountCheckSpec.from_dict(
                _daily_column_count
            )

        _daily_column_count_changed = d.pop("daily_column_count_changed", UNSET)
        daily_column_count_changed: Union[Unset, TableSchemaColumnCountChangedCheckSpec]
        if isinstance(_daily_column_count_changed, Unset):
            daily_column_count_changed = UNSET
        else:
            daily_column_count_changed = (
                TableSchemaColumnCountChangedCheckSpec.from_dict(
                    _daily_column_count_changed
                )
            )

        _daily_column_list_changed = d.pop("daily_column_list_changed", UNSET)
        daily_column_list_changed: Union[Unset, TableSchemaColumnListChangedCheckSpec]
        if isinstance(_daily_column_list_changed, Unset):
            daily_column_list_changed = UNSET
        else:
            daily_column_list_changed = TableSchemaColumnListChangedCheckSpec.from_dict(
                _daily_column_list_changed
            )

        _daily_column_list_or_order_changed = d.pop(
            "daily_column_list_or_order_changed", UNSET
        )
        daily_column_list_or_order_changed: Union[
            Unset, TableSchemaColumnListOrOrderChangedCheckSpec
        ]
        if isinstance(_daily_column_list_or_order_changed, Unset):
            daily_column_list_or_order_changed = UNSET
        else:
            daily_column_list_or_order_changed = (
                TableSchemaColumnListOrOrderChangedCheckSpec.from_dict(
                    _daily_column_list_or_order_changed
                )
            )

        _daily_column_types_changed = d.pop("daily_column_types_changed", UNSET)
        daily_column_types_changed: Union[Unset, TableSchemaColumnTypesChangedCheckSpec]
        if isinstance(_daily_column_types_changed, Unset):
            daily_column_types_changed = UNSET
        else:
            daily_column_types_changed = (
                TableSchemaColumnTypesChangedCheckSpec.from_dict(
                    _daily_column_types_changed
                )
            )

        table_schema_daily_recurring_checks_spec = cls(
            daily_column_count=daily_column_count,
            daily_column_count_changed=daily_column_count_changed,
            daily_column_list_changed=daily_column_list_changed,
            daily_column_list_or_order_changed=daily_column_list_or_order_changed,
            daily_column_types_changed=daily_column_types_changed,
        )

        table_schema_daily_recurring_checks_spec.additional_properties = d
        return table_schema_daily_recurring_checks_spec

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
