from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.incident_status import IncidentStatus
from ..models.top_incident_grouping import TopIncidentGrouping
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_severity_level_counts_model import (
        IncidentSeverityLevelCountsModel,
    )
    from ..models.top_incidents_model_top_incidents import TopIncidentsModelTopIncidents


T = TypeVar("T", bound="TopIncidentsModel")


@_attrs_define
class TopIncidentsModel:
    """
    Attributes:
        grouping (Union[Unset, TopIncidentGrouping]):
        status (Union[Unset, IncidentStatus]):
        top_incidents (Union[Unset, TopIncidentsModelTopIncidents]): Dictionary of the top incidents, grouped by the
            grouping such as the data quality dimension or a data quality check category. The incidents are sorted by the
            first seen descending (the most recent first).
        open_incident_severity_level_counts (Union[Unset, IncidentSeverityLevelCountsModel]):
        acknowledged_incident_severity_level_counts (Union[Unset, IncidentSeverityLevelCountsModel]):
    """

    grouping: Union[Unset, TopIncidentGrouping] = UNSET
    status: Union[Unset, IncidentStatus] = UNSET
    top_incidents: Union[Unset, "TopIncidentsModelTopIncidents"] = UNSET
    open_incident_severity_level_counts: Union[
        Unset, "IncidentSeverityLevelCountsModel"
    ] = UNSET
    acknowledged_incident_severity_level_counts: Union[
        Unset, "IncidentSeverityLevelCountsModel"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        grouping: Union[Unset, str] = UNSET
        if not isinstance(self.grouping, Unset):
            grouping = self.grouping.value

        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        top_incidents: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.top_incidents, Unset):
            top_incidents = self.top_incidents.to_dict()

        open_incident_severity_level_counts: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.open_incident_severity_level_counts, Unset):
            open_incident_severity_level_counts = (
                self.open_incident_severity_level_counts.to_dict()
            )

        acknowledged_incident_severity_level_counts: Union[Unset, Dict[str, Any]] = (
            UNSET
        )
        if not isinstance(self.acknowledged_incident_severity_level_counts, Unset):
            acknowledged_incident_severity_level_counts = (
                self.acknowledged_incident_severity_level_counts.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if grouping is not UNSET:
            field_dict["grouping"] = grouping
        if status is not UNSET:
            field_dict["status"] = status
        if top_incidents is not UNSET:
            field_dict["topIncidents"] = top_incidents
        if open_incident_severity_level_counts is not UNSET:
            field_dict["openIncidentSeverityLevelCounts"] = (
                open_incident_severity_level_counts
            )
        if acknowledged_incident_severity_level_counts is not UNSET:
            field_dict["acknowledgedIncidentSeverityLevelCounts"] = (
                acknowledged_incident_severity_level_counts
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_severity_level_counts_model import (
            IncidentSeverityLevelCountsModel,
        )
        from ..models.top_incidents_model_top_incidents import (
            TopIncidentsModelTopIncidents,
        )

        d = src_dict.copy()
        _grouping = d.pop("grouping", UNSET)
        grouping: Union[Unset, TopIncidentGrouping]
        if isinstance(_grouping, Unset):
            grouping = UNSET
        else:
            grouping = TopIncidentGrouping(_grouping)

        _status = d.pop("status", UNSET)
        status: Union[Unset, IncidentStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = IncidentStatus(_status)

        _top_incidents = d.pop("topIncidents", UNSET)
        top_incidents: Union[Unset, TopIncidentsModelTopIncidents]
        if isinstance(_top_incidents, Unset):
            top_incidents = UNSET
        else:
            top_incidents = TopIncidentsModelTopIncidents.from_dict(_top_incidents)

        _open_incident_severity_level_counts = d.pop(
            "openIncidentSeverityLevelCounts", UNSET
        )
        open_incident_severity_level_counts: Union[
            Unset, IncidentSeverityLevelCountsModel
        ]
        if isinstance(_open_incident_severity_level_counts, Unset):
            open_incident_severity_level_counts = UNSET
        else:
            open_incident_severity_level_counts = (
                IncidentSeverityLevelCountsModel.from_dict(
                    _open_incident_severity_level_counts
                )
            )

        _acknowledged_incident_severity_level_counts = d.pop(
            "acknowledgedIncidentSeverityLevelCounts", UNSET
        )
        acknowledged_incident_severity_level_counts: Union[
            Unset, IncidentSeverityLevelCountsModel
        ]
        if isinstance(_acknowledged_incident_severity_level_counts, Unset):
            acknowledged_incident_severity_level_counts = UNSET
        else:
            acknowledged_incident_severity_level_counts = (
                IncidentSeverityLevelCountsModel.from_dict(
                    _acknowledged_incident_severity_level_counts
                )
            )

        top_incidents_model = cls(
            grouping=grouping,
            status=status,
            top_incidents=top_incidents,
            open_incident_severity_level_counts=open_incident_severity_level_counts,
            acknowledged_incident_severity_level_counts=acknowledged_incident_severity_level_counts,
        )

        top_incidents_model.additional_properties = d
        return top_incidents_model

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
