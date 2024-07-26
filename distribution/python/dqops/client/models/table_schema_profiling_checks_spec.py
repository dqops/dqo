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
    from ..models.table_schema_profiling_checks_spec_custom_checks import (
        TableSchemaProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableSchemaProfilingChecksSpec")


@_attrs_define
class TableSchemaProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableSchemaProfilingChecksSpecCustomChecks]): Dictionary of additional custom checks
            within this category. The keys are check names defined in the definition section. The sensor parameters and
            rules should match the type of the configured sensor and rule for the custom check.
        profile_column_count (Union[Unset, TableSchemaColumnCountCheckSpec]):
        profile_column_count_changed (Union[Unset, TableSchemaColumnCountChangedCheckSpec]):
        profile_column_list_changed (Union[Unset, TableSchemaColumnListChangedCheckSpec]):
        profile_column_list_or_order_changed (Union[Unset, TableSchemaColumnListOrOrderChangedCheckSpec]):
        profile_column_types_changed (Union[Unset, TableSchemaColumnTypesChangedCheckSpec]):
    """

    custom_checks: Union[Unset, "TableSchemaProfilingChecksSpecCustomChecks"] = UNSET
    profile_column_count: Union[Unset, "TableSchemaColumnCountCheckSpec"] = UNSET
    profile_column_count_changed: Union[
        Unset, "TableSchemaColumnCountChangedCheckSpec"
    ] = UNSET
    profile_column_list_changed: Union[
        Unset, "TableSchemaColumnListChangedCheckSpec"
    ] = UNSET
    profile_column_list_or_order_changed: Union[
        Unset, "TableSchemaColumnListOrOrderChangedCheckSpec"
    ] = UNSET
    profile_column_types_changed: Union[
        Unset, "TableSchemaColumnTypesChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        profile_column_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_count, Unset):
            profile_column_count = self.profile_column_count.to_dict()

        profile_column_count_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_count_changed, Unset):
            profile_column_count_changed = self.profile_column_count_changed.to_dict()

        profile_column_list_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_list_changed, Unset):
            profile_column_list_changed = self.profile_column_list_changed.to_dict()

        profile_column_list_or_order_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_list_or_order_changed, Unset):
            profile_column_list_or_order_changed = (
                self.profile_column_list_or_order_changed.to_dict()
            )

        profile_column_types_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_column_types_changed, Unset):
            profile_column_types_changed = self.profile_column_types_changed.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if profile_column_count is not UNSET:
            field_dict["profile_column_count"] = profile_column_count
        if profile_column_count_changed is not UNSET:
            field_dict["profile_column_count_changed"] = profile_column_count_changed
        if profile_column_list_changed is not UNSET:
            field_dict["profile_column_list_changed"] = profile_column_list_changed
        if profile_column_list_or_order_changed is not UNSET:
            field_dict["profile_column_list_or_order_changed"] = (
                profile_column_list_or_order_changed
            )
        if profile_column_types_changed is not UNSET:
            field_dict["profile_column_types_changed"] = profile_column_types_changed

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
        from ..models.table_schema_profiling_checks_spec_custom_checks import (
            TableSchemaProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableSchemaProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableSchemaProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _profile_column_count = d.pop("profile_column_count", UNSET)
        profile_column_count: Union[Unset, TableSchemaColumnCountCheckSpec]
        if isinstance(_profile_column_count, Unset):
            profile_column_count = UNSET
        else:
            profile_column_count = TableSchemaColumnCountCheckSpec.from_dict(
                _profile_column_count
            )

        _profile_column_count_changed = d.pop("profile_column_count_changed", UNSET)
        profile_column_count_changed: Union[
            Unset, TableSchemaColumnCountChangedCheckSpec
        ]
        if isinstance(_profile_column_count_changed, Unset):
            profile_column_count_changed = UNSET
        else:
            profile_column_count_changed = (
                TableSchemaColumnCountChangedCheckSpec.from_dict(
                    _profile_column_count_changed
                )
            )

        _profile_column_list_changed = d.pop("profile_column_list_changed", UNSET)
        profile_column_list_changed: Union[Unset, TableSchemaColumnListChangedCheckSpec]
        if isinstance(_profile_column_list_changed, Unset):
            profile_column_list_changed = UNSET
        else:
            profile_column_list_changed = (
                TableSchemaColumnListChangedCheckSpec.from_dict(
                    _profile_column_list_changed
                )
            )

        _profile_column_list_or_order_changed = d.pop(
            "profile_column_list_or_order_changed", UNSET
        )
        profile_column_list_or_order_changed: Union[
            Unset, TableSchemaColumnListOrOrderChangedCheckSpec
        ]
        if isinstance(_profile_column_list_or_order_changed, Unset):
            profile_column_list_or_order_changed = UNSET
        else:
            profile_column_list_or_order_changed = (
                TableSchemaColumnListOrOrderChangedCheckSpec.from_dict(
                    _profile_column_list_or_order_changed
                )
            )

        _profile_column_types_changed = d.pop("profile_column_types_changed", UNSET)
        profile_column_types_changed: Union[
            Unset, TableSchemaColumnTypesChangedCheckSpec
        ]
        if isinstance(_profile_column_types_changed, Unset):
            profile_column_types_changed = UNSET
        else:
            profile_column_types_changed = (
                TableSchemaColumnTypesChangedCheckSpec.from_dict(
                    _profile_column_types_changed
                )
            )

        table_schema_profiling_checks_spec = cls(
            custom_checks=custom_checks,
            profile_column_count=profile_column_count,
            profile_column_count_changed=profile_column_count_changed,
            profile_column_list_changed=profile_column_list_changed,
            profile_column_list_or_order_changed=profile_column_list_or_order_changed,
            profile_column_types_changed=profile_column_types_changed,
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
