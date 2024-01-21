import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_type import CheckType
from ..models.time_period_gradient import TimePeriodGradient
from ..types import UNSET, Unset

T = TypeVar("T", bound="SensorReadoutEntryModel")


@_attrs_define
class SensorReadoutEntryModel:
    """
    Attributes:
        id (Union[Unset, str]): Sensor readout primary key
        check_name (Union[Unset, str]): Check name
        check_display_name (Union[Unset, str]): Check display name
        check_type (Union[Unset, CheckType]):
        actual_value (Union[Unset, float]): Actual value
        expected_value (Union[Unset, float]): Expected value
        column_name (Union[Unset, str]): Column name
        data_group (Union[Unset, str]): Data group
        duration_ms (Union[Unset, int]): Duration (ms)
        executed_at (Union[Unset, int]): Executed at
        time_gradient (Union[Unset, TimePeriodGradient]):
        time_period (Union[Unset, datetime.datetime]): Time period
        provider (Union[Unset, str]): Provider name
        quality_dimension (Union[Unset, str]): Data quality dimension
        table_comparison (Union[Unset, str]): Table comparison name
    """

    id: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    actual_value: Union[Unset, float] = UNSET
    expected_value: Union[Unset, float] = UNSET
    column_name: Union[Unset, str] = UNSET
    data_group: Union[Unset, str] = UNSET
    duration_ms: Union[Unset, int] = UNSET
    executed_at: Union[Unset, int] = UNSET
    time_gradient: Union[Unset, TimePeriodGradient] = UNSET
    time_period: Union[Unset, datetime.datetime] = UNSET
    provider: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    table_comparison: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        id = self.id
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        actual_value = self.actual_value
        expected_value = self.expected_value
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

        provider = self.provider
        quality_dimension = self.quality_dimension
        table_comparison = self.table_comparison

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if id is not UNSET:
            field_dict["id"] = id
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
        if provider is not UNSET:
            field_dict["provider"] = provider
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if table_comparison is not UNSET:
            field_dict["tableComparison"] = table_comparison

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        id = d.pop("id", UNSET)

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

        provider = d.pop("provider", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        table_comparison = d.pop("tableComparison", UNSET)

        sensor_readout_entry_model = cls(
            id=id,
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            actual_value=actual_value,
            expected_value=expected_value,
            column_name=column_name,
            data_group=data_group,
            duration_ms=duration_ms,
            executed_at=executed_at,
            time_gradient=time_gradient,
            time_period=time_period,
            provider=provider,
            quality_dimension=quality_dimension,
            table_comparison=table_comparison,
        )

        sensor_readout_entry_model.additional_properties = d
        return sensor_readout_entry_model

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
