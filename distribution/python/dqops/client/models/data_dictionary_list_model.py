from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="DataDictionaryListModel")


@_attrs_define
class DataDictionaryListModel:
    """Data dictionary CSV file list model with the basic information about the dictionary.

    Attributes:
        dictionary_name (Union[Unset, str]): Dictionary name. It is the name of a file in the dictionaries/ folder
            inside the DQOps user's home folder.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete the dictionary
            file.
        can_access_dictionary (Union[Unset, bool]): Boolean flag that decides if the current user see or download the
            dictionary file.
    """

    dictionary_name: Union[Unset, str] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_access_dictionary: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        dictionary_name = self.dictionary_name
        can_edit = self.can_edit
        can_access_dictionary = self.can_access_dictionary

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if dictionary_name is not UNSET:
            field_dict["dictionary_name"] = dictionary_name
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_access_dictionary is not UNSET:
            field_dict["can_access_dictionary"] = can_access_dictionary

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        dictionary_name = d.pop("dictionary_name", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        can_access_dictionary = d.pop("can_access_dictionary", UNSET)

        data_dictionary_list_model = cls(
            dictionary_name=dictionary_name,
            can_edit=can_edit,
            can_access_dictionary=can_access_dictionary,
        )

        data_dictionary_list_model.additional_properties = d
        return data_dictionary_list_model

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
