from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.error_samples_data_scope import ErrorSamplesDataScope
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.error_sampler_result import ErrorSamplerResult
    from ..models.physical_table_name import PhysicalTableName
    from ..models.time_window_filter_parameters import TimeWindowFilterParameters


T = TypeVar("T", bound="CollectErrorSamplesOnTableParameters")


@_attrs_define
class CollectErrorSamplesOnTableParameters:
    """
    Attributes:
        connection (Union[Unset, str]): The name of the target connection.
        max_jobs_per_connection (Union[Unset, int]): The maximum number of concurrent 'collect error samples on table'
            jobs that can be run on this connection. Limits the number of concurrent jobs.
        table (Union[Unset, PhysicalTableName]):
        check_search_filters (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        time_window_filter (Union[Unset, TimeWindowFilterParameters]): Time window configuration for partitioned checks
            (the number of recent days or months to analyze in an incremental mode) or an absolute time range to analyze.
        data_scope (Union[Unset, ErrorSamplesDataScope]):
        dummy_sensor_execution (Union[Unset, bool]): Boolean flag that enables a dummy error samples collection (sensors
            are executed, but the error samples results are not written to the parquet files).
        error_sampler_result (Union[Unset, ErrorSamplerResult]): Returns the result with the summary of the error
            samples collected.
    """

    connection: Union[Unset, str] = UNSET
    max_jobs_per_connection: Union[Unset, int] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    check_search_filters: Union[Unset, "CheckSearchFilters"] = UNSET
    time_window_filter: Union[Unset, "TimeWindowFilterParameters"] = UNSET
    data_scope: Union[Unset, ErrorSamplesDataScope] = UNSET
    dummy_sensor_execution: Union[Unset, bool] = UNSET
    error_sampler_result: Union[Unset, "ErrorSamplerResult"] = UNSET
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

        data_scope: Union[Unset, str] = UNSET
        if not isinstance(self.data_scope, Unset):
            data_scope = self.data_scope.value

        dummy_sensor_execution = self.dummy_sensor_execution
        error_sampler_result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.error_sampler_result, Unset):
            error_sampler_result = self.error_sampler_result.to_dict()

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
        if data_scope is not UNSET:
            field_dict["data_scope"] = data_scope
        if dummy_sensor_execution is not UNSET:
            field_dict["dummy_sensor_execution"] = dummy_sensor_execution
        if error_sampler_result is not UNSET:
            field_dict["error_sampler_result"] = error_sampler_result

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.error_sampler_result import ErrorSamplerResult
        from ..models.physical_table_name import PhysicalTableName
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

        _data_scope = d.pop("data_scope", UNSET)
        data_scope: Union[Unset, ErrorSamplesDataScope]
        if isinstance(_data_scope, Unset):
            data_scope = UNSET
        else:
            data_scope = ErrorSamplesDataScope(_data_scope)

        dummy_sensor_execution = d.pop("dummy_sensor_execution", UNSET)

        _error_sampler_result = d.pop("error_sampler_result", UNSET)
        error_sampler_result: Union[Unset, ErrorSamplerResult]
        if isinstance(_error_sampler_result, Unset):
            error_sampler_result = UNSET
        else:
            error_sampler_result = ErrorSamplerResult.from_dict(_error_sampler_result)

        collect_error_samples_on_table_parameters = cls(
            connection=connection,
            max_jobs_per_connection=max_jobs_per_connection,
            table=table,
            check_search_filters=check_search_filters,
            time_window_filter=time_window_filter,
            data_scope=data_scope,
            dummy_sensor_execution=dummy_sensor_execution,
            error_sampler_result=error_sampler_result,
        )

        collect_error_samples_on_table_parameters.additional_properties = d
        return collect_error_samples_on_table_parameters

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
