from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_strings_string_min_length_sensor_parameters_spec import (
        ColumnStringsStringMinLengthSensorParametersSpec,
    )


T = TypeVar("T", bound="ColumnStringsStringMinLengthStatisticsCollectorSpec")


@_attrs_define
class ColumnStringsStringMinLengthStatisticsCollectorSpec:
    """
    Attributes:
        disabled (Union[Unset, bool]): Disables this profiler. Only enabled profilers are executed during a profiling
            process.
        parameters (Union[Unset, ColumnStringsStringMinLengthSensorParametersSpec]):
    """

    disabled: Union[Unset, bool] = UNSET
    parameters: Union[Unset, "ColumnStringsStringMinLengthSensorParametersSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        disabled = self.disabled
        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if parameters is not UNSET:
            field_dict["parameters"] = parameters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_strings_string_min_length_sensor_parameters_spec import (
            ColumnStringsStringMinLengthSensorParametersSpec,
        )

        d = src_dict.copy()
        disabled = d.pop("disabled", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, ColumnStringsStringMinLengthSensorParametersSpec]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = ColumnStringsStringMinLengthSensorParametersSpec.from_dict(
                _parameters
            )

        column_strings_string_min_length_statistics_collector_spec = cls(
            disabled=disabled,
            parameters=parameters,
        )

        column_strings_string_min_length_statistics_collector_spec.additional_properties = (
            d
        )
        return column_strings_string_min_length_statistics_collector_spec

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
