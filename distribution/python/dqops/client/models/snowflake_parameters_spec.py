from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.snowflake_parameters_spec_properties import (
        SnowflakeParametersSpecProperties,
    )


T = TypeVar("T", bound="SnowflakeParametersSpec")


@_attrs_define
class SnowflakeParametersSpec:
    """
    Attributes:
        account (Union[Unset, str]): Snowflake account name, e.q. <account>, <account>-<locator>, <account>.<region> or
            <account>.<region>.<platform>.. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment
            variable.
        warehouse (Union[Unset, str]): Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration
            with a custom environment variable.
        database (Union[Unset, str]): Snowflake database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        user (Union[Unset, str]): Snowflake user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to
            use dynamic substitution.
        password (Union[Unset, str]): Snowflake database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME}
            format to use dynamic substitution.
        role (Union[Unset, str]): Snowflake role name. Supports also ${SNOWFLAKE_ROLE} configuration with a custom
            environment variable.
        properties (Union[Unset, SnowflakeParametersSpecProperties]): A dictionary of custom JDBC parameters that are
            added to the JDBC connection string, a key/value dictionary.
    """

    account: Union[Unset, str] = UNSET
    warehouse: Union[Unset, str] = UNSET
    database: Union[Unset, str] = UNSET
    user: Union[Unset, str] = UNSET
    password: Union[Unset, str] = UNSET
    role: Union[Unset, str] = UNSET
    properties: Union[Unset, "SnowflakeParametersSpecProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        account = self.account
        warehouse = self.warehouse
        database = self.database
        user = self.user
        password = self.password
        role = self.role
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if account is not UNSET:
            field_dict["account"] = account
        if warehouse is not UNSET:
            field_dict["warehouse"] = warehouse
        if database is not UNSET:
            field_dict["database"] = database
        if user is not UNSET:
            field_dict["user"] = user
        if password is not UNSET:
            field_dict["password"] = password
        if role is not UNSET:
            field_dict["role"] = role
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.snowflake_parameters_spec_properties import (
            SnowflakeParametersSpecProperties,
        )

        d = src_dict.copy()
        account = d.pop("account", UNSET)

        warehouse = d.pop("warehouse", UNSET)

        database = d.pop("database", UNSET)

        user = d.pop("user", UNSET)

        password = d.pop("password", UNSET)

        role = d.pop("role", UNSET)

        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, SnowflakeParametersSpecProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = SnowflakeParametersSpecProperties.from_dict(_properties)

        snowflake_parameters_spec = cls(
            account=account,
            warehouse=warehouse,
            database=database,
            user=user,
            password=password,
            role=role,
            properties=properties,
        )

        snowflake_parameters_spec.additional_properties = d
        return snowflake_parameters_spec

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
