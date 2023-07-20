from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.rule_basic_folder_model_folders import RuleBasicFolderModelFolders
    from ..models.rule_basic_model import RuleBasicModel


T = TypeVar("T", bound="RuleBasicFolderModel")


@attr.s(auto_attribs=True)
class RuleBasicFolderModel:
    """Rule basic folder model

    Attributes:
        folders (Union[Unset, RuleBasicFolderModelFolders]): A map of folder-level children rules.
        rules (Union[Unset, List['RuleBasicModel']]): Rule basic model list
        all_rules (Union[Unset, List['RuleBasicModel']]):
    """

    folders: Union[Unset, "RuleBasicFolderModelFolders"] = UNSET
    rules: Union[Unset, List["RuleBasicModel"]] = UNSET
    all_rules: Union[Unset, List["RuleBasicModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        from ..models.rule_basic_folder_model_folders import RuleBasicFolderModelFolders
        from ..models.rule_basic_model import RuleBasicModel

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, RuleBasicFolderModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = RuleBasicFolderModelFolders.from_dict(_folders)

        rules = []
        _rules = d.pop("rules", UNSET)
        for rules_item_data in _rules or []:
            rules_item = RuleBasicModel.from_dict(rules_item_data)

            rules.append(rules_item)

        all_rules = []
        _all_rules = d.pop("all_rules", UNSET)
        for all_rules_item_data in _all_rules or []:
            all_rules_item = RuleBasicModel.from_dict(all_rules_item_data)

            all_rules.append(all_rules_item)

        rule_basic_folder_model = cls(
            folders=folders,
            rules=rules,
            all_rules=all_rules,
        )

        rule_basic_folder_model.additional_properties = d
        return rule_basic_folder_model

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
