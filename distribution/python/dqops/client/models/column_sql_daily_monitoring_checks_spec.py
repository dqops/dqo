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
    from ..models.column_sql_daily_monitoring_checks_spec_custom_checks import (
        ColumnSqlDailyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="ColumnSqlDailyMonitoringChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSqlDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, ColumnSqlDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional custom
            checks within this category. The keys are check names defined in the definition section. The sensor parameters
            and rules should match the type of the configured sensor and rule for the custom check.
        daily_sql_condition_passed_percent_on_column (Union[Unset, ColumnSqlConditionPassedPercentCheckSpec]):
        daily_sql_condition_failed_count_on_column (Union[Unset, ColumnSqlConditionFailedCountCheckSpec]):
        daily_sql_aggregate_expr_column (Union[Unset, ColumnSqlAggregateExprCheckSpec]):
    """

    custom_checks: Union[
        Unset, "ColumnSqlDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_sql_condition_passed_percent_on_column: Union[
        Unset, "ColumnSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    daily_sql_condition_failed_count_on_column: Union[
        Unset, "ColumnSqlConditionFailedCountCheckSpec"
    ] = UNSET
    daily_sql_aggregate_expr_column: Union[
        Unset, "ColumnSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_sql_condition_passed_percent_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_sql_condition_passed_percent_on_column, Unset):
            daily_sql_condition_passed_percent_on_column = (
                self.daily_sql_condition_passed_percent_on_column.to_dict()
            )

        daily_sql_condition_failed_count_on_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_condition_failed_count_on_column, Unset):
            daily_sql_condition_failed_count_on_column = (
                self.daily_sql_condition_failed_count_on_column.to_dict()
            )

        daily_sql_aggregate_expr_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_aggregate_expr_column, Unset):
            daily_sql_aggregate_expr_column = (
                self.daily_sql_aggregate_expr_column.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_sql_condition_passed_percent_on_column is not UNSET:
            field_dict[
                "daily_sql_condition_passed_percent_on_column"
            ] = daily_sql_condition_passed_percent_on_column
        if daily_sql_condition_failed_count_on_column is not UNSET:
            field_dict[
                "daily_sql_condition_failed_count_on_column"
            ] = daily_sql_condition_failed_count_on_column
        if daily_sql_aggregate_expr_column is not UNSET:
            field_dict[
                "daily_sql_aggregate_expr_column"
            ] = daily_sql_aggregate_expr_column

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
        from ..models.column_sql_daily_monitoring_checks_spec_custom_checks import (
            ColumnSqlDailyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, ColumnSqlDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = ColumnSqlDailyMonitoringChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

        _daily_sql_condition_passed_percent_on_column = d.pop(
            "daily_sql_condition_passed_percent_on_column", UNSET
        )
        daily_sql_condition_passed_percent_on_column: Union[
            Unset, ColumnSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_daily_sql_condition_passed_percent_on_column, Unset):
            daily_sql_condition_passed_percent_on_column = UNSET
        else:
            daily_sql_condition_passed_percent_on_column = (
                ColumnSqlConditionPassedPercentCheckSpec.from_dict(
                    _daily_sql_condition_passed_percent_on_column
                )
            )

        _daily_sql_condition_failed_count_on_column = d.pop(
            "daily_sql_condition_failed_count_on_column", UNSET
        )
        daily_sql_condition_failed_count_on_column: Union[
            Unset, ColumnSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_daily_sql_condition_failed_count_on_column, Unset):
            daily_sql_condition_failed_count_on_column = UNSET
        else:
            daily_sql_condition_failed_count_on_column = (
                ColumnSqlConditionFailedCountCheckSpec.from_dict(
                    _daily_sql_condition_failed_count_on_column
                )
            )

        _daily_sql_aggregate_expr_column = d.pop(
            "daily_sql_aggregate_expr_column", UNSET
        )
        daily_sql_aggregate_expr_column: Union[Unset, ColumnSqlAggregateExprCheckSpec]
        if isinstance(_daily_sql_aggregate_expr_column, Unset):
            daily_sql_aggregate_expr_column = UNSET
        else:
            daily_sql_aggregate_expr_column = ColumnSqlAggregateExprCheckSpec.from_dict(
                _daily_sql_aggregate_expr_column
            )

        column_sql_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_sql_condition_passed_percent_on_column=daily_sql_condition_passed_percent_on_column,
            daily_sql_condition_failed_count_on_column=daily_sql_condition_failed_count_on_column,
            daily_sql_aggregate_expr_column=daily_sql_aggregate_expr_column,
        )

        column_sql_daily_monitoring_checks_spec.additional_properties = d
        return column_sql_daily_monitoring_checks_spec

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