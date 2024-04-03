from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="RemoteTableListModel")


@_attrs_define
class RemoteTableListModel:
    """Table remote list model

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        schema_name (Union[Unset, str]): Schema name.
        table_name (Union[Unset, str]): Table name.
        already_imported (Union[Unset, bool]): A flag that tells if the table been already imported.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    already_imported: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        already_imported = self.already_imported

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_name is not UNSET:
            field_dict["schemaName"] = schema_name
        if table_name is not UNSET:
            field_dict["tableName"] = table_name
        if already_imported is not UNSET:
            field_dict["alreadyImported"] = already_imported

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_name = d.pop("schemaName", UNSET)

        table_name = d.pop("tableName", UNSET)

        already_imported = d.pop("alreadyImported", UNSET)

        remote_table_list_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            already_imported=already_imported,
        )

        remote_table_list_model.additional_properties = d
        return remote_table_list_model

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
