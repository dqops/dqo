from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_quality_policy_spec import ColumnQualityPolicySpec


T = TypeVar("T", bound="ColumnQualityPolicyModel")


@_attrs_define
class ColumnQualityPolicyModel:
    """Default column-level checks pattern (data quality policy) model

    Attributes:
        policy_name (Union[Unset, str]): Quality policy name
        policy_spec (Union[Unset, ColumnQualityPolicySpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    policy_name: Union[Unset, str] = UNSET
    policy_spec: Union[Unset, "ColumnQualityPolicySpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        policy_name = self.policy_name
        policy_spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.policy_spec, Unset):
            policy_spec = self.policy_spec.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if policy_name is not UNSET:
            field_dict["policy_name"] = policy_name
        if policy_spec is not UNSET:
            field_dict["policy_spec"] = policy_spec
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_quality_policy_spec import ColumnQualityPolicySpec

        d = src_dict.copy()
        policy_name = d.pop("policy_name", UNSET)

        _policy_spec = d.pop("policy_spec", UNSET)
        policy_spec: Union[Unset, ColumnQualityPolicySpec]
        if isinstance(_policy_spec, Unset):
            policy_spec = UNSET
        else:
            policy_spec = ColumnQualityPolicySpec.from_dict(_policy_spec)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        column_quality_policy_model = cls(
            policy_name=policy_name,
            policy_spec=policy_spec,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        column_quality_policy_model.additional_properties = d
        return column_quality_policy_model

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
