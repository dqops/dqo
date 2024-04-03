from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.rule_runner_type import RuleRunnerType
from ..models.rule_time_window_mode import RuleTimeWindowMode
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.parameter_definition_spec import ParameterDefinitionSpec
    from ..models.rule_model_parameters import RuleModelParameters
    from ..models.rule_time_window_settings_spec import RuleTimeWindowSettingsSpec


T = TypeVar("T", bound="RuleModel")


@_attrs_define
class RuleModel:
    """Rule model

    Attributes:
        rule_name (Union[Unset, str]): Rule name
        rule_python_module_content (Union[Unset, str]): Rule Python module content
        type (Union[Unset, RuleRunnerType]):
        java_class_name (Union[Unset, str]): Java class name for a rule runner that will execute the sensor. The "type"
            must be "java_class".
        mode (Union[Unset, RuleTimeWindowMode]):
        time_window (Union[Unset, RuleTimeWindowSettingsSpec]):
        fields (Union[Unset, List['ParameterDefinitionSpec']]): List of fields that are parameters of a custom rule.
            Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls
            for all required fields.
        parameters (Union[Unset, RuleModelParameters]): Additional rule parameters
        custom (Union[Unset, bool]): This rule has a custom (user level) definition.
        built_in (Union[Unset, bool]): This rule has is a built-in rule.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    rule_name: Union[Unset, str] = UNSET
    rule_python_module_content: Union[Unset, str] = UNSET
    type: Union[Unset, RuleRunnerType] = UNSET
    java_class_name: Union[Unset, str] = UNSET
    mode: Union[Unset, RuleTimeWindowMode] = UNSET
    time_window: Union[Unset, "RuleTimeWindowSettingsSpec"] = UNSET
    fields: Union[Unset, List["ParameterDefinitionSpec"]] = UNSET
    parameters: Union[Unset, "RuleModelParameters"] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        rule_name = self.rule_name
        rule_python_module_content = self.rule_python_module_content
        type: Union[Unset, str] = UNSET
        if not isinstance(self.type, Unset):
            type = self.type.value

        java_class_name = self.java_class_name
        mode: Union[Unset, str] = UNSET
        if not isinstance(self.mode, Unset):
            mode = self.mode.value

        time_window: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.time_window, Unset):
            time_window = self.time_window.to_dict()

        fields: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.fields, Unset):
            fields = []
            for fields_item_data in self.fields:
                fields_item = fields_item_data.to_dict()

                fields.append(fields_item)

        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if rule_name is not UNSET:
            field_dict["rule_name"] = rule_name
        if rule_python_module_content is not UNSET:
            field_dict["rule_python_module_content"] = rule_python_module_content
        if type is not UNSET:
            field_dict["type"] = type
        if java_class_name is not UNSET:
            field_dict["java_class_name"] = java_class_name
        if mode is not UNSET:
            field_dict["mode"] = mode
        if time_window is not UNSET:
            field_dict["time_window"] = time_window
        if fields is not UNSET:
            field_dict["fields"] = fields
        if parameters is not UNSET:
            field_dict["parameters"] = parameters
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.parameter_definition_spec import ParameterDefinitionSpec
        from ..models.rule_model_parameters import RuleModelParameters
        from ..models.rule_time_window_settings_spec import RuleTimeWindowSettingsSpec

        d = src_dict.copy()
        rule_name = d.pop("rule_name", UNSET)

        rule_python_module_content = d.pop("rule_python_module_content", UNSET)

        _type = d.pop("type", UNSET)
        type: Union[Unset, RuleRunnerType]
        if isinstance(_type, Unset):
            type = UNSET
        else:
            type = RuleRunnerType(_type)

        java_class_name = d.pop("java_class_name", UNSET)

        _mode = d.pop("mode", UNSET)
        mode: Union[Unset, RuleTimeWindowMode]
        if isinstance(_mode, Unset):
            mode = UNSET
        else:
            mode = RuleTimeWindowMode(_mode)

        _time_window = d.pop("time_window", UNSET)
        time_window: Union[Unset, RuleTimeWindowSettingsSpec]
        if isinstance(_time_window, Unset):
            time_window = UNSET
        else:
            time_window = RuleTimeWindowSettingsSpec.from_dict(_time_window)

        fields = []
        _fields = d.pop("fields", UNSET)
        for fields_item_data in _fields or []:
            fields_item = ParameterDefinitionSpec.from_dict(fields_item_data)

            fields.append(fields_item)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, RuleModelParameters]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = RuleModelParameters.from_dict(_parameters)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        rule_model = cls(
            rule_name=rule_name,
            rule_python_module_content=rule_python_module_content,
            type=type,
            java_class_name=java_class_name,
            mode=mode,
            time_window=time_window,
            fields=fields,
            parameters=parameters,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        rule_model.additional_properties = d
        return rule_model

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
