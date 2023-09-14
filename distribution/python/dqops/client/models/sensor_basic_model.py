from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_basic_model import ProviderSensorBasicModel


T = TypeVar("T", bound="SensorBasicModel")


@attr.s(auto_attribs=True)
class SensorBasicModel:
    """Sensor basic model

    Attributes:
        sensor_name (Union[Unset, str]): Sensor name
        full_sensor_name (Union[Unset, str]): Full sensor name
        custom (Union[Unset, bool]): This sensor has is a custom sensor or was customized by the user.
        built_in (Union[Unset, bool]): This sensor is provided with DQO as a built-in sensor.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        provider_sensor_basic_models (Union[Unset, List['ProviderSensorBasicModel']]): Provider sensor basic model list
    """

    sensor_name: Union[Unset, str] = UNSET
    full_sensor_name: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    provider_sensor_basic_models: Union[Unset, List["ProviderSensorBasicModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        sensor_name = self.sensor_name
        full_sensor_name = self.full_sensor_name
        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit
        provider_sensor_basic_models: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.provider_sensor_basic_models, Unset):
            provider_sensor_basic_models = []
            for (
                provider_sensor_basic_models_item_data
            ) in self.provider_sensor_basic_models:
                provider_sensor_basic_models_item = (
                    provider_sensor_basic_models_item_data.to_dict()
                )

                provider_sensor_basic_models.append(provider_sensor_basic_models_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if full_sensor_name is not UNSET:
            field_dict["full_sensor_name"] = full_sensor_name
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if provider_sensor_basic_models is not UNSET:
            field_dict["provider_sensor_basic_models"] = provider_sensor_basic_models

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_basic_model import ProviderSensorBasicModel

        d = src_dict.copy()
        sensor_name = d.pop("sensor_name", UNSET)

        full_sensor_name = d.pop("full_sensor_name", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        provider_sensor_basic_models = []
        _provider_sensor_basic_models = d.pop("provider_sensor_basic_models", UNSET)
        for provider_sensor_basic_models_item_data in (
            _provider_sensor_basic_models or []
        ):
            provider_sensor_basic_models_item = ProviderSensorBasicModel.from_dict(
                provider_sensor_basic_models_item_data
            )

            provider_sensor_basic_models.append(provider_sensor_basic_models_item)

        sensor_basic_model = cls(
            sensor_name=sensor_name,
            full_sensor_name=full_sensor_name,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
            provider_sensor_basic_models=provider_sensor_basic_models,
        )

        sensor_basic_model.additional_properties = d
        return sensor_basic_model

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
