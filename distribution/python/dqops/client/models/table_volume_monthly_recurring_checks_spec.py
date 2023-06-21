from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_change_row_count_check_spec import TableChangeRowCountCheckSpec
    from ..models.table_row_count_check_spec import TableRowCountCheckSpec


T = TypeVar("T", bound="TableVolumeMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableVolumeMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_row_count (Union[Unset, TableRowCountCheckSpec]):
        monthly_row_count_change (Union[Unset, TableChangeRowCountCheckSpec]):
    """

    monthly_row_count: Union[Unset, "TableRowCountCheckSpec"] = UNSET
    monthly_row_count_change: Union[Unset, "TableChangeRowCountCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_row_count, Unset):
            monthly_row_count = self.monthly_row_count.to_dict()

        monthly_row_count_change: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_row_count_change, Unset):
            monthly_row_count_change = self.monthly_row_count_change.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_row_count is not UNSET:
            field_dict["monthly_row_count"] = monthly_row_count
        if monthly_row_count_change is not UNSET:
            field_dict["monthly_row_count_change"] = monthly_row_count_change

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_change_row_count_check_spec import (
            TableChangeRowCountCheckSpec,
        )
        from ..models.table_row_count_check_spec import TableRowCountCheckSpec

        d = src_dict.copy()
        _monthly_row_count = d.pop("monthly_row_count", UNSET)
        monthly_row_count: Union[Unset, TableRowCountCheckSpec]
        if isinstance(_monthly_row_count, Unset):
            monthly_row_count = UNSET
        else:
            monthly_row_count = TableRowCountCheckSpec.from_dict(_monthly_row_count)

        _monthly_row_count_change = d.pop("monthly_row_count_change", UNSET)
        monthly_row_count_change: Union[Unset, TableChangeRowCountCheckSpec]
        if isinstance(_monthly_row_count_change, Unset):
            monthly_row_count_change = UNSET
        else:
            monthly_row_count_change = TableChangeRowCountCheckSpec.from_dict(
                _monthly_row_count_change
            )

        table_volume_monthly_recurring_checks_spec = cls(
            monthly_row_count=monthly_row_count,
            monthly_row_count_change=monthly_row_count_change,
        )

        table_volume_monthly_recurring_checks_spec.additional_properties = d
        return table_volume_monthly_recurring_checks_spec

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
