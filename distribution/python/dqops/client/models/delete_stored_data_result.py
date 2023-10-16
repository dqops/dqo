from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.delete_stored_data_result_partition_results import (
        DeleteStoredDataResultPartitionResults,
    )


T = TypeVar("T", bound="DeleteStoredDataResult")


@_attrs_define
class DeleteStoredDataResult:
    """
    Attributes:
        partition_results (Union[Unset, DeleteStoredDataResultPartitionResults]): Dictionary of partitions that where
            deleted or updated when the rows were deleted.
    """

    partition_results: Union[Unset, "DeleteStoredDataResultPartitionResults"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        partition_results: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.partition_results, Unset):
            partition_results = self.partition_results.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if partition_results is not UNSET:
            field_dict["partitionResults"] = partition_results

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.delete_stored_data_result_partition_results import (
            DeleteStoredDataResultPartitionResults,
        )

        d = src_dict.copy()
        _partition_results = d.pop("partitionResults", UNSET)
        partition_results: Union[Unset, DeleteStoredDataResultPartitionResults]
        if isinstance(_partition_results, Unset):
            partition_results = UNSET
        else:
            partition_results = DeleteStoredDataResultPartitionResults.from_dict(
                _partition_results
            )

        delete_stored_data_result = cls(
            partition_results=partition_results,
        )

        delete_stored_data_result.additional_properties = d
        return delete_stored_data_result

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
