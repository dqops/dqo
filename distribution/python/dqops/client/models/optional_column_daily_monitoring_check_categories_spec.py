from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="OptionalColumnDailyMonitoringCheckCategoriesSpec")


@attr.s(auto_attribs=True)
class OptionalColumnDailyMonitoringCheckCategoriesSpec:
    """
    Attributes:
        empty (Union[Unset, bool]):
        present (Union[Unset, bool]):
    """

    empty: Union[Unset, bool] = UNSET
    present: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        empty = self.empty
        present = self.present

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if empty is not UNSET:
            field_dict["empty"] = empty
        if present is not UNSET:
            field_dict["present"] = present

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        empty = d.pop("empty", UNSET)

        present = d.pop("present", UNSET)

        optional_column_daily_monitoring_check_categories_spec = cls(
            empty=empty,
            present=present,
        )

        optional_column_daily_monitoring_check_categories_spec.additional_properties = d
        return optional_column_daily_monitoring_check_categories_spec

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