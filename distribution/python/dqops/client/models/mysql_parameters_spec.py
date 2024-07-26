from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.my_sql_ssl_mode import MySqlSslMode
from ..models.mysql_engine_type import MysqlEngineType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.mysql_parameters_spec_properties import MysqlParametersSpecProperties
    from ..models.single_store_db_parameters_spec import SingleStoreDbParametersSpec


T = TypeVar("T", bound="MysqlParametersSpec")


@_attrs_define
class MysqlParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment
            variable.
        port (Union[Unset, str]): MySQL port number. The default port is 3306. Supports also a ${MYSQL_PORT}
            configuration with a custom environment variable.
        database (Union[Unset, str]): MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format
            to use dynamic substitution.
        user (Union[Unset, str]): MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use
            dynamic substitution.
        password (Union[Unset, str]): MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        sslmode (Union[Unset, MySqlSslMode]):
        single_store_db_parameters_spec (Union[Unset, SingleStoreDbParametersSpec]):
        mysql_engine_type (Union[Unset, MysqlEngineType]):
        properties (Union[Unset, MysqlParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    database: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    sslmode: Union[Unset, MySqlSslMode] = UNSET
    single_store_db_parameters_spec: Union[Unset, "SingleStoreDbParametersSpec"] = UNSET
    mysql_engine_type: Union[Unset, MysqlEngineType] = UNSET
    properties: Union[Unset, "MysqlParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        database = self.database
        user = self.user
        password = self.password
        sslmode: Union[Unset, str] = UNSET
        if not isinstance(self.sslmode, Unset):
            sslmode = self.sslmode.value

        single_store_db_parameters_spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.single_store_db_parameters_spec, Unset):
            single_store_db_parameters_spec = (
                self.single_store_db_parameters_spec.to_dict()
            )

        mysql_engine_type: Union[Unset, str] = UNSET
        if not isinstance(self.mysql_engine_type, Unset):
            mysql_engine_type = self.mysql_engine_type.value

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
        if sslmode is not UNSET:
            field_dict["sslmode"] = sslmode
        if single_store_db_parameters_spec is not UNSET:
            field_dict["single_store_db_parameters_spec"] = (
                single_store_db_parameters_spec
            )
        if mysql_engine_type is not UNSET:
            field_dict["mysql_engine_type"] = mysql_engine_type
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.mysql_parameters_spec_properties import (
            MysqlParametersSpecProperties,
        )
        from ..models.single_store_db_parameters_spec import SingleStoreDbParametersSpec

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        database = d.pop("database", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        _sslmode = d.pop("sslmode", UNSET)
        sslmode: Union[Unset, MySqlSslMode]
        if isinstance(_sslmode, Unset):
            sslmode = UNSET
        else:
            sslmode = MySqlSslMode(_sslmode)

        _single_store_db_parameters_spec = d.pop(
            "single_store_db_parameters_spec", UNSET
        )
        single_store_db_parameters_spec: Union[Unset, SingleStoreDbParametersSpec]
        if isinstance(_single_store_db_parameters_spec, Unset):
            single_store_db_parameters_spec = UNSET
        else:
            single_store_db_parameters_spec = SingleStoreDbParametersSpec.from_dict(
                _single_store_db_parameters_spec
            )

        _mysql_engine_type = d.pop("mysql_engine_type", UNSET)
        mysql_engine_type: Union[Unset, MysqlEngineType]
        if isinstance(_mysql_engine_type, Unset):
            mysql_engine_type = UNSET
        else:
            mysql_engine_type = MysqlEngineType(_mysql_engine_type)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, MysqlParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = MysqlParametersSpecProperties.from_dict(_properties)

        mysql_parameters_spec = cls(
            host=host,
            port=port,
            database=database,
            user=user,
            password=password,
            sslmode=sslmode,
            single_store_db_parameters_spec=single_store_db_parameters_spec,
            mysql_engine_type=mysql_engine_type,
            properties=properties,
        )

        mysql_parameters_spec.additional_properties = d
        return mysql_parameters_spec

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
