from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="DataDictionaryModel")


@_attrs_define
class DataDictionaryModel:
    """Data dictionary CSV file full model used to create and update the dictionary. Contains the content of the CSV file
    as a text field.

        Attributes:
            dictionary_name (Union[Unset, str]): Dictionary name. It is the name of a file in the dictionaries/ folder
                inside the DQOps user's home folder.
            file_content (Union[Unset, str]): Dictionary CSV file content as a single file.
    """

    dictionary_name: Union[Unset, str] = UNSET
    file_content: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        dictionary_name = self.dictionary_name
        file_content = self.file_content

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if dictionary_name is not UNSET:
            field_dict["dictionary_name"] = dictionary_name
        if file_content is not UNSET:
            field_dict["file_content"] = file_content

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        dictionary_name = d.pop("dictionary_name", UNSET)

        file_content = d.pop("file_content", UNSET)

        data_dictionary_model = cls(
            dictionary_name=dictionary_name,
            file_content=file_content,
        )

        data_dictionary_model.additional_properties = d
        return data_dictionary_model

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
