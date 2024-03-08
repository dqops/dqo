from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_daily_monitoring_check_categories_spec import (
        ColumnDailyMonitoringCheckCategoriesSpec,
    )
    from ..models.column_monthly_monitoring_check_categories_spec import (
        ColumnMonthlyMonitoringCheckCategoriesSpec,
    )


T = TypeVar("T", bound="ColumnMonitoringCheckCategoriesSpec")


@_attrs_define
class ColumnMonitoringCheckCategoriesSpec:
    """
    Attributes:
        daily (Union[Unset, ColumnDailyMonitoringCheckCategoriesSpec]):
        monthly (Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpec]):
    """

    daily: Union[Unset, "ColumnDailyMonitoringCheckCategoriesSpec"] = UNSET
    monthly: Union[Unset, "ColumnMonthlyMonitoringCheckCategoriesSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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
        from ..models.column_daily_monitoring_check_categories_spec import (
            ColumnDailyMonitoringCheckCategoriesSpec,
        )
        from ..models.column_monthly_monitoring_check_categories_spec import (
            ColumnMonthlyMonitoringCheckCategoriesSpec,
        )

        d = src_dict.copy()
        _daily = d.pop("daily", UNSET)
        daily: Union[Unset, ColumnDailyMonitoringCheckCategoriesSpec]
        if isinstance(_daily, Unset):
            daily = UNSET
        else:
            daily = ColumnDailyMonitoringCheckCategoriesSpec.from_dict(_daily)

        _monthly = d.pop("monthly", UNSET)
        monthly: Union[Unset, ColumnMonthlyMonitoringCheckCategoriesSpec]
        if isinstance(_monthly, Unset):
            monthly = UNSET
        else:
            monthly = ColumnMonthlyMonitoringCheckCategoriesSpec.from_dict(_monthly)

        column_monitoring_check_categories_spec = cls(
            daily=daily,
            monthly=monthly,
        )

        column_monitoring_check_categories_spec.additional_properties = d
        return column_monitoring_check_categories_spec

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
