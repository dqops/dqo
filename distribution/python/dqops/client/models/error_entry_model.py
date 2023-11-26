import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_type import CheckType
from ..models.time_period_gradient import TimePeriodGradient
from ..types import UNSET, Unset

T = TypeVar("T", bound="ErrorEntryModel")


@_attrs_define
class ErrorEntryModel:
    """
    Attributes:
        actual_value (Union[Unset, float]): Actual value
        expected_value (Union[Unset, float]): Expected value
        column_name (Union[Unset, str]): Column name
        data_group (Union[Unset, str]): Data group
        check_type (Union[Unset, CheckType]):
        duration_ms (Union[Unset, int]): Duration (ms)
        executed_at (Union[Unset, int]): Executed at
        time_gradient (Union[Unset, TimePeriodGradient]):
        time_period (Union[Unset, datetime.datetime]): Time period
        provider (Union[Unset, str]): Provider name
        quality_dimension (Union[Unset, str]): Data quality dimension
        sensor_name (Union[Unset, str]): Sensor name
        readout_id (Union[Unset, str]): Sensor readout ID
        error_message (Union[Unset, str]): Error message
        error_source (Union[Unset, str]): Error source
        error_timestamp (Union[Unset, datetime.datetime]): Error timestamp
        table_comparison (Union[Unset, str]): Table comparison name
    """

    actual_value: Union[Unset, float] = UNSET
    expected_value: Union[Unset, float] = UNSET
    column_name: Union[Unset, str] = UNSET
    data_group: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    duration_ms: Union[Unset, int] = UNSET
    executed_at: Union[Unset, int] = UNSET
    time_gradient: Union[Unset, TimePeriodGradient] = UNSET
    time_period: Union[Unset, datetime.datetime] = UNSET
    provider: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    readout_id: Union[Unset, str] = UNSET
    error_message: Union[Unset, str] = UNSET
    error_source: Union[Unset, str] = UNSET
    error_timestamp: Union[Unset, datetime.datetime] = UNSET
    table_comparison: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        actual_value = self.actual_value
        expected_value = self.expected_value
        column_name = self.column_name
        data_group = self.data_group
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        duration_ms = self.duration_ms
        executed_at = self.executed_at
        time_gradient: Union[Unset, str] = UNSET
        if not isinstance(self.time_gradient, Unset):
            time_gradient = self.time_gradient.value

        time_period: Union[Unset, str] = UNSET
        if not isinstance(self.time_period, Unset):
            time_period = self.time_period.isoformat()

        provider = self.provider
        quality_dimension = self.quality_dimension
        sensor_name = self.sensor_name
        readout_id = self.readout_id
        error_message = self.error_message
        error_source = self.error_source
        error_timestamp: Union[Unset, str] = UNSET
        if not isinstance(self.error_timestamp, Unset):
            error_timestamp = self.error_timestamp.isoformat()

        table_comparison = self.table_comparison

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if actual_value is not UNSET:
            field_dict["actualValue"] = actual_value
        if expected_value is not UNSET:
            field_dict["expectedValue"] = expected_value
        if column_name is not UNSET:
            field_dict["columnName"] = column_name
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if duration_ms is not UNSET:
            field_dict["durationMs"] = duration_ms
        if executed_at is not UNSET:
            field_dict["executedAt"] = executed_at
        if time_gradient is not UNSET:
            field_dict["timeGradient"] = time_gradient
        if time_period is not UNSET:
            field_dict["timePeriod"] = time_period
        if provider is not UNSET:
            field_dict["provider"] = provider
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if readout_id is not UNSET:
            field_dict["readoutId"] = readout_id
        if error_message is not UNSET:
            field_dict["errorMessage"] = error_message
        if error_source is not UNSET:
            field_dict["errorSource"] = error_source
        if error_timestamp is not UNSET:
            field_dict["errorTimestamp"] = error_timestamp
        if table_comparison is not UNSET:
            field_dict["tableComparison"] = table_comparison

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        actual_value = d.pop("actualValue", UNSET)

        expected_value = d.pop("expectedValue", UNSET)

        column_name = d.pop("columnName", UNSET)

        data_group = d.pop("dataGroup", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

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

        provider = d.pop("provider", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        readout_id = d.pop("readoutId", UNSET)

        error_message = d.pop("errorMessage", UNSET)

        error_source = d.pop("errorSource", UNSET)

        _error_timestamp = d.pop("errorTimestamp", UNSET)
        error_timestamp: Union[Unset, datetime.datetime]
        if isinstance(_error_timestamp, Unset):
            error_timestamp = UNSET
        else:
            error_timestamp = isoparse(_error_timestamp)

        table_comparison = d.pop("tableComparison", UNSET)

        error_entry_model = cls(
            actual_value=actual_value,
            expected_value=expected_value,
            column_name=column_name,
            data_group=data_group,
            check_type=check_type,
            duration_ms=duration_ms,
            executed_at=executed_at,
            time_gradient=time_gradient,
            time_period=time_period,
            provider=provider,
            quality_dimension=quality_dimension,
            sensor_name=sensor_name,
            readout_id=readout_id,
            error_message=error_message,
            error_source=error_source,
            error_timestamp=error_timestamp,
            table_comparison=table_comparison,
        )

        error_entry_model.additional_properties = d
        return error_entry_model

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
