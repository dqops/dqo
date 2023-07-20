from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
    from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec


T = TypeVar("T", bound="ColumnBoolMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnBoolMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_true_percent (Union[Unset, ColumnTruePercentCheckSpec]):
        monthly_false_percent (Union[Unset, ColumnFalsePercentCheckSpec]):
    """

    monthly_true_percent: Union[Unset, "ColumnTruePercentCheckSpec"] = UNSET
    monthly_false_percent: Union[Unset, "ColumnFalsePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_true_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_true_percent, Unset):
            monthly_true_percent = self.monthly_true_percent.to_dict()

        monthly_false_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_false_percent, Unset):
            monthly_false_percent = self.monthly_false_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_true_percent is not UNSET:
            field_dict["monthly_true_percent"] = monthly_true_percent
        if monthly_false_percent is not UNSET:
            field_dict["monthly_false_percent"] = monthly_false_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_false_percent_check_spec import ColumnFalsePercentCheckSpec
        from ..models.column_true_percent_check_spec import ColumnTruePercentCheckSpec

        d = src_dict.copy()
        _monthly_true_percent = d.pop("monthly_true_percent", UNSET)
        monthly_true_percent: Union[Unset, ColumnTruePercentCheckSpec]
        if isinstance(_monthly_true_percent, Unset):
            monthly_true_percent = UNSET
        else:
            monthly_true_percent = ColumnTruePercentCheckSpec.from_dict(
                _monthly_true_percent
            )

        _monthly_false_percent = d.pop("monthly_false_percent", UNSET)
        monthly_false_percent: Union[Unset, ColumnFalsePercentCheckSpec]
        if isinstance(_monthly_false_percent, Unset):
            monthly_false_percent = UNSET
        else:
            monthly_false_percent = ColumnFalsePercentCheckSpec.from_dict(
                _monthly_false_percent
            )

        column_bool_monthly_recurring_checks_spec = cls(
            monthly_true_percent=monthly_true_percent,
            monthly_false_percent=monthly_false_percent,
        )

        column_bool_monthly_recurring_checks_spec.additional_properties = d
        return column_bool_monthly_recurring_checks_spec

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
