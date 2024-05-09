from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.aws_authentication_mode import AwsAuthenticationMode
from ..models.azure_authentication_mode import AzureAuthenticationMode
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
        aws_authentication_mode (Union[Unset, AwsAuthenticationMode]):
        azure_authentication_mode (Union[Unset, AzureAuthenticationMode]):
        user (Union[Unset, str]): DuckDB user name for a remote storage type. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        password (Union[Unset, str]): DuckDB password for a remote storage type. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        region (Union[Unset, str]): The region for the storage credentials. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        tenant_id (Union[Unset, str]): Azure Tenant ID used by DuckDB Secret Manager. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        client_id (Union[Unset, str]): Azure Client ID used by DuckDB Secret Manager. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        client_secret (Union[Unset, str]): Azure Client Secret used by DuckDB Secret Manager. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        account_name (Union[Unset, str]): Azure Storage Account Name used by DuckDB Secret Manager. The value can be in
            the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
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
    aws_authentication_mode: Union[Unset, AwsAuthenticationMode] = UNSET
    azure_authentication_mode: Union[Unset, AzureAuthenticationMode] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    region: Union[Unset, str] = UNSET
    tenant_id: Union[Unset, str] = UNSET
    client_id: Union[Unset, str] = UNSET
    client_secret: Union[Unset, str] = UNSET
    account_name: Union[Unset, str] = UNSET
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

        aws_authentication_mode: Union[Unset, str] = UNSET
        if not isinstance(self.aws_authentication_mode, Unset):
            aws_authentication_mode = self.aws_authentication_mode.value

        azure_authentication_mode: Union[Unset, str] = UNSET
        if not isinstance(self.azure_authentication_mode, Unset):
            azure_authentication_mode = self.azure_authentication_mode.value

        user = self.user
        password = self.password
        region = self.region
        tenant_id = self.tenant_id
        client_id = self.client_id
        client_secret = self.client_secret
        account_name = self.account_name

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
        if aws_authentication_mode is not UNSET:
            field_dict["aws_authentication_mode"] = aws_authentication_mode
        if azure_authentication_mode is not UNSET:
            field_dict["azure_authentication_mode"] = azure_authentication_mode
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if region is not UNSET:
            field_dict["region"] = region
        if tenant_id is not UNSET:
            field_dict["tenant_id"] = tenant_id
        if client_id is not UNSET:
            field_dict["client_id"] = client_id
        if client_secret is not UNSET:
            field_dict["client_secret"] = client_secret
        if account_name is not UNSET:
            field_dict["account_name"] = account_name

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

        _aws_authentication_mode = d.pop("aws_authentication_mode", UNSET)
        aws_authentication_mode: Union[Unset, AwsAuthenticationMode]
        if isinstance(_aws_authentication_mode, Unset):
            aws_authentication_mode = UNSET
        else:
            aws_authentication_mode = AwsAuthenticationMode(_aws_authentication_mode)

        _azure_authentication_mode = d.pop("azure_authentication_mode", UNSET)
        azure_authentication_mode: Union[Unset, AzureAuthenticationMode]
        if isinstance(_azure_authentication_mode, Unset):
            azure_authentication_mode = UNSET
        else:
            azure_authentication_mode = AzureAuthenticationMode(
                _azure_authentication_mode
            )

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        region = d.pop("region", UNSET)

        tenant_id = d.pop("tenant_id", UNSET)

        client_id = d.pop("client_id", UNSET)

        client_secret = d.pop("client_secret", UNSET)

        account_name = d.pop("account_name", UNSET)

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
            aws_authentication_mode=aws_authentication_mode,
            azure_authentication_mode=azure_authentication_mode,
            user=user,
            password=password,
            region=region,
            tenant_id=tenant_id,
            client_id=client_id,
            client_secret=client_secret,
            account_name=account_name,
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
