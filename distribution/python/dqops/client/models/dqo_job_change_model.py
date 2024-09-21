from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_job_status import DqoJobStatus
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dqo_job_history_entry_model import DqoJobHistoryEntryModel
    from ..models.dqo_queue_job_id import DqoQueueJobId


T = TypeVar("T", bound="DqoJobChangeModel")


@_attrs_define
class DqoJobChangeModel:
    """
    Attributes:
        status (Union[Unset, DqoJobStatus]):
        job_id (Union[Unset, DqoQueueJobId]): Identifies a single job that was pushed to the job queue.
        change_sequence (Union[Unset, int]):
        updated_model (Union[Unset, DqoJobHistoryEntryModel]):
        status_changed_at (Union[Unset, int]):
        domain_name (Union[Unset, str]):
    """

    status: Union[Unset, DqoJobStatus] = UNSET
    job_id: Union[Unset, "DqoQueueJobId"] = UNSET
    change_sequence: Union[Unset, int] = UNSET
    updated_model: Union[Unset, "DqoJobHistoryEntryModel"] = UNSET
    status_changed_at: Union[Unset, int] = UNSET
    domain_name: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        job_id: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.job_id, Unset):
            job_id = self.job_id.to_dict()

        change_sequence = self.change_sequence
        updated_model: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.updated_model, Unset):
            updated_model = self.updated_model.to_dict()

        status_changed_at = self.status_changed_at
        domain_name = self.domain_name

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if status is not UNSET:
            field_dict["status"] = status
        if job_id is not UNSET:
            field_dict["jobId"] = job_id
        if change_sequence is not UNSET:
            field_dict["changeSequence"] = change_sequence
        if updated_model is not UNSET:
            field_dict["updatedModel"] = updated_model
        if status_changed_at is not UNSET:
            field_dict["statusChangedAt"] = status_changed_at
        if domain_name is not UNSET:
            field_dict["domainName"] = domain_name

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_job_history_entry_model import DqoJobHistoryEntryModel
        from ..models.dqo_queue_job_id import DqoQueueJobId

        d = src_dict.copy()
        _status = d.pop("status", UNSET)
        status: Union[Unset, DqoJobStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = DqoJobStatus(_status)

        _job_id = d.pop("jobId", UNSET)
        job_id: Union[Unset, DqoQueueJobId]
        if isinstance(_job_id, Unset):
            job_id = UNSET
        else:
            job_id = DqoQueueJobId.from_dict(_job_id)

        change_sequence = d.pop("changeSequence", UNSET)

        _updated_model = d.pop("updatedModel", UNSET)
        updated_model: Union[Unset, DqoJobHistoryEntryModel]
        if isinstance(_updated_model, Unset):
            updated_model = UNSET
        else:
            updated_model = DqoJobHistoryEntryModel.from_dict(_updated_model)

        status_changed_at = d.pop("statusChangedAt", UNSET)

        domain_name = d.pop("domainName", UNSET)

        dqo_job_change_model = cls(
            status=status,
            job_id=job_id,
            change_sequence=change_sequence,
            updated_model=updated_model,
            status_changed_at=status_changed_at,
            domain_name=domain_name,
        )

        dqo_job_change_model.additional_properties = d
        return dqo_job_change_model

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
