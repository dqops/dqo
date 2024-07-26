from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_custom_sql_monthly_partitioned_checks_spec_custom_checks import (
        ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks,
    )
    from ..models.column_sql_aggregate_expression_check_spec import (
        ColumnSqlAggregateExpressionCheckSpec,
    )
    from ..models.column_sql_condition_failed_check_spec import (
        ColumnSqlConditionFailedCheckSpec,
    )
    from ..models.column_sql_condition_passed_percent_check_spec import (
        ColumnSqlConditionPassedPercentCheckSpec,
    )
    from ..models.column_sql_import_custom_result_check_spec import (
        ColumnSqlImportCustomResultCheckSpec,
    )


T = TypeVar("T", bound="ColumnCustomSqlMonthlyPartitionedChecksSpec")


@_attrs_define
class ColumnCustomSqlMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_sql_condition_failed_on_column (Union[Unset, ColumnSqlConditionFailedCheckSpec]):
        monthly_partition_sql_condition_passed_percent_on_column (Union[Unset,
            ColumnSqlConditionPassedPercentCheckSpec]):
        monthly_partition_sql_aggregate_expression_on_column (Union[Unset, ColumnSqlAggregateExpressionCheckSpec]):
        monthly_partition_import_custom_result_on_column (Union[Unset, ColumnSqlImportCustomResultCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_sql_condition_failed_on_column: Union[
        Unset, "ColumnSqlConditionFailedCheckSpec"
    ] = UNSET
    monthly_partition_sql_condition_passed_percent_on_column: Union[
        Unset, "ColumnSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    monthly_partition_sql_aggregate_expression_on_column: Union[
        Unset, "ColumnSqlAggregateExpressionCheckSpec"
    ] = UNSET
    monthly_partition_import_custom_result_on_column: Union[
        Unset, "ColumnSqlImportCustomResultCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_sql_condition_failed_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_sql_condition_failed_on_column, Unset):
            monthly_partition_sql_condition_failed_on_column = (
                self.monthly_partition_sql_condition_failed_on_column.to_dict()
            )

        monthly_partition_sql_condition_passed_percent_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_sql_condition_passed_percent_on_column, Unset
        ):
            monthly_partition_sql_condition_passed_percent_on_column = (
                self.monthly_partition_sql_condition_passed_percent_on_column.to_dict()
            )

        monthly_partition_sql_aggregate_expression_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(
            self.monthly_partition_sql_aggregate_expression_on_column, Unset
        ):
            monthly_partition_sql_aggregate_expression_on_column = (
                self.monthly_partition_sql_aggregate_expression_on_column.to_dict()
            )

        monthly_partition_import_custom_result_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_partition_import_custom_result_on_column, Unset):
            monthly_partition_import_custom_result_on_column = (
                self.monthly_partition_import_custom_result_on_column.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_sql_condition_failed_on_column is not UNSET:
            field_dict["monthly_partition_sql_condition_failed_on_column"] = (
                monthly_partition_sql_condition_failed_on_column
            )
        if monthly_partition_sql_condition_passed_percent_on_column is not UNSET:
            field_dict["monthly_partition_sql_condition_passed_percent_on_column"] = (
                monthly_partition_sql_condition_passed_percent_on_column
            )
        if monthly_partition_sql_aggregate_expression_on_column is not UNSET:
            field_dict["monthly_partition_sql_aggregate_expression_on_column"] = (
                monthly_partition_sql_aggregate_expression_on_column
            )
        if monthly_partition_import_custom_result_on_column is not UNSET:
            field_dict["monthly_partition_import_custom_result_on_column"] = (
                monthly_partition_import_custom_result_on_column
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_custom_sql_monthly_partitioned_checks_spec_custom_checks import (
            ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks,
        )
        from ..models.column_sql_aggregate_expression_check_spec import (
            ColumnSqlAggregateExpressionCheckSpec,
        )
        from ..models.column_sql_condition_failed_check_spec import (
            ColumnSqlConditionFailedCheckSpec,
        )
        from ..models.column_sql_condition_passed_percent_check_spec import (
            ColumnSqlConditionPassedPercentCheckSpec,
        )
        from ..models.column_sql_import_custom_result_check_spec import (
            ColumnSqlImportCustomResultCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                ColumnCustomSqlMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_sql_condition_failed_on_column = d.pop(
            "monthly_partition_sql_condition_failed_on_column", UNSET
        )
        monthly_partition_sql_condition_failed_on_column: Union[
            Unset, ColumnSqlConditionFailedCheckSpec
        ]
        if isinstance(_monthly_partition_sql_condition_failed_on_column, Unset):
            monthly_partition_sql_condition_failed_on_column = UNSET
        else:
            monthly_partition_sql_condition_failed_on_column = (
                ColumnSqlConditionFailedCheckSpec.from_dict(
                    _monthly_partition_sql_condition_failed_on_column
                )
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

        _monthly_partition_sql_aggregate_expression_on_column = d.pop(
            "monthly_partition_sql_aggregate_expression_on_column", UNSET
        )
        monthly_partition_sql_aggregate_expression_on_column: Union[
            Unset, ColumnSqlAggregateExpressionCheckSpec
        ]
        if isinstance(_monthly_partition_sql_aggregate_expression_on_column, Unset):
            monthly_partition_sql_aggregate_expression_on_column = UNSET
        else:
            monthly_partition_sql_aggregate_expression_on_column = (
                ColumnSqlAggregateExpressionCheckSpec.from_dict(
                    _monthly_partition_sql_aggregate_expression_on_column
                )
            )

        _monthly_partition_import_custom_result_on_column = d.pop(
            "monthly_partition_import_custom_result_on_column", UNSET
        )
        monthly_partition_import_custom_result_on_column: Union[
            Unset, ColumnSqlImportCustomResultCheckSpec
        ]
        if isinstance(_monthly_partition_import_custom_result_on_column, Unset):
            monthly_partition_import_custom_result_on_column = UNSET
        else:
            monthly_partition_import_custom_result_on_column = (
                ColumnSqlImportCustomResultCheckSpec.from_dict(
                    _monthly_partition_import_custom_result_on_column
                )
            )

        column_custom_sql_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_sql_condition_failed_on_column=monthly_partition_sql_condition_failed_on_column,
            monthly_partition_sql_condition_passed_percent_on_column=monthly_partition_sql_condition_passed_percent_on_column,
            monthly_partition_sql_aggregate_expression_on_column=monthly_partition_sql_aggregate_expression_on_column,
            monthly_partition_import_custom_result_on_column=monthly_partition_import_custom_result_on_column,
        )

        column_custom_sql_monthly_partitioned_checks_spec.additional_properties = d
        return column_custom_sql_monthly_partitioned_checks_spec

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
