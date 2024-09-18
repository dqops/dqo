from typing import Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_user_role import DqoUserRole

T = TypeVar("T", bound="DqoUserRolesModelDataDomainRoles")


@_attrs_define
class DqoUserRolesModelDataDomainRoles:
    """User roles within each data domain. Data domains are supported in an ENTERPRISE version of DQOps and they are
    managed by the SaaS components of DQOps Cloud.

    """

    additional_properties: Dict[str, DqoUserRole] = _attrs_field(
        init=False, factory=dict
    )

    def to_dict(self) -> Dict[str, Any]:

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.value

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        dqo_user_roles_model_data_domain_roles = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = DqoUserRole(prop_dict)

            additional_properties[prop_name] = additional_property

        dqo_user_roles_model_data_domain_roles.additional_properties = (
            additional_properties
        )
        return dqo_user_roles_model_data_domain_roles

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> DqoUserRole:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: DqoUserRole) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
