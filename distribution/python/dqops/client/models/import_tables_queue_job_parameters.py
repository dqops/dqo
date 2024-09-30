from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ImportTablesQueueJobParameters")


@_attrs_define
class ImportTablesQueueJobParameters:
    """
    Attributes:
        connection_name (Union[Unset, str]): Connection name
        schema_name (Union[Unset, str]): Schema name
        table_name_contains (Union[Unset, str]): Optional filter for the table names to import. The table names that are
            imported must contain a substring matching this parameter. This filter is case sensitive.
        table_names (Union[Unset, List[str]]): Optional list of table names inside the schema. When the list of tables
            is empty, all tables are imported.
        tables_import_limit (Union[Unset, int]): Optional parameter to configure the limit of tables that are imported
            from a single schema. Leave this parameter blank to use the default limit (300 tables).
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name_contains: Union[Unset, str] = UNSET
    table_names: Union[Unset, List[str]] = UNSET
    tables_import_limit: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name_contains = self.table_name_contains
        table_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.table_names, Unset):
            table_names = self.table_names

        tables_import_limit = self.tables_import_limit

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_name is not UNSET:
            field_dict["schemaName"] = schema_name
        if table_name_contains is not UNSET:
            field_dict["tableNameContains"] = table_name_contains
        if table_names is not UNSET:
            field_dict["tableNames"] = table_names
        if tables_import_limit is not UNSET:
            field_dict["tablesImportLimit"] = tables_import_limit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_name = d.pop("schemaName", UNSET)

        table_name_contains = d.pop("tableNameContains", UNSET)

        table_names = cast(List[str], d.pop("tableNames", UNSET))

        tables_import_limit = d.pop("tablesImportLimit", UNSET)

        import_tables_queue_job_parameters = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name_contains=table_name_contains,
            table_names=table_names,
            tables_import_limit=tables_import_limit,
        )

        import_tables_queue_job_parameters.additional_properties = d
        return import_tables_queue_job_parameters

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
