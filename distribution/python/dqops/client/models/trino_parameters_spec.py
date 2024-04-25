from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.aws_authentication_mode import AwsAuthenticationMode
from ..models.trino_engine_type import TrinoEngineType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.trino_parameters_spec_properties import TrinoParametersSpecProperties


T = TypeVar("T", bound="TrinoParametersSpec")


@_attrs_define
class TrinoParametersSpec:
    """
    Attributes:
        trino_engine_type (Union[Unset, TrinoEngineType]):
        host (Union[Unset, str]): Trino host name. Supports also a ${TRINO_HOST} configuration with a custom environment
            variable.
        port (Union[Unset, str]): Trino port number. The default port is 8080. Supports also a ${TRINO_PORT}
            configuration with a custom environment variable.
        user (Union[Unset, str]): Trino user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use
            dynamic substitution.
        password (Union[Unset, str]): Trino database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        aws_authentication_mode (Union[Unset, AwsAuthenticationMode]):
        athena_region (Union[Unset, str]): The AWS Region where queries will be run. Supports also a ${ATHENA_REGION}
            configuration with a custom environment variable.
        catalog (Union[Unset, str]): The catalog that contains the databases and the tables that will be accessed with
            the driver. Supports also a ${TRINO_CATALOG} configuration with a custom environment variable.
        athena_work_group (Union[Unset, str]): The workgroup in which queries will run. Supports also a
            ${ATHENA_WORK_GROUP} configuration with a custom environment variable.
        athena_output_location (Union[Unset, str]): The location in Amazon S3 where query results will be stored.
            Supports also a ${ATHENA_OUTPUT_LOCATION} configuration with a custom environment variable.
        properties (Union[Unset, TrinoParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
        database (Union[Unset, str]):
    """

    trino_engine_type: Union[Unset, TrinoEngineType] = UNSET
    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    aws_authentication_mode: Union[Unset, AwsAuthenticationMode] = UNSET
    athena_region: Union[Unset, str] = UNSET
    catalog: Union[Unset, str] = UNSET
    athena_work_group: Union[Unset, str] = UNSET
    athena_output_location: Union[Unset, str] = UNSET
    properties: Union[Unset, "TrinoParametersSpecProperties"] = UNSET
    database: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        trino_engine_type: Union[Unset, str] = UNSET
        if not isinstance(self.trino_engine_type, Unset):
            trino_engine_type = self.trino_engine_type.value

        host = self.host
        port = self.port
        user = self.user
        password = self.password
        aws_authentication_mode: Union[Unset, str] = UNSET
        if not isinstance(self.aws_authentication_mode, Unset):
            aws_authentication_mode = self.aws_authentication_mode.value

        athena_region = self.athena_region
        catalog = self.catalog
        athena_work_group = self.athena_work_group
        athena_output_location = self.athena_output_location
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        database = self.database

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if trino_engine_type is not UNSET:
            field_dict["trino_engine_type"] = trino_engine_type
        if host is not UNSET:
            field_dict["host"] = host
        if port is not UNSET:
            field_dict["port"] = port
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if aws_authentication_mode is not UNSET:
            field_dict["aws_authentication_mode"] = aws_authentication_mode
        if athena_region is not UNSET:
            field_dict["athena_region"] = athena_region
        if catalog is not UNSET:
            field_dict["catalog"] = catalog
        if athena_work_group is not UNSET:
            field_dict["athena_work_group"] = athena_work_group
        if athena_output_location is not UNSET:
            field_dict["athena_output_location"] = athena_output_location
        if properties is not UNSET:
            field_dict["properties"] = properties
        if database is not UNSET:
            field_dict["database"] = database

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.trino_parameters_spec_properties import (
            TrinoParametersSpecProperties,
        )

        d = src_dict.copy()
        _trino_engine_type = d.pop("trino_engine_type", UNSET)
        trino_engine_type: Union[Unset, TrinoEngineType]
        if isinstance(_trino_engine_type, Unset):
            trino_engine_type = UNSET
        else:
            trino_engine_type = TrinoEngineType(_trino_engine_type)

        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        _aws_authentication_mode = d.pop("aws_authentication_mode", UNSET)
        aws_authentication_mode: Union[Unset, AwsAuthenticationMode]
        if isinstance(_aws_authentication_mode, Unset):
            aws_authentication_mode = UNSET
        else:
            aws_authentication_mode = AwsAuthenticationMode(_aws_authentication_mode)

        athena_region = d.pop("athena_region", UNSET)

        catalog = d.pop("catalog", UNSET)

        athena_work_group = d.pop("athena_work_group", UNSET)

        athena_output_location = d.pop("athena_output_location", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, TrinoParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = TrinoParametersSpecProperties.from_dict(_properties)

        database = d.pop("database", UNSET)

        trino_parameters_spec = cls(
            trino_engine_type=trino_engine_type,
            host=host,
            port=port,
            user=user,
            password=password,
            aws_authentication_mode=aws_authentication_mode,
            athena_region=athena_region,
            catalog=catalog,
            athena_work_group=athena_work_group,
            athena_output_location=athena_output_location,
            properties=properties,
            database=database,
        )

        trino_parameters_spec.additional_properties = d
        return trino_parameters_spec

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
