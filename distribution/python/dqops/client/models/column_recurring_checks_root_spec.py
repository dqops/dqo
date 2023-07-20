from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_daily_recurring_check_categories_spec import (
        ColumnDailyRecurringCheckCategoriesSpec,
    )
    from ..models.column_monthly_recurring_check_categories_spec import (
        ColumnMonthlyRecurringCheckCategoriesSpec,
    )


T = TypeVar("T", bound="ColumnRecurringChecksRootSpec")


@attr.s(auto_attribs=True)
class ColumnRecurringChecksRootSpec:
    """
    Attributes:
        daily (Union[Unset, ColumnDailyRecurringCheckCategoriesSpec]):
        monthly (Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpec]):
    """

    daily: Union[Unset, "ColumnDailyRecurringCheckCategoriesSpec"] = UNSET
    monthly: Union[Unset, "ColumnMonthlyRecurringCheckCategoriesSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily, Unset):
            daily = self.daily.to_dict()

        monthly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly, Unset):
            monthly = self.monthly.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily is not UNSET:
            field_dict["daily"] = daily
        if monthly is not UNSET:
            field_dict["monthly"] = monthly

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_daily_recurring_check_categories_spec import (
            ColumnDailyRecurringCheckCategoriesSpec,
        )
        from ..models.column_monthly_recurring_check_categories_spec import (
            ColumnMonthlyRecurringCheckCategoriesSpec,
        )

        d = src_dict.copy()
        _daily = d.pop("daily", UNSET)
        daily: Union[Unset, ColumnDailyRecurringCheckCategoriesSpec]
        if isinstance(_daily, Unset):
            daily = UNSET
        else:
            daily = ColumnDailyRecurringCheckCategoriesSpec.from_dict(_daily)

        _monthly = d.pop("monthly", UNSET)
        monthly: Union[Unset, ColumnMonthlyRecurringCheckCategoriesSpec]
        if isinstance(_monthly, Unset):
            monthly = UNSET
        else:
            monthly = ColumnMonthlyRecurringCheckCategoriesSpec.from_dict(_monthly)

        column_recurring_checks_root_spec = cls(
            daily=daily,
            monthly=monthly,
        )

        column_recurring_checks_root_spec.additional_properties = d
        return column_recurring_checks_root_spec

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
