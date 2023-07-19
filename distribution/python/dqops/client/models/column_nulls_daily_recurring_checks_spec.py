from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
  from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
  from ..models.column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
  from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec





T = TypeVar("T", bound="ColumnNullsDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnNullsDailyRecurringChecksSpec:
    """ 
        Attributes:
            daily_nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
            daily_nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
            daily_not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
            daily_not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
     """

    daily_nulls_count: Union[Unset, 'ColumnNullsCountCheckSpec'] = UNSET
    daily_nulls_percent: Union[Unset, 'ColumnNullsPercentCheckSpec'] = UNSET
    daily_not_nulls_count: Union[Unset, 'ColumnNotNullsCountCheckSpec'] = UNSET
    daily_not_nulls_percent: Union[Unset, 'ColumnNotNullsPercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
        from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
        daily_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_count, Unset):
            daily_nulls_count = self.daily_nulls_count.to_dict()

        daily_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_nulls_percent, Unset):
            daily_nulls_percent = self.daily_nulls_percent.to_dict()

        daily_not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_nulls_count, Unset):
            daily_not_nulls_count = self.daily_not_nulls_count.to_dict()

        daily_not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_not_nulls_percent, Unset):
            daily_not_nulls_percent = self.daily_not_nulls_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if daily_nulls_count is not UNSET:
            field_dict["daily_nulls_count"] = daily_nulls_count
        if daily_nulls_percent is not UNSET:
            field_dict["daily_nulls_percent"] = daily_nulls_percent
        if daily_not_nulls_count is not UNSET:
            field_dict["daily_not_nulls_count"] = daily_not_nulls_count
        if daily_not_nulls_percent is not UNSET:
            field_dict["daily_not_nulls_percent"] = daily_not_nulls_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
        from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
        d = src_dict.copy()
        _daily_nulls_count = d.pop("daily_nulls_count", UNSET)
        daily_nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_daily_nulls_count,  Unset):
            daily_nulls_count = UNSET
        else:
            daily_nulls_count = ColumnNullsCountCheckSpec.from_dict(_daily_nulls_count)




        _daily_nulls_percent = d.pop("daily_nulls_percent", UNSET)
        daily_nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_daily_nulls_percent,  Unset):
            daily_nulls_percent = UNSET
        else:
            daily_nulls_percent = ColumnNullsPercentCheckSpec.from_dict(_daily_nulls_percent)




        _daily_not_nulls_count = d.pop("daily_not_nulls_count", UNSET)
        daily_not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_daily_not_nulls_count,  Unset):
            daily_not_nulls_count = UNSET
        else:
            daily_not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(_daily_not_nulls_count)




        _daily_not_nulls_percent = d.pop("daily_not_nulls_percent", UNSET)
        daily_not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_daily_not_nulls_percent,  Unset):
            daily_not_nulls_percent = UNSET
        else:
            daily_not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(_daily_not_nulls_percent)




        column_nulls_daily_recurring_checks_spec = cls(
            daily_nulls_count=daily_nulls_count,
            daily_nulls_percent=daily_nulls_percent,
            daily_not_nulls_count=daily_not_nulls_count,
            daily_not_nulls_percent=daily_not_nulls_percent,
        )

        column_nulls_daily_recurring_checks_spec.additional_properties = d
        return column_nulls_daily_recurring_checks_spec

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
