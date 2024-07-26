from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.check_current_data_quality_status_model import (
        CheckCurrentDataQualityStatusModel,
    )


T = TypeVar("T", bound="TableCurrentDataQualityStatusModelChecks")


@_attrs_define
class TableCurrentDataQualityStatusModelChecks:
    """The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the
    current data quality check statuses that describe the most current status.

    """

    additional_properties: Dict[str, "CheckCurrentDataQualityStatusModel"] = (
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
        from ..models.check_current_data_quality_status_model import (
            CheckCurrentDataQualityStatusModel,
        )

        d = src_dict.copy()
        table_current_data_quality_status_model_checks = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = CheckCurrentDataQualityStatusModel.from_dict(
                prop_dict
            )

            additional_properties[prop_name] = additional_property

        table_current_data_quality_status_model_checks.additional_properties = (
            additional_properties
        )
        return table_current_data_quality_status_model_checks

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "CheckCurrentDataQualityStatusModel":
        return self.additional_properties[key]

    def __setitem__(
        self, key: str, value: "CheckCurrentDataQualityStatusModel"
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
