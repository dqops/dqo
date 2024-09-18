from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_monitoring_check_categories_spec import (
        TableMonitoringCheckCategoriesSpec,
    )
    from ..models.table_partitioned_check_categories_spec import (
        TablePartitionedCheckCategoriesSpec,
    )
    from ..models.table_profiling_check_categories_spec import (
        TableProfilingCheckCategoriesSpec,
    )
    from ..models.target_table_pattern_spec import TargetTablePatternSpec


T = TypeVar("T", bound="TableQualityPolicySpec")


@_attrs_define
class TableQualityPolicySpec:
    """
    Attributes:
        priority (Union[Unset, int]): The priority of the pattern. Patterns with lower values are applied before
            patterns with higher priority values.
        disabled (Union[Unset, bool]): Disables this data quality check configuration. The checks will not be activated.
        description (Union[Unset, str]): The description (documentation) of this data quality check configuration.
        target (Union[Unset, TargetTablePatternSpec]):
        profiling_checks (Union[Unset, TableProfilingCheckCategoriesSpec]):
        monitoring_checks (Union[Unset, TableMonitoringCheckCategoriesSpec]):
        partitioned_checks (Union[Unset, TablePartitionedCheckCategoriesSpec]):
    """

    priority: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    description: Union[Unset, str] = UNSET
    target: Union[Unset, "TargetTablePatternSpec"] = UNSET
    profiling_checks: Union[Unset, "TableProfilingCheckCategoriesSpec"] = UNSET
    monitoring_checks: Union[Unset, "TableMonitoringCheckCategoriesSpec"] = UNSET
    partitioned_checks: Union[Unset, "TablePartitionedCheckCategoriesSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        priority = self.priority
        disabled = self.disabled
        description = self.description
        target: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.to_dict()

        profiling_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profiling_checks, Unset):
            profiling_checks = self.profiling_checks.to_dict()

        monitoring_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monitoring_checks, Unset):
            monitoring_checks = self.monitoring_checks.to_dict()

        partitioned_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.partitioned_checks, Unset):
            partitioned_checks = self.partitioned_checks.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if priority is not UNSET:
            field_dict["priority"] = priority
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if description is not UNSET:
            field_dict["description"] = description
        if target is not UNSET:
            field_dict["target"] = target
        if profiling_checks is not UNSET:
            field_dict["profiling_checks"] = profiling_checks
        if monitoring_checks is not UNSET:
            field_dict["monitoring_checks"] = monitoring_checks
        if partitioned_checks is not UNSET:
            field_dict["partitioned_checks"] = partitioned_checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_monitoring_check_categories_spec import (
            TableMonitoringCheckCategoriesSpec,
        )
        from ..models.table_partitioned_check_categories_spec import (
            TablePartitionedCheckCategoriesSpec,
        )
        from ..models.table_profiling_check_categories_spec import (
            TableProfilingCheckCategoriesSpec,
        )
        from ..models.target_table_pattern_spec import TargetTablePatternSpec

        d = src_dict.copy()
        priority = d.pop("priority", UNSET)

        disabled = d.pop("disabled", UNSET)

        description = d.pop("description", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, TargetTablePatternSpec]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = TargetTablePatternSpec.from_dict(_target)

        _profiling_checks = d.pop("profiling_checks", UNSET)
        profiling_checks: Union[Unset, TableProfilingCheckCategoriesSpec]
        if isinstance(_profiling_checks, Unset):
            profiling_checks = UNSET
        else:
            profiling_checks = TableProfilingCheckCategoriesSpec.from_dict(
                _profiling_checks
            )

        _monitoring_checks = d.pop("monitoring_checks", UNSET)
        monitoring_checks: Union[Unset, TableMonitoringCheckCategoriesSpec]
        if isinstance(_monitoring_checks, Unset):
            monitoring_checks = UNSET
        else:
            monitoring_checks = TableMonitoringCheckCategoriesSpec.from_dict(
                _monitoring_checks
            )

        _partitioned_checks = d.pop("partitioned_checks", UNSET)
        partitioned_checks: Union[Unset, TablePartitionedCheckCategoriesSpec]
        if isinstance(_partitioned_checks, Unset):
            partitioned_checks = UNSET
        else:
            partitioned_checks = TablePartitionedCheckCategoriesSpec.from_dict(
                _partitioned_checks
            )

        table_quality_policy_spec = cls(
            priority=priority,
            disabled=disabled,
            description=description,
            target=target,
            profiling_checks=profiling_checks,
            monitoring_checks=monitoring_checks,
            partitioned_checks=partitioned_checks,
        )

        table_quality_policy_spec.additional_properties = d
        return table_quality_policy_spec

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
