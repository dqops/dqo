from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

import attr

if TYPE_CHECKING:
    from ..models.check_container_model import CheckContainerModel


T = TypeVar("T", bound="ColumnChecksModelCheckContainers")


@attr.s(auto_attribs=True)
class ColumnChecksModelCheckContainers:
    """Mapping of check type and timescale to check container on this column."""

    additional_properties: Dict[str, "CheckContainerModel"] = attr.ib(
        init=False, factory=dict
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_container_model import CheckContainerModel

        d = src_dict.copy()
        column_checks_model_check_containers = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = CheckContainerModel.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        column_checks_model_check_containers.additional_properties = (
            additional_properties
        )
        return column_checks_model_check_containers

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "CheckContainerModel":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "CheckContainerModel") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
