import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_type import CheckType
from ..models.time_period_gradient import TimePeriodGradient
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckResultEntryModel")


@_attrs_define
class CheckResultEntryModel:
    """
    Attributes:
        id (Union[Unset, str]): Check result primary key
        check_hash (Union[Unset, int]): Check hash, do not set a value when writing results to DQOps
        check_category (Union[Unset, str]): Check category name
        check_name (Union[Unset, str]): Check name
        check_display_name (Union[Unset, str]): Check display name
        check_type (Union[Unset, CheckType]):
        actual_value (Union[Unset, float]): Actual value
        expected_value (Union[Unset, float]): Expected value
        warning_lower_bound (Union[Unset, float]): Warning lower bound
        warning_upper_bound (Union[Unset, float]): Warning upper bound
        error_lower_bound (Union[Unset, float]): Error lower bound
        error_upper_bound (Union[Unset, float]): Error upper bound
        fatal_lower_bound (Union[Unset, float]): Fatal lower bound
        fatal_upper_bound (Union[Unset, float]): Fatal upper bound
        severity (Union[Unset, int]): Issue severity, 0 - valid, 1 - warning, 2 - error, 3 - fatal
        column_name (Union[Unset, str]): Column name
        data_group (Union[Unset, str]): Data group name
        duration_ms (Union[Unset, int]): Duration (ms)
        executed_at (Union[Unset, int]): Executed at timestamp
        time_gradient (Union[Unset, TimePeriodGradient]):
        time_period (Union[Unset, datetime.datetime]): Time period
        include_in_kpi (Union[Unset, bool]): Include in KPI
        include_in_sla (Union[Unset, bool]): Include in SLA
        provider (Union[Unset, str]): Provider name
        quality_dimension (Union[Unset, str]): Data quality dimension
        sensor_name (Union[Unset, str]): Sensor name
        table_comparison (Union[Unset, str]): Table comparison name
        updated_at (Union[Unset, int]): The timestamp when the check was recently updated with a new value because it as
            rerun for the same period and the severity could be changed
    """

    id: Union[Unset, str] = UNSET
    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    actual_value: Union[Unset, float] = UNSET
    expected_value: Union[Unset, float] = UNSET
    warning_lower_bound: Union[Unset, float] = UNSET
    warning_upper_bound: Union[Unset, float] = UNSET
    error_lower_bound: Union[Unset, float] = UNSET
    error_upper_bound: Union[Unset, float] = UNSET
    fatal_lower_bound: Union[Unset, float] = UNSET
    fatal_upper_bound: Union[Unset, float] = UNSET
    severity: Union[Unset, int] = UNSET
    column_name: Union[Unset, str] = UNSET
    data_group: Union[Unset, str] = UNSET
    duration_ms: Union[Unset, int] = UNSET
    executed_at: Union[Unset, int] = UNSET
    time_gradient: Union[Unset, TimePeriodGradient] = UNSET
    time_period: Union[Unset, datetime.datetime] = UNSET
    include_in_kpi: Union[Unset, bool] = UNSET
    include_in_sla: Union[Unset, bool] = UNSET
    provider: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    table_comparison: Union[Unset, str] = UNSET
    updated_at: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        id = self.id
        check_hash = self.check_hash
        check_category = self.check_category
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        actual_value = self.actual_value
        expected_value = self.expected_value
        warning_lower_bound = self.warning_lower_bound
        warning_upper_bound = self.warning_upper_bound
        error_lower_bound = self.error_lower_bound
        error_upper_bound = self.error_upper_bound
        fatal_lower_bound = self.fatal_lower_bound
        fatal_upper_bound = self.fatal_upper_bound
        severity = self.severity
        column_name = self.column_name
        data_group = self.data_group
        duration_ms = self.duration_ms
        executed_at = self.executed_at
        time_gradient: Union[Unset, str] = UNSET
        if not isinstance(self.time_gradient, Unset):
            time_gradient = self.time_gradient.value

        time_period: Union[Unset, str] = UNSET
        if not isinstance(self.time_period, Unset):
            time_period = self.time_period.isoformat()

        include_in_kpi = self.include_in_kpi
        include_in_sla = self.include_in_sla
        provider = self.provider
        quality_dimension = self.quality_dimension
        sensor_name = self.sensor_name
        table_comparison = self.table_comparison
        updated_at = self.updated_at

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if id is not UNSET:
            field_dict["id"] = id
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if check_display_name is not UNSET:
            field_dict["checkDisplayName"] = check_display_name
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if actual_value is not UNSET:
            field_dict["actualValue"] = actual_value
        if expected_value is not UNSET:
            field_dict["expectedValue"] = expected_value
        if warning_lower_bound is not UNSET:
            field_dict["warningLowerBound"] = warning_lower_bound
        if warning_upper_bound is not UNSET:
            field_dict["warningUpperBound"] = warning_upper_bound
        if error_lower_bound is not UNSET:
            field_dict["errorLowerBound"] = error_lower_bound
        if error_upper_bound is not UNSET:
            field_dict["errorUpperBound"] = error_upper_bound
        if fatal_lower_bound is not UNSET:
            field_dict["fatalLowerBound"] = fatal_lower_bound
        if fatal_upper_bound is not UNSET:
            field_dict["fatalUpperBound"] = fatal_upper_bound
        if severity is not UNSET:
            field_dict["severity"] = severity
        if column_name is not UNSET:
            field_dict["columnName"] = column_name
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if duration_ms is not UNSET:
            field_dict["durationMs"] = duration_ms
        if executed_at is not UNSET:
            field_dict["executedAt"] = executed_at
        if time_gradient is not UNSET:
            field_dict["timeGradient"] = time_gradient
        if time_period is not UNSET:
            field_dict["timePeriod"] = time_period
        if include_in_kpi is not UNSET:
            field_dict["includeInKpi"] = include_in_kpi
        if include_in_sla is not UNSET:
            field_dict["includeInSla"] = include_in_sla
        if provider is not UNSET:
            field_dict["provider"] = provider
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if table_comparison is not UNSET:
            field_dict["tableComparison"] = table_comparison
        if updated_at is not UNSET:
            field_dict["updatedAt"] = updated_at

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        id = d.pop("id", UNSET)

        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        check_name = d.pop("checkName", UNSET)

        check_display_name = d.pop("checkDisplayName", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        actual_value = d.pop("actualValue", UNSET)

        expected_value = d.pop("expectedValue", UNSET)

        warning_lower_bound = d.pop("warningLowerBound", UNSET)

        warning_upper_bound = d.pop("warningUpperBound", UNSET)

        error_lower_bound = d.pop("errorLowerBound", UNSET)

        error_upper_bound = d.pop("errorUpperBound", UNSET)

        fatal_lower_bound = d.pop("fatalLowerBound", UNSET)

        fatal_upper_bound = d.pop("fatalUpperBound", UNSET)

        severity = d.pop("severity", UNSET)

        column_name = d.pop("columnName", UNSET)

        data_group = d.pop("dataGroup", UNSET)

        duration_ms = d.pop("durationMs", UNSET)

        executed_at = d.pop("executedAt", UNSET)

        _time_gradient = d.pop("timeGradient", UNSET)
        time_gradient: Union[Unset, TimePeriodGradient]
        if isinstance(_time_gradient, Unset):
            time_gradient = UNSET
        else:
            time_gradient = TimePeriodGradient(_time_gradient)

        _time_period = d.pop("timePeriod", UNSET)
        time_period: Union[Unset, datetime.datetime]
        if isinstance(_time_period, Unset):
            time_period = UNSET
        else:
            time_period = isoparse(_time_period)

        include_in_kpi = d.pop("includeInKpi", UNSET)

        include_in_sla = d.pop("includeInSla", UNSET)

        provider = d.pop("provider", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        table_comparison = d.pop("tableComparison", UNSET)

        updated_at = d.pop("updatedAt", UNSET)

        check_result_entry_model = cls(
            id=id,
            check_hash=check_hash,
            check_category=check_category,
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            actual_value=actual_value,
            expected_value=expected_value,
            warning_lower_bound=warning_lower_bound,
            warning_upper_bound=warning_upper_bound,
            error_lower_bound=error_lower_bound,
            error_upper_bound=error_upper_bound,
            fatal_lower_bound=fatal_lower_bound,
            fatal_upper_bound=fatal_upper_bound,
            severity=severity,
            column_name=column_name,
            data_group=data_group,
            duration_ms=duration_ms,
            executed_at=executed_at,
            time_gradient=time_gradient,
            time_period=time_period,
            include_in_kpi=include_in_kpi,
            include_in_sla=include_in_sla,
            provider=provider,
            quality_dimension=quality_dimension,
            sensor_name=sensor_name,
            table_comparison=table_comparison,
            updated_at=updated_at,
        )

        check_result_entry_model.additional_properties = d
        return check_result_entry_model

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
