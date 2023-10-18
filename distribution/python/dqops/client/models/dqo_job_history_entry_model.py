from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_job_status import DqoJobStatus
from ..models.dqo_job_type import DqoJobType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dqo_job_entry_parameters_model import DqoJobEntryParametersModel
    from ..models.dqo_queue_job_id import DqoQueueJobId


T = TypeVar("T", bound="DqoJobHistoryEntryModel")


@_attrs_define
class DqoJobHistoryEntryModel:
    """
    Attributes:
        job_id (Union[Unset, DqoQueueJobId]): Identifies a single job that was pushed to the job queue.
        job_type (Union[Unset, DqoJobType]):
        parameters (Union[Unset, DqoJobEntryParametersModel]):
        status (Union[Unset, DqoJobStatus]):
        error_message (Union[Unset, str]):
        status_changed_at (Union[Unset, int]):
    """

    job_id: Union[Unset, "DqoQueueJobId"] = UNSET
    job_type: Union[Unset, DqoJobType] = UNSET
    parameters: Union[Unset, "DqoJobEntryParametersModel"] = UNSET
    status: Union[Unset, DqoJobStatus] = UNSET
    error_message: Union[Unset, str] = UNSET
    status_changed_at: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        job_id: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.job_id, Unset):
            job_id = self.job_id.to_dict()

        job_type: Union[Unset, str] = UNSET
        if not isinstance(self.job_type, Unset):
            job_type = self.job_type.value

        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        error_message = self.error_message
        status_changed_at = self.status_changed_at

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if job_id is not UNSET:
            field_dict["jobId"] = job_id
        if job_type is not UNSET:
            field_dict["jobType"] = job_type
        if parameters is not UNSET:
            field_dict["parameters"] = parameters
        if status is not UNSET:
            field_dict["status"] = status
        if error_message is not UNSET:
            field_dict["errorMessage"] = error_message
        if status_changed_at is not UNSET:
            field_dict["statusChangedAt"] = status_changed_at

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_job_entry_parameters_model import DqoJobEntryParametersModel
        from ..models.dqo_queue_job_id import DqoQueueJobId

        d = src_dict.copy()
        _job_id = d.pop("jobId", UNSET)
        job_id: Union[Unset, DqoQueueJobId]
        if isinstance(_job_id, Unset):
            job_id = UNSET
        else:
            job_id = DqoQueueJobId.from_dict(_job_id)

        _job_type = d.pop("jobType", UNSET)
        job_type: Union[Unset, DqoJobType]
        if isinstance(_job_type, Unset):
            job_type = UNSET
        else:
            job_type = DqoJobType(_job_type)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, DqoJobEntryParametersModel]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = DqoJobEntryParametersModel.from_dict(_parameters)

        _status = d.pop("status", UNSET)
        status: Union[Unset, DqoJobStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = DqoJobStatus(_status)

        error_message = d.pop("errorMessage", UNSET)

        status_changed_at = d.pop("statusChangedAt", UNSET)

        dqo_job_history_entry_model = cls(
            job_id=job_id,
            job_type=job_type,
            parameters=parameters,
            status=status,
            error_message=error_message,
            status_changed_at=status_changed_at,
        )

        dqo_job_history_entry_model.additional_properties = d
        return dqo_job_history_entry_model

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
