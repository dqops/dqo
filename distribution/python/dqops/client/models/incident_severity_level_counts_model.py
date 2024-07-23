from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_counts_model import IncidentCountsModel


T = TypeVar("T", bound="IncidentSeverityLevelCountsModel")


@_attrs_define
class IncidentSeverityLevelCountsModel:
    """
    Attributes:
        warning_counts (Union[Unset, IncidentCountsModel]):
        error_counts (Union[Unset, IncidentCountsModel]):
        fatal_counts (Union[Unset, IncidentCountsModel]):
    """

    warning_counts: Union[Unset, "IncidentCountsModel"] = UNSET
    error_counts: Union[Unset, "IncidentCountsModel"] = UNSET
    fatal_counts: Union[Unset, "IncidentCountsModel"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        warning_counts: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.warning_counts, Unset):
            warning_counts = self.warning_counts.to_dict()

        error_counts: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.error_counts, Unset):
            error_counts = self.error_counts.to_dict()

        fatal_counts: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.fatal_counts, Unset):
            fatal_counts = self.fatal_counts.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if warning_counts is not UNSET:
            field_dict["warningCounts"] = warning_counts
        if error_counts is not UNSET:
            field_dict["errorCounts"] = error_counts
        if fatal_counts is not UNSET:
            field_dict["fatalCounts"] = fatal_counts

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_counts_model import IncidentCountsModel

        d = src_dict.copy()
        _warning_counts = d.pop("warningCounts", UNSET)
        warning_counts: Union[Unset, IncidentCountsModel]
        if isinstance(_warning_counts, Unset):
            warning_counts = UNSET
        else:
            warning_counts = IncidentCountsModel.from_dict(_warning_counts)

        _error_counts = d.pop("errorCounts", UNSET)
        error_counts: Union[Unset, IncidentCountsModel]
        if isinstance(_error_counts, Unset):
            error_counts = UNSET
        else:
            error_counts = IncidentCountsModel.from_dict(_error_counts)

        _fatal_counts = d.pop("fatalCounts", UNSET)
        fatal_counts: Union[Unset, IncidentCountsModel]
        if isinstance(_fatal_counts, Unset):
            fatal_counts = UNSET
        else:
            fatal_counts = IncidentCountsModel.from_dict(_fatal_counts)

        incident_severity_level_counts_model = cls(
            warning_counts=warning_counts,
            error_counts=error_counts,
            fatal_counts=fatal_counts,
        )

        incident_severity_level_counts_model.additional_properties = d
        return incident_severity_level_counts_model

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
