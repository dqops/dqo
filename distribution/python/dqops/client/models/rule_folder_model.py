from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.rule_folder_model_folders import RuleFolderModelFolders
    from ..models.rule_list_model import RuleListModel


T = TypeVar("T", bound="RuleFolderModel")


@_attrs_define
class RuleFolderModel:
    """Rule folder model with a list of rules defined in this folder and nested folders that contain additional rules.

    Attributes:
        folders (Union[Unset, RuleFolderModelFolders]): A dictionary of nested folders with rules, the keys are the
            folder names.
        rules (Union[Unset, List['RuleListModel']]): List of rules defined in this folder.
        all_rules (Union[Unset, List['RuleListModel']]):
    """

    folders: Union[Unset, "RuleFolderModelFolders"] = UNSET
    rules: Union[Unset, List["RuleListModel"]] = UNSET
    all_rules: Union[Unset, List["RuleListModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folders: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.folders, Unset):
            folders = self.folders.to_dict()

        rules: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.rules, Unset):
            rules = []
            for rules_item_data in self.rules:
                rules_item = rules_item_data.to_dict()

                rules.append(rules_item)

        all_rules: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.all_rules, Unset):
            all_rules = []
            for all_rules_item_data in self.all_rules:
                all_rules_item = all_rules_item_data.to_dict()

                all_rules.append(all_rules_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folders is not UNSET:
            field_dict["folders"] = folders
        if rules is not UNSET:
            field_dict["rules"] = rules
        if all_rules is not UNSET:
            field_dict["all_rules"] = all_rules

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.rule_folder_model_folders import RuleFolderModelFolders
        from ..models.rule_list_model import RuleListModel

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, RuleFolderModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = RuleFolderModelFolders.from_dict(_folders)

        rules = []
        _rules = d.pop("rules", UNSET)
        for rules_item_data in _rules or []:
            rules_item = RuleListModel.from_dict(rules_item_data)

            rules.append(rules_item)

        all_rules = []
        _all_rules = d.pop("all_rules", UNSET)
        for all_rules_item_data in _all_rules or []:
            all_rules_item = RuleListModel.from_dict(all_rules_item_data)

            all_rules.append(all_rules_item)

        rule_folder_model = cls(
            folders=folders,
            rules=rules,
            all_rules=all_rules,
        )

        rule_folder_model.additional_properties = d
        return rule_folder_model

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
