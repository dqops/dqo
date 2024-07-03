from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.table_schema_monthly_monitoring_checks_spec_custom_checks import (
        TableSchemaMonthlyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableSchemaMonthlyMonitoringChecksSpec")


@_attrs_define
class TableSchemaMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableSchemaMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_column_count (Union[Unset, TableSchemaColumnCountCheckSpec]):
        monthly_column_count_changed (Union[Unset, TableSchemaColumnCountChangedCheckSpec]):
        monthly_column_list_changed (Union[Unset, TableSchemaColumnListChangedCheckSpec]):
        monthly_column_list_or_order_changed (Union[Unset, TableSchemaColumnListOrOrderChangedCheckSpec]):
        monthly_column_types_changed (Union[Unset, TableSchemaColumnTypesChangedCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableSchemaMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
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
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

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
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_column_count is not UNSET:
            field_dict["monthly_column_count"] = monthly_column_count
        if monthly_column_count_changed is not UNSET:
            field_dict["monthly_column_count_changed"] = monthly_column_count_changed
        if monthly_column_list_changed is not UNSET:
            field_dict["monthly_column_list_changed"] = monthly_column_list_changed
        if monthly_column_list_or_order_changed is not UNSET:
            field_dict["monthly_column_list_or_order_changed"] = (
                monthly_column_list_or_order_changed
            )
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
        from ..models.table_schema_monthly_monitoring_checks_spec_custom_checks import (
            TableSchemaMonthlyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableSchemaMonthlyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableSchemaMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

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

        table_schema_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_column_count=monthly_column_count,
            monthly_column_count_changed=monthly_column_count_changed,
            monthly_column_list_changed=monthly_column_list_changed,
            monthly_column_list_or_order_changed=monthly_column_list_or_order_changed,
            monthly_column_types_changed=monthly_column_types_changed,
        )

        table_schema_monthly_monitoring_checks_spec.additional_properties = d
        return table_schema_monthly_monitoring_checks_spec

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
