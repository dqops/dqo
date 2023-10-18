from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.column_sql_monthly_partitioned_checks_spec_custom_checks import (
        ColumnSqlMonthlyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnSqlMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnSqlMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnSqlMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_sql_condition_passed_percent_on_column (Union[Unset,
            ColumnSqlConditionPassedPercentCheckSpec]):
        monthly_partition_sql_condition_failed_count_on_column (Union[Unset, ColumnSqlConditionFailedCountCheckSpec]):
        monthly_partition_sql_aggregate_expr_column (Union[Unset, ColumnSqlAggregateExprCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnSqlMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_sql_condition_passed_percent_on_column: Union[
        Unset, "ColumnSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    monthly_partition_sql_condition_failed_count_on_column: Union[
        Unset, "ColumnSqlConditionFailedCountCheckSpec"
    ] = UNSET
    monthly_partition_sql_aggregate_expr_column: Union[
        Unset, "ColumnSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

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
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
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
        from ..models.column_sql_monthly_partitioned_checks_spec_custom_checks import (
            ColumnSqlMonthlyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnSqlMonthlyPartitionedChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnSqlMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

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
            custom_checks=custom_checks,
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
