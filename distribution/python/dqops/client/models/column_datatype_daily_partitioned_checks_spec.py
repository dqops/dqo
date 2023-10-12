from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_datatype_daily_partitioned_checks_spec_custom_checks import (
        ColumnDatatypeDailyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_datatype_string_datatype_changed_check_spec import (
        ColumnDatatypeStringDatatypeChangedCheckSpec,
    )
    from ..models.column_datatype_string_datatype_detected_check_spec import (
        ColumnDatatypeStringDatatypeDetectedCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatatypeDailyPartitionedChecksSpec")


@_attrs_define
class ColumnDatatypeDailyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnDatatypeDailyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_partition_string_datatype_detected (Union[Unset, ColumnDatatypeStringDatatypeDetectedCheckSpec]):
        daily_partition_string_datatype_changed (Union[Unset, ColumnDatatypeStringDatatypeChangedCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnDatatypeDailyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    daily_partition_string_datatype_detected: Union[
        Unset, "ColumnDatatypeStringDatatypeDetectedCheckSpec"
    ] = UNSET
    daily_partition_string_datatype_changed: Union[
        Unset, "ColumnDatatypeStringDatatypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_partition_string_datatype_detected: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_string_datatype_detected, Unset):
            daily_partition_string_datatype_detected = (
                self.daily_partition_string_datatype_detected.to_dict()
            )

        daily_partition_string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_string_datatype_changed, Unset):
            daily_partition_string_datatype_changed = (
                self.daily_partition_string_datatype_changed.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_partition_string_datatype_detected is not UNSET:
            field_dict[
                "daily_partition_string_datatype_detected"
            ] = daily_partition_string_datatype_detected
        if daily_partition_string_datatype_changed is not UNSET:
            field_dict[
                "daily_partition_string_datatype_changed"
            ] = daily_partition_string_datatype_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datatype_daily_partitioned_checks_spec_custom_checks import (
            ColumnDatatypeDailyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_datatype_string_datatype_changed_check_spec import (
            ColumnDatatypeStringDatatypeChangedCheckSpec,
        )
        from ..models.column_datatype_string_datatype_detected_check_spec import (
            ColumnDatatypeStringDatatypeDetectedCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnDatatypeDailyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnDatatypeDailyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_partition_string_datatype_detected = d.pop(
            "daily_partition_string_datatype_detected", UNSET
        )
        daily_partition_string_datatype_detected: Union[
            Unset, ColumnDatatypeStringDatatypeDetectedCheckSpec
        ]
        if isinstance(_daily_partition_string_datatype_detected, Unset):
            daily_partition_string_datatype_detected = UNSET
        else:
            daily_partition_string_datatype_detected = (
                ColumnDatatypeStringDatatypeDetectedCheckSpec.from_dict(
                    _daily_partition_string_datatype_detected
                )
            )

        _daily_partition_string_datatype_changed = d.pop(
            "daily_partition_string_datatype_changed", UNSET
        )
        daily_partition_string_datatype_changed: Union[
            Unset, ColumnDatatypeStringDatatypeChangedCheckSpec
        ]
        if isinstance(_daily_partition_string_datatype_changed, Unset):
            daily_partition_string_datatype_changed = UNSET
        else:
            daily_partition_string_datatype_changed = (
                ColumnDatatypeStringDatatypeChangedCheckSpec.from_dict(
                    _daily_partition_string_datatype_changed
                )
            )

        column_datatype_daily_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            daily_partition_string_datatype_detected=daily_partition_string_datatype_detected,
            daily_partition_string_datatype_changed=daily_partition_string_datatype_changed,
        )

        column_datatype_daily_partitioned_checks_spec.additional_properties = d
        return column_datatype_daily_partitioned_checks_spec

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
