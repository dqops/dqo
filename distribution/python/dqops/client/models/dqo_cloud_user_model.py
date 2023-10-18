from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_user_role import DqoUserRole
from ..types import UNSET, Unset

T = TypeVar("T", bound="DqoCloudUserModel")


@_attrs_define
class DqoCloudUserModel:
    """DQOps Cloud user model - identifies a user in a multi-user DQOps deployment.

    Attributes:
        email (Union[Unset, str]): User's email that identifies the user.
        account_role (Union[Unset, DqoUserRole]):
    """

    email: Union[Unset, str] = UNSET
    account_role: Union[Unset, DqoUserRole] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        email = self.email
        account_role: Union[Unset, str] = UNSET
        if not isinstance(self.account_role, Unset):
            account_role = self.account_role.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if email is not UNSET:
            field_dict["email"] = email
        if account_role is not UNSET:
            field_dict["accountRole"] = account_role

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        email = d.pop("email", UNSET)

        _account_role = d.pop("accountRole", UNSET)
        account_role: Union[Unset, DqoUserRole]
        if isinstance(_account_role, Unset):
            account_role = UNSET
        else:
            account_role = DqoUserRole(_account_role)

        dqo_cloud_user_model = cls(
            email=email,
            account_role=account_role,
        )

        dqo_cloud_user_model.additional_properties = d
        return dqo_cloud_user_model

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
