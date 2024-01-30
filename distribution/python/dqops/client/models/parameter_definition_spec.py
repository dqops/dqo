from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.display_hint import DisplayHint
from ..models.parameter_data_type import ParameterDataType
from ..types import UNSET, Unset

T = TypeVar("T", bound="ParameterDefinitionSpec")


@_attrs_define
class ParameterDefinitionSpec:
    """Defines a single field that is a sensor parameter or a rule parameter.

    Attributes:
        field_name (Union[Unset, str]): Field name that matches the field name (snake_case) used in the YAML
            specification.
        display_name (Union[Unset, str]): Field display name that should be shown as a label for the control.
        help_text (Union[Unset, str]): Help text (full description) that will be shown to the user as a hint when the
            cursor is moved over the control.
        data_type (Union[Unset, ParameterDataType]):
        display_hint (Union[Unset, DisplayHint]):
        required (Union[Unset, bool]): True when the value for the parameter must be provided.
        allowed_values (Union[Unset, List[str]]): List of allowed values for a field that is of an enum type.
        default_value (Union[Unset, str]): The default value for a parameter in a custom check or a custom rule.
    """

    field_name: Union[Unset, str] = UNSET
    display_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    data_type: Union[Unset, ParameterDataType] = UNSET
    display_hint: Union[Unset, DisplayHint] = UNSET
    required: Union[Unset, bool] = UNSET
    allowed_values: Union[Unset, List[str]] = UNSET
    default_value: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        field_name = self.field_name
        display_name = self.display_name
        help_text = self.help_text
        data_type: Union[Unset, str] = UNSET
        if not isinstance(self.data_type, Unset):
            data_type = self.data_type.value

        display_hint: Union[Unset, str] = UNSET
        if not isinstance(self.display_hint, Unset):
            display_hint = self.display_hint.value

        required = self.required
        allowed_values: Union[Unset, List[str]] = UNSET
        if not isinstance(self.allowed_values, Unset):
            allowed_values = self.allowed_values

        default_value = self.default_value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if field_name is not UNSET:
            field_dict["field_name"] = field_name
        if display_name is not UNSET:
            field_dict["display_name"] = display_name
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if data_type is not UNSET:
            field_dict["data_type"] = data_type
        if display_hint is not UNSET:
            field_dict["display_hint"] = display_hint
        if required is not UNSET:
            field_dict["required"] = required
        if allowed_values is not UNSET:
            field_dict["allowed_values"] = allowed_values
        if default_value is not UNSET:
            field_dict["default_value"] = default_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        field_name = d.pop("field_name", UNSET)

        display_name = d.pop("display_name", UNSET)

        help_text = d.pop("help_text", UNSET)

        _data_type = d.pop("data_type", UNSET)
        data_type: Union[Unset, ParameterDataType]
        if isinstance(_data_type, Unset):
            data_type = UNSET
        else:
            data_type = ParameterDataType(_data_type)

        _display_hint = d.pop("display_hint", UNSET)
        display_hint: Union[Unset, DisplayHint]
        if isinstance(_display_hint, Unset):
            display_hint = UNSET
        else:
            display_hint = DisplayHint(_display_hint)

        required = d.pop("required", UNSET)

        allowed_values = cast(List[str], d.pop("allowed_values", UNSET))

        default_value = d.pop("default_value", UNSET)

        parameter_definition_spec = cls(
            field_name=field_name,
            display_name=display_name,
            help_text=help_text,
            data_type=data_type,
            display_hint=display_hint,
            required=required,
            allowed_values=allowed_values,
            default_value=default_value,
        )

        parameter_definition_spec.additional_properties = d
        return parameter_definition_spec

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
