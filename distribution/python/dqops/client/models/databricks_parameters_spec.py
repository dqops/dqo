from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.databricks_parameters_spec_properties import (
        DatabricksParametersSpecProperties,
    )


T = TypeVar("T", bound="DatabricksParametersSpec")


@_attrs_define
class DatabricksParametersSpec:
    """
    Attributes:
        host (Union[Unset, str]): Databricks host name. Supports also a ${DATABRICKS_HOST} configuration with a custom
            environment variable.
        port (Union[Unset, str]): Databricks port number. The default port is 443. Supports also a ${DATABRICKS_PORT}
            configuration with a custom environment variable.
        catalog (Union[Unset, str]): Databricks catalog name. Supports also a ${DATABRICKS_CATALOG} configuration with a
            custom environment variable.
        user (Union[Unset, str]): Databricks user name. Supports also a ${DATABRICKS_USER} configuration with a custom
            environment variable.
        password (Union[Unset, str]): Databricks database password. Supports also a ${DATABRICKS_PASSWORD} configuration
            with a custom environment variable.
        http_path (Union[Unset, str]): Databricks http path to the warehouse. For example:
            /sql/1.0/warehouses/<warehouse instance id>. Supports also a ${DATABRICKS_HTTP_PATH} configuration with a custom
            environment variable.
        access_token (Union[Unset, str]): Databricks access token the warehouse. Supports also a
            ${DATABRICKS_ACCESS_TOKEN} configuration with a custom environment variable.
        properties (Union[Unset, DatabricksParametersSpecProperties]): A dictionary of custom JDBC parameters that are
            added to the JDBC connection string, a key/value dictionary.
        database (Union[Unset, str]):
    """

    host: Union[Unset, str] = UNSET
    port: Union[Unset, str] = UNSET
    catalog: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    http_path: Union[Unset, str] = UNSET
    access_token: Union[Unset, str] = UNSET
    properties: Union[Unset, "DatabricksParametersSpecProperties"] = UNSET
    database: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        host = self.host
        port = self.port
        catalog = self.catalog
        user = self.user
        password = self.password
        http_path = self.http_path
        access_token = self.access_token
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
        if catalog is not UNSET:
            field_dict["catalog"] = catalog
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if http_path is not UNSET:
            field_dict["http_path"] = http_path
        if access_token is not UNSET:
            field_dict["access_token"] = access_token
        if properties is not UNSET:
            field_dict["properties"] = properties
        if database is not UNSET:
            field_dict["database"] = database

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.databricks_parameters_spec_properties import (
            DatabricksParametersSpecProperties,
        )

        d = src_dict.copy()
        host = d.pop("host", UNSET)

        port = d.pop("port", UNSET)

        catalog = d.pop("catalog", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        http_path = d.pop("http_path", UNSET)

        access_token = d.pop("access_token", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, DatabricksParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = DatabricksParametersSpecProperties.from_dict(_properties)

        database = d.pop("database", UNSET)

        databricks_parameters_spec = cls(
            host=host,
            port=port,
            catalog=catalog,
            user=user,
            password=password,
            http_path=http_path,
            access_token=access_token,
            properties=properties,
            database=database,
        )

        databricks_parameters_spec.additional_properties = d
        return databricks_parameters_spec

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
