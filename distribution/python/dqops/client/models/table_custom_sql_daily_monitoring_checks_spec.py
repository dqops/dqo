from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_custom_sql_daily_monitoring_checks_spec_custom_checks import (
        TableCustomSqlDailyMonitoringChecksSpecCustomChecks,
    )
    from ..models.table_sql_aggregate_expression_check_spec import (
        TableSqlAggregateExpressionCheckSpec,
    )
    from ..models.table_sql_condition_failed_check_spec import (
        TableSqlConditionFailedCheckSpec,
    )
    from ..models.table_sql_condition_passed_percent_check_spec import (
        TableSqlConditionPassedPercentCheckSpec,
    )
    from ..models.table_sql_import_custom_result_check_spec import (
        TableSqlImportCustomResultCheckSpec,
    )
    from ..models.table_sql_invalid_record_count_check_spec import (
        TableSqlInvalidRecordCountCheckSpec,
    )


T = TypeVar("T", bound="TableCustomSqlDailyMonitoringChecksSpec")


@_attrs_define
class TableCustomSqlDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableCustomSqlDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_sql_condition_failed_on_table (Union[Unset, TableSqlConditionFailedCheckSpec]):
        daily_sql_condition_passed_percent_on_table (Union[Unset, TableSqlConditionPassedPercentCheckSpec]):
        daily_sql_aggregate_expression_on_table (Union[Unset, TableSqlAggregateExpressionCheckSpec]):
        daily_sql_invalid_record_count_on_table (Union[Unset, TableSqlInvalidRecordCountCheckSpec]):
        daily_import_custom_result_on_table (Union[Unset, TableSqlImportCustomResultCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableCustomSqlDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_sql_condition_failed_on_table: Union[
        Unset, "TableSqlConditionFailedCheckSpec"
    ] = UNSET
    daily_sql_condition_passed_percent_on_table: Union[
        Unset, "TableSqlConditionPassedPercentCheckSpec"
    ] = UNSET
    daily_sql_aggregate_expression_on_table: Union[
        Unset, "TableSqlAggregateExpressionCheckSpec"
    ] = UNSET
    daily_sql_invalid_record_count_on_table: Union[
        Unset, "TableSqlInvalidRecordCountCheckSpec"
    ] = UNSET
    daily_import_custom_result_on_table: Union[
        Unset, "TableSqlImportCustomResultCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_sql_condition_failed_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_condition_failed_on_table, Unset):
            daily_sql_condition_failed_on_table = (
                self.daily_sql_condition_failed_on_table.to_dict()
            )

        daily_sql_condition_passed_percent_on_table: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.daily_sql_condition_passed_percent_on_table, Unset):
            daily_sql_condition_passed_percent_on_table = (
                self.daily_sql_condition_passed_percent_on_table.to_dict()
            )

        daily_sql_aggregate_expression_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_aggregate_expression_on_table, Unset):
            daily_sql_aggregate_expression_on_table = (
                self.daily_sql_aggregate_expression_on_table.to_dict()
            )

        daily_sql_invalid_record_count_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_sql_invalid_record_count_on_table, Unset):
            daily_sql_invalid_record_count_on_table = (
                self.daily_sql_invalid_record_count_on_table.to_dict()
            )

        daily_import_custom_result_on_table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_import_custom_result_on_table, Unset):
            daily_import_custom_result_on_table = (
                self.daily_import_custom_result_on_table.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_sql_condition_failed_on_table is not UNSET:
            field_dict["daily_sql_condition_failed_on_table"] = (
                daily_sql_condition_failed_on_table
            )
        if daily_sql_condition_passed_percent_on_table is not UNSET:
            field_dict["daily_sql_condition_passed_percent_on_table"] = (
                daily_sql_condition_passed_percent_on_table
            )
        if daily_sql_aggregate_expression_on_table is not UNSET:
            field_dict["daily_sql_aggregate_expression_on_table"] = (
                daily_sql_aggregate_expression_on_table
            )
        if daily_sql_invalid_record_count_on_table is not UNSET:
            field_dict["daily_sql_invalid_record_count_on_table"] = (
                daily_sql_invalid_record_count_on_table
            )
        if daily_import_custom_result_on_table is not UNSET:
            field_dict["daily_import_custom_result_on_table"] = (
                daily_import_custom_result_on_table
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_custom_sql_daily_monitoring_checks_spec_custom_checks import (
            TableCustomSqlDailyMonitoringChecksSpecCustomChecks,
        )
        from ..models.table_sql_aggregate_expression_check_spec import (
            TableSqlAggregateExpressionCheckSpec,
        )
        from ..models.table_sql_condition_failed_check_spec import (
            TableSqlConditionFailedCheckSpec,
        )
        from ..models.table_sql_condition_passed_percent_check_spec import (
            TableSqlConditionPassedPercentCheckSpec,
        )
        from ..models.table_sql_import_custom_result_check_spec import (
            TableSqlImportCustomResultCheckSpec,
        )
        from ..models.table_sql_invalid_record_count_check_spec import (
            TableSqlInvalidRecordCountCheckSpec,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[Unset, TableCustomSqlDailyMonitoringChecksSpecCustomChecks]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableCustomSqlDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_sql_condition_failed_on_table = d.pop(
            "daily_sql_condition_failed_on_table", UNSET
        )
        daily_sql_condition_failed_on_table: Union[
            Unset, TableSqlConditionFailedCheckSpec
        ]
        if isinstance(_daily_sql_condition_failed_on_table, Unset):
            daily_sql_condition_failed_on_table = UNSET
        else:
            daily_sql_condition_failed_on_table = (
                TableSqlConditionFailedCheckSpec.from_dict(
                    _daily_sql_condition_failed_on_table
                )
            )

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

        _daily_sql_aggregate_expression_on_table = d.pop(
            "daily_sql_aggregate_expression_on_table", UNSET
        )
        daily_sql_aggregate_expression_on_table: Union[
            Unset, TableSqlAggregateExpressionCheckSpec
        ]
        if isinstance(_daily_sql_aggregate_expression_on_table, Unset):
            daily_sql_aggregate_expression_on_table = UNSET
        else:
            daily_sql_aggregate_expression_on_table = (
                TableSqlAggregateExpressionCheckSpec.from_dict(
                    _daily_sql_aggregate_expression_on_table
                )
            )

        _daily_sql_invalid_record_count_on_table = d.pop(
            "daily_sql_invalid_record_count_on_table", UNSET
        )
        daily_sql_invalid_record_count_on_table: Union[
            Unset, TableSqlInvalidRecordCountCheckSpec
        ]
        if isinstance(_daily_sql_invalid_record_count_on_table, Unset):
            daily_sql_invalid_record_count_on_table = UNSET
        else:
            daily_sql_invalid_record_count_on_table = (
                TableSqlInvalidRecordCountCheckSpec.from_dict(
                    _daily_sql_invalid_record_count_on_table
                )
            )

        _daily_import_custom_result_on_table = d.pop(
            "daily_import_custom_result_on_table", UNSET
        )
        daily_import_custom_result_on_table: Union[
            Unset, TableSqlImportCustomResultCheckSpec
        ]
        if isinstance(_daily_import_custom_result_on_table, Unset):
            daily_import_custom_result_on_table = UNSET
        else:
            daily_import_custom_result_on_table = (
                TableSqlImportCustomResultCheckSpec.from_dict(
                    _daily_import_custom_result_on_table
                )
            )

        table_custom_sql_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_sql_condition_failed_on_table=daily_sql_condition_failed_on_table,
            daily_sql_condition_passed_percent_on_table=daily_sql_condition_passed_percent_on_table,
            daily_sql_aggregate_expression_on_table=daily_sql_aggregate_expression_on_table,
            daily_sql_invalid_record_count_on_table=daily_sql_invalid_record_count_on_table,
            daily_import_custom_result_on_table=daily_import_custom_result_on_table,
        )

        table_custom_sql_daily_monitoring_checks_spec.additional_properties = d
        return table_custom_sql_daily_monitoring_checks_spec

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
