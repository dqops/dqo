from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="LabelModel")


@_attrs_define
class LabelModel:
    """Label model that is returned by the REST API. A label is a tag that was assigned to a data source, table, column or
    a single check. Labels play the role of a business glossary.

        Attributes:
            label (Union[Unset, str]): Label text.
            labels_count (Union[Unset, int]): The number of data assets tagged with this label.
            nested_labels_count (Union[Unset, int]): The number of data assets tagged with nested labels below this prefix
                node. For example, if the current label is "address", and there are nested labels "address/city" and
                "address/zipcode", this value returns the count of data assets tagged with these nested tags.
    """

    label: Union[Unset, str] = UNSET
    labels_count: Union[Unset, int] = UNSET
    nested_labels_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        label = self.label
        labels_count = self.labels_count
        nested_labels_count = self.nested_labels_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if label is not UNSET:
            field_dict["label"] = label
        if labels_count is not UNSET:
            field_dict["labels_count"] = labels_count
        if nested_labels_count is not UNSET:
            field_dict["nested_labels_count"] = nested_labels_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        label = d.pop("label", UNSET)

        labels_count = d.pop("labels_count", UNSET)

        nested_labels_count = d.pop("nested_labels_count", UNSET)

        label_model = cls(
            label=label,
            labels_count=labels_count,
            nested_labels_count=nested_labels_count,
        )

        label_model.additional_properties = d
        return label_model

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
