from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CronScheduleSpec")


@_attrs_define
class CronScheduleSpec:
    """
    Attributes:
        cron_expression (Union[Unset, str]): Unix style cron expression that specifies when to execute scheduled
            operations like running data quality checks or synchronizing the configuration with the cloud.
        disabled (Union[Unset, bool]): Disables the schedule. When the value of this 'disable' field is false, the
            schedule is stored in the metadata but it is not activated to run data quality checks.
    """

    cron_expression: Union[Unset, str] = UNSET
    disabled: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        cron_expression = self.cron_expression
        disabled = self.disabled

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if cron_expression is not UNSET:
            field_dict["cron_expression"] = cron_expression
        if disabled is not UNSET:
            field_dict["disabled"] = disabled

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        cron_expression = d.pop("cron_expression", UNSET)

        disabled = d.pop("disabled", UNSET)

        cron_schedule_spec = cls(
            cron_expression=cron_expression,
            disabled=disabled,
        )

        cron_schedule_spec.additional_properties = d
        return cron_schedule_spec

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
