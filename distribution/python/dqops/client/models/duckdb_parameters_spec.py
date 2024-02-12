from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.duckdb_read_mode import DuckdbReadMode
from ..models.duckdb_source_files_type import DuckdbSourceFilesType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.duckdb_parameters_spec_properties import (
        DuckdbParametersSpecProperties,
    )


T = TypeVar("T", bound="DuckdbParametersSpec")


@_attrs_define
class DuckdbParametersSpec:
    """
    Attributes:
        read_mode (Union[Unset, DuckdbReadMode]):
        source_files_type (Union[Unset, DuckdbSourceFilesType]):
        database (Union[Unset, str]): DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format
            to use dynamic substitution.
        options (Union[Unset, str]): DuckDB connection 'options' initialization parameter. For example setting this to
            -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also
            a ${DUCKDB_OPTIONS} configuration with a custom environment variable.
        properties (Union[Unset, DuckdbParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
    """

    read_mode: Union[Unset, DuckdbReadMode] = UNSET
    source_files_type: Union[Unset, DuckdbSourceFilesType] = UNSET
    database: Union[Unset, str] = UNSET
    options: Union[Unset, str] = UNSET
    properties: Union[Unset, "DuckdbParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        read_mode: Union[Unset, str] = UNSET
        if not isinstance(self.read_mode, Unset):
            read_mode = self.read_mode.value

        source_files_type: Union[Unset, str] = UNSET
        if not isinstance(self.source_files_type, Unset):
            source_files_type = self.source_files_type.value

        database = self.database
        options = self.options
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if read_mode is not UNSET:
            field_dict["read_mode"] = read_mode
        if source_files_type is not UNSET:
            field_dict["source_files_type"] = source_files_type
        if database is not UNSET:
            field_dict["database"] = database
        if options is not UNSET:
            field_dict["options"] = options
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.duckdb_parameters_spec_properties import (
            DuckdbParametersSpecProperties,
        )

        d = src_dict.copy()
        _read_mode = d.pop("read_mode", UNSET)
        read_mode: Union[Unset, DuckdbReadMode]
        if isinstance(_read_mode, Unset):
            read_mode = UNSET
        else:
            read_mode = DuckdbReadMode(_read_mode)

        _source_files_type = d.pop("source_files_type", UNSET)
        source_files_type: Union[Unset, DuckdbSourceFilesType]
        if isinstance(_source_files_type, Unset):
            source_files_type = UNSET
        else:
            source_files_type = DuckdbSourceFilesType(_source_files_type)

        database = d.pop("database", UNSET)

        options = d.pop("options", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, DuckdbParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = DuckdbParametersSpecProperties.from_dict(_properties)

        duckdb_parameters_spec = cls(
            read_mode=read_mode,
            source_files_type=source_files_type,
            database=database,
            options=options,
            properties=properties,
        )

        duckdb_parameters_spec.additional_properties = d
        return duckdb_parameters_spec

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
