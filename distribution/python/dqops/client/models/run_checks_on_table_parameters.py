from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.physical_table_name import PhysicalTableName
    from ..models.run_checks_result import RunChecksResult
    from ..models.time_window_filter_parameters import TimeWindowFilterParameters


T = TypeVar("T", bound="RunChecksOnTableParameters")


@_attrs_define
class RunChecksOnTableParameters:
    """Run checks configuration for a job that will run checks on a single table, specifies the target table and the target
    checks that should be executed and an optional time window.

        Attributes:
            connection (Union[Unset, str]): The name of the target connection.
            max_jobs_per_connection (Union[Unset, int]): The maximum number of concurrent 'run checks on table' jobs that
                can be run on this connection. Limits the number of concurrent jobs.
            table (Union[Unset, PhysicalTableName]):
            check_search_filters (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
                checks on which tables and columns should be executed.
            time_window_filter (Union[Unset, TimeWindowFilterParameters]): Time window configuration for partitioned checks
                (the number of recent days or months to analyze in an incremental mode) or an absolute time range to analyze.
            dummy_execution (Union[Unset, bool]): Set the value to true when the data quality checks should be executed in a
                dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will
                be rendered.
            collect_error_samples (Union[Unset, bool]): Set the value to true to collect error samples for failed data
                quality checks.
            run_checks_result (Union[Unset, RunChecksResult]): Returns the result (highest data quality check severity and
                the finished checks count) for the checks that were recently executed.
    """

    connection: Union[Unset, str] = UNSET
    max_jobs_per_connection: Union[Unset, int] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    check_search_filters: Union[Unset, "CheckSearchFilters"] = UNSET
    time_window_filter: Union[Unset, "TimeWindowFilterParameters"] = UNSET
    dummy_execution: Union[Unset, bool] = UNSET
    collect_error_samples: Union[Unset, bool] = UNSET
    run_checks_result: Union[Unset, "RunChecksResult"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        max_jobs_per_connection = self.max_jobs_per_connection
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        check_search_filters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.check_search_filters, Unset):
            check_search_filters = self.check_search_filters.to_dict()

        time_window_filter: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.time_window_filter, Unset):
            time_window_filter = self.time_window_filter.to_dict()

        dummy_execution = self.dummy_execution
        collect_error_samples = self.collect_error_samples
        run_checks_result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_result, Unset):
            run_checks_result = self.run_checks_result.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if max_jobs_per_connection is not UNSET:
            field_dict["max_jobs_per_connection"] = max_jobs_per_connection
        if table is not UNSET:
            field_dict["table"] = table
        if check_search_filters is not UNSET:
            field_dict["check_search_filters"] = check_search_filters
        if time_window_filter is not UNSET:
            field_dict["time_window_filter"] = time_window_filter
        if dummy_execution is not UNSET:
            field_dict["dummy_execution"] = dummy_execution
        if collect_error_samples is not UNSET:
            field_dict["collect_error_samples"] = collect_error_samples
        if run_checks_result is not UNSET:
            field_dict["run_checks_result"] = run_checks_result

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.physical_table_name import PhysicalTableName
        from ..models.run_checks_result import RunChecksResult
        from ..models.time_window_filter_parameters import TimeWindowFilterParameters

        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        max_jobs_per_connection = d.pop("max_jobs_per_connection", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

        _check_search_filters = d.pop("check_search_filters", UNSET)
        check_search_filters: Union[Unset, CheckSearchFilters]
        if isinstance(_check_search_filters, Unset):
            check_search_filters = UNSET
        else:
            check_search_filters = CheckSearchFilters.from_dict(_check_search_filters)

        _time_window_filter = d.pop("time_window_filter", UNSET)
        time_window_filter: Union[Unset, TimeWindowFilterParameters]
        if isinstance(_time_window_filter, Unset):
            time_window_filter = UNSET
        else:
            time_window_filter = TimeWindowFilterParameters.from_dict(
                _time_window_filter
            )

        dummy_execution = d.pop("dummy_execution", UNSET)

        collect_error_samples = d.pop("collect_error_samples", UNSET)

        _run_checks_result = d.pop("run_checks_result", UNSET)
        run_checks_result: Union[Unset, RunChecksResult]
        if isinstance(_run_checks_result, Unset):
            run_checks_result = UNSET
        else:
            run_checks_result = RunChecksResult.from_dict(_run_checks_result)

        run_checks_on_table_parameters = cls(
            connection=connection,
            max_jobs_per_connection=max_jobs_per_connection,
            table=table,
            check_search_filters=check_search_filters,
            time_window_filter=time_window_filter,
            dummy_execution=dummy_execution,
            collect_error_samples=collect_error_samples,
            run_checks_result=run_checks_result,
        )

        run_checks_on_table_parameters.additional_properties = d
        return run_checks_on_table_parameters

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
