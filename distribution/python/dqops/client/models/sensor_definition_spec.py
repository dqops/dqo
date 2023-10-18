from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.parameter_definition_spec import ParameterDefinitionSpec
    from ..models.sensor_definition_spec_parameters import (
        SensorDefinitionSpecParameters,
    )


T = TypeVar("T", bound="SensorDefinitionSpec")


@_attrs_define
class SensorDefinitionSpec:
    """
    Attributes:
        fields (Union[Unset, List['ParameterDefinitionSpec']]): List of fields that are parameters of a custom sensor.
            Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls
            for all required fields.
        requires_event_timestamp (Union[Unset, bool]): The data quality sensor depends on the configuration of the event
            timestamp column name on the analyzed table. When true, the name of the column that stores the event
            (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the
            table.
        requires_ingestion_timestamp (Union[Unset, bool]): The data quality sensor depends on the configuration of the
            ingestion timestamp column name on the analyzed table. When true, the name of the column that stores the
            ingestion (created_at, loaded_at, etc.) timestamp must be specified in the
            timestamp_columns.ingestion_timestamp_column field on the table.
        default_value (Union[Unset, float]): Default value that is used when the sensor returns no rows. A row count
            sensor may return no rows when a GROUP BY condition is added to capture the database server's local time zone.
            In order to always return a value, a sensor may have a default value configured.
        parameters (Union[Unset, SensorDefinitionSpecParameters]): Additional sensor definition parameters
    """

    fields: Union[Unset, List["ParameterDefinitionSpec"]] = UNSET
    requires_event_timestamp: Union[Unset, bool] = UNSET
    requires_ingestion_timestamp: Union[Unset, bool] = UNSET
    default_value: Union[Unset, float] = UNSET
    parameters: Union[Unset, "SensorDefinitionSpecParameters"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        fields: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.fields, Unset):
            fields = []
            for fields_item_data in self.fields:
                fields_item = fields_item_data.to_dict()

                fields.append(fields_item)

        requires_event_timestamp = self.requires_event_timestamp
        requires_ingestion_timestamp = self.requires_ingestion_timestamp
        default_value = self.default_value
        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if fields is not UNSET:
            field_dict["fields"] = fields
        if requires_event_timestamp is not UNSET:
            field_dict["requires_event_timestamp"] = requires_event_timestamp
        if requires_ingestion_timestamp is not UNSET:
            field_dict["requires_ingestion_timestamp"] = requires_ingestion_timestamp
        if default_value is not UNSET:
            field_dict["default_value"] = default_value
        if parameters is not UNSET:
            field_dict["parameters"] = parameters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.parameter_definition_spec import ParameterDefinitionSpec
        from ..models.sensor_definition_spec_parameters import (
            SensorDefinitionSpecParameters,
        )

        d = src_dict.copy()
        fields = []
        _fields = d.pop("fields", UNSET)
        for fields_item_data in _fields or []:
            fields_item = ParameterDefinitionSpec.from_dict(fields_item_data)

            fields.append(fields_item)

        requires_event_timestamp = d.pop("requires_event_timestamp", UNSET)

        requires_ingestion_timestamp = d.pop("requires_ingestion_timestamp", UNSET)

        default_value = d.pop("default_value", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, SensorDefinitionSpecParameters]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = SensorDefinitionSpecParameters.from_dict(_parameters)

        sensor_definition_spec = cls(
            fields=fields,
            requires_event_timestamp=requires_event_timestamp,
            requires_ingestion_timestamp=requires_ingestion_timestamp,
            default_value=default_value,
            parameters=parameters,
        )

        sensor_definition_spec.additional_properties = d
        return sensor_definition_spec

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
