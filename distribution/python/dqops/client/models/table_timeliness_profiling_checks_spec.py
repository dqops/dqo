from typing import Any, Dict, Type, TypeVar, Tuple, Optional, BinaryIO, TextIO, TYPE_CHECKING

from typing import List


import attr

from ..types import UNSET, Unset

from ..types import UNSET, Unset
from typing import Union
from typing import cast
from typing import Dict

if TYPE_CHECKING:
  from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
  from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
  from ..models.table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec





T = TypeVar("T", bound="TableTimelinessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessProfilingChecksSpec:
    """ 
        Attributes:
            data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
            data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
            data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
     """

    data_freshness: Union[Unset, 'TableDataFreshnessCheckSpec'] = UNSET
    data_staleness: Union[Unset, 'TableDataStalenessCheckSpec'] = UNSET
    data_ingestion_delay: Union[Unset, 'TableDataIngestionDelayCheckSpec'] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)


    def to_dict(self) -> Dict[str, Any]:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec
        data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_freshness, Unset):
            data_freshness = self.data_freshness.to_dict()

        data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_staleness, Unset):
            data_staleness = self.data_staleness.to_dict()

        data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_ingestion_delay, Unset):
            data_ingestion_delay = self.data_ingestion_delay.to_dict()


        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({
        })
        if data_freshness is not UNSET:
            field_dict["data_freshness"] = data_freshness
        if data_staleness is not UNSET:
            field_dict["data_staleness"] = data_staleness
        if data_ingestion_delay is not UNSET:
            field_dict["data_ingestion_delay"] = data_ingestion_delay

        return field_dict



    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import TableDataIngestionDelayCheckSpec
        d = src_dict.copy()
        _data_freshness = d.pop("data_freshness", UNSET)
        data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_data_freshness,  Unset):
            data_freshness = UNSET
        else:
            data_freshness = TableDataFreshnessCheckSpec.from_dict(_data_freshness)




        _data_staleness = d.pop("data_staleness", UNSET)
        data_staleness: Union[Unset, TableDataStalenessCheckSpec]
        if isinstance(_data_staleness,  Unset):
            data_staleness = UNSET
        else:
            data_staleness = TableDataStalenessCheckSpec.from_dict(_data_staleness)




        _data_ingestion_delay = d.pop("data_ingestion_delay", UNSET)
        data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_data_ingestion_delay,  Unset):
            data_ingestion_delay = UNSET
        else:
            data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(_data_ingestion_delay)




        table_timeliness_profiling_checks_spec = cls(
            data_freshness=data_freshness,
            data_staleness=data_staleness,
            data_ingestion_delay=data_ingestion_delay,
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
