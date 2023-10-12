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
    from ..models.table_sql_profiling_checks_spec_custom_checks import (
        TableSqlProfilingChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableSqlProfilingChecksSpec")


@_attrs_define
class TableSqlProfilingChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableSqlProfilingChecksSpecCustomChecks]): Dictionary of additional custom checks
            within this category. The keys are check names defined in the definition section. The sensor parameters and
            rules should match the type of the configured sensor and rule for the custom check.
        profile_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        profile_sql_condition_failed_count_on_table (Union[Unset, TableSqlConditionFailedCountCheckSpec]):
        profile_sql_aggregate_expr_table (Union[Unset, TableSqlAggregateExprCheckSpec]):
    """

    custom_checks: Union[Unset, "TableSqlProfilingChecksSpecCustomChecks"] = UNSET
    profile_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    profile_sql_condition_failed_count_on_table: Union[
        Unset, "TableSqlConditionFailedCountCheckSpec"
    ] = UNSET
    profile_sql_aggregate_expr_table: Union[
        Unset, "TableSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

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
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
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
        from ..models.table_sql_profiling_checks_spec_custom_checks import (
            TableSqlProfilingChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableSqlProfilingChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = TableSqlProfilingChecksSpecCustomChecks.from_dict(
                _custom_checks
            )

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
            custom_checks=custom_checks,
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
