from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TimestampColumnsSpec")


@_attrs_define
class TimestampColumnsSpec:
    """
    Attributes:
        event_timestamp_column (Union[Unset, str]): Column name that identifies an event timestamp (date/time), such as
            a transaction timestamp, impression timestamp, event timestamp.
        ingestion_timestamp_column (Union[Unset, str]): Column name that contains the timestamp (or date/time) when the
            row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL
            process at the time of the data loading.
        partition_by_column (Union[Unset, str]): Column name that contains the date, datetime or timestamp column for
            date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this
            column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a
            DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).
    """

    event_timestamp_column: Union[Unset, str] = UNSET
    ingestion_timestamp_column: Union[Unset, str] = UNSET
    partition_by_column: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        event_timestamp_column = self.event_timestamp_column
        ingestion_timestamp_column = self.ingestion_timestamp_column
        partition_by_column = self.partition_by_column

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if event_timestamp_column is not UNSET:
            field_dict["event_timestamp_column"] = event_timestamp_column
        if ingestion_timestamp_column is not UNSET:
            field_dict["ingestion_timestamp_column"] = ingestion_timestamp_column
        if partition_by_column is not UNSET:
            field_dict["partition_by_column"] = partition_by_column

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        event_timestamp_column = d.pop("event_timestamp_column", UNSET)

        ingestion_timestamp_column = d.pop("ingestion_timestamp_column", UNSET)

        partition_by_column = d.pop("partition_by_column", UNSET)

        timestamp_columns_spec = cls(
            event_timestamp_column=event_timestamp_column,
            ingestion_timestamp_column=ingestion_timestamp_column,
            partition_by_column=partition_by_column,
        )

        timestamp_columns_spec.additional_properties = d
        return timestamp_columns_spec

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
