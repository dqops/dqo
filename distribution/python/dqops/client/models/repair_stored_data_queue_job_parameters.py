from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="RepairStoredDataQueueJobParameters")


@_attrs_define
class RepairStoredDataQueueJobParameters:
    """
    Attributes:
        connection_name (Union[Unset, str]):
        schema_table_name (Union[Unset, str]):
        repair_errors (Union[Unset, bool]):
        repair_statistics (Union[Unset, bool]):
        repair_check_results (Union[Unset, bool]):
        repair_sensor_readouts (Union[Unset, bool]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_table_name: Union[Unset, str] = UNSET
    repair_errors: Union[Unset, bool] = UNSET
    repair_statistics: Union[Unset, bool] = UNSET
    repair_check_results: Union[Unset, bool] = UNSET
    repair_sensor_readouts: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_table_name = self.schema_table_name
        repair_errors = self.repair_errors
        repair_statistics = self.repair_statistics
        repair_check_results = self.repair_check_results
        repair_sensor_readouts = self.repair_sensor_readouts

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_table_name is not UNSET:
            field_dict["schemaTableName"] = schema_table_name
        if repair_errors is not UNSET:
            field_dict["repairErrors"] = repair_errors
        if repair_statistics is not UNSET:
            field_dict["repairStatistics"] = repair_statistics
        if repair_check_results is not UNSET:
            field_dict["repairCheckResults"] = repair_check_results
        if repair_sensor_readouts is not UNSET:
            field_dict["repairSensorReadouts"] = repair_sensor_readouts

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_table_name = d.pop("schemaTableName", UNSET)

        repair_errors = d.pop("repairErrors", UNSET)

        repair_statistics = d.pop("repairStatistics", UNSET)

        repair_check_results = d.pop("repairCheckResults", UNSET)

        repair_sensor_readouts = d.pop("repairSensorReadouts", UNSET)

        repair_stored_data_queue_job_parameters = cls(
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            repair_errors=repair_errors,
            repair_statistics=repair_statistics,
            repair_check_results=repair_check_results,
            repair_sensor_readouts=repair_sensor_readouts,
        )

        repair_stored_data_queue_job_parameters.additional_properties = d
        return repair_stored_data_queue_job_parameters

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
