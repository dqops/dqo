from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_accuracy_daily_partitioned_checks_spec_custom_checks import (
        ColumnAccuracyDailyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnAccuracyDailyPartitionedChecksSpec")


@_attrs_define
class ColumnAccuracyDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnAccuracyDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
    """

    custom_checks: Union[
        Unset, "ColumnAccuracyDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_accuracy_daily_partitioned_checks_spec_custom_checks import (
            ColumnAccuracyDailyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnAccuracyDailyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnAccuracyDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        column_accuracy_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
        )

        column_accuracy_daily_partitioned_checks_spec.additional_properties = d
        return column_accuracy_daily_partitioned_checks_spec

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
