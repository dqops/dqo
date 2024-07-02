from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_datatype_detected_datatype_in_text_changed_check_spec import (
        ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec,
    )
    from ..models.column_datatype_monthly_partitioned_checks_spec_custom_checks import (
        ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_detected_datatype_in_text_check_spec import (
        ColumnDetectedDatatypeInTextCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatatypeMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnDatatypeMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_detected_datatype_in_text (Union[Unset, ColumnDetectedDatatypeInTextCheckSpec]):
        monthly_partition_detected_datatype_in_text_changed (Union[Unset,
            ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_detected_datatype_in_text: Union[
        Unset, "ColumnDetectedDatatypeInTextCheckSpec"
    ] = UNSET
    monthly_partition_detected_datatype_in_text_changed: Union[
        Unset, "ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_detected_datatype_in_text: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.monthly_partition_detected_datatype_in_text, Unset):
            monthly_partition_detected_datatype_in_text = (
                self.monthly_partition_detected_datatype_in_text.to_dict()
            )

        monthly_partition_detected_datatype_in_text_changed: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_detected_datatype_in_text_changed, Unset
        ):
            monthly_partition_detected_datatype_in_text_changed = (
                self.monthly_partition_detected_datatype_in_text_changed.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_detected_datatype_in_text is not UNSET:
            field_dict["monthly_partition_detected_datatype_in_text"] = (
                monthly_partition_detected_datatype_in_text
            )
        if monthly_partition_detected_datatype_in_text_changed is not UNSET:
            field_dict["monthly_partition_detected_datatype_in_text_changed"] = (
                monthly_partition_detected_datatype_in_text_changed
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datatype_detected_datatype_in_text_changed_check_spec import (
            ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec,
        )
        from ..models.column_datatype_monthly_partitioned_checks_spec_custom_checks import (
            ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_detected_datatype_in_text_check_spec import (
            ColumnDetectedDatatypeInTextCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnDatatypeMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_detected_datatype_in_text = d.pop(
            "monthly_partition_detected_datatype_in_text", UNSET
        )
        monthly_partition_detected_datatype_in_text: Union[
            Unset, ColumnDetectedDatatypeInTextCheckSpec
        ]
        if isinstance(_monthly_partition_detected_datatype_in_text, Unset):
            monthly_partition_detected_datatype_in_text = UNSET
        else:
            monthly_partition_detected_datatype_in_text = (
                ColumnDetectedDatatypeInTextCheckSpec.from_dict(
                    _monthly_partition_detected_datatype_in_text
                )
            )

        _monthly_partition_detected_datatype_in_text_changed = d.pop(
            "monthly_partition_detected_datatype_in_text_changed", UNSET
        )
        monthly_partition_detected_datatype_in_text_changed: Union[
            Unset, ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec
        ]
        if isinstance(_monthly_partition_detected_datatype_in_text_changed, Unset):
            monthly_partition_detected_datatype_in_text_changed = UNSET
        else:
            monthly_partition_detected_datatype_in_text_changed = (
                ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec.from_dict(
                    _monthly_partition_detected_datatype_in_text_changed
                )
            )

        column_datatype_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_detected_datatype_in_text=monthly_partition_detected_datatype_in_text,
            monthly_partition_detected_datatype_in_text_changed=monthly_partition_detected_datatype_in_text_changed,
        )

        column_datatype_monthly_partitioned_checks_spec.additional_properties = d
        return column_datatype_monthly_partitioned_checks_spec

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
