from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_default_checks_pattern_spec import (
        ColumnDefaultChecksPatternSpec,
    )


T = TypeVar("T", bound="DefaultColumnChecksPatternModel")


@_attrs_define
class DefaultColumnChecksPatternModel:
    """Default column-level checks pattern model

    Attributes:
        pattern_name (Union[Unset, str]): Pattern name
        pattern_spec (Union[Unset, ColumnDefaultChecksPatternSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    pattern_name: Union[Unset, str] = UNSET
    pattern_spec: Union[Unset, "ColumnDefaultChecksPatternSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        pattern_name = self.pattern_name
        pattern_spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.pattern_spec, Unset):
            pattern_spec = self.pattern_spec.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if pattern_name is not UNSET:
            field_dict["pattern_name"] = pattern_name
        if pattern_spec is not UNSET:
            field_dict["pattern_spec"] = pattern_spec
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_default_checks_pattern_spec import (
            ColumnDefaultChecksPatternSpec,
        )

        d = src_dict.copy()
        pattern_name = d.pop("pattern_name", UNSET)

        _pattern_spec = d.pop("pattern_spec", UNSET)
        pattern_spec: Union[Unset, ColumnDefaultChecksPatternSpec]
        if isinstance(_pattern_spec, Unset):
            pattern_spec = UNSET
        else:
            pattern_spec = ColumnDefaultChecksPatternSpec.from_dict(_pattern_spec)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        default_column_checks_pattern_model = cls(
            pattern_name=pattern_name,
            pattern_spec=pattern_spec,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        default_column_checks_pattern_model.additional_properties = d
        return default_column_checks_pattern_model

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
