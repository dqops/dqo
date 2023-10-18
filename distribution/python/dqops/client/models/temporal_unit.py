from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.duration import Duration


T = TypeVar("T", bound="TemporalUnit")


@_attrs_define
class TemporalUnit:
    """
    Attributes:
        date_based (Union[Unset, bool]):
        time_based (Union[Unset, bool]):
        duration (Union[Unset, Duration]):
        duration_estimated (Union[Unset, bool]):
    """

    date_based: Union[Unset, bool] = UNSET
    time_based: Union[Unset, bool] = UNSET
    duration: Union[Unset, "Duration"] = UNSET
    duration_estimated: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        date_based = self.date_based
        time_based = self.time_based
        duration: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duration, Unset):
            duration = self.duration.to_dict()

        duration_estimated = self.duration_estimated

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if date_based is not UNSET:
            field_dict["dateBased"] = date_based
        if time_based is not UNSET:
            field_dict["timeBased"] = time_based
        if duration is not UNSET:
            field_dict["duration"] = duration
        if duration_estimated is not UNSET:
            field_dict["durationEstimated"] = duration_estimated

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.duration import Duration

        d = src_dict.copy()
        date_based = d.pop("dateBased", UNSET)

        time_based = d.pop("timeBased", UNSET)

        _duration = d.pop("duration", UNSET)
        duration: Union[Unset, Duration]
        if isinstance(_duration, Unset):
            duration = UNSET
        else:
            duration = Duration.from_dict(_duration)

        duration_estimated = d.pop("durationEstimated", UNSET)

        temporal_unit = cls(
            date_based=date_based,
            time_based=time_based,
            duration=duration,
            duration_estimated=duration_estimated,
        )

        temporal_unit.additional_properties = d
        return temporal_unit

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
