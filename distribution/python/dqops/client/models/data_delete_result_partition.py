from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="DataDeleteResultPartition")


@_attrs_define
class DataDeleteResultPartition:
    """
    Attributes:
        rows_affected_count (Union[Unset, int]): The number of rows that were deleted from the partition.
        partition_deleted (Union[Unset, bool]): True if a whole partition (a parquet file) was deleted instead of
            removing only selected rows.
    """

    rows_affected_count: Union[Unset, int] = UNSET
    partition_deleted: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        rows_affected_count = self.rows_affected_count
        partition_deleted = self.partition_deleted

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if rows_affected_count is not UNSET:
            field_dict["rowsAffectedCount"] = rows_affected_count
        if partition_deleted is not UNSET:
            field_dict["partitionDeleted"] = partition_deleted

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        rows_affected_count = d.pop("rowsAffectedCount", UNSET)

        partition_deleted = d.pop("partitionDeleted", UNSET)

        data_delete_result_partition = cls(
            rows_affected_count=rows_affected_count,
            partition_deleted=partition_deleted,
        )

        data_delete_result_partition.additional_properties = d
        return data_delete_result_partition

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
