from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
    from ..models.column_duplicate_percent_check_spec import (
        ColumnDuplicatePercentCheckSpec,
    )
    from ..models.column_unique_count_check_spec import ColumnUniqueCountCheckSpec
    from ..models.column_unique_percent_check_spec import ColumnUniquePercentCheckSpec


T = TypeVar("T", bound="ColumnUniquenessMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_unique_count (Union[Unset, ColumnUniqueCountCheckSpec]):
        monthly_unique_percent (Union[Unset, ColumnUniquePercentCheckSpec]):
        monthly_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        monthly_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    monthly_unique_count: Union[Unset, "ColumnUniqueCountCheckSpec"] = UNSET
    monthly_unique_percent: Union[Unset, "ColumnUniquePercentCheckSpec"] = UNSET
    monthly_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    monthly_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_unique_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_unique_count, Unset):
            monthly_unique_count = self.monthly_unique_count.to_dict()

        monthly_unique_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_unique_percent, Unset):
            monthly_unique_percent = self.monthly_unique_percent.to_dict()

        monthly_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_duplicate_count, Unset):
            monthly_duplicate_count = self.monthly_duplicate_count.to_dict()

        monthly_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_duplicate_percent, Unset):
            monthly_duplicate_percent = self.monthly_duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_unique_count is not UNSET:
            field_dict["monthly_unique_count"] = monthly_unique_count
        if monthly_unique_percent is not UNSET:
            field_dict["monthly_unique_percent"] = monthly_unique_percent
        if monthly_duplicate_count is not UNSET:
            field_dict["monthly_duplicate_count"] = monthly_duplicate_count
        if monthly_duplicate_percent is not UNSET:
            field_dict["monthly_duplicate_percent"] = monthly_duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_duplicate_count_check_spec import (
            ColumnDuplicateCountCheckSpec,
        )
        from ..models.column_duplicate_percent_check_spec import (
            ColumnDuplicatePercentCheckSpec,
        )
        from ..models.column_unique_count_check_spec import ColumnUniqueCountCheckSpec
        from ..models.column_unique_percent_check_spec import (
            ColumnUniquePercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_unique_count = d.pop("monthly_unique_count", UNSET)
        monthly_unique_count: Union[Unset, ColumnUniqueCountCheckSpec]
        if isinstance(_monthly_unique_count, Unset):
            monthly_unique_count = UNSET
        else:
            monthly_unique_count = ColumnUniqueCountCheckSpec.from_dict(
                _monthly_unique_count
            )

        _monthly_unique_percent = d.pop("monthly_unique_percent", UNSET)
        monthly_unique_percent: Union[Unset, ColumnUniquePercentCheckSpec]
        if isinstance(_monthly_unique_percent, Unset):
            monthly_unique_percent = UNSET
        else:
            monthly_unique_percent = ColumnUniquePercentCheckSpec.from_dict(
                _monthly_unique_percent
            )

        _monthly_duplicate_count = d.pop("monthly_duplicate_count", UNSET)
        monthly_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_monthly_duplicate_count, Unset):
            monthly_duplicate_count = UNSET
        else:
            monthly_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _monthly_duplicate_count
            )

        _monthly_duplicate_percent = d.pop("monthly_duplicate_percent", UNSET)
        monthly_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_monthly_duplicate_percent, Unset):
            monthly_duplicate_percent = UNSET
        else:
            monthly_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _monthly_duplicate_percent
            )

        column_uniqueness_monthly_recurring_checks_spec = cls(
            monthly_unique_count=monthly_unique_count,
            monthly_unique_percent=monthly_unique_percent,
            monthly_duplicate_count=monthly_duplicate_count,
            monthly_duplicate_percent=monthly_duplicate_percent,
        )

        column_uniqueness_monthly_recurring_checks_spec.additional_properties = d
        return column_uniqueness_monthly_recurring_checks_spec

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
