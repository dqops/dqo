from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.rule_parameters_model import RuleParametersModel


T = TypeVar("T", bound="RuleThresholdsModel")


@_attrs_define
class RuleThresholdsModel:
    """Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low,
    medium, high).

        Attributes:
            error (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
                parameters (thresholds) for a rule at a single severity level (low, medium, high).
            warning (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
                parameters (thresholds) for a rule at a single severity level (low, medium, high).
            fatal (Union[Unset, RuleParametersModel]): Model that returns the form definition and the form data to edit
                parameters (thresholds) for a rule at a single severity level (low, medium, high).
    """

    error: Union[Unset, "RuleParametersModel"] = UNSET
    warning: Union[Unset, "RuleParametersModel"] = UNSET
    fatal: Union[Unset, "RuleParametersModel"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        error: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.error, Unset):
            error = self.error.to_dict()

        warning: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.warning, Unset):
            warning = self.warning.to_dict()

        fatal: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.fatal, Unset):
            fatal = self.fatal.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if error is not UNSET:
            field_dict["error"] = error
        if warning is not UNSET:
            field_dict["warning"] = warning
        if fatal is not UNSET:
            field_dict["fatal"] = fatal

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.rule_parameters_model import RuleParametersModel

        d = src_dict.copy()
        _error = d.pop("error", UNSET)
        error: Union[Unset, RuleParametersModel]
        if isinstance(_error, Unset):
            error = UNSET
        else:
            error = RuleParametersModel.from_dict(_error)

        _warning = d.pop("warning", UNSET)
        warning: Union[Unset, RuleParametersModel]
        if isinstance(_warning, Unset):
            warning = UNSET
        else:
            warning = RuleParametersModel.from_dict(_warning)

        _fatal = d.pop("fatal", UNSET)
        fatal: Union[Unset, RuleParametersModel]
        if isinstance(_fatal, Unset):
            fatal = UNSET
        else:
            fatal = RuleParametersModel.from_dict(_fatal)

        rule_thresholds_model = cls(
            error=error,
            warning=warning,
            fatal=fatal,
        )

        rule_thresholds_model.additional_properties = d
        return rule_thresholds_model

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
