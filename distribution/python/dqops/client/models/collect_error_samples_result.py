from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_job_status import DqoJobStatus
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dqo_queue_job_id import DqoQueueJobId
    from ..models.error_sampler_result import ErrorSamplerResult


T = TypeVar("T", bound="CollectErrorSamplesResult")


@_attrs_define
class CollectErrorSamplesResult:
    """Object returned from the operation that queues a "collect error samples" job. The result contains the job id that
    was started and optionally can also contain the result of collecting the error samples  if the operation was started
    with wait=true parameter to wait for the "collect error samples" job to finish.

        Attributes:
            job_id (Union[Unset, DqoQueueJobId]): Identifies a single job that was pushed to the job queue.
            result (Union[Unset, ErrorSamplerResult]): Returns the result with the summary of the error samples collected.
            status (Union[Unset, DqoJobStatus]):
    """

    job_id: Union[Unset, "DqoQueueJobId"] = UNSET
    result: Union[Unset, "ErrorSamplerResult"] = UNSET
    status: Union[Unset, DqoJobStatus] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        job_id: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.job_id, Unset):
            job_id = self.job_id.to_dict()

        result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.result, Unset):
            result = self.result.to_dict()

        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if job_id is not UNSET:
            field_dict["jobId"] = job_id
        if result is not UNSET:
            field_dict["result"] = result
        if status is not UNSET:
            field_dict["status"] = status

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_queue_job_id import DqoQueueJobId
        from ..models.error_sampler_result import ErrorSamplerResult

        d = src_dict.copy()
        _job_id = d.pop("jobId", UNSET)
        job_id: Union[Unset, DqoQueueJobId]
        if isinstance(_job_id, Unset):
            job_id = UNSET
        else:
            job_id = DqoQueueJobId.from_dict(_job_id)

        _result = d.pop("result", UNSET)
        result: Union[Unset, ErrorSamplerResult]
        if isinstance(_result, Unset):
            result = UNSET
        else:
            result = ErrorSamplerResult.from_dict(_result)

        _status = d.pop("status", UNSET)
        status: Union[Unset, DqoJobStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = DqoJobStatus(_status)

        collect_error_samples_result = cls(
            job_id=job_id,
            result=result,
            status=status,
        )

        collect_error_samples_result.additional_properties = d
        return collect_error_samples_result

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
