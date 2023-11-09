from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.connection_spec import ConnectionSpec


T = TypeVar("T", bound="ConnectionSpecificationModel")


@_attrs_define
class ConnectionSpecificationModel:
    """Full connection model

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        connection_hash (Union[Unset, int]): Connection hash that identifies the connection using a unique hash code.
        spec (Union[Unset, ConnectionSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    connection_name: Union[Unset, str] = UNSET
    connection_hash: Union[Unset, int] = UNSET
    spec: Union[Unset, "ConnectionSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        connection_hash = self.connection_hash
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if connection_hash is not UNSET:
            field_dict["connection_hash"] = connection_hash
        if spec is not UNSET:
            field_dict["spec"] = spec
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.connection_spec import ConnectionSpec

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        connection_hash = d.pop("connection_hash", UNSET)

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, ConnectionSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = ConnectionSpec.from_dict(_spec)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        connection_specification_model = cls(
            connection_name=connection_name,
            connection_hash=connection_hash,
            spec=spec,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        connection_specification_model.additional_properties = d
        return connection_specification_model

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
