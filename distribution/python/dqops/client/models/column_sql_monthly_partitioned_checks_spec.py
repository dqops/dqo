from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_sql_aggregate_expr_check_spec import (
        ColumnSqlAggregateExprCheckSpec,
    )
    from ..models.column_sql_condition_failed_count_check_spec import (
        ColumnSqlConditionFailedCountCheckSpec,
    )
    from ..models.column_sql_condition_passed_percent_check_spec import (
        ColumnSqlConditionPassedPercentCheckSpec,
    )


T = TypeVar("T", bound="ColumnSqlMonthlyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSqlMonthlyPartitionedChecksSpec:
    """
    Attributes:
        monthly_partition_sql_condition_passed_percent_on_column (Union[Unset,
            ColumnSqlConditionPassedPercentCheckSpec]):
        monthly_partition_sql_condition_failed_count_on_column (Union[Unset, ColumnSqlConditionFailedCountCheckSpec]):
        monthly_partition_sql_aggregate_expr_column (Union[Unset, ColumnSqlAggregateExprCheckSpec]):
    """

    monthly_partition_sql_condition_passed_percent_on_column: Union[
        Unset, "ColumnSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    monthly_partition_sql_condition_failed_count_on_column: Union[
        Unset, "ColumnSqlConditionFailedCountCheckSpec"
    ] = UNSET
    monthly_partition_sql_aggregate_expr_column: Union[
        Unset, "ColumnSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_partition_sql_condition_passed_percent_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_sql_condition_passed_percent_on_column, Unset
        ):
            monthly_partition_sql_condition_passed_percent_on_column = (
                self.monthly_partition_sql_condition_passed_percent_on_column.to_dict()
            )

        monthly_partition_sql_condition_failed_count_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_sql_condition_failed_count_on_column, Unset
        ):
            monthly_partition_sql_condition_failed_count_on_column = (
                self.monthly_partition_sql_condition_failed_count_on_column.to_dict()
            )

        monthly_partition_sql_aggregate_expr_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_sql_aggregate_expr_column, Unset):
            monthly_partition_sql_aggregate_expr_column = (
                self.monthly_partition_sql_aggregate_expr_column.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_partition_sql_condition_passed_percent_on_column is not UNSET:
            field_dict[
                "monthly_partition_sql_condition_passed_percent_on_column"
            ] = monthly_partition_sql_condition_passed_percent_on_column
        if monthly_partition_sql_condition_failed_count_on_column is not UNSET:
            field_dict[
                "monthly_partition_sql_condition_failed_count_on_column"
            ] = monthly_partition_sql_condition_failed_count_on_column
        if monthly_partition_sql_aggregate_expr_column is not UNSET:
            field_dict[
                "monthly_partition_sql_aggregate_expr_column"
            ] = monthly_partition_sql_aggregate_expr_column

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_sql_aggregate_expr_check_spec import (
            ColumnSqlAggregateExprCheckSpec,
        )
        from ..models.column_sql_condition_failed_count_check_spec import (
            ColumnSqlConditionFailedCountCheckSpec,
        )
        from ..models.column_sql_condition_passed_percent_check_spec import (
            ColumnSqlConditionPassedPercentCheckSpec,
        )

        d = src_dict.copy()
        _monthly_partition_sql_condition_passed_percent_on_column = d.pop(
            "monthly_partition_sql_condition_passed_percent_on_column", UNSET
        )
        monthly_partition_sql_condition_passed_percent_on_column: Union[
            Unset, ColumnSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_monthly_partition_sql_condition_passed_percent_on_column, Unset):
            monthly_partition_sql_condition_passed_percent_on_column = UNSET
        else:
            monthly_partition_sql_condition_passed_percent_on_column = (
                ColumnSqlConditionPassedPercentCheckSpec.from_dict(
                    _monthly_partition_sql_condition_passed_percent_on_column
                )
            )

        _monthly_partition_sql_condition_failed_count_on_column = d.pop(
            "monthly_partition_sql_condition_failed_count_on_column", UNSET
        )
        monthly_partition_sql_condition_failed_count_on_column: Union[
            Unset, ColumnSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_monthly_partition_sql_condition_failed_count_on_column, Unset):
            monthly_partition_sql_condition_failed_count_on_column = UNSET
        else:
            monthly_partition_sql_condition_failed_count_on_column = (
                ColumnSqlConditionFailedCountCheckSpec.from_dict(
                    _monthly_partition_sql_condition_failed_count_on_column
                )
            )

        _monthly_partition_sql_aggregate_expr_column = d.pop(
            "monthly_partition_sql_aggregate_expr_column", UNSET
        )
        monthly_partition_sql_aggregate_expr_column: Union[
            Unset, ColumnSqlAggregateExprCheckSpec
        ]
        if isinstance(_monthly_partition_sql_aggregate_expr_column, Unset):
            monthly_partition_sql_aggregate_expr_column = UNSET
        else:
            monthly_partition_sql_aggregate_expr_column = (
                ColumnSqlAggregateExprCheckSpec.from_dict(
                    _monthly_partition_sql_aggregate_expr_column
                )
            )

        column_sql_monthly_partitioned_checks_spec = cls(
            monthly_partition_sql_condition_passed_percent_on_column=monthly_partition_sql_condition_passed_percent_on_column,
            monthly_partition_sql_condition_failed_count_on_column=monthly_partition_sql_condition_failed_count_on_column,
            monthly_partition_sql_aggregate_expr_column=monthly_partition_sql_aggregate_expr_column,
        )

        column_sql_monthly_partitioned_checks_spec.additional_properties = d
        return column_sql_monthly_partitioned_checks_spec

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
