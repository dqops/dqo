from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

import attr

if TYPE_CHECKING:
    from ..models.ui_check_container_model import UICheckContainerModel


T = TypeVar("T", bound="UITableChecksModelCheckContainers")


@attr.s(auto_attribs=True)
class UITableChecksModelCheckContainers:
    """Mapping of check type and timescale to check container on this table."""

    additional_properties: Dict[str, "UICheckContainerModel"] = attr.ib(
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
        from ..models.ui_check_container_model import UICheckContainerModel

        d = src_dict.copy()
        ui_table_checks_model_check_containers = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = UICheckContainerModel.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        ui_table_checks_model_check_containers.additional_properties = (
            additional_properties
        )
        return ui_table_checks_model_check_containers

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "UICheckContainerModel":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "UICheckContainerModel") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
