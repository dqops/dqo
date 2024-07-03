from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="DataGroupingConfigurationListModel")


@_attrs_define
class DataGroupingConfigurationListModel:
    """Data grouping configurations list model not containing nested objects, but only the name of the grouping
    configuration.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            schema_name (Union[Unset, str]): Schema name.
            table_name (Union[Unset, str]): Table name.
            data_grouping_configuration_name (Union[Unset, str]): Data grouping configuration name.
            default_data_grouping_configuration (Union[Unset, bool]): True when this is the default data grouping
                configuration for the table.
            can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    data_grouping_configuration_name: Union[Unset, str] = UNSET
    default_data_grouping_configuration: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        data_grouping_configuration_name = self.data_grouping_configuration_name
        default_data_grouping_configuration = self.default_data_grouping_configuration
        can_edit = self.can_edit

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
            field_dict["data_grouping_configuration_name"] = (
                data_grouping_configuration_name
            )
        if default_data_grouping_configuration is not UNSET:
            field_dict["default_data_grouping_configuration"] = (
                default_data_grouping_configuration
            )
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

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

        can_edit = d.pop("can_edit", UNSET)

        data_grouping_configuration_list_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            data_grouping_configuration_name=data_grouping_configuration_name,
            default_data_grouping_configuration=default_data_grouping_configuration,
            can_edit=can_edit,
        )

        data_grouping_configuration_list_model.additional_properties = d
        return data_grouping_configuration_list_model

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
