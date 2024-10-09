from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.hana_parameters_spec_properties import HanaParametersSpecProperties


T = TypeVar("T", bound="HanaParametersSpec")


@_attrs_define
class HanaParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): Hana host name. Supports also a ${HANA_HOST} configuration with a custom environment
            variable.
        port (Union[Unset, str]): Hana port number. The default port is 30015. Supports also a ${HANA_PORT}
            configuration with a custom environment variable.
        instance_number (Union[Unset, str]): Hana instance number. Supports also a ${HANA_INSTANCE_NUMBER} configuration
            with a custom environment variable.
        user (Union[Unset, str]): Hana user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use
            dynamic substitution.
        password (Union[Unset, str]): Hana database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        properties (Union[Unset, HanaParametersSpecProperties]): A dictionary of custom JDBC parameters that are added
            to the JDBC connection string, a key/value dictionary.
        database (Union[Unset, str]):
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    instance_number: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    properties: Union[Unset, "HanaParametersSpecProperties"] = UNSET
    database: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        instance_number = self.instance_number
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
        if instance_number is not UNSET:
            field_dict["instance_number"] = instance_number
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
        from ..models.hana_parameters_spec_properties import (
            HanaParametersSpecProperties,
        )

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        instance_number = d.pop("instance_number", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, HanaParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = HanaParametersSpecProperties.from_dict(_properties)

        database = d.pop("database", UNSET)

        hana_parameters_spec = cls(
            host=host,
            port=port,
            instance_number=instance_number,
            user=user,
            password=password,
            properties=properties,
            database=database,
        )

        hana_parameters_spec.additional_properties = d
        return hana_parameters_spec

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
