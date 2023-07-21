from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.data_grouping_configuration_spec import DataGroupingConfigurationSpec


T = TypeVar("T", bound="DataGroupingConfigurationTrimmedModel")


@attr.s(auto_attribs=True)
class DataGroupingConfigurationTrimmedModel:
    """Data grouping configuration model with trimmed path

    Attributes:
        data_grouping_configuration_name (Union[Unset, str]): Data grouping configuration name.
        spec (Union[Unset, DataGroupingConfigurationSpec]):
    """

    data_grouping_configuration_name: Union[Unset, str] = UNSET
    spec: Union[Unset, "DataGroupingConfigurationSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        data_grouping_configuration_name = self.data_grouping_configuration_name
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if data_grouping_configuration_name is not UNSET:
            field_dict[
                "data_grouping_configuration_name"
            ] = data_grouping_configuration_name
        if spec is not UNSET:
            field_dict["spec"] = spec

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.data_grouping_configuration_spec import (
            DataGroupingConfigurationSpec,
        )

        d = src_dict.copy()
        data_grouping_configuration_name = d.pop(
            "data_grouping_configuration_name", UNSET
        )

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, DataGroupingConfigurationSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = DataGroupingConfigurationSpec.from_dict(_spec)

        data_grouping_configuration_trimmed_model = cls(
            data_grouping_configuration_name=data_grouping_configuration_name,
            spec=spec,
        )

        data_grouping_configuration_trimmed_model.additional_properties = d
        return data_grouping_configuration_trimmed_model

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
