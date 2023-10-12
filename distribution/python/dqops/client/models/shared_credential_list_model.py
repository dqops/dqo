from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.credential_type import CredentialType
from ..types import UNSET, Unset

T = TypeVar("T", bound="SharedCredentialListModel")


@_attrs_define
class SharedCredentialListModel:
    """Shared credentials list model with the basic information about the credential.

    Attributes:
        credential_name (Union[Unset, str]): Credential name. It is the name of a file in the .credentials/ folder
            inside the DQOps user's home folder.
        type (Union[Unset, CredentialType]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete the shared
            credential file.
        can_access_credential (Union[Unset, bool]): Boolean flag that decides if the current user see or download the
            credential file.
    """

    credential_name: Union[Unset, str] = UNSET
    type: Union[Unset, CredentialType] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_access_credential: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        credential_name = self.credential_name
        type: Union[Unset, str] = UNSET
        if not isinstance(self.type, Unset):
            type = self.type.value

        can_edit = self.can_edit
        can_access_credential = self.can_access_credential

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if credential_name is not UNSET:
            field_dict["credential_name"] = credential_name
        if type is not UNSET:
            field_dict["type"] = type
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_access_credential is not UNSET:
            field_dict["can_access_credential"] = can_access_credential

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        credential_name = d.pop("credential_name", UNSET)

        _type = d.pop("type", UNSET)
        type: Union[Unset, CredentialType]
        if isinstance(_type, Unset):
            type = UNSET
        else:
            type = CredentialType(_type)

        can_edit = d.pop("can_edit", UNSET)

        can_access_credential = d.pop("can_access_credential", UNSET)

        shared_credential_list_model = cls(
            credential_name=credential_name,
            type=type,
            can_edit=can_edit,
            can_access_credential=can_access_credential,
        )

        shared_credential_list_model.additional_properties = d
        return shared_credential_list_model

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
