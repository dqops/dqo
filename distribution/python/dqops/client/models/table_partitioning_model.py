from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.partition_incremental_time_window_spec import (
        PartitionIncrementalTimeWindowSpec,
    )
    from ..models.physical_table_name import PhysicalTableName
    from ..models.timestamp_columns_spec import TimestampColumnsSpec


T = TypeVar("T", bound="TablePartitioningModel")


@_attrs_define
class TablePartitioningModel:
    """Table model with objects that describe the table partitioning.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        target (Union[Unset, PhysicalTableName]):
        timestamp_columns (Union[Unset, TimestampColumnsSpec]):
        incremental_time_window (Union[Unset, PartitionIncrementalTimeWindowSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    connection_name: Union[Unset, str] = UNSET
    target: Union[Unset, "PhysicalTableName"] = UNSET
    timestamp_columns: Union[Unset, "TimestampColumnsSpec"] = UNSET
    incremental_time_window: Union[Unset, "PartitionIncrementalTimeWindowSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        target: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.to_dict()

        timestamp_columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.timestamp_columns, Unset):
            timestamp_columns = self.timestamp_columns.to_dict()

        incremental_time_window: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.incremental_time_window, Unset):
            incremental_time_window = self.incremental_time_window.to_dict()

        can_edit = self.can_edit

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if target is not UNSET:
            field_dict["target"] = target
        if timestamp_columns is not UNSET:
            field_dict["timestamp_columns"] = timestamp_columns
        if incremental_time_window is not UNSET:
            field_dict["incremental_time_window"] = incremental_time_window
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.partition_incremental_time_window_spec import (
            PartitionIncrementalTimeWindowSpec,
        )
        from ..models.physical_table_name import PhysicalTableName
        from ..models.timestamp_columns_spec import TimestampColumnsSpec

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, PhysicalTableName]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = PhysicalTableName.from_dict(_target)

        _timestamp_columns = d.pop("timestamp_columns", UNSET)
        timestamp_columns: Union[Unset, TimestampColumnsSpec]
        if isinstance(_timestamp_columns, Unset):
            timestamp_columns = UNSET
        else:
            timestamp_columns = TimestampColumnsSpec.from_dict(_timestamp_columns)

        _incremental_time_window = d.pop("incremental_time_window", UNSET)
        incremental_time_window: Union[Unset, PartitionIncrementalTimeWindowSpec]
        if isinstance(_incremental_time_window, Unset):
            incremental_time_window = UNSET
        else:
            incremental_time_window = PartitionIncrementalTimeWindowSpec.from_dict(
                _incremental_time_window
            )

        can_edit = d.pop("can_edit", UNSET)

        table_partitioning_model = cls(
            connection_name=connection_name,
            target=target,
            timestamp_columns=timestamp_columns,
            incremental_time_window=incremental_time_window,
            can_edit=can_edit,
        )

        table_partitioning_model.additional_properties = d
        return table_partitioning_model

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
