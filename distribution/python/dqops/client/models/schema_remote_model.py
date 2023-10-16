from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.import_tables_queue_job_parameters import (
        ImportTablesQueueJobParameters,
    )


T = TypeVar("T", bound="SchemaRemoteModel")


@_attrs_define
class SchemaRemoteModel:
    """Schema remote model

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        schema_name (Union[Unset, str]): Schema name.
        already_imported (Union[Unset, bool]): Has the schema been imported.
        import_table_job_parameters (Union[Unset, ImportTablesQueueJobParameters]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    already_imported: Union[Unset, bool] = UNSET
    import_table_job_parameters: Union[Unset, "ImportTablesQueueJobParameters"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        already_imported = self.already_imported
        import_table_job_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.import_table_job_parameters, Unset):
            import_table_job_parameters = self.import_table_job_parameters.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connectionName"] = connection_name
        if schema_name is not UNSET:
            field_dict["schemaName"] = schema_name
        if already_imported is not UNSET:
            field_dict["alreadyImported"] = already_imported
        if import_table_job_parameters is not UNSET:
            field_dict["importTableJobParameters"] = import_table_job_parameters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.import_tables_queue_job_parameters import (
            ImportTablesQueueJobParameters,
        )

        d = src_dict.copy()
        connection_name = d.pop("connectionName", UNSET)

        schema_name = d.pop("schemaName", UNSET)

        already_imported = d.pop("alreadyImported", UNSET)

        _import_table_job_parameters = d.pop("importTableJobParameters", UNSET)
        import_table_job_parameters: Union[Unset, ImportTablesQueueJobParameters]
        if isinstance(_import_table_job_parameters, Unset):
            import_table_job_parameters = UNSET
        else:
            import_table_job_parameters = ImportTablesQueueJobParameters.from_dict(
                _import_table_job_parameters
            )

        schema_remote_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            already_imported=already_imported,
            import_table_job_parameters=import_table_job_parameters,
        )

        schema_remote_model.additional_properties = d
        return schema_remote_model

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
