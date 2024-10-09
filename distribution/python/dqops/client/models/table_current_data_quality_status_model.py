from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.rule_severity_level import RuleSeverityLevel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_current_data_quality_status_model_checks import (
        TableCurrentDataQualityStatusModelChecks,
    )
    from ..models.table_current_data_quality_status_model_columns import (
        TableCurrentDataQualityStatusModelColumns,
    )
    from ..models.table_current_data_quality_status_model_dimensions import (
        TableCurrentDataQualityStatusModelDimensions,
    )


T = TypeVar("T", bound="TableCurrentDataQualityStatusModel")


@_attrs_define
class TableCurrentDataQualityStatusModel:
    """The table's most recent data quality status. It is a summary of the results of the most recently executed data
    quality checks on the table. Verify the value of the highest_severity_level to see if there are any data quality
    issues on the table. The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was
    detected, 2 - an error was detected, 3 - a fatal data quality issue was detected.

        Attributes:
            data_domain (Union[Unset, str]): Data domain name.
            connection_name (Union[Unset, str]): The connection name in DQOps.
            schema_name (Union[Unset, str]): The schema name.
            table_name (Union[Unset, str]): The table name.
            total_row_count (Union[Unset, int]): Most recent row count. Returned only when the status of the monitoring or
                profiling checks was requested.
            data_freshness_delay_days (Union[Unset, float]): The last measured data freshness delay in days. Requires any of
                the data freshness checks in the monitoring section configured and up to date.
            current_severity (Union[Unset, RuleSeverityLevel]):
            highest_historical_severity (Union[Unset, RuleSeverityLevel]):
            last_check_executed_at (Union[Unset, int]): The UTC timestamp when the most recent data quality check was
                executed on the table.
            executed_checks (Union[Unset, int]): The total number of most recent checks that were executed on the table.
                Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
            valid_results (Union[Unset, int]): The number of most recent valid data quality checks that passed without
                raising any issues.
            warnings (Union[Unset, int]): The number of most recent data quality checks that failed by raising a warning
                severity data quality issue.
            errors (Union[Unset, int]): The number of most recent data quality checks that failed by raising an error
                severity data quality issue.
            fatals (Union[Unset, int]): The number of most recent data quality checks that failed by raising a fatal
                severity data quality issue.
            execution_errors (Union[Unset, int]): The number of data quality check execution errors that were reported due
                to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or
                invalid python rules. When an execution error is reported, the configuration of a data quality check on a table
                must be updated.
            data_quality_kpi (Union[Unset, float]): Data quality KPI score for the table, measured as a percentage of passed
                data quality checks. DQOps counts data quality issues at a warning severity level as passed checks. The data
                quality KPI score is a value in the range 0..100.
            checks (Union[Unset, TableCurrentDataQualityStatusModelChecks]): The dictionary of statuses for data quality
                checks. The keys are data quality check names, the values are the current data quality check statuses that
                describe the most current status.
            columns (Union[Unset, TableCurrentDataQualityStatusModelColumns]): Dictionary of data statues for all columns
                that have any known data quality results. The keys in the dictionary are the column names.
            dimensions (Union[Unset, TableCurrentDataQualityStatusModelDimensions]): Dictionary of the current data quality
                statues for each data quality dimension.
            table_exist (Union[Unset, bool]): The flag informing whether the table exist. The table might not exist for the
                imported data lineage source tables.
    """

    data_domain: Union[Unset, str] = UNSET
    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    total_row_count: Union[Unset, int] = UNSET
    data_freshness_delay_days: Union[Unset, float] = UNSET
    current_severity: Union[Unset, RuleSeverityLevel] = UNSET
    highest_historical_severity: Union[Unset, RuleSeverityLevel] = UNSET
    last_check_executed_at: Union[Unset, int] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    data_quality_kpi: Union[Unset, float] = UNSET
    checks: Union[Unset, "TableCurrentDataQualityStatusModelChecks"] = UNSET
    columns: Union[Unset, "TableCurrentDataQualityStatusModelColumns"] = UNSET
    dimensions: Union[Unset, "TableCurrentDataQualityStatusModelDimensions"] = UNSET
    table_exist: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        data_domain = self.data_domain
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        total_row_count = self.total_row_count
        data_freshness_delay_days = self.data_freshness_delay_days
        current_severity: Union[Unset, str] = UNSET
        if not isinstance(self.current_severity, Unset):
            current_severity = self.current_severity.value

        highest_historical_severity: Union[Unset, str] = UNSET
        if not isinstance(self.highest_historical_severity, Unset):
            highest_historical_severity = self.highest_historical_severity.value

        last_check_executed_at = self.last_check_executed_at
        executed_checks = self.executed_checks
        valid_results = self.valid_results
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        execution_errors = self.execution_errors
        data_quality_kpi = self.data_quality_kpi
        checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = self.checks.to_dict()

        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        dimensions: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.dimensions, Unset):
            dimensions = self.dimensions.to_dict()

        table_exist = self.table_exist

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if data_domain is not UNSET:
            field_dict["data_domain"] = data_domain
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if total_row_count is not UNSET:
            field_dict["total_row_count"] = total_row_count
        if data_freshness_delay_days is not UNSET:
            field_dict["data_freshness_delay_days"] = data_freshness_delay_days
        if current_severity is not UNSET:
            field_dict["current_severity"] = current_severity
        if highest_historical_severity is not UNSET:
            field_dict["highest_historical_severity"] = highest_historical_severity
        if last_check_executed_at is not UNSET:
            field_dict["last_check_executed_at"] = last_check_executed_at
        if executed_checks is not UNSET:
            field_dict["executed_checks"] = executed_checks
        if valid_results is not UNSET:
            field_dict["valid_results"] = valid_results
        if warnings is not UNSET:
            field_dict["warnings"] = warnings
        if errors is not UNSET:
            field_dict["errors"] = errors
        if fatals is not UNSET:
            field_dict["fatals"] = fatals
        if execution_errors is not UNSET:
            field_dict["execution_errors"] = execution_errors
        if data_quality_kpi is not UNSET:
            field_dict["data_quality_kpi"] = data_quality_kpi
        if checks is not UNSET:
            field_dict["checks"] = checks
        if columns is not UNSET:
            field_dict["columns"] = columns
        if dimensions is not UNSET:
            field_dict["dimensions"] = dimensions
        if table_exist is not UNSET:
            field_dict["table_exist"] = table_exist

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_current_data_quality_status_model_checks import (
            TableCurrentDataQualityStatusModelChecks,
        )
        from ..models.table_current_data_quality_status_model_columns import (
            TableCurrentDataQualityStatusModelColumns,
        )
        from ..models.table_current_data_quality_status_model_dimensions import (
            TableCurrentDataQualityStatusModelDimensions,
        )

        d = src_dict.copy()
        data_domain = d.pop("data_domain", UNSET)

        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        total_row_count = d.pop("total_row_count", UNSET)

        data_freshness_delay_days = d.pop("data_freshness_delay_days", UNSET)

        _current_severity = d.pop("current_severity", UNSET)
        current_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_current_severity, Unset):
            current_severity = UNSET
        else:
            current_severity = RuleSeverityLevel(_current_severity)

        _highest_historical_severity = d.pop("highest_historical_severity", UNSET)
        highest_historical_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_highest_historical_severity, Unset):
            highest_historical_severity = UNSET
        else:
            highest_historical_severity = RuleSeverityLevel(
                _highest_historical_severity
            )

        last_check_executed_at = d.pop("last_check_executed_at", UNSET)

        executed_checks = d.pop("executed_checks", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        data_quality_kpi = d.pop("data_quality_kpi", UNSET)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, TableCurrentDataQualityStatusModelChecks]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = TableCurrentDataQualityStatusModelChecks.from_dict(_checks)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, TableCurrentDataQualityStatusModelColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = TableCurrentDataQualityStatusModelColumns.from_dict(_columns)

        _dimensions = d.pop("dimensions", UNSET)
        dimensions: Union[Unset, TableCurrentDataQualityStatusModelDimensions]
        if isinstance(_dimensions, Unset):
            dimensions = UNSET
        else:
            dimensions = TableCurrentDataQualityStatusModelDimensions.from_dict(
                _dimensions
            )

        table_exist = d.pop("table_exist", UNSET)

        table_current_data_quality_status_model = cls(
            data_domain=data_domain,
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            total_row_count=total_row_count,
            data_freshness_delay_days=data_freshness_delay_days,
            current_severity=current_severity,
            highest_historical_severity=highest_historical_severity,
            last_check_executed_at=last_check_executed_at,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
            data_quality_kpi=data_quality_kpi,
            checks=checks,
            columns=columns,
            dimensions=dimensions,
            table_exist=table_exist,
        )

        table_current_data_quality_status_model.additional_properties = d
        return table_current_data_quality_status_model

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
