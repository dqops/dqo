from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_consistency_date_match_format_percent_check_spec import ColumnConsistencyDateMatchFormatPercentCheckSpec
  from ..models.column_string_datatype_changed_check_spec import ColumnStringDatatypeChangedCheckSpec





T = TypeVar("T", bound="ColumnConsistencyMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnConsistencyMonthlyRecurringChecksSpec:
    """ 
        Attributes:
            monthly_date_match_format_percent (Union[Unset, ColumnConsistencyDateMatchFormatPercentCheckSpec]):
            monthly_string_datatype_changed (Union[Unset, ColumnStringDatatypeChangedCheckSpec]):
     """

    monthly_date_match_format_percent: Union[Unset, 'ColumnConsistencyDateMatchFormatPercentCheckSpec'] = UNSET
    monthly_string_datatype_changed: Union[Unset, 'ColumnStringDatatypeChangedCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_consistency_date_match_format_percent_check_spec import ColumnConsistencyDateMatchFormatPercentCheckSpec
        from ..models.column_string_datatype_changed_check_spec import ColumnStringDatatypeChangedCheckSpec
        monthly_date_match_format_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_date_match_format_percent, Unset):
            monthly_date_match_format_percent = self.monthly_date_match_format_percent.to_dict()

        monthly_string_datatype_changed: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_string_datatype_changed, Unset):
            monthly_string_datatype_changed = self.monthly_string_datatype_changed.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if monthly_date_match_format_percent is not UNSET:
            field_dict["monthly_date_match_format_percent"] = monthly_date_match_format_percent
        if monthly_string_datatype_changed is not UNSET:
            field_dict["monthly_string_datatype_changed"] = monthly_string_datatype_changed

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_consistency_date_match_format_percent_check_spec import ColumnConsistencyDateMatchFormatPercentCheckSpec
        from ..models.column_string_datatype_changed_check_spec import ColumnStringDatatypeChangedCheckSpec
        d = src_dict.copy()
        _monthly_date_match_format_percent = d.pop("monthly_date_match_format_percent", UNSET)
        monthly_date_match_format_percent: Union[Unset, ColumnConsistencyDateMatchFormatPercentCheckSpec]
        if isinstance(_monthly_date_match_format_percent,  Unset):
            monthly_date_match_format_percent = UNSET
        else:
            monthly_date_match_format_percent = ColumnConsistencyDateMatchFormatPercentCheckSpec.from_dict(_monthly_date_match_format_percent)




        _monthly_string_datatype_changed = d.pop("monthly_string_datatype_changed", UNSET)
        monthly_string_datatype_changed: Union[Unset, ColumnStringDatatypeChangedCheckSpec]
        if isinstance(_monthly_string_datatype_changed,  Unset):
            monthly_string_datatype_changed = UNSET
        else:
            monthly_string_datatype_changed = ColumnStringDatatypeChangedCheckSpec.from_dict(_monthly_string_datatype_changed)




        column_consistency_monthly_recurring_checks_spec = cls(
            monthly_date_match_format_percent=monthly_date_match_format_percent,
            monthly_string_datatype_changed=monthly_string_datatype_changed,
        )

        column_consistency_monthly_recurring_checks_spec.additional_properties = d
        return column_consistency_monthly_recurring_checks_spec

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
