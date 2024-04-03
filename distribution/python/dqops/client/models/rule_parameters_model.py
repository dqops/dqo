from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.field_model import FieldModel


T = TypeVar("T", bound="RuleParametersModel")


@_attrs_define
class RuleParametersModel:
    """Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single
    severity level (low, medium, high).

        Attributes:
            rule_name (Union[Unset, str]): Full rule name. This field is for information purposes and can be used to create
                additional custom checks that reuse the same data quality rule.
            rule_parameters (Union[Unset, List['FieldModel']]): List of fields for editing the rule parameters like
                thresholds.
            disabled (Union[Unset, bool]): Disable the rule. The rule will not be evaluated. The sensor will also not be
                executed if it has no enabled rules.
            configured (Union[Unset, bool]): Returns true when the rule is configured (is not null), so it should be shown
                in the UI as configured (having values).
    """

    rule_name: Union[Unset, str] = UNSET
    rule_parameters: Union[Unset, List["FieldModel"]] = UNSET
    disabled: Union[Unset, bool] = UNSET
    configured: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        rule_name = self.rule_name
        rule_parameters: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.rule_parameters, Unset):
            rule_parameters = []
            for rule_parameters_item_data in self.rule_parameters:
                rule_parameters_item = rule_parameters_item_data.to_dict()

                rule_parameters.append(rule_parameters_item)

        disabled = self.disabled
        configured = self.configured

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if rule_name is not UNSET:
            field_dict["rule_name"] = rule_name
        if rule_parameters is not UNSET:
            field_dict["rule_parameters"] = rule_parameters
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if configured is not UNSET:
            field_dict["configured"] = configured

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.field_model import FieldModel

        d = src_dict.copy()
        rule_name = d.pop("rule_name", UNSET)

        rule_parameters = []
        _rule_parameters = d.pop("rule_parameters", UNSET)
        for rule_parameters_item_data in _rule_parameters or []:
            rule_parameters_item = FieldModel.from_dict(rule_parameters_item_data)

            rule_parameters.append(rule_parameters_item)

        disabled = d.pop("disabled", UNSET)

        configured = d.pop("configured", UNSET)

        rule_parameters_model = cls(
            rule_name=rule_name,
            rule_parameters=rule_parameters,
            disabled=disabled,
            configured=configured,
        )

        rule_parameters_model.additional_properties = d
        return rule_parameters_model

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
