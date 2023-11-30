from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_type import CheckType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.sensor_readout_entry_model import SensorReadoutEntryModel


T = TypeVar("T", bound="SensorReadoutsListModel")


@_attrs_define
class SensorReadoutsListModel:
    """
    Attributes:
        check_name (Union[Unset, str]): Check name
        check_display_name (Union[Unset, str]): Check display name
        check_type (Union[Unset, CheckType]):
        check_hash (Union[Unset, int]): Check hash
        check_category (Union[Unset, str]): Check category name
        sensor_name (Union[Unset, str]): Sensor name
        data_group_names (Union[Unset, List[str]]): List of data groups that have values for this sensor readout (list
            of time series)
        data_group (Union[Unset, str]): Selected data group
        sensor_readout_entries (Union[Unset, List['SensorReadoutEntryModel']]): Sensor readout entries
    """

    check_name: Union[Unset, str] = UNSET
    check_display_name: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    data_group_names: Union[Unset, List[str]] = UNSET
    data_group: Union[Unset, str] = UNSET
    sensor_readout_entries: Union[Unset, List["SensorReadoutEntryModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        check_display_name = self.check_display_name
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        check_hash = self.check_hash
        check_category = self.check_category
        sensor_name = self.sensor_name
        data_group_names: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_group_names, Unset):
            data_group_names = self.data_group_names

        data_group = self.data_group
        sensor_readout_entries: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.sensor_readout_entries, Unset):
            sensor_readout_entries = []
            for sensor_readout_entries_item_data in self.sensor_readout_entries:
                sensor_readout_entries_item = sensor_readout_entries_item_data.to_dict()

                sensor_readout_entries.append(sensor_readout_entries_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if check_display_name is not UNSET:
            field_dict["checkDisplayName"] = check_display_name
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if sensor_name is not UNSET:
            field_dict["sensorName"] = sensor_name
        if data_group_names is not UNSET:
            field_dict["dataGroupNames"] = data_group_names
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if sensor_readout_entries is not UNSET:
            field_dict["sensorReadoutEntries"] = sensor_readout_entries

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.sensor_readout_entry_model import SensorReadoutEntryModel

        d = src_dict.copy()
        check_name = d.pop("checkName", UNSET)

        check_display_name = d.pop("checkDisplayName", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        sensor_name = d.pop("sensorName", UNSET)

        data_group_names = cast(List[str], d.pop("dataGroupNames", UNSET))

        data_group = d.pop("dataGroup", UNSET)

        sensor_readout_entries = []
        _sensor_readout_entries = d.pop("sensorReadoutEntries", UNSET)
        for sensor_readout_entries_item_data in _sensor_readout_entries or []:
            sensor_readout_entries_item = SensorReadoutEntryModel.from_dict(
                sensor_readout_entries_item_data
            )

            sensor_readout_entries.append(sensor_readout_entries_item)

        sensor_readouts_list_model = cls(
            check_name=check_name,
            check_display_name=check_display_name,
            check_type=check_type,
            check_hash=check_hash,
            check_category=check_category,
            sensor_name=sensor_name,
            data_group_names=data_group_names,
            data_group=data_group,
            sensor_readout_entries=sensor_readout_entries,
        )

        sensor_readouts_list_model.additional_properties = d
        return sensor_readouts_list_model

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
