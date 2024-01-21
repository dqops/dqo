from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_target import CheckTarget
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_container_type_model import CheckContainerTypeModel
    from ..models.check_model import CheckModel
    from ..models.parameter_definition_spec import ParameterDefinitionSpec


T = TypeVar("T", bound="CheckTemplate")


@_attrs_define
class CheckTemplate:
    """Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy
    tree.

        Attributes:
            check_target (Union[Unset, CheckTarget]):
            check_category (Union[Unset, str]): Data quality check category.
            check_name (Union[Unset, str]): Data quality check name that is used in YAML.
            help_text (Union[Unset, str]): Help text that describes the data quality check.
            check_container_type (Union[Unset, CheckContainerTypeModel]): Model identifying the check type and timescale of
                checks belonging to a container.
            sensor_name (Union[Unset, str]): Full sensor name.
            check_model (Union[Unset, CheckModel]): Model that returns the form definition and the form data to edit a
                single data quality check.
            sensor_parameters_definitions (Union[Unset, List['ParameterDefinitionSpec']]): List of sensor parameter fields
                definitions.
            rule_parameters_definitions (Union[Unset, List['ParameterDefinitionSpec']]): List of threshold (alerting) rule's
                parameters definitions (for a single rule, regardless of severity).
    """

    check_target: Union[Unset, CheckTarget] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    check_container_type: Union[Unset, "CheckContainerTypeModel"] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    check_model: Union[Unset, "CheckModel"] = UNSET
    sensor_parameters_definitions: Union[Unset, List["ParameterDefinitionSpec"]] = UNSET
    rule_parameters_definitions: Union[Unset, List["ParameterDefinitionSpec"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_target: Union[Unset, str] = UNSET
        if not isinstance(self.check_target, Unset):
            check_target = self.check_target.value

        check_category = self.check_category
        check_name = self.check_name
        help_text = self.help_text
        check_container_type: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.check_container_type, Unset):
            check_container_type = self.check_container_type.to_dict()

        sensor_name = self.sensor_name
        check_model: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.check_model, Unset):
            check_model = self.check_model.to_dict()

        sensor_parameters_definitions: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.sensor_parameters_definitions, Unset):
            sensor_parameters_definitions = []
            for (
                sensor_parameters_definitions_item_data
            ) in self.sensor_parameters_definitions:
                sensor_parameters_definitions_item = (
                    sensor_parameters_definitions_item_data.to_dict()
                )

                sensor_parameters_definitions.append(sensor_parameters_definitions_item)

        rule_parameters_definitions: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.rule_parameters_definitions, Unset):
            rule_parameters_definitions = []
            for (
                rule_parameters_definitions_item_data
            ) in self.rule_parameters_definitions:
                rule_parameters_definitions_item = (
                    rule_parameters_definitions_item_data.to_dict()
                )

                rule_parameters_definitions.append(rule_parameters_definitions_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_target is not UNSET:
            field_dict["check_target"] = check_target
        if check_category is not UNSET:
            field_dict["check_category"] = check_category
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if check_container_type is not UNSET:
            field_dict["check_container_type"] = check_container_type
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if check_model is not UNSET:
            field_dict["check_model"] = check_model
        if sensor_parameters_definitions is not UNSET:
            field_dict["sensor_parameters_definitions"] = sensor_parameters_definitions
        if rule_parameters_definitions is not UNSET:
            field_dict["rule_parameters_definitions"] = rule_parameters_definitions

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_container_type_model import CheckContainerTypeModel
        from ..models.check_model import CheckModel
        from ..models.parameter_definition_spec import ParameterDefinitionSpec

        d = src_dict.copy()
        _check_target = d.pop("check_target", UNSET)
        check_target: Union[Unset, CheckTarget]
        if isinstance(_check_target, Unset):
            check_target = UNSET
        else:
            check_target = CheckTarget(_check_target)

        check_category = d.pop("check_category", UNSET)

        check_name = d.pop("check_name", UNSET)

        help_text = d.pop("help_text", UNSET)

        _check_container_type = d.pop("check_container_type", UNSET)
        check_container_type: Union[Unset, CheckContainerTypeModel]
        if isinstance(_check_container_type, Unset):
            check_container_type = UNSET
        else:
            check_container_type = CheckContainerTypeModel.from_dict(
                _check_container_type
            )

        sensor_name = d.pop("sensor_name", UNSET)

        _check_model = d.pop("check_model", UNSET)
        check_model: Union[Unset, CheckModel]
        if isinstance(_check_model, Unset):
            check_model = UNSET
        else:
            check_model = CheckModel.from_dict(_check_model)

        sensor_parameters_definitions = []
        _sensor_parameters_definitions = d.pop("sensor_parameters_definitions", UNSET)
        for sensor_parameters_definitions_item_data in (
            _sensor_parameters_definitions or []
        ):
            sensor_parameters_definitions_item = ParameterDefinitionSpec.from_dict(
                sensor_parameters_definitions_item_data
            )

            sensor_parameters_definitions.append(sensor_parameters_definitions_item)

        rule_parameters_definitions = []
        _rule_parameters_definitions = d.pop("rule_parameters_definitions", UNSET)
        for rule_parameters_definitions_item_data in _rule_parameters_definitions or []:
            rule_parameters_definitions_item = ParameterDefinitionSpec.from_dict(
                rule_parameters_definitions_item_data
            )

            rule_parameters_definitions.append(rule_parameters_definitions_item)

        check_template = cls(
            check_target=check_target,
            check_category=check_category,
            check_name=check_name,
            help_text=help_text,
            check_container_type=check_container_type,
            sensor_name=sensor_name,
            check_model=check_model,
            sensor_parameters_definitions=sensor_parameters_definitions,
            rule_parameters_definitions=rule_parameters_definitions,
        )

        check_template.additional_properties = d
        return check_template

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
