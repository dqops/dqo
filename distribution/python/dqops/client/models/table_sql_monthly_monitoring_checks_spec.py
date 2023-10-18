from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

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
    from ..models.table_sql_monthly_monitoring_checks_spec_custom_checks import (
        TableSqlMonthlyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableSqlMonthlyMonitoringChecksSpec")


@_attrs_define
class TableSqlMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableSqlMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        monthly_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        monthly_sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
        monthly_sql_aggregate_expr_table (Union[Unset, TableSqlAggregateExprCheckSpec]):
        min_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableSqlMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    monthly_sql_condition_failed_count_on_table: Union[
        Unset, "TableSqlConditionFailedCountCheckSpec"
    ] = UNSET
    monthly_sql_aggregate_expr_table: Union[
        Unset, "TableSqlAggregateExprCheckSpec"
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

        monthly_sql_aggregate_expr_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_sql_aggregate_expr_table, Unset):
            monthly_sql_aggregate_expr_table = (
                self.monthly_sql_aggregate_expr_table.to_dict()
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
        if monthly_sql_aggregate_expr_table is not UNSET:
            field_dict[
                "monthly_sql_aggregate_expr_table"
            ] = monthly_sql_aggregate_expr_table
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
        from ..models.table_sql_monthly_monitoring_checks_spec_custom_checks import (
            TableSqlMonthlyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableSqlMonthlyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableSqlMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
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

        _monthly_sql_aggregate_expr_table = d.pop(
            "monthly_sql_aggregate_expr_table", UNSET
        )
        monthly_sql_aggregate_expr_table: Union[Unset, TableSqlAggregateExprCheckSpec]
        if isinstance(_monthly_sql_aggregate_expr_table, Unset):
            monthly_sql_aggregate_expr_table = UNSET
        else:
            monthly_sql_aggregate_expr_table = TableSqlAggregateExprCheckSpec.from_dict(
                _monthly_sql_aggregate_expr_table
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

        table_sql_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_sql_condition_passed_percent_on_table=monthly_sql_condition_passed_percent_on_table,
            monthly_sql_condition_failed_count_on_table=monthly_sql_condition_failed_count_on_table,
            monthly_sql_aggregate_expr_table=monthly_sql_aggregate_expr_table,
            min_sql_condition_passed_percent_on_table=min_sql_condition_passed_percent_on_table,
        )

        table_sql_monthly_monitoring_checks_spec.additional_properties = d
        return table_sql_monthly_monitoring_checks_spec

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
