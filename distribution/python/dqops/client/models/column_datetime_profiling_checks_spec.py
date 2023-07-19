from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_datetime_value_in_range_date_percent_check_spec import ColumnDatetimeValueInRangeDatePercentCheckSpec
  from ..models.column_date_values_in_future_percent_check_spec import ColumnDateValuesInFuturePercentCheckSpec





T = TypeVar("T", bound="ColumnDatetimeProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnDatetimeProfilingChecksSpec:
    """ 
        Attributes:
            date_values_in_future_percent (Union[Unset, ColumnDateValuesInFuturePercentCheckSpec]):
            datetime_value_in_range_date_percent (Union[Unset, ColumnDatetimeValueInRangeDatePercentCheckSpec]):
     """

    date_values_in_future_percent: Union[Unset, 'ColumnDateValuesInFuturePercentCheckSpec'] = UNSET
    datetime_value_in_range_date_percent: Union[Unset, 'ColumnDatetimeValueInRangeDatePercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_datetime_value_in_range_date_percent_check_spec import ColumnDatetimeValueInRangeDatePercentCheckSpec
        from ..models.column_date_values_in_future_percent_check_spec import ColumnDateValuesInFuturePercentCheckSpec
        date_values_in_future_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.date_values_in_future_percent, Unset):
            date_values_in_future_percent = self.date_values_in_future_percent.to_dict()

        datetime_value_in_range_date_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.datetime_value_in_range_date_percent, Unset):
            datetime_value_in_range_date_percent = self.datetime_value_in_range_date_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if date_values_in_future_percent is not UNSET:
            field_dict["date_values_in_future_percent"] = date_values_in_future_percent
        if datetime_value_in_range_date_percent is not UNSET:
            field_dict["datetime_value_in_range_date_percent"] = datetime_value_in_range_date_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_datetime_value_in_range_date_percent_check_spec import ColumnDatetimeValueInRangeDatePercentCheckSpec
        from ..models.column_date_values_in_future_percent_check_spec import ColumnDateValuesInFuturePercentCheckSpec
        d = src_dict.copy()
        _date_values_in_future_percent = d.pop("date_values_in_future_percent", UNSET)
        date_values_in_future_percent: Union[Unset, ColumnDateValuesInFuturePercentCheckSpec]
        if isinstance(_date_values_in_future_percent,  Unset):
            date_values_in_future_percent = UNSET
        else:
            date_values_in_future_percent = ColumnDateValuesInFuturePercentCheckSpec.from_dict(_date_values_in_future_percent)




        _datetime_value_in_range_date_percent = d.pop("datetime_value_in_range_date_percent", UNSET)
        datetime_value_in_range_date_percent: Union[Unset, ColumnDatetimeValueInRangeDatePercentCheckSpec]
        if isinstance(_datetime_value_in_range_date_percent,  Unset):
            datetime_value_in_range_date_percent = UNSET
        else:
            datetime_value_in_range_date_percent = ColumnDatetimeValueInRangeDatePercentCheckSpec.from_dict(_datetime_value_in_range_date_percent)




        column_datetime_profiling_checks_spec = cls(
            date_values_in_future_percent=date_values_in_future_percent,
            datetime_value_in_range_date_percent=datetime_value_in_range_date_percent,
        )

        column_datetime_profiling_checks_spec.additional_properties = d
        return column_datetime_profiling_checks_spec

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
