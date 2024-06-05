from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ImportSchemaQueueJobParameters")


@_attrs_define
class ImportSchemaQueueJobParameters:
    """
    Attributes:
        connection_name (Union[Unset, str]): Connection name where the tables are imported.
        schema_name (Union[Unset, str]): Source schema name from which the tables are imported.
        table_name_contains (Union[Unset, str]): Optional filter for the names of tables to import, it is a text
            (substring) that must be present inside table names. This filter is case sensitive.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name_contains: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name_contains = self.table_name_contains

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_name is not UNSET:
            field_dict["schemaName"] = schema_name
        if table_name_contains is not UNSET:
            field_dict["tableNameContains"] = table_name_contains

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_name = d.pop("schemaName", UNSET)

        table_name_contains = d.pop("tableNameContains", UNSET)

        import_schema_queue_job_parameters = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name_contains=table_name_contains,
        )

        import_schema_queue_job_parameters.additional_properties = d
        return import_schema_queue_job_parameters

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
