from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.credential_type import CredentialType
from ..types import UNSET, Unset

T = TypeVar("T", bound="SharedCredentialModel")


@_attrs_define
class SharedCredentialModel:
    """Shared credentials full model used to create and update the credential. Contains one of two forms of the
    credential's value: a text or a base64 binary value.

        Attributes:
            credential_name (Union[Unset, str]): Credential name. It is the name of a file in the .credentials/ folder
                inside the DQOps user's home folder.
            type (Union[Unset, CredentialType]):
            text_value (Union[Unset, str]): Credential's value as a text. Only one value (the text_value or binary_value)
                should be not empty.
            binary_value (Union[Unset, str]): Credential's value for a binary credential that is stored as a base64 value.
                Only one value (the text_value or binary_value) should be not empty.
    """

    credential_name: Union[Unset, str] = UNSET
    type: Union[Unset, CredentialType] = UNSET
    text_value: Union[Unset, str] = UNSET
    binary_value: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        credential_name = self.credential_name
        type: Union[Unset, str] = UNSET
        if not isinstance(self.type, Unset):
            type = self.type.value

        text_value = self.text_value
        binary_value = self.binary_value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if credential_name is not UNSET:
            field_dict["credential_name"] = credential_name
        if type is not UNSET:
            field_dict["type"] = type
        if text_value is not UNSET:
            field_dict["text_value"] = text_value
        if binary_value is not UNSET:
            field_dict["binary_value"] = binary_value

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

        text_value = d.pop("text_value", UNSET)

        binary_value = d.pop("binary_value", UNSET)

        shared_credential_model = cls(
            credential_name=credential_name,
            type=type,
            text_value=text_value,
            binary_value=binary_value,
        )

        shared_credential_model.additional_properties = d
        return shared_credential_model

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
