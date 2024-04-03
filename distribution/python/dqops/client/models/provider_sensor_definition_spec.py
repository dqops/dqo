from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.provider_sensor_runner_type import ProviderSensorRunnerType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_definition_spec_parameters import (
        ProviderSensorDefinitionSpecParameters,
    )


T = TypeVar("T", bound="ProviderSensorDefinitionSpec")


@_attrs_define
class ProviderSensorDefinitionSpec:
    """
    Attributes:
        type (Union[Unset, ProviderSensorRunnerType]):
        java_class_name (Union[Unset, str]): Java class name for a sensor runner that will execute the sensor. The
            "type" must be "java_class".
        supports_grouping (Union[Unset, bool]): The sensor supports grouping, using the GROUP BY clause in SQL. Sensors
            that support a GROUP BY condition can capture separate data quality scores for each data group. The default
            value is true, because most of the data quality sensor support grouping.
        supports_partitioned_checks (Union[Unset, bool]): The sensor supports grouping by a partition date, using the
            GROUP BY clause in SQL. Sensors that support grouping by a partition_by_column can be used for partition checks,
            calculating separate data quality metrics for each daily/monthly partition. The default value is true, because
            most of the data quality sensor support partitioned checks.
        parameters (Union[Unset, ProviderSensorDefinitionSpecParameters]): Additional provider specific sensor
            parameters
        disable_merging_queries (Union[Unset, bool]): Disables merging this sensor's SQL with other sensors. When this
            parameter is 'true', the sensor's SQL will be executed as an independent query.
    """

    type: Union[Unset, ProviderSensorRunnerType] = UNSET
    java_class_name: Union[Unset, str] = UNSET
    supports_grouping: Union[Unset, bool] = UNSET
    supports_partitioned_checks: Union[Unset, bool] = UNSET
    parameters: Union[Unset, "ProviderSensorDefinitionSpecParameters"] = UNSET
    disable_merging_queries: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        type: Union[Unset, str] = UNSET
        if not isinstance(self.type, Unset):
            type = self.type.value

        java_class_name = self.java_class_name
        supports_grouping = self.supports_grouping
        supports_partitioned_checks = self.supports_partitioned_checks
        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        disable_merging_queries = self.disable_merging_queries

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if type is not UNSET:
            field_dict["type"] = type
        if java_class_name is not UNSET:
            field_dict["java_class_name"] = java_class_name
        if supports_grouping is not UNSET:
            field_dict["supports_grouping"] = supports_grouping
        if supports_partitioned_checks is not UNSET:
            field_dict["supports_partitioned_checks"] = supports_partitioned_checks
        if parameters is not UNSET:
            field_dict["parameters"] = parameters
        if disable_merging_queries is not UNSET:
            field_dict["disable_merging_queries"] = disable_merging_queries

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_definition_spec_parameters import (
            ProviderSensorDefinitionSpecParameters,
        )

        d = src_dict.copy()
        _type = d.pop("type", UNSET)
        type: Union[Unset, ProviderSensorRunnerType]
        if isinstance(_type, Unset):
            type = UNSET
        else:
            type = ProviderSensorRunnerType(_type)

        java_class_name = d.pop("java_class_name", UNSET)

        supports_grouping = d.pop("supports_grouping", UNSET)

        supports_partitioned_checks = d.pop("supports_partitioned_checks", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, ProviderSensorDefinitionSpecParameters]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = ProviderSensorDefinitionSpecParameters.from_dict(_parameters)

        disable_merging_queries = d.pop("disable_merging_queries", UNSET)

        provider_sensor_definition_spec = cls(
            type=type,
            java_class_name=java_class_name,
            supports_grouping=supports_grouping,
            supports_partitioned_checks=supports_partitioned_checks,
            parameters=parameters,
            disable_merging_queries=disable_merging_queries,
        )

        provider_sensor_definition_spec.additional_properties = d
        return provider_sensor_definition_spec

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
