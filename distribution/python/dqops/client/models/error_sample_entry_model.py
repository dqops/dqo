import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_type import CheckType
from ..models.error_sample_result_data_type import ErrorSampleResultDataType
from ..models.time_period_gradient import TimePeriodGradient
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.error_sample_entry_model_result import ErrorSampleEntryModelResult


T = TypeVar("T", bound="ErrorSampleEntryModel")


@_attrs_define
class ErrorSampleEntryModel:
    """
    Attributes:
        id (Union[Unset, str]): Unique ID of the error sample value
        result (Union[Unset, ErrorSampleEntryModelResult]): Error sample (value)
        result_data_type (Union[Unset, ErrorSampleResultDataType]):
        collected_at (Union[Unset, datetime.datetime]): The local timestamp when the error sample was collected
        sample_index (Union[Unset, int]): The index of the result that was returned. Identifies a single error sample
            within a list.
        check_type (Union[Unset, CheckType]):
        time_gradient (Union[Unset, TimePeriodGradient]):
        duration_ms (Union[Unset, int]): Execution duration (ms)
        executed_at (Union[Unset, int]): Executed at
        row_id_1 (Union[Unset, str]): The value of the first column of the unique identifier that identifies a row
            containing the error sample.
        row_id_2 (Union[Unset, str]): The value of the second column of the unique identifier that identifies a row
            containing the error sample.
        row_id_3 (Union[Unset, str]): The value of the third column of the unique identifier that identifies a row
            containing the error sample.
        row_id_4 (Union[Unset, str]): The value of the fourth column of the unique identifier that identifies a row
            containing the error sample.
        row_id_5 (Union[Unset, str]): The value of the fifth column of the unique identifier that identifies a row
            containing the error sample.
        column_name (Union[Unset, str]): Column name
        data_group (Union[Unset, str]): Data group
        provider (Union[Unset, str]): Provider name
        quality_dimension (Union[Unset, str]): Data quality dimension
        sensor_name (Union[Unset, str]): Sensor name
        table_comparison (Union[Unset, str]): Table comparison name
    """

    id: Union[Unset, str] = UNSET
    result: Union[Unset, "ErrorSampleEntryModelResult"] = UNSET
    result_data_type: Union[Unset, ErrorSampleResultDataType] = UNSET
    collected_at: Union[Unset, datetime.datetime] = UNSET
    sample_index: Union[Unset, int] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    time_gradient: Union[Unset, TimePeriodGradient] = UNSET
    duration_ms: Union[Unset, int] = UNSET
    executed_at: Union[Unset, int] = UNSET
    row_id_1: Union[Unset, str] = UNSET
    row_id_2: Union[Unset, str] = UNSET
    row_id_3: Union[Unset, str] = UNSET
    row_id_4: Union[Unset, str] = UNSET
    row_id_5: Union[Unset, str] = UNSET
    column_name: Union[Unset, str] = UNSET
    data_group: Union[Unset, str] = UNSET
    provider: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    table_comparison: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        id = self.id
        result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.result, Unset):
            result = self.result.to_dict()

        result_data_type: Union[Unset, str] = UNSET
        if not isinstance(self.result_data_type, Unset):
            result_data_type = self.result_data_type.value

        collected_at: Union[Unset, str] = UNSET
        if not isinstance(self.collected_at, Unset):
            collected_at = self.collected_at.isoformat()

        sample_index = self.sample_index
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        time_gradient: Union[Unset, str] = UNSET
        if not isinstance(self.time_gradient, Unset):
            time_gradient = self.time_gradient.value

        duration_ms = self.duration_ms
        executed_at = self.executed_at
        row_id_1 = self.row_id_1
        row_id_2 = self.row_id_2
        row_id_3 = self.row_id_3
        row_id_4 = self.row_id_4
        row_id_5 = self.row_id_5
        column_name = self.column_name
        data_group = self.data_group
        provider = self.provider
        quality_dimension = self.quality_dimension
        sensor_name = self.sensor_name
        table_comparison = self.table_comparison

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if id is not UNSET:
            field_dict["id"] = id
        if result is not UNSET:
            field_dict["result"] = result
        if result_data_type is not UNSET:
            field_dict["resultDataType"] = result_data_type
        if collected_at is not UNSET:
            field_dict["collectedAt"] = collected_at
        if sample_index is not UNSET:
            field_dict["sampleIndex"] = sample_index
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if time_gradient is not UNSET:
            field_dict["timeGradient"] = time_gradient
        if duration_ms is not UNSET:
            field_dict["durationMs"] = duration_ms
        if executed_at is not UNSET:
            field_dict["executedAt"] = executed_at
        if row_id_1 is not UNSET:
            field_dict["rowId1"] = row_id_1
        if row_id_2 is not UNSET:
            field_dict["rowId2"] = row_id_2
        if row_id_3 is not UNSET:
            field_dict["rowId3"] = row_id_3
        if row_id_4 is not UNSET:
            field_dict["rowId4"] = row_id_4
        if row_id_5 is not UNSET:
            field_dict["rowId5"] = row_id_5
        if column_name is not UNSET:
            field_dict["columnName"] = column_name
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if provider is not UNSET:
            field_dict["provider"] = provider
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if table_comparison is not UNSET:
            field_dict["tableComparison"] = table_comparison

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.error_sample_entry_model_result import ErrorSampleEntryModelResult

        d = src_dict.copy()
        id = d.pop("id", UNSET)

        _result = d.pop("result", UNSET)
        result: Union[Unset, ErrorSampleEntryModelResult]
        if isinstance(_result, Unset):
            result = UNSET
        else:
            result = ErrorSampleEntryModelResult.from_dict(_result)

        _result_data_type = d.pop("resultDataType", UNSET)
        result_data_type: Union[Unset, ErrorSampleResultDataType]
        if isinstance(_result_data_type, Unset):
            result_data_type = UNSET
        else:
            result_data_type = ErrorSampleResultDataType(_result_data_type)

        _collected_at = d.pop("collectedAt", UNSET)
        collected_at: Union[Unset, datetime.datetime]
        if isinstance(_collected_at, Unset):
            collected_at = UNSET
        else:
            collected_at = isoparse(_collected_at)

        sample_index = d.pop("sampleIndex", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        _time_gradient = d.pop("timeGradient", UNSET)
        time_gradient: Union[Unset, TimePeriodGradient]
        if isinstance(_time_gradient, Unset):
            time_gradient = UNSET
        else:
            time_gradient = TimePeriodGradient(_time_gradient)

        duration_ms = d.pop("durationMs", UNSET)

        executed_at = d.pop("executedAt", UNSET)

        row_id_1 = d.pop("rowId1", UNSET)

        row_id_2 = d.pop("rowId2", UNSET)

        row_id_3 = d.pop("rowId3", UNSET)

        row_id_4 = d.pop("rowId4", UNSET)

        row_id_5 = d.pop("rowId5", UNSET)

        column_name = d.pop("columnName", UNSET)

        data_group = d.pop("dataGroup", UNSET)

        provider = d.pop("provider", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        table_comparison = d.pop("tableComparison", UNSET)

        error_sample_entry_model = cls(
            id=id,
            result=result,
            result_data_type=result_data_type,
            collected_at=collected_at,
            sample_index=sample_index,
            check_type=check_type,
            time_gradient=time_gradient,
            duration_ms=duration_ms,
            executed_at=executed_at,
            row_id_1=row_id_1,
            row_id_2=row_id_2,
            row_id_3=row_id_3,
            row_id_4=row_id_4,
            row_id_5=row_id_5,
            column_name=column_name,
            data_group=data_group,
            provider=provider,
            quality_dimension=quality_dimension,
            sensor_name=sensor_name,
            table_comparison=table_comparison,
        )

        error_sample_entry_model.additional_properties = d
        return error_sample_entry_model

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
