from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_spec import ColumnSpec





T = TypeVar("T", bound="TableSpecColumns")


@attr.s(auto_attribs=True)
class TableSpecColumns:
    """ Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data
    type and a list of column level data quality checks that are enabled for a column.

     """

    additional_properties: Dict[str, 'ColumnSpec'] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_spec import ColumnSpec
        
        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({
        })

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_spec import ColumnSpec
        d = src_dict.copy()
        table_spec_columns = cls(
        )


        from ..models.column_partitioned_checks_root_spec import ColumnPartitionedChecksRootSpec
        from ..models.column_statistics_collectors_root_categories_spec import ColumnStatisticsCollectorsRootCategoriesSpec
        from ..models.column_recurring_checks_root_spec import ColumnRecurringChecksRootSpec
        from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
        from ..models.column_profiling_check_categories_spec import ColumnProfilingCheckCategoriesSpec
        from ..models.comment_spec import CommentSpec
        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = ColumnSpec.from_dict(prop_dict)



            additional_properties[prop_name] = additional_property

        table_spec_columns.additional_properties = additional_properties
        return table_spec_columns

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> 'ColumnSpec':
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: 'ColumnSpec') -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
