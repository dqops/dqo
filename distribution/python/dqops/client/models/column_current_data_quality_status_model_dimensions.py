from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.dimension_current_data_quality_status_model import (
        DimensionCurrentDataQualityStatusModel,
    )


T = TypeVar("T", bound="ColumnCurrentDataQualityStatusModelDimensions")


@_attrs_define
class ColumnCurrentDataQualityStatusModelDimensions:
    """Dictionary of the current data quality statues for each data quality dimension."""

    additional_properties: Dict[str, "DimensionCurrentDataQualityStatusModel"] = (
        _attrs_field(init=False, factory=dict)
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dimension_current_data_quality_status_model import (
            DimensionCurrentDataQualityStatusModel,
        )

        d = src_dict.copy()
        column_current_data_quality_status_model_dimensions = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = DimensionCurrentDataQualityStatusModel.from_dict(
                prop_dict
            )

            additional_properties[prop_name] = additional_property

        column_current_data_quality_status_model_dimensions.additional_properties = (
            additional_properties
        )
        return column_current_data_quality_status_model_dimensions

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "DimensionCurrentDataQualityStatusModel":
        return self.additional_properties[key]

    def __setitem__(
        self, key: str, value: "DimensionCurrentDataQualityStatusModel"
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
