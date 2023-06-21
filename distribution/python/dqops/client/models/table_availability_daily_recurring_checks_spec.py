from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_availability_check_spec import TableAvailabilityCheckSpec


T = TypeVar("T", bound="TableAvailabilityDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableAvailabilityDailyRecurringChecksSpec:
    """
    Attributes:
        daily_table_availability (Union[Unset, TableAvailabilityCheckSpec]):
    """

    daily_table_availability: Union[Unset, "TableAvailabilityCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_table_availability: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_table_availability, Unset):
            daily_table_availability = self.daily_table_availability.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_table_availability is not UNSET:
            field_dict["daily_table_availability"] = daily_table_availability

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_availability_check_spec import TableAvailabilityCheckSpec

        d = src_dict.copy()
        _daily_table_availability = d.pop("daily_table_availability", UNSET)
        daily_table_availability: Union[Unset, TableAvailabilityCheckSpec]
        if isinstance(_daily_table_availability, Unset):
            daily_table_availability = UNSET
        else:
            daily_table_availability = TableAvailabilityCheckSpec.from_dict(
                _daily_table_availability
            )

        table_availability_daily_recurring_checks_spec = cls(
            daily_table_availability=daily_table_availability,
        )

        table_availability_daily_recurring_checks_spec.additional_properties = d
        return table_availability_daily_recurring_checks_spec

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
