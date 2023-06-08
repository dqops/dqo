from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_sql_aggregate_expr_check_spec import (
        TableSqlAggregateExprCheckSpec,
    )
    from ..models.table_sql_condition_failed_count_check_spec import (
        TableSqlConditionFailedCountCheckSpec,
    )
    from ..models.table_sql_condition_passed_percent_check_spec import (
        TableSqlConditionPassedPercentCheckSpec,
    )


T = TypeVar("T", bound="TableSqlDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableSqlDailyRecurringChecksSpec:
    """
    Attributes:
        daily_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        daily_sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
        daily_sql_aggregate_expr_table (Union[Unset, TableSqlAggregateExprCheckSpec]):
        min_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
    """

    daily_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    daily_sql_condition_failed_count_on_table: Union[
        Unset, "TableSqlConditionFailedCountCheckSpec"
    ] = UNSET
    daily_sql_aggregate_expr_table: Union[
        Unset, "TableSqlAggregateExprCheckSpec"
    ] = UNSET
    min_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_sql_condition_passed_percent_on_table: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_sql_condition_passed_percent_on_table, Unset):
            daily_sql_condition_passed_percent_on_table = (
                self.daily_sql_condition_passed_percent_on_table.to_dict()
            )

        daily_sql_condition_failed_count_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_condition_failed_count_on_table, Unset):
            daily_sql_condition_failed_count_on_table = (
                self.daily_sql_condition_failed_count_on_table.to_dict()
            )

        daily_sql_aggregate_expr_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_aggregate_expr_table, Unset):
            daily_sql_aggregate_expr_table = (
                self.daily_sql_aggregate_expr_table.to_dict()
            )

        min_sql_condition_passed_percent_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_sql_condition_passed_percent_on_table, Unset):
            min_sql_condition_passed_percent_on_table = (
                self.min_sql_condition_passed_percent_on_table.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_sql_condition_passed_percent_on_table is not UNSET:
            field_dict[
                "daily_sql_condition_passed_percent_on_table"
            ] = daily_sql_condition_passed_percent_on_table
        if daily_sql_condition_failed_count_on_table is not UNSET:
            field_dict[
                "daily_sql_condition_failed_count_on_table"
            ] = daily_sql_condition_failed_count_on_table
        if daily_sql_aggregate_expr_table is not UNSET:
            field_dict[
                "daily_sql_aggregate_expr_table"
            ] = daily_sql_aggregate_expr_table
        if min_sql_condition_passed_percent_on_table is not UNSET:
            field_dict[
                "min_sql_condition_passed_percent_on_table"
            ] = min_sql_condition_passed_percent_on_table

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_sql_aggregate_expr_check_spec import (
            TableSqlAggregateExprCheckSpec,
        )
        from ..models.table_sql_condition_failed_count_check_spec import (
            TableSqlConditionFailedCountCheckSpec,
        )
        from ..models.table_sql_condition_passed_percent_check_spec import (
            TableSqlConditionPassedPercentCheckSpec,
        )

        d = src_dict.copy()
        _daily_sql_condition_passed_percent_on_table = d.pop(
            "daily_sql_condition_passed_percent_on_table", UNSET
        )
        daily_sql_condition_passed_percent_on_table: Union[
            Unset, TableSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_daily_sql_condition_passed_percent_on_table, Unset):
            daily_sql_condition_passed_percent_on_table = UNSET
        else:
            daily_sql_condition_passed_percent_on_table = (
                TableSqlConditionPassedPercentCheckSpec.from_dict(
                    _daily_sql_condition_passed_percent_on_table
                )
            )

        _daily_sql_condition_failed_count_on_table = d.pop(
            "daily_sql_condition_failed_count_on_table", UNSET
        )
        daily_sql_condition_failed_count_on_table: Union[
            Unset, TableSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_daily_sql_condition_failed_count_on_table, Unset):
            daily_sql_condition_failed_count_on_table = UNSET
        else:
            daily_sql_condition_failed_count_on_table = (
                TableSqlConditionFailedCountCheckSpec.from_dict(
                    _daily_sql_condition_failed_count_on_table
                )
            )

        _daily_sql_aggregate_expr_table = d.pop("daily_sql_aggregate_expr_table", UNSET)
        daily_sql_aggregate_expr_table: Union[Unset, TableSqlAggregateExprCheckSpec]
        if isinstance(_daily_sql_aggregate_expr_table, Unset):
            daily_sql_aggregate_expr_table = UNSET
        else:
            daily_sql_aggregate_expr_table = TableSqlAggregateExprCheckSpec.from_dict(
                _daily_sql_aggregate_expr_table
            )

        _min_sql_condition_passed_percent_on_table = d.pop(
            "min_sql_condition_passed_percent_on_table", UNSET
        )
        min_sql_condition_passed_percent_on_table: Union[
            Unset, TableSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_min_sql_condition_passed_percent_on_table, Unset):
            min_sql_condition_passed_percent_on_table = UNSET
        else:
            min_sql_condition_passed_percent_on_table = (
                TableSqlConditionPassedPercentCheckSpec.from_dict(
                    _min_sql_condition_passed_percent_on_table
                )
            )

        table_sql_daily_recurring_checks_spec = cls(
            daily_sql_condition_passed_percent_on_table=daily_sql_condition_passed_percent_on_table,
            daily_sql_condition_failed_count_on_table=daily_sql_condition_failed_count_on_table,
            daily_sql_aggregate_expr_table=daily_sql_aggregate_expr_table,
            min_sql_condition_passed_percent_on_table=min_sql_condition_passed_percent_on_table,
        )

        table_sql_daily_recurring_checks_spec.additional_properties = d
        return table_sql_daily_recurring_checks_spec

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
