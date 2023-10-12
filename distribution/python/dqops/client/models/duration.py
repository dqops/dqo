from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.temporal_unit import TemporalUnit


T = TypeVar("T", bound="Duration")


@_attrs_define
class Duration:
    """
    Attributes:
        seconds (Union[Unset, int]):
        nano (Union[Unset, int]):
        negative (Union[Unset, bool]):
        zero (Union[Unset, bool]):
        units (Union[Unset, List['TemporalUnit']]):
    """

    seconds: Union[Unset, int] = UNSET
    nano: Union[Unset, int] = UNSET
    negative: Union[Unset, bool] = UNSET
    zero: Union[Unset, bool] = UNSET
    units: Union[Unset, List["TemporalUnit"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        seconds = self.seconds
        nano = self.nano
        negative = self.negative
        zero = self.zero
        units: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.units, Unset):
            units = []
            for units_item_data in self.units:
                units_item = units_item_data.to_dict()

                units.append(units_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if seconds is not UNSET:
            field_dict["seconds"] = seconds
        if nano is not UNSET:
            field_dict["nano"] = nano
        if negative is not UNSET:
            field_dict["negative"] = negative
        if zero is not UNSET:
            field_dict["zero"] = zero
        if units is not UNSET:
            field_dict["units"] = units

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.temporal_unit import TemporalUnit

        d = src_dict.copy()
        seconds = d.pop("seconds", UNSET)

        nano = d.pop("nano", UNSET)

        negative = d.pop("negative", UNSET)

        zero = d.pop("zero", UNSET)

        units = []
        _units = d.pop("units", UNSET)
        for units_item_data in _units or []:
            units_item = TemporalUnit.from_dict(units_item_data)

            units.append(units_item)

        duration = cls(
            seconds=seconds,
            nano=nano,
            negative=negative,
            zero=zero,
            units=units,
        )

        duration.additional_properties = d
        return duration

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
