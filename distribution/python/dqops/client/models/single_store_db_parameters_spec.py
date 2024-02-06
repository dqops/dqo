from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.single_store_db_load_balancing_mode import SingleStoreDbLoadBalancingMode
from ..types import UNSET, Unset

T = TypeVar("T", bound="SingleStoreDbParametersSpec")


@_attrs_define
class SingleStoreDbParametersSpec:
    """
    Attributes:
        load_balancing_mode (Union[Unset, SingleStoreDbLoadBalancingMode]):
        host_descriptions (Union[Unset, List[str]]): SingleStoreDB Host descriptions. Supports also a
            ${SINGLE_STORE_HOST_DESCRIPTIONS} configuration with a custom environment variable.
        schema (Union[Unset, str]): SingleStoreDB database/schema name. The value can be in the
            ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.
        use_ssl (Union[Unset, bool]): Force enables SSL/TLS on the connection. Supports also a ${SINGLE_STORE_USE_SSL}
            configuration with a custom environment variable.
    """

    load_balancing_mode: Union[Unset, SingleStoreDbLoadBalancingMode] = UNSET
    host_descriptions: Union[Unset, List[str]] = UNSET
    schema: Union[Unset, str] = UNSET
    use_ssl: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        load_balancing_mode: Union[Unset, str] = UNSET
        if not isinstance(self.load_balancing_mode, Unset):
            load_balancing_mode = self.load_balancing_mode.value

        host_descriptions: Union[Unset, List[str]] = UNSET
        if not isinstance(self.host_descriptions, Unset):
            host_descriptions = self.host_descriptions

        schema = self.schema
        use_ssl = self.use_ssl

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if load_balancing_mode is not UNSET:
            field_dict["load_balancing_mode"] = load_balancing_mode
        if host_descriptions is not UNSET:
            field_dict["host_descriptions"] = host_descriptions
        if schema is not UNSET:
            field_dict["schema"] = schema
        if use_ssl is not UNSET:
            field_dict["use_ssl"] = use_ssl

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _load_balancing_mode = d.pop("load_balancing_mode", UNSET)
        load_balancing_mode: Union[Unset, SingleStoreDbLoadBalancingMode]
        if isinstance(_load_balancing_mode, Unset):
            load_balancing_mode = UNSET
        else:
            load_balancing_mode = SingleStoreDbLoadBalancingMode(_load_balancing_mode)

        host_descriptions = cast(List[str], d.pop("host_descriptions", UNSET))

        schema = d.pop("schema", UNSET)

        use_ssl = d.pop("use_ssl", UNSET)

        single_store_db_parameters_spec = cls(
            load_balancing_mode=load_balancing_mode,
            host_descriptions=host_descriptions,
            schema=schema,
            use_ssl=use_ssl,
        )

        single_store_db_parameters_spec.additional_properties = d
        return single_store_db_parameters_spec

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
