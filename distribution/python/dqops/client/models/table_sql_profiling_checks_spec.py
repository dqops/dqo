from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.table_sql_condition_failed_count_check_spec import TableSqlConditionFailedCountCheckSpec
  from ..models.table_sql_condition_passed_percent_check_spec import TableSqlConditionPassedPercentCheckSpec
  from ..models.table_sql_aggregate_expr_check_spec import TableSqlAggregateExprCheckSpec





T = TypeVar("T", bound="TableSqlProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableSqlProfilingChecksSpec:
    """ 
        Attributes:
            sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
            sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
            sql_aggregate_expr_table (Union[Unset, TableSqlAggregateExprCheckSpec]):
     """

    sql_condition_passed_percent_on_table: Union[Unset, 'TableSqlConditionPassedPercentCheckSpec'] = UNSET
    sql_condition_failed_count_on_table: Union[Unset, 'TableSqlConditionFailedCountCheckSpec'] = UNSET
    sql_aggregate_expr_table: Union[Unset, 'TableSqlAggregateExprCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.table_sql_condition_failed_count_check_spec import TableSqlConditionFailedCountCheckSpec
        from ..models.table_sql_condition_passed_percent_check_spec import TableSqlConditionPassedPercentCheckSpec
        from ..models.table_sql_aggregate_expr_check_spec import TableSqlAggregateExprCheckSpec
        sql_condition_passed_percent_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_condition_passed_percent_on_table, Unset):
            sql_condition_passed_percent_on_table = self.sql_condition_passed_percent_on_table.to_dict()

        sql_condition_failed_count_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_condition_failed_count_on_table, Unset):
            sql_condition_failed_count_on_table = self.sql_condition_failed_count_on_table.to_dict()

        sql_aggregate_expr_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_aggregate_expr_table, Unset):
            sql_aggregate_expr_table = self.sql_aggregate_expr_table.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if sql_condition_passed_percent_on_table is not UNSET:
            field_dict["sql_condition_passed_percent_on_table"] = sql_condition_passed_percent_on_table
        if sql_condition_failed_count_on_table is not UNSET:
            field_dict["sql_condition_failed_count_on_table"] = sql_condition_failed_count_on_table
        if sql_aggregate_expr_table is not UNSET:
            field_dict["sql_aggregate_expr_table"] = sql_aggregate_expr_table

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_sql_condition_failed_count_check_spec import TableSqlConditionFailedCountCheckSpec
        from ..models.table_sql_condition_passed_percent_check_spec import TableSqlConditionPassedPercentCheckSpec
        from ..models.table_sql_aggregate_expr_check_spec import TableSqlAggregateExprCheckSpec
        d = src_dict.copy()
        _sql_condition_passed_percent_on_table = d.pop("sql_condition_passed_percent_on_table", UNSET)
        sql_condition_passed_percent_on_table: Union[Unset, TableSqlConditionPassedPercentCheckSpec]
        if isinstance(_sql_condition_passed_percent_on_table,  Unset):
            sql_condition_passed_percent_on_table = UNSET
        else:
            sql_condition_passed_percent_on_table = TableSqlConditionPassedPercentCheckSpec.from_dict(_sql_condition_passed_percent_on_table)




        _sql_condition_failed_count_on_table = d.pop("sql_condition_failed_count_on_table", UNSET)
        sql_condition_failed_count_on_table: Union[Unset, TableSqlConditionFailedCountCheckSpec]
        if isinstance(_sql_condition_failed_count_on_table,  Unset):
            sql_condition_failed_count_on_table = UNSET
        else:
            sql_condition_failed_count_on_table = TableSqlConditionFailedCountCheckSpec.from_dict(_sql_condition_failed_count_on_table)




        _sql_aggregate_expr_table = d.pop("sql_aggregate_expr_table", UNSET)
        sql_aggregate_expr_table: Union[Unset, TableSqlAggregateExprCheckSpec]
        if isinstance(_sql_aggregate_expr_table,  Unset):
            sql_aggregate_expr_table = UNSET
        else:
            sql_aggregate_expr_table = TableSqlAggregateExprCheckSpec.from_dict(_sql_aggregate_expr_table)




        table_sql_profiling_checks_spec = cls(
            sql_condition_passed_percent_on_table=sql_condition_passed_percent_on_table,
            sql_condition_failed_count_on_table=sql_condition_failed_count_on_table,
            sql_aggregate_expr_table=sql_aggregate_expr_table,
        )

        table_sql_profiling_checks_spec.additional_properties = d
        return table_sql_profiling_checks_spec

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
