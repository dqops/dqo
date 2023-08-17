from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec


T = TypeVar("T", bound="TableTimelinessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessProfilingChecksSpec:
    """
    Attributes:
        profile_data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
        profile_data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
        profile_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
    """

    profile_data_freshness: Union[Unset, "TableDataFreshnessCheckSpec"] = UNSET
    profile_data_staleness: Union[Unset, "TableDataStalenessCheckSpec"] = UNSET
    profile_data_ingestion_delay: Union[
        Unset, "TableDataIngestionDelayCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profile_data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_data_freshness, Unset):
            profile_data_freshness = self.profile_data_freshness.to_dict()

        profile_data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_data_staleness, Unset):
            profile_data_staleness = self.profile_data_staleness.to_dict()

        profile_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profile_data_ingestion_delay, Unset):
            profile_data_ingestion_delay = self.profile_data_ingestion_delay.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profile_data_freshness is not UNSET:
            field_dict["profile_data_freshness"] = profile_data_freshness
        if profile_data_staleness is not UNSET:
            field_dict["profile_data_staleness"] = profile_data_staleness
        if profile_data_ingestion_delay is not UNSET:
            field_dict["profile_data_ingestion_delay"] = profile_data_ingestion_delay

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec

        d = src_dict.copy()
        _profile_data_freshness = d.pop("profile_data_freshness", UNSET)
        profile_data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_profile_data_freshness, Unset):
            profile_data_freshness = UNSET
        else:
            profile_data_freshness = TableDataFreshnessCheckSpec.from_dict(
                _profile_data_freshness
            )

        _profile_data_staleness = d.pop("profile_data_staleness", UNSET)
        profile_data_staleness: Union[Unset, TableDataStalenessCheckSpec]
        if isinstance(_profile_data_staleness, Unset):
            profile_data_staleness = UNSET
        else:
            profile_data_staleness = TableDataStalenessCheckSpec.from_dict(
                _profile_data_staleness
            )

        _profile_data_ingestion_delay = d.pop("profile_data_ingestion_delay", UNSET)
        profile_data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_profile_data_ingestion_delay, Unset):
            profile_data_ingestion_delay = UNSET
        else:
            profile_data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _profile_data_ingestion_delay
            )

        table_timeliness_profiling_checks_spec = cls(
            profile_data_freshness=profile_data_freshness,
            profile_data_staleness=profile_data_staleness,
            profile_data_ingestion_delay=profile_data_ingestion_delay,
        )

        table_timeliness_profiling_checks_spec.additional_properties = d
        return table_timeliness_profiling_checks_spec

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
