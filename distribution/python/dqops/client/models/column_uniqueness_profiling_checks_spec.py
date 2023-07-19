from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
  from ..models.column_distinct_percent_check_spec import ColumnDistinctPercentCheckSpec
  from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
  from ..models.column_duplicate_percent_check_spec import ColumnDuplicatePercentCheckSpec





T = TypeVar("T", bound="ColumnUniquenessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessProfilingChecksSpec:
    """ 
        Attributes:
            distinct_count (Union[Unset, ColumnDistinctCountCheckSpec]):
            distinct_percent (Union[Unset, ColumnDistinctPercentCheckSpec]):
            duplicate_count (Union[Unset, ColumnDuplicateCountCheckSpec]):
            duplicate_percent (Union[Unset, ColumnDuplicatePercentCheckSpec]):
     """

    distinct_count: Union[Unset, 'ColumnDistinctCountCheckSpec'] = UNSET
    distinct_percent: Union[Unset, 'ColumnDistinctPercentCheckSpec'] = UNSET
    duplicate_count: Union[Unset, 'ColumnDuplicateCountCheckSpec'] = UNSET
    duplicate_percent: Union[Unset, 'ColumnDuplicatePercentCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
        from ..models.column_distinct_percent_check_spec import ColumnDistinctPercentCheckSpec
        from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
        from ..models.column_duplicate_percent_check_spec import ColumnDuplicatePercentCheckSpec
        distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.distinct_count, Unset):
            distinct_count = self.distinct_count.to_dict()

        distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.distinct_percent, Unset):
            distinct_percent = self.distinct_percent.to_dict()

        duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_count, Unset):
            duplicate_count = self.duplicate_count.to_dict()

        duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_percent, Unset):
            duplicate_percent = self.duplicate_percent.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if distinct_count is not UNSET:
            field_dict["distinct_count"] = distinct_count
        if distinct_percent is not UNSET:
            field_dict["distinct_percent"] = distinct_percent
        if duplicate_count is not UNSET:
            field_dict["duplicate_count"] = duplicate_count
        if duplicate_percent is not UNSET:
            field_dict["duplicate_percent"] = duplicate_percent

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_distinct_count_check_spec import ColumnDistinctCountCheckSpec
        from ..models.column_distinct_percent_check_spec import ColumnDistinctPercentCheckSpec
        from ..models.column_duplicate_count_check_spec import ColumnDuplicateCountCheckSpec
        from ..models.column_duplicate_percent_check_spec import ColumnDuplicatePercentCheckSpec
        d = src_dict.copy()
        _distinct_count = d.pop("distinct_count", UNSET)
        distinct_count: Union[Unset, ColumnDistinctCountCheckSpec]
        if isinstance(_distinct_count,  Unset):
            distinct_count = UNSET
        else:
            distinct_count = ColumnDistinctCountCheckSpec.from_dict(_distinct_count)




        _distinct_percent = d.pop("distinct_percent", UNSET)
        distinct_percent: Union[Unset, ColumnDistinctPercentCheckSpec]
        if isinstance(_distinct_percent,  Unset):
            distinct_percent = UNSET
        else:
            distinct_percent = ColumnDistinctPercentCheckSpec.from_dict(_distinct_percent)




        _duplicate_count = d.pop("duplicate_count", UNSET)
        duplicate_count: Union[Unset, ColumnDuplicateCountCheckSpec]
        if isinstance(_duplicate_count,  Unset):
            duplicate_count = UNSET
        else:
            duplicate_count = ColumnDuplicateCountCheckSpec.from_dict(_duplicate_count)




        _duplicate_percent = d.pop("duplicate_percent", UNSET)
        duplicate_percent: Union[Unset, ColumnDuplicatePercentCheckSpec]
        if isinstance(_duplicate_percent,  Unset):
            duplicate_percent = UNSET
        else:
            duplicate_percent = ColumnDuplicatePercentCheckSpec.from_dict(_duplicate_percent)




        column_uniqueness_profiling_checks_spec = cls(
            distinct_count=distinct_count,
            distinct_percent=distinct_percent,
            duplicate_count=duplicate_count,
            duplicate_percent=duplicate_percent,
        )

        column_uniqueness_profiling_checks_spec.additional_properties = d
        return column_uniqueness_profiling_checks_spec

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
