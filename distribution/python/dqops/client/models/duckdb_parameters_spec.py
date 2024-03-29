from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.duckdb_files_format_type import DuckdbFilesFormatType
from ..models.duckdb_read_mode import DuckdbReadMode
from ..models.duckdb_storage_type import DuckdbStorageType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.csv_file_format_spec import CsvFileFormatSpec
    from ..models.duckdb_parameters_spec_directories import (
        DuckdbParametersSpecDirectories,
    )
    from ..models.duckdb_parameters_spec_properties import (
        DuckdbParametersSpecProperties,
    )
    from ..models.json_file_format_spec import JsonFileFormatSpec
    from ..models.parquet_file_format_spec import ParquetFileFormatSpec


T = TypeVar("T", bound="DuckdbParametersSpec")


@_attrs_define
class DuckdbParametersSpec:
    """
    Attributes:
        read_mode (Union[Unset, DuckdbReadMode]):
        files_format_type (Union[Unset, DuckdbFilesFormatType]):
        database (Union[Unset, str]): DuckDB database name for in-memory read mode. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        properties (Union[Unset, DuckdbParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
        csv (Union[Unset, CsvFileFormatSpec]):
        json (Union[Unset, JsonFileFormatSpec]):
        parquet (Union[Unset, ParquetFileFormatSpec]):
        directories (Union[Unset, DuckdbParametersSpecDirectories]): Virtual schema name to directory mappings. The path
            must be an absolute path.
        storage_type (Union[Unset, DuckdbStorageType]):
        user (Union[Unset, str]): DuckDB user name for a remote storage type. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        password (Union[Unset, str]): DuckDB password for a remote storage type. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        region (Union[Unset, str]): The region for the storage credentials. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
    """

    read_mode: Union[Unset, DuckdbReadMode] = UNSET
    files_format_type: Union[Unset, DuckdbFilesFormatType] = UNSET
    database: Union[Unset, str] = UNSET
    properties: Union[Unset, "DuckdbParametersSpecProperties"] = UNSET
    csv: Union[Unset, "CsvFileFormatSpec"] = UNSET
    json: Union[Unset, "JsonFileFormatSpec"] = UNSET
    parquet: Union[Unset, "ParquetFileFormatSpec"] = UNSET
    directories: Union[Unset, "DuckdbParametersSpecDirectories"] = UNSET
    storage_type: Union[Unset, DuckdbStorageType] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    region: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        read_mode: Union[Unset, str] = UNSET
        if not isinstance(self.read_mode, Unset):
            read_mode = self.read_mode.value

        files_format_type: Union[Unset, str] = UNSET
        if not isinstance(self.files_format_type, Unset):
            files_format_type = self.files_format_type.value

        database = self.database
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        csv: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.csv, Unset):
            csv = self.csv.to_dict()

        json: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.json, Unset):
            json = self.json.to_dict()

        parquet: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parquet, Unset):
            parquet = self.parquet.to_dict()

        directories: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.directories, Unset):
            directories = self.directories.to_dict()

        storage_type: Union[Unset, str] = UNSET
        if not isinstance(self.storage_type, Unset):
            storage_type = self.storage_type.value

        user = self.user
        password = self.password
        region = self.region

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if read_mode is not UNSET:
            field_dict["read_mode"] = read_mode
        if files_format_type is not UNSET:
            field_dict["files_format_type"] = files_format_type
        if database is not UNSET:
            field_dict["database"] = database
        if properties is not UNSET:
            field_dict["properties"] = properties
        if csv is not UNSET:
            field_dict["csv"] = csv
        if json is not UNSET:
            field_dict["json"] = json
        if parquet is not UNSET:
            field_dict["parquet"] = parquet
        if directories is not UNSET:
            field_dict["directories"] = directories
        if storage_type is not UNSET:
            field_dict["storage_type"] = storage_type
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if region is not UNSET:
            field_dict["region"] = region

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.csv_file_format_spec import CsvFileFormatSpec
        from ..models.duckdb_parameters_spec_directories import (
            DuckdbParametersSpecDirectories,
        )
        from ..models.duckdb_parameters_spec_properties import (
            DuckdbParametersSpecProperties,
        )
        from ..models.json_file_format_spec import JsonFileFormatSpec
        from ..models.parquet_file_format_spec import ParquetFileFormatSpec

        d = src_dict.copy()
        _read_mode = d.pop("read_mode", UNSET)
        read_mode: Union[Unset, DuckdbReadMode]
        if isinstance(_read_mode, Unset):
            read_mode = UNSET
        else:
            read_mode = DuckdbReadMode(_read_mode)

        _files_format_type = d.pop("files_format_type", UNSET)
        files_format_type: Union[Unset, DuckdbFilesFormatType]
        if isinstance(_files_format_type, Unset):
            files_format_type = UNSET
        else:
            files_format_type = DuckdbFilesFormatType(_files_format_type)

        database = d.pop("database", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, DuckdbParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = DuckdbParametersSpecProperties.from_dict(_properties)

        _csv = d.pop("csv", UNSET)
        csv: Union[Unset, CsvFileFormatSpec]
        if isinstance(_csv, Unset):
            csv = UNSET
        else:
            csv = CsvFileFormatSpec.from_dict(_csv)

        _json = d.pop("json", UNSET)
        json: Union[Unset, JsonFileFormatSpec]
        if isinstance(_json, Unset):
            json = UNSET
        else:
            json = JsonFileFormatSpec.from_dict(_json)

        _parquet = d.pop("parquet", UNSET)
        parquet: Union[Unset, ParquetFileFormatSpec]
        if isinstance(_parquet, Unset):
            parquet = UNSET
        else:
            parquet = ParquetFileFormatSpec.from_dict(_parquet)

        _directories = d.pop("directories", UNSET)
        directories: Union[Unset, DuckdbParametersSpecDirectories]
        if isinstance(_directories, Unset):
            directories = UNSET
        else:
            directories = DuckdbParametersSpecDirectories.from_dict(_directories)

        _storage_type = d.pop("storage_type", UNSET)
        storage_type: Union[Unset, DuckdbStorageType]
        if isinstance(_storage_type, Unset):
            storage_type = UNSET
        else:
            storage_type = DuckdbStorageType(_storage_type)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        region = d.pop("region", UNSET)

        duckdb_parameters_spec = cls(
            read_mode=read_mode,
            files_format_type=files_format_type,
            database=database,
            properties=properties,
            csv=csv,
            json=json,
            parquet=parquet,
            directories=directories,
            storage_type=storage_type,
            user=user,
            password=password,
            region=region,
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
