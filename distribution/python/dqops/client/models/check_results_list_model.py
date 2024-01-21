from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_result_entry_model import CheckResultEntryModel


T = TypeVar("T", bound="CheckResultsListModel")


@_attrs_define
class CheckResultsListModel:
    """
    Attributes:
        check_hash (Union[Unset, int]): Check hash
        check_category (Union[Unset, str]): Check category name
        check_name (Union[Unset, str]): Check name
        check_display_name (Union[Unset, str]): Check display name
        check_type (Union[Unset, CheckType]):
        data_groups (Union[Unset, List[str]]): Data groups list
        data_group (Union[Unset, str]): Selected data group
        check_result_entries (Union[Unset, List['CheckResultEntryModel']]): Single check results
    """

    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    data_groups: Union[Unset, List[str]] = UNSET
    data_group: Union[Unset, str] = UNSET
    check_result_entries: Union[Unset, List["CheckResultEntryModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_hash = self.check_hash
        check_category = self.check_category
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        data_groups: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_groups, Unset):
            data_groups = self.data_groups

        data_group = self.data_group
        check_result_entries: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.check_result_entries, Unset):
            check_result_entries = []
            for check_result_entries_item_data in self.check_result_entries:
                check_result_entries_item = check_result_entries_item_data.to_dict()

                check_result_entries.append(check_result_entries_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if check_display_name is not UNSET:
            field_dict["checkDisplayName"] = check_display_name
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if data_groups is not UNSET:
            field_dict["dataGroups"] = data_groups
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if check_result_entries is not UNSET:
            field_dict["checkResultEntries"] = check_result_entries

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_result_entry_model import CheckResultEntryModel

        d = src_dict.copy()
        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        check_name = d.pop("checkName", UNSET)

        check_display_name = d.pop("checkDisplayName", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        data_groups = cast(List[str], d.pop("dataGroups", UNSET))

        data_group = d.pop("dataGroup", UNSET)

        check_result_entries = []
        _check_result_entries = d.pop("checkResultEntries", UNSET)
        for check_result_entries_item_data in _check_result_entries or []:
            check_result_entries_item = CheckResultEntryModel.from_dict(
                check_result_entries_item_data
            )

            check_result_entries.append(check_result_entries_item)

        check_results_list_model = cls(
            check_hash=check_hash,
            check_category=check_category,
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            data_groups=data_groups,
            data_group=data_group,
            check_result_entries=check_result_entries,
        )

        check_results_list_model.additional_properties = d
        return check_results_list_model

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
