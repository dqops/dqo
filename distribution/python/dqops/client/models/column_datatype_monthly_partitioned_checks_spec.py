from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_datatype_date_match_format_percent_check_spec import (
        ColumnDatatypeDateMatchFormatPercentCheckSpec,
    )
    from ..models.column_datatype_string_datatype_changed_check_spec import (
        ColumnDatatypeStringDatatypeChangedCheckSpec,
    )


T = TypeVar("T", bound="ColumnDatatypeMonthlyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnDatatypeMonthlyPartitionedChecksSpec:
    """
    Attributes:
        monthly_partition_date_match_format_percent (Union[Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec]):
        monthly_partition_string_datatype_changed (Union[Unset, ColumnDatatypeStringDatatypeChangedCheckSpec]):
    """

    monthly_partition_date_match_format_percent: Union[
        Unset, "ColumnDatatypeDateMatchFormatPercentCheckSpec"
    ] = UNSET
    monthly_partition_string_datatype_changed: Union[
        Unset, "ColumnDatatypeStringDatatypeChangedCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_partition_date_match_format_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_date_match_format_percent, Unset):
            monthly_partition_date_match_format_percent = (
                self.monthly_partition_date_match_format_percent.to_dict()
            )

        monthly_partition_string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_string_datatype_changed, Unset):
            monthly_partition_string_datatype_changed = (
                self.monthly_partition_string_datatype_changed.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_partition_date_match_format_percent is not UNSET:
            field_dict[
                "monthly_partition_date_match_format_percent"
            ] = monthly_partition_date_match_format_percent
        if monthly_partition_string_datatype_changed is not UNSET:
            field_dict[
                "monthly_partition_string_datatype_changed"
            ] = monthly_partition_string_datatype_changed

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datatype_date_match_format_percent_check_spec import (
            ColumnDatatypeDateMatchFormatPercentCheckSpec,
        )
        from ..models.column_datatype_string_datatype_changed_check_spec import (
            ColumnDatatypeStringDatatypeChangedCheckSpec,
        )

        d = src_dict.copy()
        _monthly_partition_date_match_format_percent = d.pop(
            "monthly_partition_date_match_format_percent", UNSET
        )
        monthly_partition_date_match_format_percent: Union[
            Unset, ColumnDatatypeDateMatchFormatPercentCheckSpec
        ]
        if isinstance(_monthly_partition_date_match_format_percent, Unset):
            monthly_partition_date_match_format_percent = UNSET
        else:
            monthly_partition_date_match_format_percent = (
                ColumnDatatypeDateMatchFormatPercentCheckSpec.from_dict(
                    _monthly_partition_date_match_format_percent
                )
            )

        _monthly_partition_string_datatype_changed = d.pop(
            "monthly_partition_string_datatype_changed", UNSET
        )
        monthly_partition_string_datatype_changed: Union[
            Unset, ColumnDatatypeStringDatatypeChangedCheckSpec
        ]
        if isinstance(_monthly_partition_string_datatype_changed, Unset):
            monthly_partition_string_datatype_changed = UNSET
        else:
            monthly_partition_string_datatype_changed = (
                ColumnDatatypeStringDatatypeChangedCheckSpec.from_dict(
                    _monthly_partition_string_datatype_changed
                )
            )

        column_datatype_monthly_partitioned_checks_spec = cls(
            monthly_partition_date_match_format_percent=monthly_partition_date_match_format_percent,
            monthly_partition_string_datatype_changed=monthly_partition_string_datatype_changed,
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
