from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_list_model import CheckListModel


T = TypeVar("T", bound="CheckContainerListModel")


@_attrs_define
class CheckContainerListModel:
    """Simplistic model that returns the list of data quality checks, their names, categories and "configured" flag.

    Attributes:
        checks (Union[Unset, List['CheckListModel']]): Simplistic list of all data quality checks.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can edit the check.
        can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
        can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
    """

    checks: Union[Unset, List["CheckListModel"]] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = []
            for checks_item_data in self.checks:
                checks_item = checks_item_data.to_dict()

                checks.append(checks_item)

        can_edit = self.can_edit
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if checks is not UNSET:
            field_dict["checks"] = checks
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_list_model import CheckListModel

        d = src_dict.copy()
        checks = []
        _checks = d.pop("checks", UNSET)
        for checks_item_data in _checks or []:
            checks_item = CheckListModel.from_dict(checks_item_data)

            checks.append(checks_item)

        can_edit = d.pop("can_edit", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        check_container_list_model = cls(
            checks=checks,
            can_edit=can_edit,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
        )

        check_container_list_model.additional_properties = d
        return check_container_list_model

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
