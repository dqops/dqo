import datetime
from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

T = TypeVar("T", bound="DeleteStoredDataQueueJobParameters")


@_attrs_define
class DeleteStoredDataQueueJobParameters:
    """
    Attributes:
        connection_name (Union[Unset, str]):
        schema_table_name (Union[Unset, str]):
        date_start (Union[Unset, datetime.date]):
        date_end (Union[Unset, datetime.date]):
        delete_errors (Union[Unset, bool]):
        delete_statistics (Union[Unset, bool]):
        delete_check_results (Union[Unset, bool]):
        delete_sensor_readouts (Union[Unset, bool]):
        column_names (Union[Unset, List[str]]):
        check_category (Union[Unset, str]):
        table_comparison_name (Union[Unset, str]):
        check_name (Union[Unset, str]):
        check_type (Union[Unset, str]):
        sensor_name (Union[Unset, str]):
        data_group_tag (Union[Unset, str]):
        quality_dimension (Union[Unset, str]):
        time_gradient (Union[Unset, str]):
        collector_category (Union[Unset, str]):
        collector_name (Union[Unset, str]):
        collector_target (Union[Unset, str]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_table_name: Union[Unset, str] = UNSET
    date_start: Union[Unset, datetime.date] = UNSET
    date_end: Union[Unset, datetime.date] = UNSET
    delete_errors: Union[Unset, bool] = UNSET
    delete_statistics: Union[Unset, bool] = UNSET
    delete_check_results: Union[Unset, bool] = UNSET
    delete_sensor_readouts: Union[Unset, bool] = UNSET
    column_names: Union[Unset, List[str]] = UNSET
    check_category: Union[Unset, str] = UNSET
    table_comparison_name: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    data_group_tag: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    time_gradient: Union[Unset, str] = UNSET
    collector_category: Union[Unset, str] = UNSET
    collector_name: Union[Unset, str] = UNSET
    collector_target: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_table_name = self.schema_table_name
        date_start: Union[Unset, str] = UNSET
        if not isinstance(self.date_start, Unset):
            date_start = self.date_start.isoformat()

        date_end: Union[Unset, str] = UNSET
        if not isinstance(self.date_end, Unset):
            date_end = self.date_end.isoformat()

        delete_errors = self.delete_errors
        delete_statistics = self.delete_statistics
        delete_check_results = self.delete_check_results
        delete_sensor_readouts = self.delete_sensor_readouts
        column_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.column_names, Unset):
            column_names = self.column_names

        check_category = self.check_category
        table_comparison_name = self.table_comparison_name
        check_name = self.check_name
        check_type = self.check_type
        sensor_name = self.sensor_name
        data_group_tag = self.data_group_tag
        quality_dimension = self.quality_dimension
        time_gradient = self.time_gradient
        collector_category = self.collector_category
        collector_name = self.collector_name
        collector_target = self.collector_target

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_table_name is not UNSET:
            field_dict["schemaTableName"] = schema_table_name
        if date_start is not UNSET:
            field_dict["dateStart"] = date_start
        if date_end is not UNSET:
            field_dict["dateEnd"] = date_end
        if delete_errors is not UNSET:
            field_dict["deleteErrors"] = delete_errors
        if delete_statistics is not UNSET:
            field_dict["deleteStatistics"] = delete_statistics
        if delete_check_results is not UNSET:
            field_dict["deleteCheckResults"] = delete_check_results
        if delete_sensor_readouts is not UNSET:
            field_dict["deleteSensorReadouts"] = delete_sensor_readouts
        if column_names is not UNSET:
            field_dict["columnNames"] = column_names
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if table_comparison_name is not UNSET:
            field_dict["tableComparisonName"] = table_comparison_name
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if data_group_tag is not UNSET:
            field_dict["dataGroupTag"] = data_group_tag
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if time_gradient is not UNSET:
            field_dict["timeGradient"] = time_gradient
        if collector_category is not UNSET:
            field_dict["collectorCategory"] = collector_category
        if collector_name is not UNSET:
            field_dict["collectorName"] = collector_name
        if collector_target is not UNSET:
            field_dict["collectorTarget"] = collector_target

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_table_name = d.pop("schemaTableName", UNSET)

        _date_start = d.pop("dateStart", UNSET)
        date_start: Union[Unset, datetime.date]
        if isinstance(_date_start, Unset):
            date_start = UNSET
        else:
            date_start = isoparse(_date_start).date()

        _date_end = d.pop("dateEnd", UNSET)
        date_end: Union[Unset, datetime.date]
        if isinstance(_date_end, Unset):
            date_end = UNSET
        else:
            date_end = isoparse(_date_end).date()

        delete_errors = d.pop("deleteErrors", UNSET)

        delete_statistics = d.pop("deleteStatistics", UNSET)

        delete_check_results = d.pop("deleteCheckResults", UNSET)

        delete_sensor_readouts = d.pop("deleteSensorReadouts", UNSET)

        column_names = cast(List[str], d.pop("columnNames", UNSET))

        check_category = d.pop("checkCategory", UNSET)

        table_comparison_name = d.pop("tableComparisonName", UNSET)

        check_name = d.pop("checkName", UNSET)

        check_type = d.pop("checkType", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        data_group_tag = d.pop("dataGroupTag", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        time_gradient = d.pop("timeGradient", UNSET)

        collector_category = d.pop("collectorCategory", UNSET)

        collector_name = d.pop("collectorName", UNSET)

        collector_target = d.pop("collectorTarget", UNSET)

        delete_stored_data_queue_job_parameters = cls(
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            date_start=date_start,
            date_end=date_end,
            delete_errors=delete_errors,
            delete_statistics=delete_statistics,
            delete_check_results=delete_check_results,
            delete_sensor_readouts=delete_sensor_readouts,
            column_names=column_names,
            check_category=check_category,
            table_comparison_name=table_comparison_name,
            check_name=check_name,
            check_type=check_type,
            sensor_name=sensor_name,
            data_group_tag=data_group_tag,
            quality_dimension=quality_dimension,
            time_gradient=time_gradient,
            collector_category=collector_category,
            collector_name=collector_name,
            collector_target=collector_target,
        )

        delete_stored_data_queue_job_parameters.additional_properties = d
        return delete_stored_data_queue_job_parameters

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
