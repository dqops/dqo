from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_daily_partitioned_check_categories_spec import (
        TableDailyPartitionedCheckCategoriesSpec,
    )
    from ..models.table_monthly_partitioned_check_categories_spec import (
        TableMonthlyPartitionedCheckCategoriesSpec,
    )


T = TypeVar("T", bound="TablePartitionedCheckCategoriesSpec")


@_attrs_define
class TablePartitionedCheckCategoriesSpec:
    """
    Attributes:
        daily (Union[Unset, TableDailyPartitionedCheckCategoriesSpec]):
        monthly (Union[Unset, TableMonthlyPartitionedCheckCategoriesSpec]):
    """

    daily: Union[Unset, "TableDailyPartitionedCheckCategoriesSpec"] = UNSET
    monthly: Union[Unset, "TableMonthlyPartitionedCheckCategoriesSpec"] = UNSET
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
        from ..models.table_daily_partitioned_check_categories_spec import (
            TableDailyPartitionedCheckCategoriesSpec,
        )
        from ..models.table_monthly_partitioned_check_categories_spec import (
            TableMonthlyPartitionedCheckCategoriesSpec,
        )

        d = src_dict.copy()
        _daily = d.pop("daily", UNSET)
        daily: Union[Unset, TableDailyPartitionedCheckCategoriesSpec]
        if isinstance(_daily, Unset):
            daily = UNSET
        else:
            daily = TableDailyPartitionedCheckCategoriesSpec.from_dict(_daily)

        _monthly = d.pop("monthly", UNSET)
        monthly: Union[Unset, TableMonthlyPartitionedCheckCategoriesSpec]
        if isinstance(_monthly, Unset):
            monthly = UNSET
        else:
            monthly = TableMonthlyPartitionedCheckCategoriesSpec.from_dict(_monthly)

        table_partitioned_check_categories_spec = cls(
            daily=daily,
            monthly=monthly,
        )

        table_partitioned_check_categories_spec.additional_properties = d
        return table_partitioned_check_categories_spec

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
