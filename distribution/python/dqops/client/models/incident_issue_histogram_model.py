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
        has_profiling_issues (Union[Unset, bool]): True when this data quality incident is based on data quality issues
            from profiling checks within the filters applied to search for linked data quality issues.
        has_daily_monitoring_issues (Union[Unset, bool]): True when this data quality incident is based on data quality
            issues from daily monitoring checks within the filters applied to search for linked data quality issues.
        has_monthly_monitoring_issues (Union[Unset, bool]): True when this data quality incident is based on data
            quality issues from monthly monitoring checks within the filters applied to search for linked data quality
            issues.
        has_daily_partitioned_issues (Union[Unset, bool]): True when this data quality incident is based on data quality
            issues from daily partitioned checks within the filters applied to search for linked data quality issues.
        has_monthly_partitioned_issues (Union[Unset, bool]): True when this data quality incident is based on data
            quality issues from monthly partitioned checks within the filters applied to search for linked data quality
            issues.
        days (Union[Unset, IncidentIssueHistogramModelDays]): A map of the numbers of data quality issues per day, the
            day uses the DQOps server timezone.
        columns (Union[Unset, IncidentIssueHistogramModelColumns]): A map of column names with the most data quality
            issues related to the incident. The map returns the count of issues as the value.
        checks (Union[Unset, IncidentIssueHistogramModelChecks]): A map of data quality check names with the most data
            quality issues related to the incident. The map returns the count of issues as the value.
    """

    has_profiling_issues: Union[Unset, bool] = UNSET
    has_daily_monitoring_issues: Union[Unset, bool] = UNSET
    has_monthly_monitoring_issues: Union[Unset, bool] = UNSET
    has_daily_partitioned_issues: Union[Unset, bool] = UNSET
    has_monthly_partitioned_issues: Union[Unset, bool] = UNSET
    days: Union[Unset, "IncidentIssueHistogramModelDays"] = UNSET
    columns: Union[Unset, "IncidentIssueHistogramModelColumns"] = UNSET
    checks: Union[Unset, "IncidentIssueHistogramModelChecks"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        has_profiling_issues = self.has_profiling_issues
        has_daily_monitoring_issues = self.has_daily_monitoring_issues
        has_monthly_monitoring_issues = self.has_monthly_monitoring_issues
        has_daily_partitioned_issues = self.has_daily_partitioned_issues
        has_monthly_partitioned_issues = self.has_monthly_partitioned_issues
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
        if has_profiling_issues is not UNSET:
            field_dict["hasProfilingIssues"] = has_profiling_issues
        if has_daily_monitoring_issues is not UNSET:
            field_dict["hasDailyMonitoringIssues"] = has_daily_monitoring_issues
        if has_monthly_monitoring_issues is not UNSET:
            field_dict["hasMonthlyMonitoringIssues"] = has_monthly_monitoring_issues
        if has_daily_partitioned_issues is not UNSET:
            field_dict["hasDailyPartitionedIssues"] = has_daily_partitioned_issues
        if has_monthly_partitioned_issues is not UNSET:
            field_dict["hasMonthlyPartitionedIssues"] = has_monthly_partitioned_issues
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
        has_profiling_issues = d.pop("hasProfilingIssues", UNSET)

        has_daily_monitoring_issues = d.pop("hasDailyMonitoringIssues", UNSET)

        has_monthly_monitoring_issues = d.pop("hasMonthlyMonitoringIssues", UNSET)

        has_daily_partitioned_issues = d.pop("hasDailyPartitionedIssues", UNSET)

        has_monthly_partitioned_issues = d.pop("hasMonthlyPartitionedIssues", UNSET)

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
            has_profiling_issues=has_profiling_issues,
            has_daily_monitoring_issues=has_daily_monitoring_issues,
            has_monthly_monitoring_issues=has_monthly_monitoring_issues,
            has_daily_partitioned_issues=has_daily_partitioned_issues,
            has_monthly_partitioned_issues=has_monthly_partitioned_issues,
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
