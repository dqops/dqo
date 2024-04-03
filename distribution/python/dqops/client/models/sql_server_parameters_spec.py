from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.sql_server_authentication_mode import SqlServerAuthenticationMode
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.sql_server_parameters_spec_properties import (
        SqlServerParametersSpecProperties,
    )


T = TypeVar("T", bound="SqlServerParametersSpec")


@_attrs_define
class SqlServerParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): SQL Server host name. Supports also a ${SQLSERVER_HOST} configuration with a custom
            environment variable.
        port (Union[Unset, str]): SQL Server port number. The default port is 1433. Supports also a ${SQLSERVER_PORT}
            configuration with a custom environment variable.
        database (Union[Unset, str]): SQL Server database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        user (Union[Unset, str]): SQL Server user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to
            use dynamic substitution.
        password (Union[Unset, str]): SQL Server database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        disable_encryption (Union[Unset, bool]): Disable SSL encryption parameter. The default value is false. You may
            need to disable encryption when SQL Server is started in Docker.
        authentication_mode (Union[Unset, SqlServerAuthenticationMode]):
        properties (Union[Unset, SqlServerParametersSpecProperties]): A dictionary of custom JDBC parameters that are
            added to the JDBC connection string, a key/value dictionary.
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    database: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    disable_encryption: Union[Unset, bool] = UNSET
    authentication_mode: Union[Unset, SqlServerAuthenticationMode] = UNSET
    properties: Union[Unset, "SqlServerParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        database = self.database
        user = self.user
        password = self.password
        disable_encryption = self.disable_encryption
        authentication_mode: Union[Unset, str] = UNSET
        if not isinstance(self.authentication_mode, Unset):
            authentication_mode = self.authentication_mode.value

        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if host is not UNSET:
            field_dict["host"] = host
        if port is not UNSET:
            field_dict["port"] = port
        if database is not UNSET:
            field_dict["database"] = database
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if disable_encryption is not UNSET:
            field_dict["disable_encryption"] = disable_encryption
        if authentication_mode is not UNSET:
            field_dict["authentication_mode"] = authentication_mode
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.sql_server_parameters_spec_properties import (
            SqlServerParametersSpecProperties,
        )

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        database = d.pop("database", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        disable_encryption = d.pop("disable_encryption", UNSET)

        _authentication_mode = d.pop("authentication_mode", UNSET)
        authentication_mode: Union[Unset, SqlServerAuthenticationMode]
        if isinstance(_authentication_mode, Unset):
            authentication_mode = UNSET
        else:
            authentication_mode = SqlServerAuthenticationMode(_authentication_mode)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, SqlServerParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = SqlServerParametersSpecProperties.from_dict(_properties)

        sql_server_parameters_spec = cls(
            host=host,
            port=port,
            database=database,
            user=user,
            password=password,
            disable_encryption=disable_encryption,
            authentication_mode=authentication_mode,
            properties=properties,
        )

        sql_server_parameters_spec.additional_properties = d
        return sql_server_parameters_spec

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
