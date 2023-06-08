from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.sensor_readout_detailed_single_model import (
        SensorReadoutDetailedSingleModel,
    )


T = TypeVar("T", bound="SensorReadoutsDetailedDataModel")


@attr.s(auto_attribs=True)
class SensorReadoutsDetailedDataModel:
    """
    Attributes:
        check_hash (Union[Unset, int]): Check hash.
        check_category (Union[Unset, str]): Check category name.
        sensor_name (Union[Unset, str]): Sensor name.
        data_stream_names (Union[Unset, List[str]]): Data stream list.
        data_stream (Union[Unset, str]): Selected data-stream.
        single_sensor_readouts (Union[Unset, List['SensorReadoutDetailedSingleModel']]): Single sensor readouts
    """

    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    data_stream_names: Union[Unset, List[str]] = UNSET
    data_stream: Union[Unset, str] = UNSET
    single_sensor_readouts: Union[
        Unset, List["SensorReadoutDetailedSingleModel"]
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_hash = self.check_hash
        check_category = self.check_category
        sensor_name = self.sensor_name
        data_stream_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_stream_names, Unset):
            data_stream_names = self.data_stream_names

        data_stream = self.data_stream
        single_sensor_readouts: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.single_sensor_readouts, Unset):
            single_sensor_readouts = []
            for single_sensor_readouts_item_data in self.single_sensor_readouts:
                single_sensor_readouts_item = single_sensor_readouts_item_data.to_dict()

                single_sensor_readouts.append(single_sensor_readouts_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if data_stream_names is not UNSET:
            field_dict["dataStreamNames"] = data_stream_names
        if data_stream is not UNSET:
            field_dict["dataStream"] = data_stream
        if single_sensor_readouts is not UNSET:
            field_dict["singleSensorReadouts"] = single_sensor_readouts

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.sensor_readout_detailed_single_model import (
            SensorReadoutDetailedSingleModel,
        )

        d = src_dict.copy()
        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        data_stream_names = cast(List[str], d.pop("dataStreamNames", UNSET))

        data_stream = d.pop("dataStream", UNSET)

        single_sensor_readouts = []
        _single_sensor_readouts = d.pop("singleSensorReadouts", UNSET)
        for single_sensor_readouts_item_data in _single_sensor_readouts or []:
            single_sensor_readouts_item = SensorReadoutDetailedSingleModel.from_dict(
                single_sensor_readouts_item_data
            )

            single_sensor_readouts.append(single_sensor_readouts_item)

        sensor_readouts_detailed_data_model = cls(
            check_hash=check_hash,
            check_category=check_category,
            sensor_name=sensor_name,
            data_stream_names=data_stream_names,
            data_stream=data_stream,
            single_sensor_readouts=single_sensor_readouts,
        )

        sensor_readouts_detailed_data_model.additional_properties = d
        return sensor_readouts_detailed_data_model

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
