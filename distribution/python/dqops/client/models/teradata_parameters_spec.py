from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.teradata_parameters_spec_properties import (
        TeradataParametersSpecProperties,
    )


T = TypeVar("T", bound="TeradataParametersSpec")


@_attrs_define
class TeradataParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): Teradata host name. Supports also a ${TERADATA_HOST} configuration with a custom
            environment variable.
        port (Union[Unset, str]): Teradata port number. The default port is 1025. Supports also a ${TERADATA_PORT}
            configuration with a custom environment variable.
        user (Union[Unset, str]): Teradata user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use
            dynamic substitution.
        password (Union[Unset, str]): Teradata database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        properties (Union[Unset, TeradataParametersSpecProperties]): A dictionary of custom JDBC parameters that are
            added to the JDBC connection string, a key/value dictionary.
        database (Union[Unset, str]):
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    properties: Union[Unset, "TeradataParametersSpecProperties"] = UNSET
    database: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        user = self.user
        password = self.password
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        database = self.database

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if host is not UNSET:
            field_dict["host"] = host
        if port is not UNSET:
            field_dict["port"] = port
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if properties is not UNSET:
            field_dict["properties"] = properties
        if database is not UNSET:
            field_dict["database"] = database

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.teradata_parameters_spec_properties import (
            TeradataParametersSpecProperties,
        )

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, TeradataParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = TeradataParametersSpecProperties.from_dict(_properties)

        database = d.pop("database", UNSET)

        teradata_parameters_spec = cls(
            host=host,
            port=port,
            user=user,
            password=password,
            properties=properties,
            database=database,
        )

        teradata_parameters_spec.additional_properties = d
        return teradata_parameters_spec

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
