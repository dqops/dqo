from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.target_column_pattern_spec import TargetColumnPatternSpec


T = TypeVar("T", bound="ColumnQualityPolicyListModel")


@_attrs_define
class ColumnQualityPolicyListModel:
    """Default column-level checks pattern (data quality policy) list model

    Attributes:
        policy_name (Union[Unset, str]): Quality policy name.
        priority (Union[Unset, int]): The priority of the policy. Policies with lower values are applied before policies
            with higher priority values.
        disabled (Union[Unset, bool]): Disables this data quality check configuration. The checks will not be activated.
        description (Union[Unset, str]): The description (documentation) of this data quality check configuration.
        target_column (Union[Unset, TargetColumnPatternSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    policy_name: Union[Unset, str] = UNSET
    priority: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    description: Union[Unset, str] = UNSET
    target_column: Union[Unset, "TargetColumnPatternSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        policy_name = self.policy_name
        priority = self.priority
        disabled = self.disabled
        description = self.description
        target_column: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target_column, Unset):
            target_column = self.target_column.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if policy_name is not UNSET:
            field_dict["policy_name"] = policy_name
        if priority is not UNSET:
            field_dict["priority"] = priority
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if description is not UNSET:
            field_dict["description"] = description
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
        policy_name = d.pop("policy_name", UNSET)

        priority = d.pop("priority", UNSET)

        disabled = d.pop("disabled", UNSET)

        description = d.pop("description", UNSET)

        _target_column = d.pop("target_column", UNSET)
        target_column: Union[Unset, TargetColumnPatternSpec]
        if isinstance(_target_column, Unset):
            target_column = UNSET
        else:
            target_column = TargetColumnPatternSpec.from_dict(_target_column)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        column_quality_policy_list_model = cls(
            policy_name=policy_name,
            priority=priority,
            disabled=disabled,
            description=description,
            target_column=target_column,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        column_quality_policy_list_model.additional_properties = d
        return column_quality_policy_list_model

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
