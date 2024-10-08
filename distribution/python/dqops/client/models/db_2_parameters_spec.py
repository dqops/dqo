from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.db_2_platform_type import Db2PlatformType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.db_2_parameters_spec_properties import Db2ParametersSpecProperties


T = TypeVar("T", bound="Db2ParametersSpec")


@_attrs_define
class Db2ParametersSpec:
    """
    Attributes:
        db2_platform_type (Union[Unset, Db2PlatformType]):
        host (Union[Unset, str]): DB2 host name. Supports also a ${DB2_HOST} configuration with a custom environment
            variable.
        port (Union[Unset, str]): DB2 port number. The default port is 50000. Supports also a ${DB2_PORT} configuration
            with a custom environment variable.
        database (Union[Unset, str]): DB2 database name. Supports also a ${DB2_DATABASE} configuration with a custom
            environment variable.
        user (Union[Unset, str]): DB2 user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use
            dynamic substitution.
        password (Union[Unset, str]): DB2 database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format
            to use dynamic substitution.
        properties (Union[Unset, Db2ParametersSpecProperties]): A dictionary of custom JDBC parameters that are added to
            the JDBC connection string, a key/value dictionary.
    """

    db2_platform_type: Union[Unset, Db2PlatformType] = UNSET
    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    database: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    properties: Union[Unset, "Db2ParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        db2_platform_type: Union[Unset, str] = UNSET
        if not isinstance(self.db2_platform_type, Unset):
            db2_platform_type = self.db2_platform_type.value

        host = self.host
        port = self.port
        database = self.database
        user = self.user
        password = self.password
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if db2_platform_type is not UNSET:
            field_dict["db2_platform_type"] = db2_platform_type
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
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.db_2_parameters_spec_properties import Db2ParametersSpecProperties

        d = src_dict.copy()
        _db2_platform_type = d.pop("db2_platform_type", UNSET)
        db2_platform_type: Union[Unset, Db2PlatformType]
        if isinstance(_db2_platform_type, Unset):
            db2_platform_type = UNSET
        else:
            db2_platform_type = Db2PlatformType(_db2_platform_type)

        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        database = d.pop("database", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, Db2ParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = Db2ParametersSpecProperties.from_dict(_properties)

        db_2_parameters_spec = cls(
            db2_platform_type=db2_platform_type,
            host=host,
            port=port,
            database=database,
            user=user,
            password=password,
            properties=properties,
        )

        db_2_parameters_spec.additional_properties = d
        return db_2_parameters_spec

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
