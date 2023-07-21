from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_integrity_foreign_key_match_percent_check_spec import (
        ColumnIntegrityForeignKeyMatchPercentCheckSpec,
    )
    from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
        ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
    )


T = TypeVar("T", bound="ColumnIntegrityMonthlyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnIntegrityMonthlyPartitionedChecksSpec:
    """
    Attributes:
        monthly_partition_foreign_key_not_match_count (Union[Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec]):
        monthly_partition_foreign_key_match_percent (Union[Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec]):
    """

    monthly_partition_foreign_key_not_match_count: Union[
        Unset, "ColumnIntegrityForeignKeyNotMatchCountCheckSpec"
    ] = UNSET
    monthly_partition_foreign_key_match_percent: Union[
        Unset, "ColumnIntegrityForeignKeyMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_partition_foreign_key_not_match_count: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_foreign_key_not_match_count, Unset):
            monthly_partition_foreign_key_not_match_count = (
                self.monthly_partition_foreign_key_not_match_count.to_dict()
            )

        monthly_partition_foreign_key_match_percent: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_foreign_key_match_percent, Unset):
            monthly_partition_foreign_key_match_percent = (
                self.monthly_partition_foreign_key_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_partition_foreign_key_not_match_count is not UNSET:
            field_dict[
                "monthly_partition_foreign_key_not_match_count"
            ] = monthly_partition_foreign_key_not_match_count
        if monthly_partition_foreign_key_match_percent is not UNSET:
            field_dict[
                "monthly_partition_foreign_key_match_percent"
            ] = monthly_partition_foreign_key_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_integrity_foreign_key_match_percent_check_spec import (
            ColumnIntegrityForeignKeyMatchPercentCheckSpec,
        )
        from ..models.column_integrity_foreign_key_not_match_count_check_spec import (
            ColumnIntegrityForeignKeyNotMatchCountCheckSpec,
        )

        d = src_dict.copy()
        _monthly_partition_foreign_key_not_match_count = d.pop(
            "monthly_partition_foreign_key_not_match_count", UNSET
        )
        monthly_partition_foreign_key_not_match_count: Union[
            Unset, ColumnIntegrityForeignKeyNotMatchCountCheckSpec
        ]
        if isinstance(_monthly_partition_foreign_key_not_match_count, Unset):
            monthly_partition_foreign_key_not_match_count = UNSET
        else:
            monthly_partition_foreign_key_not_match_count = (
                ColumnIntegrityForeignKeyNotMatchCountCheckSpec.from_dict(
                    _monthly_partition_foreign_key_not_match_count
                )
            )

        _monthly_partition_foreign_key_match_percent = d.pop(
            "monthly_partition_foreign_key_match_percent", UNSET
        )
        monthly_partition_foreign_key_match_percent: Union[
            Unset, ColumnIntegrityForeignKeyMatchPercentCheckSpec
        ]
        if isinstance(_monthly_partition_foreign_key_match_percent, Unset):
            monthly_partition_foreign_key_match_percent = UNSET
        else:
            monthly_partition_foreign_key_match_percent = (
                ColumnIntegrityForeignKeyMatchPercentCheckSpec.from_dict(
                    _monthly_partition_foreign_key_match_percent
                )
            )

        column_integrity_monthly_partitioned_checks_spec = cls(
            monthly_partition_foreign_key_not_match_count=monthly_partition_foreign_key_not_match_count,
            monthly_partition_foreign_key_match_percent=monthly_partition_foreign_key_match_percent,
        )

        column_integrity_monthly_partitioned_checks_spec.additional_properties = d
        return column_integrity_monthly_partitioned_checks_spec

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
