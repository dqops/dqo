from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_schema_column_exists_check_spec import (
        ColumnSchemaColumnExistsCheckSpec,
    )
    from ..models.column_schema_daily_monitoring_checks_spec_custom_checks import (
        ColumnSchemaDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.column_schema_type_changed_check_spec import (
        ColumnSchemaTypeChangedCheckSpec,
    )


T = TypeVar("T", bound="ColumnSchemaDailyMonitoringChecksSpec")


@_attrs_define
class ColumnSchemaDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnSchemaDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_column_exists (Union[Unset, ColumnSchemaColumnExistsCheckSpec]):
        daily_column_type_changed (Union[Unset, ColumnSchemaTypeChangedCheckSpec]):
    """

    custom_checks: Union[Unset, "ColumnSchemaDailyMonitoringChecksSpecCustomChecks"] = (
        UNSET
    )
    daily_column_exists: Union[Unset, "ColumnSchemaColumnExistsCheckSpec"] = UNSET
    daily_column_type_changed: Union[Unset, "ColumnSchemaTypeChangedCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_column_exists: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_exists, Unset):
            daily_column_exists = self.daily_column_exists.to_dict()

        daily_column_type_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_column_type_changed, Unset):
            daily_column_type_changed = self.daily_column_type_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_column_exists is not UNSET:
            field_dict["daily_column_exists"] = daily_column_exists
        if daily_column_type_changed is not UNSET:
            field_dict["daily_column_type_changed"] = daily_column_type_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_schema_column_exists_check_spec import (
            ColumnSchemaColumnExistsCheckSpec,
        )
        from ..models.column_schema_daily_monitoring_checks_spec_custom_checks import (
            ColumnSchemaDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.column_schema_type_changed_check_spec import (
            ColumnSchemaTypeChangedCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnSchemaDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnSchemaDailyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_column_exists = d.pop("daily_column_exists", UNSET)
        daily_column_exists: Union[Unset, ColumnSchemaColumnExistsCheckSpec]
        if isinstance(_daily_column_exists, Unset):
            daily_column_exists = UNSET
        else:
            daily_column_exists = ColumnSchemaColumnExistsCheckSpec.from_dict(
                _daily_column_exists
            )

        _daily_column_type_changed = d.pop("daily_column_type_changed", UNSET)
        daily_column_type_changed: Union[Unset, ColumnSchemaTypeChangedCheckSpec]
        if isinstance(_daily_column_type_changed, Unset):
            daily_column_type_changed = UNSET
        else:
            daily_column_type_changed = ColumnSchemaTypeChangedCheckSpec.from_dict(
                _daily_column_type_changed
            )

        column_schema_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_column_exists=daily_column_exists,
            daily_column_type_changed=daily_column_type_changed,
        )

        column_schema_daily_monitoring_checks_spec.additional_properties = d
        return column_schema_daily_monitoring_checks_spec

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
