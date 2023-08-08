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


T = TypeVar("T", bound="TableSqlProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableSqlProfilingChecksSpec:
    """
    Attributes:
        profile_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        profile_sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
        profile_sql_aggregate_expr_table (Union[Unset, TableSqlAggregateExprCheckSpec]):
    """

    profile_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    profile_sql_condition_failed_count_on_table: Union[
        Unset, "TableSqlConditionFailedCountCheckSpec"
    ] = UNSET
    profile_sql_aggregate_expr_table: Union[
        Unset, "TableSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_sql_condition_passed_percent_on_table: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_sql_condition_passed_percent_on_table, Unset):
            profile_sql_condition_passed_percent_on_table = (
                self.profile_sql_condition_passed_percent_on_table.to_dict()
            )

        profile_sql_condition_failed_count_on_table: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_sql_condition_failed_count_on_table, Unset):
            profile_sql_condition_failed_count_on_table = (
                self.profile_sql_condition_failed_count_on_table.to_dict()
            )

        profile_sql_aggregate_expr_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sql_aggregate_expr_table, Unset):
            profile_sql_aggregate_expr_table = (
                self.profile_sql_aggregate_expr_table.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_sql_condition_passed_percent_on_table is not UNSET:
            field_dict[
                "profile_sql_condition_passed_percent_on_table"
            ] = profile_sql_condition_passed_percent_on_table
        if profile_sql_condition_failed_count_on_table is not UNSET:
            field_dict[
                "profile_sql_condition_failed_count_on_table"
            ] = profile_sql_condition_failed_count_on_table
        if profile_sql_aggregate_expr_table is not UNSET:
            field_dict[
                "profile_sql_aggregate_expr_table"
            ] = profile_sql_aggregate_expr_table

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
        _profile_sql_condition_passed_percent_on_table = d.pop(
            "profile_sql_condition_passed_percent_on_table", UNSET
        )
        profile_sql_condition_passed_percent_on_table: Union[
            Unset, TableSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_profile_sql_condition_passed_percent_on_table, Unset):
            profile_sql_condition_passed_percent_on_table = UNSET
        else:
            profile_sql_condition_passed_percent_on_table = (
                TableSqlConditionPassedPercentCheckSpec.from_dict(
                    _profile_sql_condition_passed_percent_on_table
                )
            )

        _profile_sql_condition_failed_count_on_table = d.pop(
            "profile_sql_condition_failed_count_on_table", UNSET
        )
        profile_sql_condition_failed_count_on_table: Union[
            Unset, TableSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_profile_sql_condition_failed_count_on_table, Unset):
            profile_sql_condition_failed_count_on_table = UNSET
        else:
            profile_sql_condition_failed_count_on_table = (
                TableSqlConditionFailedCountCheckSpec.from_dict(
                    _profile_sql_condition_failed_count_on_table
                )
            )

        _profile_sql_aggregate_expr_table = d.pop(
            "profile_sql_aggregate_expr_table", UNSET
        )
        profile_sql_aggregate_expr_table: Union[Unset, TableSqlAggregateExprCheckSpec]
        if isinstance(_profile_sql_aggregate_expr_table, Unset):
            profile_sql_aggregate_expr_table = UNSET
        else:
            profile_sql_aggregate_expr_table = TableSqlAggregateExprCheckSpec.from_dict(
                _profile_sql_aggregate_expr_table
            )

        table_sql_profiling_checks_spec = cls(
            profile_sql_condition_passed_percent_on_table=profile_sql_condition_passed_percent_on_table,
            profile_sql_condition_failed_count_on_table=profile_sql_condition_failed_count_on_table,
            profile_sql_aggregate_expr_table=profile_sql_aggregate_expr_table,
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
