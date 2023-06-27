from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
    from ..models.column_distinct_percent_check_spec import (
        ColumnDistinctPercentCheckSpec,
    )
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnUniquenessMonthlyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessMonthlyPartitionedChecksSpec:
    """
    Attributes:
        monthly_partition_distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
        monthly_partition_distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
        monthly_partition_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        monthly_partition_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    monthly_partition_distinct_count: Union[
        Unset, "ColumnDistinctCountCheckSpec"
    ] = UNSET
    monthly_partition_distinct_percent: Union[
        Unset, "ColumnDistinctPercentCheckSpec"
    ] = UNSET
    monthly_partition_duplicate_count: Union[
        Unset, "ColumnDuplicateCountCheckSpec"
    ] = UNSET
    monthly_partition_duplicate_percent: Union[
        Unset, "ColumnDuplicatePercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_partition_distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_distinct_count, Unset):
            monthly_partition_distinct_count = (
                self.monthly_partition_distinct_count.to_dict()
            )

        monthly_partition_distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_distinct_percent, Unset):
            monthly_partition_distinct_percent = (
                self.monthly_partition_distinct_percent.to_dict()
            )

        monthly_partition_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_duplicate_count, Unset):
            monthly_partition_duplicate_count = (
                self.monthly_partition_duplicate_count.to_dict()
            )

        monthly_partition_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_duplicate_percent, Unset):
            monthly_partition_duplicate_percent = (
                self.monthly_partition_duplicate_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_partition_distinct_count is not UNSET:
            field_dict[
                "monthly_partition_distinct_count"
            ] = monthly_partition_distinct_count
        if monthly_partition_distinct_percent is not UNSET:
            field_dict[
                "monthly_partition_distinct_percent"
            ] = monthly_partition_distinct_percent
        if monthly_partition_duplicate_count is not UNSET:
            field_dict[
                "monthly_partition_duplicate_count"
            ] = monthly_partition_duplicate_count
        if monthly_partition_duplicate_percent is not UNSET:
            field_dict[
                "monthly_partition_duplicate_percent"
            ] = monthly_partition_duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_distinct_count_check_spec import (
            ColumnDistinctCountCheckSpec,
        )
        from ..models.column_distinct_percent_check_spec import (
            ColumnDistinctPercentCheckSpec,
        )
        from ..models.column_duplicate_count_check_spec import (
            ColumnDuplicateCountCheckSpec,
        )
        from ..models.column_duplicate_percent_check_spec import (
            ColumnDuplicatePercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_partition_distinct_count = d.pop(
            "monthly_partition_distinct_count", UNSET
        )
        monthly_partition_distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_monthly_partition_distinct_count, Unset):
            monthly_partition_distinct_count = UNSET
        else:
            monthly_partition_distinct_count = ColumnDistinctCountCheckSpec.from_dict(
                _monthly_partition_distinct_count
            )

        _monthly_partition_distinct_percent = d.pop(
            "monthly_partition_distinct_percent", UNSET
        )
        monthly_partition_distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_monthly_partition_distinct_percent, Unset):
            monthly_partition_distinct_percent = UNSET
        else:
            monthly_partition_distinct_percent = (
                ColumnDistinctPercentCheckSpec.from_dict(
                    _monthly_partition_distinct_percent
                )
            )

        _monthly_partition_duplicate_count = d.pop(
            "monthly_partition_duplicate_count", UNSET
        )
        monthly_partition_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_monthly_partition_duplicate_count, Unset):
            monthly_partition_duplicate_count = UNSET
        else:
            monthly_partition_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _monthly_partition_duplicate_count
            )

        _monthly_partition_duplicate_percent = d.pop(
            "monthly_partition_duplicate_percent", UNSET
        )
        monthly_partition_duplicate_percent: Union[
            Unset, ColumnDuplicatePercentCheckSpec
        ]
        if isinstance(_monthly_partition_duplicate_percent, Unset):
            monthly_partition_duplicate_percent = UNSET
        else:
            monthly_partition_duplicate_percent = (
                ColumnDuplicatePercentCheckSpec.from_dict(
                    _monthly_partition_duplicate_percent
                )
            )

        column_uniqueness_monthly_partitioned_checks_spec = cls(
            monthly_partition_distinct_count=monthly_partition_distinct_count,
            monthly_partition_distinct_percent=monthly_partition_distinct_percent,
            monthly_partition_duplicate_count=monthly_partition_duplicate_count,
            monthly_partition_duplicate_percent=monthly_partition_duplicate_percent,
        )

        column_uniqueness_monthly_partitioned_checks_spec.additional_properties = d
        return column_uniqueness_monthly_partitioned_checks_spec

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
