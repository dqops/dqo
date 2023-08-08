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


T = TypeVar("T", bound="ColumnSqlProfilingChecksSpec")


@attr.s(auto_attribs=True)
class ColumnSqlProfilingChecksSpec:
    """
    Attributes:
        profile_sql_condition_passed_percent_on_column (Union[Unset, ColumnSqlConditionPassedPercentCheckSpec]):
        profile_sql_condition_failed_count_on_column (Union[Unset, ColumnSqlConditionFailedCountCheckSpec]):
        profile_sql_aggregate_expr_column (Union[Unset, ColumnSqlAggregateExprCheckSpec]):
    """

    profile_sql_condition_passed_percent_on_column: Union[
        Unset, "ColumnSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    profile_sql_condition_failed_count_on_column: Union[
        Unset, "ColumnSqlConditionFailedCountCheckSpec"
    ] = UNSET
    profile_sql_aggregate_expr_column: Union[
        Unset, "ColumnSqlAggregateExprCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_sql_condition_passed_percent_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_sql_condition_passed_percent_on_column, Unset):
            profile_sql_condition_passed_percent_on_column = (
                self.profile_sql_condition_passed_percent_on_column.to_dict()
            )

        profile_sql_condition_failed_count_on_column: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.profile_sql_condition_failed_count_on_column, Unset):
            profile_sql_condition_failed_count_on_column = (
                self.profile_sql_condition_failed_count_on_column.to_dict()
            )

        profile_sql_aggregate_expr_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_sql_aggregate_expr_column, Unset):
            profile_sql_aggregate_expr_column = (
                self.profile_sql_aggregate_expr_column.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_sql_condition_passed_percent_on_column is not UNSET:
            field_dict[
                "profile_sql_condition_passed_percent_on_column"
            ] = profile_sql_condition_passed_percent_on_column
        if profile_sql_condition_failed_count_on_column is not UNSET:
            field_dict[
                "profile_sql_condition_failed_count_on_column"
            ] = profile_sql_condition_failed_count_on_column
        if profile_sql_aggregate_expr_column is not UNSET:
            field_dict[
                "profile_sql_aggregate_expr_column"
            ] = profile_sql_aggregate_expr_column

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
        _profile_sql_condition_passed_percent_on_column = d.pop(
            "profile_sql_condition_passed_percent_on_column", UNSET
        )
        profile_sql_condition_passed_percent_on_column: Union[
            Unset, ColumnSqlConditionPassedPercentCheckSpec
        ]
        if isinstance(_profile_sql_condition_passed_percent_on_column, Unset):
            profile_sql_condition_passed_percent_on_column = UNSET
        else:
            profile_sql_condition_passed_percent_on_column = (
                ColumnSqlConditionPassedPercentCheckSpec.from_dict(
                    _profile_sql_condition_passed_percent_on_column
                )
            )

        _profile_sql_condition_failed_count_on_column = d.pop(
            "profile_sql_condition_failed_count_on_column", UNSET
        )
        profile_sql_condition_failed_count_on_column: Union[
            Unset, ColumnSqlConditionFailedCountCheckSpec
        ]
        if isinstance(_profile_sql_condition_failed_count_on_column, Unset):
            profile_sql_condition_failed_count_on_column = UNSET
        else:
            profile_sql_condition_failed_count_on_column = (
                ColumnSqlConditionFailedCountCheckSpec.from_dict(
                    _profile_sql_condition_failed_count_on_column
                )
            )

        _profile_sql_aggregate_expr_column = d.pop(
            "profile_sql_aggregate_expr_column", UNSET
        )
        profile_sql_aggregate_expr_column: Union[Unset, ColumnSqlAggregateExprCheckSpec]
        if isinstance(_profile_sql_aggregate_expr_column, Unset):
            profile_sql_aggregate_expr_column = UNSET
        else:
            profile_sql_aggregate_expr_column = (
                ColumnSqlAggregateExprCheckSpec.from_dict(
                    _profile_sql_aggregate_expr_column
                )
            )

        column_sql_profiling_checks_spec = cls(
            profile_sql_condition_passed_percent_on_column=profile_sql_condition_passed_percent_on_column,
            profile_sql_condition_failed_count_on_column=profile_sql_condition_failed_count_on_column,
            profile_sql_aggregate_expr_column=profile_sql_aggregate_expr_column,
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
