from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
        TableAccuracyTotalRowCountMatchPercentCheckSpec,
    )


T = TypeVar("T", bound="TableAccuracyMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableAccuracyMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_total_row_count_match_percent (Union[Unset, TableAccuracyTotalRowCountMatchPercentCheckSpec]):
    """

    monthly_total_row_count_match_percent: Union[
        Unset, "TableAccuracyTotalRowCountMatchPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_total_row_count_match_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_total_row_count_match_percent, Unset):
            monthly_total_row_count_match_percent = (
                self.monthly_total_row_count_match_percent.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_total_row_count_match_percent is not UNSET:
            field_dict[
                "monthly_total_row_count_match_percent"
            ] = monthly_total_row_count_match_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_accuracy_total_row_count_match_percent_check_spec import (
            TableAccuracyTotalRowCountMatchPercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_total_row_count_match_percent = d.pop(
            "monthly_total_row_count_match_percent", UNSET
        )
        monthly_total_row_count_match_percent: Union[
            Unset, TableAccuracyTotalRowCountMatchPercentCheckSpec
        ]
        if isinstance(_monthly_total_row_count_match_percent, Unset):
            monthly_total_row_count_match_percent = UNSET
        else:
            monthly_total_row_count_match_percent = (
                TableAccuracyTotalRowCountMatchPercentCheckSpec.from_dict(
                    _monthly_total_row_count_match_percent
                )
            )

        table_accuracy_monthly_recurring_checks_spec = cls(
            monthly_total_row_count_match_percent=monthly_total_row_count_match_percent,
        )

        table_accuracy_monthly_recurring_checks_spec.additional_properties = d
        return table_accuracy_monthly_recurring_checks_spec

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
