from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.cloud_synchronization_folders_status_model import (
        CloudSynchronizationFoldersStatusModel,
    )
    from ..models.dqo_job_history_entry_model import DqoJobHistoryEntryModel


T = TypeVar("T", bound="DqoJobQueueInitialSnapshotModel")


@_attrs_define
class DqoJobQueueInitialSnapshotModel:
    """
    Attributes:
        jobs (Union[Unset, List['DqoJobHistoryEntryModel']]):
        folder_synchronization_status (Union[Unset, CloudSynchronizationFoldersStatusModel]):
        last_sequence_number (Union[Unset, int]):
    """

    jobs: Union[Unset, List["DqoJobHistoryEntryModel"]] = UNSET
    folder_synchronization_status: Union[
        Unset, "CloudSynchronizationFoldersStatusModel"
    ] = UNSET
    last_sequence_number: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        jobs: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.jobs, Unset):
            jobs = []
            for jobs_item_data in self.jobs:
                jobs_item = jobs_item_data.to_dict()

                jobs.append(jobs_item)

        folder_synchronization_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.folder_synchronization_status, Unset):
            folder_synchronization_status = self.folder_synchronization_status.to_dict()

        last_sequence_number = self.last_sequence_number

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if jobs is not UNSET:
            field_dict["jobs"] = jobs
        if folder_synchronization_status is not UNSET:
            field_dict["folderSynchronizationStatus"] = folder_synchronization_status
        if last_sequence_number is not UNSET:
            field_dict["lastSequenceNumber"] = last_sequence_number

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.cloud_synchronization_folders_status_model import (
            CloudSynchronizationFoldersStatusModel,
        )
        from ..models.dqo_job_history_entry_model import DqoJobHistoryEntryModel

        d = src_dict.copy()
        jobs = []
        _jobs = d.pop("jobs", UNSET)
        for jobs_item_data in _jobs or []:
            jobs_item = DqoJobHistoryEntryModel.from_dict(jobs_item_data)

            jobs.append(jobs_item)

        _folder_synchronization_status = d.pop("folderSynchronizationStatus", UNSET)
        folder_synchronization_status: Union[
            Unset, CloudSynchronizationFoldersStatusModel
        ]
        if isinstance(_folder_synchronization_status, Unset):
            folder_synchronization_status = UNSET
        else:
            folder_synchronization_status = (
                CloudSynchronizationFoldersStatusModel.from_dict(
                    _folder_synchronization_status
                )
            )

        last_sequence_number = d.pop("lastSequenceNumber", UNSET)

        dqo_job_queue_initial_snapshot_model = cls(
            jobs=jobs,
            folder_synchronization_status=folder_synchronization_status,
            last_sequence_number=last_sequence_number,
        )

        dqo_job_queue_initial_snapshot_model.additional_properties = d
        return dqo_job_queue_initial_snapshot_model

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
