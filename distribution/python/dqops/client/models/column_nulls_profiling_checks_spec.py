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





T = TypeVar("T", bound="ColumnNullsProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnNullsProfilingChecksSpec:
    """ 
        Attributes:
            nulls_count (Union[Unset, ColumnNullsCountCheckSpec]):
            nulls_percent (Union[Unset, ColumnNullsPercentCheckSpec]):
            not_nulls_count (Union[Unset, ColumnNotNullsCountCheckSpec]):
            not_nulls_percent (Union[Unset, ColumnNotNullsPercentCheckSpec]):
     """

    nulls_count: Union[Unset, 'ColumnNullsCountCheckSpec'] = UNSET
    nulls_percent: Union[Unset, 'ColumnNullsPercentCheckSpec'] = UNSET
    not_nulls_count: Union[Unset, 'ColumnNotNullsCountCheckSpec'] = UNSET
    not_nulls_percent: Union[Unset, 'ColumnNotNullsPercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
        from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
        nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_count, Unset):
            nulls_count = self.nulls_count.to_dict()

        nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent, Unset):
            nulls_percent = self.nulls_percent.to_dict()

        not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_count, Unset):
            not_nulls_count = self.not_nulls_count.to_dict()

        not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_percent, Unset):
            not_nulls_percent = self.not_nulls_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if nulls_count is not UNSET:
            field_dict["nulls_count"] = nulls_count
        if nulls_percent is not UNSET:
            field_dict["nulls_percent"] = nulls_percent
        if not_nulls_count is not UNSET:
            field_dict["not_nulls_count"] = not_nulls_count
        if not_nulls_percent is not UNSET:
            field_dict["not_nulls_percent"] = not_nulls_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_nulls_percent_check_spec import ColumnNullsPercentCheckSpec
        from ..models.column_nulls_count_check_spec import ColumnNullsCountCheckSpec
        from ..models.column_not_nulls_percent_check_spec import ColumnNotNullsPercentCheckSpec
        from ..models.column_not_nulls_count_check_spec import ColumnNotNullsCountCheckSpec
        d = src_dict.copy()
        _nulls_count = d.pop("nulls_count", UNSET)
        nulls_count: Union[Unset, ColumnNullsCountCheckSpec]
        if isinstance(_nulls_count,  Unset):
            nulls_count = UNSET
        else:
            nulls_count = ColumnNullsCountCheckSpec.from_dict(_nulls_count)




        _nulls_percent = d.pop("nulls_percent", UNSET)
        nulls_percent: Union[Unset, ColumnNullsPercentCheckSpec]
        if isinstance(_nulls_percent,  Unset):
            nulls_percent = UNSET
        else:
            nulls_percent = ColumnNullsPercentCheckSpec.from_dict(_nulls_percent)




        _not_nulls_count = d.pop("not_nulls_count", UNSET)
        not_nulls_count: Union[Unset, ColumnNotNullsCountCheckSpec]
        if isinstance(_not_nulls_count,  Unset):
            not_nulls_count = UNSET
        else:
            not_nulls_count = ColumnNotNullsCountCheckSpec.from_dict(_not_nulls_count)




        _not_nulls_percent = d.pop("not_nulls_percent", UNSET)
        not_nulls_percent: Union[Unset, ColumnNotNullsPercentCheckSpec]
        if isinstance(_not_nulls_percent,  Unset):
            not_nulls_percent = UNSET
        else:
            not_nulls_percent = ColumnNotNullsPercentCheckSpec.from_dict(_not_nulls_percent)




        column_nulls_profiling_checks_spec = cls(
            nulls_count=nulls_count,
            nulls_percent=nulls_percent,
            not_nulls_count=not_nulls_count,
            not_nulls_percent=not_nulls_percent,
        )

        column_nulls_profiling_checks_spec.additional_properties = d
        return column_nulls_profiling_checks_spec

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
