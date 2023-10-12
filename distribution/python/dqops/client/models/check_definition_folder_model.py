from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_definition_folder_model_folders import (
        CheckDefinitionFolderModelFolders,
    )
    from ..models.check_definition_list_model import CheckDefinitionListModel


T = TypeVar("T", bound="CheckDefinitionFolderModel")


@_attrs_define
class CheckDefinitionFolderModel:
    """Check folder list model with a list of data quality checks in this folder or a list of nested folders.

    Attributes:
        folders (Union[Unset, CheckDefinitionFolderModelFolders]): A dictionary of nested folders with data quality
            checks. The keys are the folder names.
        checks (Union[Unset, List['CheckDefinitionListModel']]): List of data quality checks defined in this folder.
    """

    folders: Union[Unset, "CheckDefinitionFolderModelFolders"] = UNSET
    checks: Union[Unset, List["CheckDefinitionListModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folders: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.folders, Unset):
            folders = self.folders.to_dict()

        checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = []
            for checks_item_data in self.checks:
                checks_item = checks_item_data.to_dict()

                checks.append(checks_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folders is not UNSET:
            field_dict["folders"] = folders
        if checks is not UNSET:
            field_dict["checks"] = checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_definition_folder_model_folders import (
            CheckDefinitionFolderModelFolders,
        )
        from ..models.check_definition_list_model import CheckDefinitionListModel

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, CheckDefinitionFolderModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = CheckDefinitionFolderModelFolders.from_dict(_folders)

        checks = []
        _checks = d.pop("checks", UNSET)
        for checks_item_data in _checks or []:
            checks_item = CheckDefinitionListModel.from_dict(checks_item_data)

            checks.append(checks_item)

        check_definition_folder_model = cls(
            folders=folders,
            checks=checks,
        )

        check_definition_folder_model.additional_properties = d
        return check_definition_folder_model

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
