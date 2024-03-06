from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.target_column_pattern_spec import TargetColumnPatternSpec


T = TypeVar("T", bound="DefaultColumnChecksPatternListModel")


@_attrs_define
class DefaultColumnChecksPatternListModel:
    """Default column-level checks pattern list model

    Attributes:
        pattern_name (Union[Unset, str]): Pattern name.
        priority (Union[Unset, int]): The priority of the pattern. Patterns with lower values are applied before
            patterns with higher priority values.
        target_column (Union[Unset, TargetColumnPatternSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    pattern_name: Union[Unset, str] = UNSET
    priority: Union[Unset, int] = UNSET
    target_column: Union[Unset, "TargetColumnPatternSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        pattern_name = self.pattern_name
        priority = self.priority
        target_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target_column, Unset):
            target_column = self.target_column.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if pattern_name is not UNSET:
            field_dict["pattern_name"] = pattern_name
        if priority is not UNSET:
            field_dict["priority"] = priority
        if target_column is not UNSET:
            field_dict["target_column"] = target_column
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.target_column_pattern_spec import TargetColumnPatternSpec

        d = src_dict.copy()
        pattern_name = d.pop("pattern_name", UNSET)

        priority = d.pop("priority", UNSET)

        _target_column = d.pop("target_column", UNSET)
        target_column: Union[Unset, TargetColumnPatternSpec]
        if isinstance(_target_column, Unset):
            target_column = UNSET
        else:
            target_column = TargetColumnPatternSpec.from_dict(_target_column)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        default_column_checks_pattern_list_model = cls(
            pattern_name=pattern_name,
            priority=priority,
            target_column=target_column,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        default_column_checks_pattern_list_model.additional_properties = d
        return default_column_checks_pattern_list_model

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
