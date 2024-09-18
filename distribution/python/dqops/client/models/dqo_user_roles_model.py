from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_user_role import DqoUserRole
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dqo_user_roles_model_data_domain_roles import (
        DqoUserRolesModelDataDomainRoles,
    )


T = TypeVar("T", bound="DqoUserRolesModel")


@_attrs_define
class DqoUserRolesModel:
    """DQOps user model - identifies a user in a multi-user DQOps deployment and the user's roles.

    Attributes:
        email (Union[Unset, str]): User's email that identifies the user.
        account_role (Union[Unset, DqoUserRole]):
        data_domain_roles (Union[Unset, DqoUserRolesModelDataDomainRoles]): User roles within each data domain. Data
            domains are supported in an ENTERPRISE version of DQOps and they are managed by the SaaS components of DQOps
            Cloud.
    """

    email: Union[Unset, str] = UNSET
    account_role: Union[Unset, DqoUserRole] = UNSET
    data_domain_roles: Union[Unset, "DqoUserRolesModelDataDomainRoles"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        email = self.email
        account_role: Union[Unset, str] = UNSET
        if not isinstance(self.account_role, Unset):
            account_role = self.account_role.value

        data_domain_roles: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_domain_roles, Unset):
            data_domain_roles = self.data_domain_roles.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if email is not UNSET:
            field_dict["email"] = email
        if account_role is not UNSET:
            field_dict["accountRole"] = account_role
        if data_domain_roles is not UNSET:
            field_dict["dataDomainRoles"] = data_domain_roles

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_user_roles_model_data_domain_roles import (
            DqoUserRolesModelDataDomainRoles,
        )

        d = src_dict.copy()
        email = d.pop("email", UNSET)

        _account_role = d.pop("accountRole", UNSET)
        account_role: Union[Unset, DqoUserRole]
        if isinstance(_account_role, Unset):
            account_role = UNSET
        else:
            account_role = DqoUserRole(_account_role)

        _data_domain_roles = d.pop("dataDomainRoles", UNSET)
        data_domain_roles: Union[Unset, DqoUserRolesModelDataDomainRoles]
        if isinstance(_data_domain_roles, Unset):
            data_domain_roles = UNSET
        else:
            data_domain_roles = DqoUserRolesModelDataDomainRoles.from_dict(
                _data_domain_roles
            )

        dqo_user_roles_model = cls(
            email=email,
            account_role=account_role,
            data_domain_roles=data_domain_roles,
        )

        dqo_user_roles_model.additional_properties = d
        return dqo_user_roles_model

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
