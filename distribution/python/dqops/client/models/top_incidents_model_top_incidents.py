from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.incident_model import IncidentModel


T = TypeVar("T", bound="TopIncidentsModelTopIncidents")


@_attrs_define
class TopIncidentsModelTopIncidents:
    """Dictionary of the top incidents, grouped by the grouping such as the data quality dimension or a data quality check
    category. The incidents are sorted by the first seen descending (the most recent first).

    """

    additional_properties: Dict[str, List["IncidentModel"]] = _attrs_field(
        init=False, factory=dict
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = []
            for additional_property_item_data in prop:
                additional_property_item = additional_property_item_data.to_dict()

                field_dict[prop_name].append(additional_property_item)

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_model import IncidentModel

        d = src_dict.copy()
        top_incidents_model_top_incidents = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = []
            _additional_property = prop_dict
            for additional_property_item_data in _additional_property:
                additional_property_item = IncidentModel.from_dict(
                    additional_property_item_data
                )

                additional_property.append(additional_property_item)

            additional_properties[prop_name] = additional_property

        top_incidents_model_top_incidents.additional_properties = additional_properties
        return top_incidents_model_top_incidents

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> List["IncidentModel"]:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: List["IncidentModel"]) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
