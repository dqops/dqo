from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="DataGroupingConfigurationBasicModel")


@attr.s(auto_attribs=True)
class DataGroupingConfigurationBasicModel:
    """Basic data grouping configuration model not containing nested objects, but only the name of the grouping
    configuration.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            schema_name (Union[Unset, str]): Schema name.
            table_name (Union[Unset, str]): Table name.
            data_grouping_configuration_name (Union[Unset, str]): Data grouping configuration name.
            default_data_grouping_configuration (Union[Unset, bool]): True when this is the default data grouping
                configuration for the table.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    data_grouping_configuration_name: Union[Unset, str] = UNSET
    default_data_grouping_configuration: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        data_grouping_configuration_name = self.data_grouping_configuration_name
        default_data_grouping_configuration = self.default_data_grouping_configuration

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if data_grouping_configuration_name is not UNSET:
            field_dict[
                "data_grouping_configuration_name"
            ] = data_grouping_configuration_name
        if default_data_grouping_configuration is not UNSET:
            field_dict[
                "default_data_grouping_configuration"
            ] = default_data_grouping_configuration

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        data_grouping_configuration_name = d.pop(
            "data_grouping_configuration_name", UNSET
        )

        default_data_grouping_configuration = d.pop(
            "default_data_grouping_configuration", UNSET
        )

        data_grouping_configuration_basic_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            data_grouping_configuration_name=data_grouping_configuration_name,
            default_data_grouping_configuration=default_data_grouping_configuration,
        )

        data_grouping_configuration_basic_model.additional_properties = d
        return data_grouping_configuration_basic_model

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
