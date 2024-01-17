from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.postgresql_ssl_mode import PostgresqlSslMode
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.postgresql_parameters_spec_properties import (
        PostgresqlParametersSpecProperties,
    )


T = TypeVar("T", bound="PostgresqlParametersSpec")


@_attrs_define
class PostgresqlParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom
            environment variable.
        port (Union[Unset, str]): PostgreSQL port number. The default port is 5432. Supports also a ${POSTGRESQL_PORT}
            configuration with a custom environment variable.
        database (Union[Unset, str]): PostgreSQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        user (Union[Unset, str]): PostgreSQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to
            use dynamic substitution.
        password (Union[Unset, str]): PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        options (Union[Unset, str]): PostgreSQL connection 'options' initialization parameter. For example setting this
            to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports
            also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.
        sslmode (Union[Unset, PostgresqlSslMode]):
        properties (Union[Unset, PostgresqlParametersSpecProperties]): A dictionary of custom JDBC parameters that are
            added to the JDBC connection string, a key/value dictionary.
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    database: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    options: Union[Unset, str] = UNSET
    sslmode: Union[Unset, PostgresqlSslMode] = UNSET
    properties: Union[Unset, "PostgresqlParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        database = self.database
        user = self.user
        password = self.password
        options = self.options
        sslmode: Union[Unset, str] = UNSET
        if not isinstance(self.sslmode, Unset):
            sslmode = self.sslmode.value

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
        if options is not UNSET:
            field_dict["options"] = options
        if sslmode is not UNSET:
            field_dict["sslmode"] = sslmode
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.postgresql_parameters_spec_properties import (
            PostgresqlParametersSpecProperties,
        )

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        database = d.pop("database", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        options = d.pop("options", UNSET)

        _sslmode = d.pop("sslmode", UNSET)
        sslmode: Union[Unset, PostgresqlSslMode]
        if isinstance(_sslmode, Unset):
            sslmode = UNSET
        else:
            sslmode = PostgresqlSslMode(_sslmode)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, PostgresqlParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = PostgresqlParametersSpecProperties.from_dict(_properties)

        postgresql_parameters_spec = cls(
            host=host,
            port=port,
            database=database,
            user=user,
            password=password,
            options=options,
            sslmode=sslmode,
            properties=properties,
        )

        postgresql_parameters_spec.additional_properties = d
        return postgresql_parameters_spec

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
