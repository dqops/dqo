from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_basic_model import CheckBasicModel


T = TypeVar("T", bound="CheckContainerBasicModel")


@attr.s(auto_attribs=True)
class CheckContainerBasicModel:
    """Simplistic model that returns the list of data quality checks, their names, categories and "configured" flag.

    Attributes:
        checks (Union[Unset, List['CheckBasicModel']]): Simplistic list of all data quality checks.
    """

    checks: Union[Unset, List["CheckBasicModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        checks: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = []
            for checks_item_data in self.checks:
                checks_item = checks_item_data.to_dict()

                checks.append(checks_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if checks is not UNSET:
            field_dict["checks"] = checks

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_basic_model import CheckBasicModel

        d = src_dict.copy()
        checks = []
        _checks = d.pop("checks", UNSET)
        for checks_item_data in _checks or []:
            checks_item = CheckBasicModel.from_dict(checks_item_data)

            checks.append(checks_item)

        check_container_basic_model = cls(
            checks=checks,
        )

        check_container_basic_model.additional_properties = d
        return check_container_basic_model

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
