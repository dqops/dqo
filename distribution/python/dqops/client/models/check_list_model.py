from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckListModel")


@_attrs_define
class CheckListModel:
    """Simplistic model that returns a single data quality check, its name and "configured" flag

    Attributes:
        check_category (Union[Unset, str]): Check category.
        check_name (Union[Unset, str]): Data quality check name that is used in YAML.
        help_text (Union[Unset, str]): Help text that describes the data quality check.
        configured (Union[Unset, bool]): True if the data quality check is configured (not null). When saving the data
            quality check configuration, set the flag to true for storing the check.
    """

    check_category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    configured: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_category = self.check_category
        check_name = self.check_name
        help_text = self.help_text
        configured = self.configured

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_category is not UNSET:
            field_dict["check_category"] = check_category
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if configured is not UNSET:
            field_dict["configured"] = configured

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        check_category = d.pop("check_category", UNSET)

        check_name = d.pop("check_name", UNSET)

        help_text = d.pop("help_text", UNSET)

        configured = d.pop("configured", UNSET)

        check_list_model = cls(
            check_category=check_category,
            check_name=check_name,
            help_text=help_text,
            configured=configured,
        )

        check_list_model.additional_properties = d
        return check_list_model

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
