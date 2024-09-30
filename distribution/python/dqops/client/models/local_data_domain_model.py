from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="LocalDataDomainModel")


@_attrs_define
class LocalDataDomainModel:
    """Model that describes a local data domain. It is also used to create new data domains.

    Attributes:
        domain_name (Union[Unset, str]): Data domain name.
        display_name (Union[Unset, str]): Data domain display name (user-friendly name).
        created_at (Union[Unset, int]): Data domain creation time.
        enable_scheduler (Union[Unset, bool]): Enables the job scheduler for this domain.
    """

    domain_name: Union[Unset, str] = UNSET
    display_name: Union[Unset, str] = UNSET
    created_at: Union[Unset, int] = UNSET
    enable_scheduler: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        domain_name = self.domain_name
        display_name = self.display_name
        created_at = self.created_at
        enable_scheduler = self.enable_scheduler

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if domain_name is not UNSET:
            field_dict["domain_name"] = domain_name
        if display_name is not UNSET:
            field_dict["display_name"] = display_name
        if created_at is not UNSET:
            field_dict["created_at"] = created_at
        if enable_scheduler is not UNSET:
            field_dict["enable_scheduler"] = enable_scheduler

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        domain_name = d.pop("domain_name", UNSET)

        display_name = d.pop("display_name", UNSET)

        created_at = d.pop("created_at", UNSET)

        enable_scheduler = d.pop("enable_scheduler", UNSET)

        local_data_domain_model = cls(
            domain_name=domain_name,
            display_name=display_name,
            created_at=created_at,
            enable_scheduler=enable_scheduler,
        )

        local_data_domain_model.additional_properties = d
        return local_data_domain_model

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
