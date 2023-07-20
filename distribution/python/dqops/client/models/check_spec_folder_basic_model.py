from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_spec_basic_model import CheckSpecBasicModel
    from ..models.check_spec_folder_basic_model_folders import (
        CheckSpecFolderBasicModelFolders,
    )


T = TypeVar("T", bound="CheckSpecFolderBasicModel")


@attr.s(auto_attribs=True)
class CheckSpecFolderBasicModel:
    """Check spec folder basic model

    Attributes:
        folders (Union[Unset, CheckSpecFolderBasicModelFolders]): A map of folder-level children checks.
        checks (Union[Unset, List['CheckSpecBasicModel']]): Check basic model list of checks defined at this level.
        all_checks (Union[Unset, List['CheckSpecBasicModel']]):
    """

    folders: Union[Unset, "CheckSpecFolderBasicModelFolders"] = UNSET
    checks: Union[Unset, List["CheckSpecBasicModel"]] = UNSET
    all_checks: Union[Unset, List["CheckSpecBasicModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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

        all_checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.all_checks, Unset):
            all_checks = []
            for all_checks_item_data in self.all_checks:
                all_checks_item = all_checks_item_data.to_dict()

                all_checks.append(all_checks_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folders is not UNSET:
            field_dict["folders"] = folders
        if checks is not UNSET:
            field_dict["checks"] = checks
        if all_checks is not UNSET:
            field_dict["all_checks"] = all_checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_spec_basic_model import CheckSpecBasicModel
        from ..models.check_spec_folder_basic_model_folders import (
            CheckSpecFolderBasicModelFolders,
        )

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, CheckSpecFolderBasicModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = CheckSpecFolderBasicModelFolders.from_dict(_folders)

        checks = []
        _checks = d.pop("checks", UNSET)
        for checks_item_data in _checks or []:
            checks_item = CheckSpecBasicModel.from_dict(checks_item_data)

            checks.append(checks_item)

        all_checks = []
        _all_checks = d.pop("all_checks", UNSET)
        for all_checks_item_data in _all_checks or []:
            all_checks_item = CheckSpecBasicModel.from_dict(all_checks_item_data)

            all_checks.append(all_checks_item)

        check_spec_folder_basic_model = cls(
            folders=folders,
            checks=checks,
            all_checks=all_checks,
        )

        check_spec_folder_basic_model.additional_properties = d
        return check_spec_folder_basic_model

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
