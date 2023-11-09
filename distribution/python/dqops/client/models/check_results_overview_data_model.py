import datetime
from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.check_result_status import CheckResultStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckResultsOverviewDataModel")


@_attrs_define
class CheckResultsOverviewDataModel:
    """
    Attributes:
        check_hash (Union[Unset, int]): Check hash.
        check_category (Union[Unset, str]): Check category name.
        check_name (Union[Unset, str]): Check name.
        comparison_name (Union[Unset, str]): Optional table comparison name for table comparison checks only.
        time_periods (Union[Unset, List[datetime.datetime]]): List of time periods for the results, returned as a local
            time, sorted from the newest to the oldest.
        time_periods_utc (Union[Unset, List[int]]): List of time periods for the results, returned as absolute UTC time.
        executed_at_timestamps (Union[Unset, List[int]]): List of absolute timestamp (UTC) when the check was executed
            or an error was raised.
        time_period_display_texts (Union[Unset, List[str]]): List of time periods, sorted descending, returned as a text
            with a possible time zone.
        statuses (Union[Unset, List[CheckResultStatus]]): List of check severity levels or an error status, indexes with
            the severity levels match the time periods.
        data_groups (Union[Unset, List[str]]): List of data group names. Identifies the data group with the highest
            severity or error result.
        results (Union[Unset, List[float]]): List of sensor results. Returns the data quality result readout for the
            data group with the alert of the highest severity level.
    """

    check_hash: Union[Unset, int] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    comparison_name: Union[Unset, str] = UNSET
    time_periods: Union[Unset, List[datetime.datetime]] = UNSET
    time_periods_utc: Union[Unset, List[int]] = UNSET
    executed_at_timestamps: Union[Unset, List[int]] = UNSET
    time_period_display_texts: Union[Unset, List[str]] = UNSET
    statuses: Union[Unset, List[CheckResultStatus]] = UNSET
    data_groups: Union[Unset, List[str]] = UNSET
    results: Union[Unset, List[float]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_hash = self.check_hash
        check_category = self.check_category
        check_name = self.check_name
        comparison_name = self.comparison_name
        time_periods: Union[Unset, List[str]] = UNSET
        if not isinstance(self.time_periods, Unset):
            time_periods = []
            for time_periods_item_data in self.time_periods:
                time_periods_item = time_periods_item_data.isoformat()

                time_periods.append(time_periods_item)

        time_periods_utc: Union[Unset, List[int]] = UNSET
        if not isinstance(self.time_periods_utc, Unset):
            time_periods_utc = self.time_periods_utc

        executed_at_timestamps: Union[Unset, List[int]] = UNSET
        if not isinstance(self.executed_at_timestamps, Unset):
            executed_at_timestamps = self.executed_at_timestamps

        time_period_display_texts: Union[Unset, List[str]] = UNSET
        if not isinstance(self.time_period_display_texts, Unset):
            time_period_display_texts = self.time_period_display_texts

        statuses: Union[Unset, List[str]] = UNSET
        if not isinstance(self.statuses, Unset):
            statuses = []
            for statuses_item_data in self.statuses:
                statuses_item = statuses_item_data.value

                statuses.append(statuses_item)

        data_groups: Union[Unset, List[str]] = UNSET
        if not isinstance(self.data_groups, Unset):
            data_groups = self.data_groups

        results: Union[Unset, List[float]] = UNSET
        if not isinstance(self.results, Unset):
            results = self.results

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_hash is not UNSET:
            field_dict["checkHash"] = check_hash
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if comparison_name is not UNSET:
            field_dict["comparisonName"] = comparison_name
        if time_periods is not UNSET:
            field_dict["timePeriods"] = time_periods
        if time_periods_utc is not UNSET:
            field_dict["timePeriodsUtc"] = time_periods_utc
        if executed_at_timestamps is not UNSET:
            field_dict["executedAtTimestamps"] = executed_at_timestamps
        if time_period_display_texts is not UNSET:
            field_dict["timePeriodDisplayTexts"] = time_period_display_texts
        if statuses is not UNSET:
            field_dict["statuses"] = statuses
        if data_groups is not UNSET:
            field_dict["dataGroups"] = data_groups
        if results is not UNSET:
            field_dict["results"] = results

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        check_hash = d.pop("checkHash", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        check_name = d.pop("checkName", UNSET)

        comparison_name = d.pop("comparisonName", UNSET)

        time_periods = []
        _time_periods = d.pop("timePeriods", UNSET)
        for time_periods_item_data in _time_periods or []:
            time_periods_item = isoparse(time_periods_item_data)

            time_periods.append(time_periods_item)

        time_periods_utc = cast(List[int], d.pop("timePeriodsUtc", UNSET))

        executed_at_timestamps = cast(List[int], d.pop("executedAtTimestamps", UNSET))

        time_period_display_texts = cast(
            List[str], d.pop("timePeriodDisplayTexts", UNSET)
        )

        statuses = []
        _statuses = d.pop("statuses", UNSET)
        for statuses_item_data in _statuses or []:
            statuses_item = CheckResultStatus(statuses_item_data)

            statuses.append(statuses_item)

        data_groups = cast(List[str], d.pop("dataGroups", UNSET))

        results = cast(List[float], d.pop("results", UNSET))

        check_results_overview_data_model = cls(
            check_hash=check_hash,
            check_category=check_category,
            check_name=check_name,
            comparison_name=comparison_name,
            time_periods=time_periods,
            time_periods_utc=time_periods_utc,
            executed_at_timestamps=executed_at_timestamps,
            time_period_display_texts=time_period_display_texts,
            statuses=statuses,
            data_groups=data_groups,
            results=results,
        )

        check_results_overview_data_model.additional_properties = d
        return check_results_overview_data_model

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
