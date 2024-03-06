from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_monitoring_check_categories_spec import (
        ColumnMonitoringCheckCategoriesSpec,
    )
    from ..models.column_partitioned_check_categories_spec import (
        ColumnPartitionedCheckCategoriesSpec,
    )
    from ..models.column_profiling_check_categories_spec import (
        ColumnProfilingCheckCategoriesSpec,
    )
    from ..models.target_column_pattern_spec import TargetColumnPatternSpec


T = TypeVar("T", bound="ColumnDefaultChecksPatternSpec")


@_attrs_define
class ColumnDefaultChecksPatternSpec:
    """
    Attributes:
        priority (Union[Unset, int]): The priority of the pattern. Patterns with lower values are applied before
            patterns with higher priority values.
        target (Union[Unset, TargetColumnPatternSpec]):
        profiling_checks (Union[Unset, ColumnProfilingCheckCategoriesSpec]):
        monitoring_checks (Union[Unset, ColumnMonitoringCheckCategoriesSpec]):
        partitioned_checks (Union[Unset, ColumnPartitionedCheckCategoriesSpec]):
    """

    priority: Union[Unset, int] = UNSET
    target: Union[Unset, "TargetColumnPatternSpec"] = UNSET
    profiling_checks: Union[Unset, "ColumnProfilingCheckCategoriesSpec"] = UNSET
    monitoring_checks: Union[Unset, "ColumnMonitoringCheckCategoriesSpec"] = UNSET
    partitioned_checks: Union[Unset, "ColumnPartitionedCheckCategoriesSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        priority = self.priority
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
        from ..models.column_monitoring_check_categories_spec import (
            ColumnMonitoringCheckCategoriesSpec,
        )
        from ..models.column_partitioned_check_categories_spec import (
            ColumnPartitionedCheckCategoriesSpec,
        )
        from ..models.column_profiling_check_categories_spec import (
            ColumnProfilingCheckCategoriesSpec,
        )
        from ..models.target_column_pattern_spec import TargetColumnPatternSpec

        d = src_dict.copy()
        priority = d.pop("priority", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, TargetColumnPatternSpec]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = TargetColumnPatternSpec.from_dict(_target)

        _profiling_checks = d.pop("profiling_checks", UNSET)
        profiling_checks: Union[Unset, ColumnProfilingCheckCategoriesSpec]
        if isinstance(_profiling_checks, Unset):
            profiling_checks = UNSET
        else:
            profiling_checks = ColumnProfilingCheckCategoriesSpec.from_dict(
                _profiling_checks
            )

        _monitoring_checks = d.pop("monitoring_checks", UNSET)
        monitoring_checks: Union[Unset, ColumnMonitoringCheckCategoriesSpec]
        if isinstance(_monitoring_checks, Unset):
            monitoring_checks = UNSET
        else:
            monitoring_checks = ColumnMonitoringCheckCategoriesSpec.from_dict(
                _monitoring_checks
            )

        _partitioned_checks = d.pop("partitioned_checks", UNSET)
        partitioned_checks: Union[Unset, ColumnPartitionedCheckCategoriesSpec]
        if isinstance(_partitioned_checks, Unset):
            partitioned_checks = UNSET
        else:
            partitioned_checks = ColumnPartitionedCheckCategoriesSpec.from_dict(
                _partitioned_checks
            )

        column_default_checks_pattern_spec = cls(
            priority=priority,
            target=target,
            profiling_checks=profiling_checks,
            monitoring_checks=monitoring_checks,
            partitioned_checks=partitioned_checks,
        )

        column_default_checks_pattern_spec.additional_properties = d
        return column_default_checks_pattern_spec

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
