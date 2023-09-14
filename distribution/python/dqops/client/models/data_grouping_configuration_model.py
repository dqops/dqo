from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.data_grouping_configuration_spec import DataGroupingConfigurationSpec


T = TypeVar("T", bound="DataGroupingConfigurationModel")


@attr.s(auto_attribs=True)
class DataGroupingConfigurationModel:
    """Data grouping configuration model containing nested objects and the configuration of grouping dimensions.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        schema_name (Union[Unset, str]): Schema name.
        table_name (Union[Unset, str]): Table name.
        data_grouping_configuration_name (Union[Unset, str]): Data grouping configuration name.
        spec (Union[Unset, DataGroupingConfigurationSpec]):
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    data_grouping_configuration_name: Union[Unset, str] = UNSET
    spec: Union[Unset, "DataGroupingConfigurationSpec"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        data_grouping_configuration_name = self.data_grouping_configuration_name
        spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.spec, Unset):
            spec = self.spec.to_dict()

        can_edit = self.can_edit

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if data_grouping_configuration_name is not UNSET:
            field_dict[
                "data_grouping_configuration_name"
            ] = data_grouping_configuration_name
        if spec is not UNSET:
            field_dict["spec"] = spec
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.data_grouping_configuration_spec import (
            DataGroupingConfigurationSpec,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        data_grouping_configuration_name = d.pop(
            "data_grouping_configuration_name", UNSET
        )

        _spec = d.pop("spec", UNSET)
        spec: Union[Unset, DataGroupingConfigurationSpec]
        if isinstance(_spec, Unset):
            spec = UNSET
        else:
            spec = DataGroupingConfigurationSpec.from_dict(_spec)

        can_edit = d.pop("can_edit", UNSET)

        data_grouping_configuration_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            data_grouping_configuration_name=data_grouping_configuration_name,
            spec=spec,
            can_edit=can_edit,
        )

        data_grouping_configuration_model.additional_properties = d
        return data_grouping_configuration_model

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
