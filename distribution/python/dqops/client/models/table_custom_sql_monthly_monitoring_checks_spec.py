from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_custom_sql_monthly_monitoring_checks_spec_custom_checks import (
        TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks,
    )
    from ..models.table_sql_aggregate_expression_check_spec import (
        TableSqlAggregateExpressionCheckSpec,
    )
    from ..models.table_sql_condition_failed_count_check_spec import (
        TableSqlConditionFailedCountCheckSpec,
    )
    from ..models.table_sql_condition_passed_percent_check_spec import (
        TableSqlConditionPassedPercentCheckSpec,
    )


T = TypeVar("T", bound="TableCustomSqlMonthlyMonitoringChecksSpec")


@_attrs_define
class TableCustomSqlMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        monthly_sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
        monthly_sql_aggregate_expression_on_table (Union[Unset, TableSqlAggregateExpressionCheckSpec]):
        min_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    monthly_sql_condition_failed_count_on_table: Union[
        Unset, "TableSqlConditionFailedCountCheckSpec"
    ] = UNSET
    monthly_sql_aggregate_expression_on_table: Union[
        Unset, "TableSqlAggregateExpressionCheckSpec"
    ] = UNSET
    min_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_sql_condition_passed_percent_on_table: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_sql_condition_passed_percent_on_table, Unset):
            monthly_sql_condition_passed_percent_on_table = (
                self.monthly_sql_condition_passed_percent_on_table.to_dict()
            )

        monthly_sql_condition_failed_count_on_table: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.monthly_sql_condition_failed_count_on_table, Unset):
            monthly_sql_condition_failed_count_on_table = (
                self.monthly_sql_condition_failed_count_on_table.to_dict()
            )

        monthly_sql_aggregate_expression_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_sql_aggregate_expression_on_table, Unset):
            monthly_sql_aggregate_expression_on_table = (
                self.monthly_sql_aggregate_expression_on_table.to_dict()
            )

        min_sql_condition_passed_percent_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_sql_condition_passed_percent_on_table, Unset):
            min_sql_condition_passed_percent_on_table = (
                self.min_sql_condition_passed_percent_on_table.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_sql_condition_passed_percent_on_table is not UNSET:
            field_dict[
                "monthly_sql_condition_passed_percent_on_table"
            ] = monthly_sql_condition_passed_percent_on_table
        if monthly_sql_condition_failed_count_on_table is not UNSET:
            field_dict[
                "monthly_sql_condition_failed_count_on_table"
            ] = monthly_sql_condition_failed_count_on_table
        if monthly_sql_aggregate_expression_on_table is not UNSET:
            field_dict[
                "monthly_sql_aggregate_expression_on_table"
            ] = monthly_sql_aggregate_expression_on_table
        if min_sql_condition_passed_percent_on_table is not UNSET:
            field_dict[
                "min_sql_condition_passed_percent_on_table"
            ] = min_sql_condition_passed_percent_on_table

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_custom_sql_monthly_monitoring_checks_spec_custom_checks import (
            TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks,
        )
        from ..models.table_sql_aggregate_expression_check_spec import (
            TableSqlAggregateExpressionCheckSpec,
        )
        from ..models.table_sql_condition_failed_count_check_spec import (
            TableSqlConditionFailedCountCheckSpec,
        )
        from ..models.table_sql_condition_passed_percent_check_spec import (
            TableSqlConditionPassedPercentCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableCustomSqlMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_sql_condition_passed_percent_on_table = d.pop(
            "monthly_sql_condition_passed_percent_on_table", UNSET
        )
        monthly_sql_condition_passed_percent_on_table: Union[
            Unset, TableSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_monthly_sql_condition_passed_percent_on_table, Unset):
            monthly_sql_condition_passed_percent_on_table = UNSET
        else:
            monthly_sql_condition_passed_percent_on_table = (
                TableSqlConditionPassedPercentCheckSpec.from_dict(
                    _monthly_sql_condition_passed_percent_on_table
                )
            )

        _monthly_sql_condition_failed_count_on_table = d.pop(
            "monthly_sql_condition_failed_count_on_table", UNSET
        )
        monthly_sql_condition_failed_count_on_table: Union[
            Unset, TableSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_monthly_sql_condition_failed_count_on_table, Unset):
            monthly_sql_condition_failed_count_on_table = UNSET
        else:
            monthly_sql_condition_failed_count_on_table = (
                TableSqlConditionFailedCountCheckSpec.from_dict(
                    _monthly_sql_condition_failed_count_on_table
                )
            )

        _monthly_sql_aggregate_expression_on_table = d.pop(
            "monthly_sql_aggregate_expression_on_table", UNSET
        )
        monthly_sql_aggregate_expression_on_table: Union[
            Unset, TableSqlAggregateExpressionCheckSpec
        ]
        if isinstance(_monthly_sql_aggregate_expression_on_table, Unset):
            monthly_sql_aggregate_expression_on_table = UNSET
        else:
            monthly_sql_aggregate_expression_on_table = (
                TableSqlAggregateExpressionCheckSpec.from_dict(
                    _monthly_sql_aggregate_expression_on_table
                )
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

        table_custom_sql_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_sql_condition_passed_percent_on_table=monthly_sql_condition_passed_percent_on_table,
            monthly_sql_condition_failed_count_on_table=monthly_sql_condition_failed_count_on_table,
            monthly_sql_aggregate_expression_on_table=monthly_sql_aggregate_expression_on_table,
            min_sql_condition_passed_percent_on_table=min_sql_condition_passed_percent_on_table,
        )

        table_custom_sql_monthly_monitoring_checks_spec.additional_properties = d
        return table_custom_sql_monthly_monitoring_checks_spec

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