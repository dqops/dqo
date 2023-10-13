import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_run_schedule_group import CheckRunScheduleGroup
from ..models.effective_schedule_level_model import EffectiveScheduleLevelModel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.duration import Duration


T = TypeVar("T", bound="EffectiveScheduleModel")


@_attrs_define
class EffectiveScheduleModel:
    """Model of a configured schedule (enabled on connection or table) or schedule override (on check). Describes the CRON
    expression and the time of the upcoming execution, as well as the duration until this time.

        Attributes:
            schedule_group (Union[Unset, CheckRunScheduleGroup]):
            schedule_level (Union[Unset, EffectiveScheduleLevelModel]):
            cron_expression (Union[Unset, str]): Field value for a CRON expression defining the scheduling.
            time_of_execution (Union[Unset, datetime.datetime]): Field value for the time at which the scheduled checks will
                be executed.
            time_until_execution (Union[Unset, Duration]):
            disabled (Union[Unset, bool]): Field value stating if the schedule has been explicitly disabled.
    """

    schedule_group: Union[Unset, CheckRunScheduleGroup] = UNSET
    schedule_level: Union[Unset, EffectiveScheduleLevelModel] = UNSET
    cron_expression: Union[Unset, str] = UNSET
    time_of_execution: Union[Unset, datetime.datetime] = UNSET
    time_until_execution: Union[Unset, "Duration"] = UNSET
    disabled: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        schedule_group: Union[Unset, str] = UNSET
        if not isinstance(self.schedule_group, Unset):
            schedule_group = self.schedule_group.value

        schedule_level: Union[Unset, str] = UNSET
        if not isinstance(self.schedule_level, Unset):
            schedule_level = self.schedule_level.value

        cron_expression = self.cron_expression
        time_of_execution: Union[Unset, str] = UNSET
        if not isinstance(self.time_of_execution, Unset):
            time_of_execution = self.time_of_execution.isoformat()

        time_until_execution: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.time_until_execution, Unset):
            time_until_execution = self.time_until_execution.to_dict()

        disabled = self.disabled

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if schedule_group is not UNSET:
            field_dict["schedule_group"] = schedule_group
        if schedule_level is not UNSET:
            field_dict["schedule_level"] = schedule_level
        if cron_expression is not UNSET:
            field_dict["cron_expression"] = cron_expression
        if time_of_execution is not UNSET:
            field_dict["time_of_execution"] = time_of_execution
        if time_until_execution is not UNSET:
            field_dict["time_until_execution"] = time_until_execution
        if disabled is not UNSET:
            field_dict["disabled"] = disabled

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.duration import Duration

        d = src_dict.copy()
        _schedule_group = d.pop("schedule_group", UNSET)
        schedule_group: Union[Unset, CheckRunScheduleGroup]
        if isinstance(_schedule_group, Unset):
            schedule_group = UNSET
        else:
            schedule_group = CheckRunScheduleGroup(_schedule_group)

        _schedule_level = d.pop("schedule_level", UNSET)
        schedule_level: Union[Unset, EffectiveScheduleLevelModel]
        if isinstance(_schedule_level, Unset):
            schedule_level = UNSET
        else:
            schedule_level = EffectiveScheduleLevelModel(_schedule_level)

        cron_expression = d.pop("cron_expression", UNSET)

        _time_of_execution = d.pop("time_of_execution", UNSET)
        time_of_execution: Union[Unset, datetime.datetime]
        if isinstance(_time_of_execution, Unset):
            time_of_execution = UNSET
        else:
            time_of_execution = isoparse(_time_of_execution)

        _time_until_execution = d.pop("time_until_execution", UNSET)
        time_until_execution: Union[Unset, Duration]
        if isinstance(_time_until_execution, Unset):
            time_until_execution = UNSET
        else:
            time_until_execution = Duration.from_dict(_time_until_execution)

        disabled = d.pop("disabled", UNSET)

        effective_schedule_model = cls(
            schedule_group=schedule_group,
            schedule_level=schedule_level,
            cron_expression=cron_expression,
            time_of_execution=time_of_execution,
            time_until_execution=time_until_execution,
            disabled=disabled,
        )

        effective_schedule_model.additional_properties = d
        return effective_schedule_model

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
