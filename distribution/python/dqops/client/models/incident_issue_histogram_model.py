from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_issue_histogram_model_checks import (
        IncidentIssueHistogramModelChecks,
    )
    from ..models.incident_issue_histogram_model_columns import (
        IncidentIssueHistogramModelColumns,
    )
    from ..models.incident_issue_histogram_model_days import (
        IncidentIssueHistogramModelDays,
    )


T = TypeVar("T", bound="IncidentIssueHistogramModel")


@_attrs_define
class IncidentIssueHistogramModel:
    """
    Attributes:
        days (Union[Unset, IncidentIssueHistogramModelDays]): A map of the numbers of data quality issues per day, the
            day uses the DQOps server timezone.
        columns (Union[Unset, IncidentIssueHistogramModelColumns]): A map of column names with the most data quality
            issues related to the incident. The map returns the count of issues as the value.
        checks (Union[Unset, IncidentIssueHistogramModelChecks]): A map of data quality check names with the most data
            quality issues related to the incident. The map returns the count of issues as the value.
    """

    days: Union[Unset, "IncidentIssueHistogramModelDays"] = UNSET
    columns: Union[Unset, "IncidentIssueHistogramModelColumns"] = UNSET
    checks: Union[Unset, "IncidentIssueHistogramModelChecks"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        days: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.days, Unset):
            days = self.days.to_dict()

        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = self.checks.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if days is not UNSET:
            field_dict["days"] = days
        if columns is not UNSET:
            field_dict["columns"] = columns
        if checks is not UNSET:
            field_dict["checks"] = checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_issue_histogram_model_checks import (
            IncidentIssueHistogramModelChecks,
        )
        from ..models.incident_issue_histogram_model_columns import (
            IncidentIssueHistogramModelColumns,
        )
        from ..models.incident_issue_histogram_model_days import (
            IncidentIssueHistogramModelDays,
        )

        d = src_dict.copy()
        _days = d.pop("days", UNSET)
        days: Union[Unset, IncidentIssueHistogramModelDays]
        if isinstance(_days, Unset):
            days = UNSET
        else:
            days = IncidentIssueHistogramModelDays.from_dict(_days)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, IncidentIssueHistogramModelColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = IncidentIssueHistogramModelColumns.from_dict(_columns)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, IncidentIssueHistogramModelChecks]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = IncidentIssueHistogramModelChecks.from_dict(_checks)

        incident_issue_histogram_model = cls(
            days=days,
            columns=columns,
            checks=checks,
        )

        incident_issue_histogram_model.additional_properties = d
        return incident_issue_histogram_model

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
