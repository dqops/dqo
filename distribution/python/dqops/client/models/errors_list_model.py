from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.error_entry_model import ErrorEntryModel


T = TypeVar("T", bound="ErrorsListModel")


@_attrs_define
class ErrorsListModel:
    """
    Attributes:
        check_name (Union[Unset, str]): Check name
        check_display_name (Union[Unset, str]): Check display name
        check_type (Union[Unset, CheckType]):
        check_hash (Union[Unset, int]): Check hash
        check_category (Union[Unset, str]): Check category name
        data_groups_names (Union[Unset, List[str]]): Data groups list
        data_group (Union[Unset, str]): Selected data group
        error_entries (Union[Unset, List['ErrorEntryModel']]): Error entries
    """

    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    data_groups_names: Union[Unset, List[str]] = UNSET
    data_group: Union[Unset, str] = UNSET
    error_entries: Union[Unset, List["ErrorEntryModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        check_hash = self.check_hash
        check_category = self.check_category
        data_groups_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_groups_names, Unset):
            data_groups_names = self.data_groups_names

        data_group = self.data_group
        error_entries: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.error_entries, Unset):
            error_entries = []
            for error_entries_item_data in self.error_entries:
                error_entries_item = error_entries_item_data.to_dict()

                error_entries.append(error_entries_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if check_display_name is not UNSET:
            field_dict["checkDisplayName"] = check_display_name
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if data_groups_names is not UNSET:
            field_dict["dataGroupsNames"] = data_groups_names
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if error_entries is not UNSET:
            field_dict["errorEntries"] = error_entries

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.error_entry_model import ErrorEntryModel

        d = src_dict.copy()
        check_name = d.pop("checkName", UNSET)

        check_display_name = d.pop("checkDisplayName", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        data_groups_names = cast(List[str], d.pop("dataGroupsNames", UNSET))

        data_group = d.pop("dataGroup", UNSET)

        error_entries = []
        _error_entries = d.pop("errorEntries", UNSET)
        for error_entries_item_data in _error_entries or []:
            error_entries_item = ErrorEntryModel.from_dict(error_entries_item_data)

            error_entries.append(error_entries_item)

        errors_list_model = cls(
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            check_hash=check_hash,
            check_category=check_category,
            data_groups_names=data_groups_names,
            data_group=data_group,
            error_entries=error_entries,
        )

        errors_list_model.additional_properties = d
        return errors_list_model

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
