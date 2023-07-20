from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.error_detailed_single_model import ErrorDetailedSingleModel


T = TypeVar("T", bound="ErrorsDetailedDataModel")


@attr.s(auto_attribs=True)
class ErrorsDetailedDataModel:
    """
    Attributes:
        check_name (Union[Unset, str]): Check name.
        check_display_name (Union[Unset, str]): Check display name.
        check_type (Union[Unset, str]): Check type.
        check_hash (Union[Unset, int]): Check hash.
        check_category (Union[Unset, str]): Check category name.
        data_groups_names (Union[Unset, List[str]]): Data groups list.
        data_group (Union[Unset, str]): Selected data group.
        single_errors (Union[Unset, List['ErrorDetailedSingleModel']]): Single error statuses
    """

    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, str] = UNSET
    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    data_groups_names: Union[Unset, List[str]] = UNSET
    data_group: Union[Unset, str] = UNSET
    single_errors: Union[Unset, List["ErrorDetailedSingleModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type = self.check_type
        check_hash = self.check_hash
        check_category = self.check_category
        data_groups_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_groups_names, Unset):
            data_groups_names = self.data_groups_names

        data_group = self.data_group
        single_errors: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.single_errors, Unset):
            single_errors = []
            for single_errors_item_data in self.single_errors:
                single_errors_item = single_errors_item_data.to_dict()

                single_errors.append(single_errors_item)

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
        if single_errors is not UNSET:
            field_dict["singleErrors"] = single_errors

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.error_detailed_single_model import ErrorDetailedSingleModel

        d = src_dict.copy()
        check_name = d.pop("checkName", UNSET)

        check_display_name = d.pop("checkDisplayName", UNSET)

        check_type = d.pop("checkType", UNSET)

        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        data_groups_names = cast(List[str], d.pop("dataGroupsNames", UNSET))

        data_group = d.pop("dataGroup", UNSET)

        single_errors = []
        _single_errors = d.pop("singleErrors", UNSET)
        for single_errors_item_data in _single_errors or []:
            single_errors_item = ErrorDetailedSingleModel.from_dict(
                single_errors_item_data
            )

            single_errors.append(single_errors_item)

        errors_detailed_data_model = cls(
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            check_hash=check_hash,
            check_category=check_category,
            data_groups_names=data_groups_names,
            data_group=data_group,
            single_errors=single_errors,
        )

        errors_detailed_data_model.additional_properties = d
        return errors_detailed_data_model

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
