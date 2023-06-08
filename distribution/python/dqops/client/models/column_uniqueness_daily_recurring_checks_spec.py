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


T = TypeVar("T", bound="ColumnUniquenessDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessDailyRecurringChecksSpec:
    """
    Attributes:
        daily_unique_count (Union[Unset, ColumnUniqueCountCheckSpec]):
        daily_unique_percent (Union[Unset, ColumnUniquePercentCheckSpec]):
        daily_duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
        daily_duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
    """

    daily_unique_count: Union[Unset, "ColumnUniqueCountCheckSpec"] = UNSET
    daily_unique_percent: Union[Unset, "ColumnUniquePercentCheckSpec"] = UNSET
    daily_duplicate_count: Union[Unset, "ColumnDuplicateCountCheckSpec"] = UNSET
    daily_duplicate_percent: Union[Unset, "ColumnDuplicatePercentCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_unique_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_unique_count, Unset):
            daily_unique_count = self.daily_unique_count.to_dict()

        daily_unique_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_unique_percent, Unset):
            daily_unique_percent = self.daily_unique_percent.to_dict()

        daily_duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_duplicate_count, Unset):
            daily_duplicate_count = self.daily_duplicate_count.to_dict()

        daily_duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_duplicate_percent, Unset):
            daily_duplicate_percent = self.daily_duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_unique_count is not UNSET:
            field_dict["daily_unique_count"] = daily_unique_count
        if daily_unique_percent is not UNSET:
            field_dict["daily_unique_percent"] = daily_unique_percent
        if daily_duplicate_count is not UNSET:
            field_dict["daily_duplicate_count"] = daily_duplicate_count
        if daily_duplicate_percent is not UNSET:
            field_dict["daily_duplicate_percent"] = daily_duplicate_percent

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
        _daily_unique_count = d.pop("daily_unique_count", UNSET)
        daily_unique_count: Union[Unset, ColumnUniqueCountCheckSpec]
        if isinstance(_daily_unique_count, Unset):
            daily_unique_count = UNSET
        else:
            daily_unique_count = ColumnUniqueCountCheckSpec.from_dict(
                _daily_unique_count
            )

        _daily_unique_percent = d.pop("daily_unique_percent", UNSET)
        daily_unique_percent: Union[Unset, ColumnUniquePercentCheckSpec]
        if isinstance(_daily_unique_percent, Unset):
            daily_unique_percent = UNSET
        else:
            daily_unique_percent = ColumnUniquePercentCheckSpec.from_dict(
                _daily_unique_percent
            )

        _daily_duplicate_count = d.pop("daily_duplicate_count", UNSET)
        daily_duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_daily_duplicate_count, Unset):
            daily_duplicate_count = UNSET
        else:
            daily_duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(
                _daily_duplicate_count
            )

        _daily_duplicate_percent = d.pop("daily_duplicate_percent", UNSET)
        daily_duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_daily_duplicate_percent, Unset):
            daily_duplicate_percent = UNSET
        else:
            daily_duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(
                _daily_duplicate_percent
            )

        column_uniqueness_daily_recurring_checks_spec = cls(
            daily_unique_count=daily_unique_count,
            daily_unique_percent=daily_unique_percent,
            daily_duplicate_count=daily_duplicate_count,
            daily_duplicate_percent=daily_duplicate_percent,
        )

        column_uniqueness_daily_recurring_checks_spec.additional_properties = d
        return column_uniqueness_daily_recurring_checks_spec

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
