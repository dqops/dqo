from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.column_sql_condition_passed_percent_check_spec import ColumnSqlConditionPassedPercentCheckSpec
  from ..models.column_sql_condition_failed_count_check_spec import ColumnSqlConditionFailedCountCheckSpec
  from ..models.column_sql_aggregate_expr_check_spec import ColumnSqlAggregateExprCheckSpec





T = TypeVar("T", bound="ColumnSqlProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSqlProfilingChecksSpec:
    """ 
        Attributes:
            sql_condition_passed_percent_on_column (Union[Unset, ColumnSqlConditionPassedPercentCheckSpec]):
            sql_condition_failed_count_on_column (Union[Unset, ColumnSqlConditionFailedCountCheckSpec]):
            sql_aggregate_expr_column (Union[Unset, ColumnSqlAggregateExprCheckSpec]):
     """

    sql_condition_passed_percent_on_column: Union[Unset, 'ColumnSqlConditionPassedPercentCheckSpec'] = UNSET
    sql_condition_failed_count_on_column: Union[Unset, 'ColumnSqlConditionFailedCountCheckSpec'] = UNSET
    sql_aggregate_expr_column: Union[Unset, 'ColumnSqlAggregateExprCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.column_sql_condition_passed_percent_check_spec import ColumnSqlConditionPassedPercentCheckSpec
        from ..models.column_sql_condition_failed_count_check_spec import ColumnSqlConditionFailedCountCheckSpec
        from ..models.column_sql_aggregate_expr_check_spec import ColumnSqlAggregateExprCheckSpec
        sql_condition_passed_percent_on_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_condition_passed_percent_on_column, Unset):
            sql_condition_passed_percent_on_column = self.sql_condition_passed_percent_on_column.to_dict()

        sql_condition_failed_count_on_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_condition_failed_count_on_column, Unset):
            sql_condition_failed_count_on_column = self.sql_condition_failed_count_on_column.to_dict()

        sql_aggregate_expr_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sql_aggregate_expr_column, Unset):
            sql_aggregate_expr_column = self.sql_aggregate_expr_column.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if sql_condition_passed_percent_on_column is not UNSET:
            field_dict["sql_condition_passed_percent_on_column"] = sql_condition_passed_percent_on_column
        if sql_condition_failed_count_on_column is not UNSET:
            field_dict["sql_condition_failed_count_on_column"] = sql_condition_failed_count_on_column
        if sql_aggregate_expr_column is not UNSET:
            field_dict["sql_aggregate_expr_column"] = sql_aggregate_expr_column

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_sql_condition_passed_percent_check_spec import ColumnSqlConditionPassedPercentCheckSpec
        from ..models.column_sql_condition_failed_count_check_spec import ColumnSqlConditionFailedCountCheckSpec
        from ..models.column_sql_aggregate_expr_check_spec import ColumnSqlAggregateExprCheckSpec
        d = src_dict.copy()
        _sql_condition_passed_percent_on_column = d.pop("sql_condition_passed_percent_on_column", UNSET)
        sql_condition_passed_percent_on_column: Union[Unset, ColumnSqlConditionPassedPercentCheckSpec]
        if isinstance(_sql_condition_passed_percent_on_column,  Unset):
            sql_condition_passed_percent_on_column = UNSET
        else:
            sql_condition_passed_percent_on_column = ColumnSqlConditionPassedPercentCheckSpec.from_dict(_sql_condition_passed_percent_on_column)




        _sql_condition_failed_count_on_column = d.pop("sql_condition_failed_count_on_column", UNSET)
        sql_condition_failed_count_on_column: Union[Unset, ColumnSqlConditionFailedCountCheckSpec]
        if isinstance(_sql_condition_failed_count_on_column,  Unset):
            sql_condition_failed_count_on_column = UNSET
        else:
            sql_condition_failed_count_on_column = ColumnSqlConditionFailedCountCheckSpec.from_dict(_sql_condition_failed_count_on_column)




        _sql_aggregate_expr_column = d.pop("sql_aggregate_expr_column", UNSET)
        sql_aggregate_expr_column: Union[Unset, ColumnSqlAggregateExprCheckSpec]
        if isinstance(_sql_aggregate_expr_column,  Unset):
            sql_aggregate_expr_column = UNSET
        else:
            sql_aggregate_expr_column = ColumnSqlAggregateExprCheckSpec.from_dict(_sql_aggregate_expr_column)




        column_sql_profiling_checks_spec = cls(
            sql_condition_passed_percent_on_column=sql_condition_passed_percent_on_column,
            sql_condition_failed_count_on_column=sql_condition_failed_count_on_column,
            sql_aggregate_expr_column=sql_aggregate_expr_column,
        )

        column_sql_profiling_checks_spec.additional_properties = d
        return column_sql_profiling_checks_spec

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
