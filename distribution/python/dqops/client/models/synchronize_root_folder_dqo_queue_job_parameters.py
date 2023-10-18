from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.synchronize_root_folder_parameters import (
        SynchronizeRootFolderParameters,
    )


T = TypeVar("T", bound="SynchronizeRootFolderDqoQueueJobParameters")


@_attrs_define
class SynchronizeRootFolderDqoQueueJobParameters:
    """
    Attributes:
        synchronization_parameter (Union[Unset, SynchronizeRootFolderParameters]):
    """

    synchronization_parameter: Union[Unset, "SynchronizeRootFolderParameters"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        synchronization_parameter: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.synchronization_parameter, Unset):
            synchronization_parameter = self.synchronization_parameter.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if synchronization_parameter is not UNSET:
            field_dict["synchronizationParameter"] = synchronization_parameter

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.synchronize_root_folder_parameters import (
            SynchronizeRootFolderParameters,
        )

        d = src_dict.copy()
        _synchronization_parameter = d.pop("synchronizationParameter", UNSET)
        synchronization_parameter: Union[Unset, SynchronizeRootFolderParameters]
        if isinstance(_synchronization_parameter, Unset):
            synchronization_parameter = UNSET
        else:
            synchronization_parameter = SynchronizeRootFolderParameters.from_dict(
                _synchronization_parameter
            )

        synchronize_root_folder_dqo_queue_job_parameters = cls(
            synchronization_parameter=synchronization_parameter,
        )

        synchronize_root_folder_dqo_queue_job_parameters.additional_properties = d
        return synchronize_root_folder_dqo_queue_job_parameters

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
