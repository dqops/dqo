from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="DqoQueueJobId")


@_attrs_define
class DqoQueueJobId:
    """Identifies a single job that was pushed to the job queue.

    Attributes:
        job_id (Union[Unset, int]): Job id.
        job_business_key (Union[Unset, str]): Optional job business key that was assigned to the job. A business key is
            an alternative user assigned unique job identifier used to find the status of a job finding it by the business
            key.
        parent_job_id (Union[Unset, DqoQueueJobId]): Identifies a single job that was pushed to the job queue.
        created_at (Union[Unset, int]): The timestamp when the job was created.
    """

    job_id: Union[Unset, int] = UNSET
    job_business_key: Union[Unset, str] = UNSET
    parent_job_id: Union[Unset, "DqoQueueJobId"] = UNSET
    created_at: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        job_id = self.job_id
        job_business_key = self.job_business_key
        parent_job_id: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parent_job_id, Unset):
            parent_job_id = self.parent_job_id.to_dict()

        created_at = self.created_at

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if job_id is not UNSET:
            field_dict["jobId"] = job_id
        if job_business_key is not UNSET:
            field_dict["jobBusinessKey"] = job_business_key
        if parent_job_id is not UNSET:
            field_dict["parentJobId"] = parent_job_id
        if created_at is not UNSET:
            field_dict["createdAt"] = created_at

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        job_id = d.pop("jobId", UNSET)

        job_business_key = d.pop("jobBusinessKey", UNSET)

        _parent_job_id = d.pop("parentJobId", UNSET)
        parent_job_id: Union[Unset, DqoQueueJobId]
        if isinstance(_parent_job_id, Unset):
            parent_job_id = UNSET
        else:
            parent_job_id = DqoQueueJobId.from_dict(_parent_job_id)

        created_at = d.pop("createdAt", UNSET)

        dqo_queue_job_id = cls(
            job_id=job_id,
            job_business_key=job_business_key,
            parent_job_id=parent_job_id,
            created_at=created_at,
        )

        dqo_queue_job_id.additional_properties = d
        return dqo_queue_job_id

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
